package rest;

import businessfacades.AdminDTOFacade;
import businessfacades.UserDTOFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ProjectDTO;
import dtos.UserDTO;
import errorhandling.API_Exception;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

@Path("admin")
public class AdminResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private AdminDTOFacade facade = AdminDTOFacade.getInstance(EMF);
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();


    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createProject(String content) throws API_Exception {
        ProjectDTO projectDTO = GSON.fromJson(content, ProjectDTO.class);
        ProjectDTO newProjectDTO = facade.createProject(projectDTO);
        return Response.ok().entity(GSON.toJson(newProjectDTO)).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @GET
    @Path("/all")
    public Response listAllProjects() throws API_Exception {
        return Response.ok().entity(GSON.toJson(facade.listAllProjects())).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @POST
    @Path("/add/{userName}/{projectId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response assignDeveloperToProject(@PathParam("userName") String userName,@PathParam("projectId") int projectId) throws API_Exception {
        return Response.ok().entity(GSON.toJson(facade.assignDeveloperToProject(userName,projectId))).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @GET
    @Path("/invoice/{projectId}")
    public Response collectInvoice(@PathParam("projectId") int projectId) throws API_Exception {
        return Response.ok().entity(GSON.toJson(facade.collectInvoice(projectId))).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @GET
    @Path("/invoiceDetails/{projectId}")
    public Response invoiceDetails(@PathParam("projectId") int projectId) throws API_Exception {
        return Response.ok().entity(GSON.toJson(facade.invoiceDetails(projectId))).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }





}
