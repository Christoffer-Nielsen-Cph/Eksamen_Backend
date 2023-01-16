package datafacades;

import entities.Project;
import entities.ProjectHour;
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

public class DeveloperFacadeTest {
    private static EntityManagerFactory emf;

    private static DeveloperFacade facade;

    User u1, u2;
    Project p1, p2;

    ProjectHour ph1,ph2;

    public DeveloperFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DeveloperFacade.getDeveloperFacade(emf);
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
        p1 = new Project(1,"Android app","Small interactive game");
        p2 = new Project(2,"Booking system","Fullstack application to help a local company");
        ph1 = new ProjectHour(1,u1.getUserName(),25,3,"Test description");
        ph2 = new ProjectHour(2,u2.getUserName(),40,6,"Test description");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("ProjectHour.deleteAllRows").executeUpdate();
            em.createNamedQuery("Project.deleteAllRows").executeUpdate();
            em.persist(userRole);
            em.persist(u1);
            em.persist(u2);
            em.persist(p1);
            em.persist(p2);
            em.persist(ph1);
            em.persist(ph2);
            u1.assignProject(p1);
            u2.assignProject(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void recordProjectHoursTest () throws API_Exception {
        ProjectHour projectHour = new ProjectHour(1,u1.getUserName(),20,1,"Test description");
        ProjectHour actual = facade.recordProjectHours(projectHour);
        assertEquals(20,actual.getHoursSpent());

    }

    @Test
    public void myAssignedProjectsTest () throws API_Exception {
        List<Project> myProjects = facade.myAssignedProjects("Mark");
        assertEquals(1,myProjects.size());
    }

    @Test
    public void editProjectHoursTest () throws API_Exception {
        ph1.setDescription("Testing new description");
        ProjectHour projectHour = facade.editProjectHours(ph1);
        assertEquals("Testing new description",projectHour.getDescription());
    }

    @Test
    public void timeSpentOnSpecificProjectTest () throws API_Exception {
        List<ProjectHour> projectHours = facade.timeSpentOnSpecificProject(u1.getUserName(),1);
        assertEquals(1,projectHours.size());
    }

}
