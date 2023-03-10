package businessfacades;

import dtos.UserDTO;
import entities.Role;
import entities.User;
import errorhandling.API_Exception;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserDTOFacadeTest {

    private static EntityManagerFactory emf;
    private static UserDTOFacade facade;

    UserDTO udto1, udto2;

    public UserDTOFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserDTOFacade.getInstance(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Role userRole = new Role("user");
        User u1 = new User();
        User u2 = new User();
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
            udto1 = new UserDTO(u1);
            udto2 = new UserDTO(u2);
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void createUserDTOTest() throws API_Exception {
        UserDTO userDTO = new UserDTO(new User("Chris","Chris@gmail.com","PW",20403193,300));
        facade.createUser(userDTO);
        assertNotNull(userDTO.getUserName());
        int actualSize = facade.getAllUsers().size();
        assertEquals(3, actualSize);
    }

    @Test
    void createNoDuplicateUserDTOs() throws API_Exception {
        UserDTO userDTO = new UserDTO(new User("Oscar", "oscar@gmail.com","test",30481032,280));
        assertThrows(API_Exception.class, () -> facade.createUser(userDTO));
    }

    @Test
    void findUserDTOByUsername() throws API_Exception {
        UserDTO userDTO = facade.getUserByUserName(udto1.getUserName());
        assertEquals(udto1, userDTO);
    }

    @Test
    void findAllUserDTOs() throws API_Exception {
        List<UserDTO> actual = facade.getAllUsers();
        int expected = 2;
        assertEquals(expected, actual.size());
    }

    @Test
    void deleteUserDTO() throws API_Exception {
        facade.deleteUser("Oscar");
        int actualSize = facade.getAllUsers().size();
        assertEquals(1, actualSize);
    }

    @Test
    void CantFindUserDTOToDelete() {
        assertThrows(API_Exception.class, () -> facade.deleteUser("HEJSA"));
    }

}


