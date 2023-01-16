package businessfacades;

import dtos.ProjectDTO;
import dtos.UserDTO;
import entities.Project;
import entities.Role;
import entities.User;
import errorhandling.API_Exception;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdminDTOFacadeTest {

    private static EntityManagerFactory emf;
    private static AdminDTOFacade facade;

    UserDTO udto1, udto2;

    ProjectDTO pdto1, pdto2;

    public AdminDTOFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = AdminDTOFacade.getInstance(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Role userRole = new Role("admin");
        User u1 = new User("Oscar","Oscar@gmail.com","test",20309040,300);
        User u2 = new User("Mark","Mark@gmail.com","test",30490591,250);
        u1.addRole(userRole);
        u2.addRole(userRole);
        Project p1 = new Project("Android app","Small interactive game");
        Project p2 = new Project("Booking system","Fullstack application to help a local company");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("Project.deleteAllRows").executeUpdate();
            em.persist(userRole);
            em.persist(u1);
            em.persist(u2);
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            udto1 = new UserDTO(u1);
            udto2 = new UserDTO(u2);
            pdto1 = new ProjectDTO(p1);
            pdto2 = new ProjectDTO(p2);
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void createProjectDTOTest() throws API_Exception {
        ProjectDTO projectDTO = new ProjectDTO(new Project("Mobile app","test case"));
        facade.createProject(projectDTO);
        assertNotNull(projectDTO.getProjectName());
        int actualSize = facade.listAllProjects().size();
        assertEquals(3, actualSize);
    }

    @Test
    void listAllProjectDTOsTest() throws API_Exception {
        List<ProjectDTO> actual = facade.listAllProjects();
        int expected = 2;
        assertEquals(expected, actual.size());
    }

}
