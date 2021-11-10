package springMVCHibernate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springMVCHibernate.model.Role;
import springMVCHibernate.model.User;
import springMVCHibernate.service.role.RoleService;
import springMVCHibernate.service.user.UserService;
import springMVCHibernate.service.user.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class PostConstructImpl {

    private UserService userServiceImpl;
    private RoleService roleServiceImpl;

    @Autowired
    public PostConstructImpl(UserService userServiceImpl, RoleService roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @PostConstruct
    @Transactional
    public void addUsersAndRoles(){
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleServiceImpl.addRole(roleAdmin);
        roleServiceImpl.addRole(roleUser);

        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        userServiceImpl.addByAdmin("Pavel", "Sorosov", new Date(1998, 5, 15), "password", roles);
        roles.add(roleAdmin);
        userServiceImpl.addByAdmin("Alex", "Crud", new Date(1988, 2, 3), "password", roles);
        }
}
