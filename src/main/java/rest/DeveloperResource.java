package rest;

import businessfacades.DeveloperDTOFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ProjectHourDTO;
import errorhandling.API_Exception;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

@Path("developer")
public class DeveloperResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private DeveloperDTOFacade facade = DeveloperDTOFacade.getInstance(EMF);
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    @POST
    @Path("/record")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response recordProjectHours( String content) throws API_Exception {
        ProjectHourDTO projectHourDTO = GSON.fromJson(content, ProjectHourDTO.class);
        ProjectHourDTO newProjectHourDTO = facade.recordProjectHours(projectHourDTO);

        return Response.ok().entity(GSON.toJson(newProjectHourDTO)).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }


    @GET
    @Path("/timeSpent/{userName}/{projectId}")
    public Response getAllUsers(@PathParam("userName")String userName,@PathParam("projectId")int projectId) throws API_Exception {
        return Response.ok().entity(GSON.toJson(facade.timeSpentOnSpecificProject(userName,projectId))).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @PUT
    @Path("/{projectHourId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("projectHourId")int projectHourId, String content) throws EntityNotFoundException, API_Exception {
        ProjectHourDTO phdto = GSON.fromJson(content, ProjectHourDTO.class);
        phdto.setId(projectHourId);
        ProjectHourDTO updatedProjectHour = facade.editProjectHour(phdto);
        return Response.ok().entity(GSON.toJson(updatedProjectHour)).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @DELETE
    @Path("/{projectHourId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteRecording(@PathParam("projectHourId") int projectHourId) throws API_Exception {
        ProjectHourDTO deletedRecording = facade.deleteRecording(projectHourId);
        return Response.ok().entity(GSON.toJson(deletedRecording)).type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();

    }

}
