package businessfacades;

import dtos.ProjectDTO;
import dtos.ProjectHourDTO;
import dtos.UserDTO;
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

public class DeveloperDTOFacadeTest {

    private static EntityManagerFactory emf;
    private static DeveloperDTOFacade facade;

    UserDTO udto1, udto2;

    ProjectDTO pdto1, pdto2;

    ProjectHourDTO phdto1, phdto2;

    public DeveloperDTOFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DeveloperDTOFacade.getInstance(emf);
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
        Project p1 = new Project(1,"Android app","Small interactive game");
        Project p2 = new Project(2,"Booking system","Fullstack application to help a local company");
        ProjectHour ph1 = new ProjectHour(1,u1.getUserName(),25,3,"Test description");
        ProjectHour ph2 = new ProjectHour(2,u2.getUserName(),40,6,"Test description");

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
            udto1 = new UserDTO(u1);
            udto2 = new UserDTO(u2);
            pdto1 = new ProjectDTO(p1);
            pdto2 = new ProjectDTO(p2);
            phdto1 = new ProjectHourDTO(ph1);
            phdto2 = new ProjectHourDTO(ph2);
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void myAssignedProjectsDTOTest () throws API_Exception {
        List<ProjectDTO> myProjects = facade.myProjects("Mark");
        assertEquals(1,myProjects.size());
    }

    @Test
    public void editProjectHoursDTOTest () throws API_Exception {
        phdto1.setDescription("Testing new description");
        ProjectHourDTO projectHourDTO = facade.editProjectHour(phdto1);
        assertEquals("Testing new description",projectHourDTO.getDescription());
    }

    @Test
    public void timeSpentOnSpecificProjectDTOTest () throws API_Exception {
        List<ProjectHourDTO> projectHoursDTO = facade.timeSpentOnSpecificProject(udto1.getUserName(),1);
        assertEquals(1,projectHoursDTO.size());
    }


}
