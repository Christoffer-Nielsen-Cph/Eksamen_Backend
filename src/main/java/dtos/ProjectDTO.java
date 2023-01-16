package dtos;

import entities.Project;
import entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectDTO {

    private int id;

    private String projectName;

    private String projectDescription;

    public ProjectDTO (Project project){
        if(project.getId() != null){
            this.id = project.getId();
        }
        this.projectName = project.getProjectName();
        this.projectDescription = project.getProjectDescription();
    }

    public Project getEntity(){
        Project project = new Project();
        if(this.id != 0){
            project.setId(this.id);
        }
        project.setProjectName(this.projectName);
        project.setProjectDescription(this.projectDescription);
        return project;
    }

    public static List<ProjectDTO> getProjectDTOs(List<Project> projects){
        List<ProjectDTO> projectDTOs = new ArrayList();
        projects.forEach(project->projectDTOs.add(new ProjectDTO(project)));
        return projectDTOs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectDTO)) return false;
        ProjectDTO that = (ProjectDTO) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                '}';
    }
}
