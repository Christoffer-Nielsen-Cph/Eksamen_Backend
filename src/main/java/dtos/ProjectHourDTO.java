package dtos;

import entities.Project;
import entities.ProjectHour;
import entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectHourDTO {

    private int id;

    private int projectId;

    private String userName;

    private int hoursSpent;

    private int userStory;

    private String description;

    public ProjectHourDTO (ProjectHour projectHour){

            this.id = projectHour.getId();

            this.projectId = projectHour.getProjectId();

            this.userName = projectHour.getUserName();

            this.hoursSpent = projectHour.getHoursSpent();

            this.userStory = projectHour.getUserStory();

            this.description = projectHour.getDescription();


    }



    public ProjectHour getEntity(){
        ProjectHour  projectHour = new ProjectHour();
        if(this.id != 0){
            projectHour.setId(this.id);
        }
        projectHour.setProjectId(this.projectId);
        projectHour.setUserName(this.userName);
        projectHour.setHoursSpent(this.hoursSpent);
        projectHour.setUserStory(this.userStory);
        projectHour.setDescription(this.description);
        return projectHour;
    }

    public static List<ProjectHourDTO> getProjectHourDTOs(List<ProjectHour> projectHours){
        List<ProjectHourDTO> projectHourDTOs = new ArrayList();
        projectHours.forEach(projectHour->projectHourDTOs.add(new ProjectHourDTO(projectHour)));
        return projectHourDTOs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(int hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    public int getUserStory() {
        return userStory;
    }

    public void setUserStory(int userStory) {
        this.userStory = userStory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectHourDTO)) return false;
        ProjectHourDTO that = (ProjectHourDTO) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "ProjectHourDTO{" +
                "id=" + id +
                ", userName=" + userName +
                ", hoursSpent=" + hoursSpent +
                ", userStory=" + userStory +
                ", description='" + description + '\'' +
                '}';
    }
}
