package businessfacades;

import datafacades.AdminFacade;
import datafacades.DeveloperFacade;
import dtos.ProjectDTO;
import dtos.ProjectHourDTO;
import dtos.UserDTO;
import entities.Project;
import entities.ProjectHour;
import errorhandling.API_Exception;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class DeveloperDTOFacade {

    private static DeveloperDTOFacade instance;
    private static DeveloperFacade developerFacade;

    private DeveloperDTOFacade() {}

    public static DeveloperDTOFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            developerFacade = DeveloperFacade.getDeveloperFacade(_emf);
            instance = new DeveloperDTOFacade();
        }
        return instance;
    }

    public ProjectHourDTO recordProjectHours( ProjectHourDTO projectHourDTO) throws API_Exception {
        return new ProjectHourDTO(developerFacade.recordProjectHours(projectHourDTO.getEntity()));
    }

    public List<ProjectHourDTO> timeSpentOnSpecificProject(String userName, int projectId) throws API_Exception {
        return ProjectHourDTO.getProjectHourDTOs(developerFacade.timeSpentOnSpecificProject(userName,projectId));
    }

    public ProjectHourDTO editProjectHour(ProjectHourDTO projectHourDTO) throws API_Exception {
        return new ProjectHourDTO(developerFacade.editProjectHours(projectHourDTO.getEntity()));
    }

    public List<ProjectDTO> myProjects(String userName) throws API_Exception {
        return ProjectDTO.getProjectDTOs(developerFacade.myAssignedProjects(userName));
    }

    public ProjectHourDTO deleteRecording(int projectHourId) throws API_Exception {
        return new ProjectHourDTO(developerFacade.deleteMyOwnSpecificRecording(projectHourId));
    }

}
