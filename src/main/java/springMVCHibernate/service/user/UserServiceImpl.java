package springMVCHibernate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springMVCHibernate.model.Role;
import springMVCHibernate.repository.UserRepository;
import springMVCHibernate.model.User;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addByAdmin(String name, String surname, Date birthdate, String password, Set<Role> roles) {
        return userRepository.saveAndFlush(new User(name, surname, birthdate, password, roles));
    }

    @Override
    public User addByUser(String name, String surname, Date birthdate, String password) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("USER"));
        return userRepository.saveAndFlush(new User(0L, name, surname, birthdate, password, roles));
    }

    @Override
    public User read(long id) {
        return userRepository.getById(id);
    }

    @Override
    public User update(long id, String name, String surname, Date birthdate, String password, Set<Role> roles) {
        System.out.println(id);
        return userRepository.saveAndFlush(new User(id, name, surname, birthdate, password, roles));
    }

    @Override
    public void delete(long id) {
        User user = new User();
        user.setId(id);
        userRepository.delete(user);
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }
}
