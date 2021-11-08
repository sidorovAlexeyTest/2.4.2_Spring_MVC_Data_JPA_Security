package springMVCHibernate.service.user;

import springMVCHibernate.model.Role;
import springMVCHibernate.model.User;
import java.sql.Date;
import java.util.List;
import java.util.Set;


public interface UserService {
    User addByAdmin(String name, String surname, Date birthdate, String password, Set<Role> roles);
    User addByUser(String name, String surname, Date birthdate, String password);
    User read(long id);
    User update(long id, String name, String surname, Date birthdate, String password, Set<Role> roles);
    void delete(long id);
    User findUserByName(String name);
    List<User> readAll();
}
