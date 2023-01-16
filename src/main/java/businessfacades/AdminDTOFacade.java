package businessfacades;

import datafacades.AdminFacade;
import datafacades.UserFacade;
import dtos.ProjectDTO;
import dtos.UserDTO;
import errorhandling.API_Exception;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class AdminDTOFacade {

    private static AdminDTOFacade instance;
    private static AdminFacade adminFacade;

    private AdminDTOFacade() {}

    public static AdminDTOFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            adminFacade = AdminFacade.getAdminFacade(_emf);
            instance = new AdminDTOFacade();
        }
        return instance;
    }

    public ProjectDTO createProject(ProjectDTO projectDTO) throws API_Exception {
        return new ProjectDTO(adminFacade.createProject(projectDTO.getEntity()));
    }

    public List<ProjectDTO> listAllProjects() throws API_Exception {
        return ProjectDTO.getProjectDTOs(adminFacade.listAllProjects());
    }

    public UserDTO assignDeveloperToProject(String userName,int projectId) throws API_Exception {
        return new UserDTO(adminFacade.assignDeveloperToProject(userName,projectId));
    }
}
