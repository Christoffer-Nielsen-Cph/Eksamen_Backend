package datafacades;

import entities.Role;
import entities.User;
import errorhandling.API_Exception;
import errorhandling.NotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserFacadeTest {
    private static EntityManagerFactory emf;
    private static UserFacade facade;

    User u1, u2;

    public UserFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Role userRole = new Role("user");
        u1 = new User();
        u2 = new User();
        u1.setUserName("Oscar");
        u1.setUserPass("test");
        u1.addRole(userRole);
        u1.setUserEmail("Oscar@gmail.com");
        u1.setUserPhone(20309040);
        u1.setUserBillingPrHour(300);
        u2.setUserName("Mark");
        u2.setUserPass("test");
        u2.addRole(userRole);
        u2.setUserEmail("Mark@gmail.com");
        u2.setUserPhone(30490591);
        u2.setUserBillingPrHour(250);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.persist(userRole);
            em.persist(u1);
            em.persist(u2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void createUserTest() throws API_Exception {
        User user = new User("Chris", "Chris@gmail.com","PW",40809132,300);
        facade.createUser(user);
        assertNotNull(user.getUserName());
        int actualSize = facade.getAllUsers().size();
        assertEquals(3, actualSize);
    }

    @Test
    void createNoDuplicateUsers() {
        User user = new User("Oscar", "oscar@gmail.com", "PW",10329104,280);
        assertThrows(API_Exception.class, () -> facade.createUser(user));
    }

    @Test
    void findUserByUsername() throws API_Exception {
        User user = facade.getUserByUserName(u1.getUserName());
        assertEquals(u1, user);
    }

    @Test
    void findAllUsers() throws API_Exception {
        List<User> actual = facade.getAllUsers();
        int expected = 2;
        assertEquals(expected, actual.size());
    }

    @Test
    void deleteUser() throws API_Exception, NotFoundException {
        facade.deleteUser("Oscar");
        int actualSize = facade.getAllUsers().size();
        assertEquals(1, actualSize);
    }

    @Test
    void CantFindUserToDelete() {
        assertThrows(API_Exception.class, () -> facade.deleteUser("HEJSA"));
    }
}
