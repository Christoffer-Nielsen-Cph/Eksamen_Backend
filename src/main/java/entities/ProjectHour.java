package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@NamedQuery(name = "ProjectHour.deleteAllRows", query = "DELETE from ProjectHour ")
@Table(name = "project_hours")
public class ProjectHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_hours_id", nullable = false)
    private Integer id;

    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotNull
    @Column(name = "hours_spent", nullable = false)
    private Integer hoursSpent;

    @NotNull
    @Column(name = "user_story", nullable = false)
    private Integer userStory;

    @Size(max = 100)
    @NotNull
    @Column(name = "description", nullable = false, length = 100)
    private String description;

    public ProjectHour() {
    }

    public ProjectHour(int projectId, String userName, int hoursSpent, int userStory, String description) {
        this.projectId = projectId;
        this.userName = userName;
        this.hoursSpent = hoursSpent;
        this.userStory = userStory;
        this.description = description;
    }

    public ProjectHour(int projectId, int hoursSpent, int userStory, String description) {
        this.projectId = projectId;
        this.hoursSpent = hoursSpent;
        this.userStory = userStory;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(Integer hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    public Integer getUserStory() {
        return userStory;
    }

    public void setUserStory(Integer userStory) {
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
        if (!(o instanceof ProjectHour)) return false;
        ProjectHour that = (ProjectHour) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "ProjectHour{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", userName=" + userName +
                ", hoursSpent=" + hoursSpent +
                ", userStory=" + userStory +
                ", description='" + description + '\'' +
                '}';
    }
}