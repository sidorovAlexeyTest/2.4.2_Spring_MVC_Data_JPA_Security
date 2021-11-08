package springMVCHibernate.config.service;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import springMVCHibernate.config.TestPersistenceConfig;
import springMVCHibernate.model.User;
import springMVCHibernate.repository.UserRepository;
import springMVCHibernate.service.user.UserService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Date;
import java.util.List;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestPersistenceConfig.class)
@WebAppConfiguration
@Transactional
public class UserServiceTest {

    private static User user1, user2;

    @Autowired
    private EntityManagerFactory emf;
    protected EntityManager em;

    @Autowired
    private UserService userServiceImpl;
    @Autowired
    private UserRepository userRepository;

    @BeforeClass
    public static void setUsers() {
        user1 = new User("Ivan", "Ivanov", new Date(1990, 5, 9), "password");
        user2 = new User("Petr", "Petrov", new Date(1991, 4, 10), "password");
    }

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }

    @After
    public void clearAfterTest() {
        userRepository.deleteAll();
    }

    @Test
    public void testSaveUser() throws Exception {
        userServiceImpl.addByUser(
                user1.getUsername(),
                user1.getSurname(),
                user1.getBirthdate(),
                user1.getPassword()
        );
        User actualUser = userServiceImpl.readAll().get(0);
        Assert.assertEquals(user1, actualUser);
    }

    @Test
    public void testDeleteUser() {
        userServiceImpl.addByUser(
                user1.getUsername(),
                user1.getSurname(),
                user1.getBirthdate(),
                user1.getPassword()
        );
        userServiceImpl.addByUser(
                user2.getUsername(),
                user2.getSurname(),
                user2.getBirthdate(),
                user2.getPassword()
        );
        long id1 = userServiceImpl.readAll().get(0).getId();
        long id2 = userServiceImpl.readAll().get(1).getId();
        userServiceImpl.delete(id1);
        userServiceImpl.delete(id2);
        List<User> list = userServiceImpl.readAll();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void updateUser(){
        userServiceImpl.addByUser(
                user1.getUsername(),
                user1.getSurname(),
                user1.getBirthdate(),
                user1.getPassword()
        );
        long id1 = userServiceImpl.readAll().get(0).getId();
        userServiceImpl.update(
                id1,
                user2.getUsername(),
                user2.getSurname(),
                user2.getBirthdate(),
                user2.getPassword(),
                null
        );
        Assert.assertEquals(user2, userServiceImpl.read(id1));
    }
}
