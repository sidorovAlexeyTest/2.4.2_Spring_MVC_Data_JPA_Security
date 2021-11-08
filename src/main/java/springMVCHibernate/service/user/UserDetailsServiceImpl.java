package springMVCHibernate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springMVCHibernate.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userServiceImpl;

    @Autowired
    public UserDetailsServiceImpl(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceImpl.findUserByName(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User name %s not found", username));
        }
        return user;
    }
}
