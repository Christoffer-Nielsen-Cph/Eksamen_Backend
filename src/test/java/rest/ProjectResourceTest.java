package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ProjectDTO;
import dtos.ProjectHourDTO;
import dtos.UserDTO;
import entities.Project;
import entities.ProjectHour;
import entities.Role;
import entities.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class ProjectResourceTest    {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static UserDTO udto1, udto2;
    private static ProjectDTO pdto1, pdto2;
    private static ProjectHourDTO phdto1, phdto2;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
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
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/admin/all").then().statusCode(200);
    }

    @Test
    public void testLogRequest() {
        System.out.println("Testing logging request details");
        given().log().all()
                .when().get("/admin/all")
                .then().statusCode(200);
    }

    @Test
    public void testLogResponse() {
        System.out.println("Testing logging response details");
        given()
                .when().get("/admin/all")
                .then().log().body().statusCode(200);
    }

    @Test
    public void testPrintResponse() {
        Response response = given().when().get("/users/" + udto1.getUserName());
        ResponseBody body = response.getBody();
        System.out.println(body.prettyPrint());

        response
                .then()
                .assertThat()
                .body("userName", equalTo("Oscar"));
    }

    @Test
    void createRecord() {
        ProjectHour projectHour = new ProjectHour();
        projectHour.setId(5);
        projectHour.setProjectId(1);
        projectHour.setHoursSpent(20);
        projectHour.setDescription("Testing description");
        projectHour.setUserStory(1);
        projectHour.setUserName("Mark");


        ProjectHourDTO phdto = new ProjectHourDTO(projectHour);
        String requestBody = GSON.toJson(phdto);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/developer/record")
                .then()
                .assertThat()
                .statusCode(200)
                .body("userName", equalTo("Mark"))
                .body("hoursSpent", equalTo(20))
                .body("userStory",equalTo(1))
                .body("description",equalTo("Testing description"));

    }
    

    @Test
    void updateRecord () {
        phdto1.setUserName("Frank");
        phdto1.setDescription("New Description");
        phdto1.setHoursSpent(40);
        phdto1.setUserStory(15);
        phdto1.setProjectId(1);

        String requestBody = GSON.toJson(phdto1);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put("/developer/"+phdto1.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .body("userName", equalTo("Frank"))
                .body("hoursSpent", equalTo(40))
                .body("userStory",equalTo(15))
                .body("description",equalTo("New Description"));

    }

    @Test
    void deleteRecord () {
        given()
                .contentType(ContentType.JSON)
                .pathParam("projectHourId", phdto1.getId())
                .delete("/developer/{projectHourId}")
                .then()
                .statusCode(200);
    }

    @Test
    void getMyProjects () {
        given()
                .contentType(ContentType.JSON)
                .get("/developer/myProjects/" + udto1.getUserName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("projectName", containsInAnyOrder(pdto1.getProjectName()))
                .body("projectDescription",containsInAnyOrder(pdto1.getProjectDescription()));

    }

    @Test
    void createProject () {

        Project project = new Project();
        project.setId(5);
        project.setProjectDescription("Rest testing description");
        project.setProjectName("Mobile test");


        ProjectDTO pdto = new ProjectDTO(project);
        String requestBody = GSON.toJson(pdto);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/admin")
                .then()
                .assertThat()
                .statusCode(200)
                .body("projectDescription", equalTo("Rest testing description"))
                .body("projectName", equalTo("Mobile test"));

    }

    @Test
    void listAllProjects () {
        List<ProjectDTO> projectDTOS;
        projectDTOS = given()
                .contentType("application/json")
                .when()
                .get("/admin/all")
                .then()
                .extract().body().jsonPath().getList("", ProjectDTO.class);
        assertThat(projectDTOS, containsInAnyOrder(pdto1, pdto2));
    }

}
