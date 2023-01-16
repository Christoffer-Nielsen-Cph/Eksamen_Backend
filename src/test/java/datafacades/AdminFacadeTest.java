package datafacades;

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

public class AdminFacadeTest {

    private static EntityManagerFactory emf;

    private static AdminFacade facade;

    User u1, u2;
    Project p1, p2;

    public AdminFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = AdminFacade.getAdminFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Role userRole = new Role("admin");
        u1 = new User("Oscar","Oscar@gmail.com","test",20309040,300);
        u2 = new User("Mark","Mark@gmail.com","test",30490591,250);
        u1.addRole(userRole);
        u2.addRole(userRole);
        p1 = new Project("Android app","Small interactive game");
        p2 = new Project("Booking system","Fullstack application to help a local company");

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
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void createProjectTest() throws API_Exception {
        Project project = new Project("School project","Test case");
        facade.createProject(project);
        assertNotNull(project.getProjectName());
        int actualSize = facade.listAllProjects().size();
        assertEquals(3, actualSize);
    }

    @Test
    void listAllProjectsTest() throws API_Exception {
        List<Project> actual = facade.listAllProjects();
        int expected = 2;
        assertEquals(expected, actual.size());
    }
}
