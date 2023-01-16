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
    @Column(name = "project_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_name", nullable = false)
    private User userName;

    @NotNull
    @Column(name = "hours_spent", nullable = false)
    private int hoursSpent;

    @NotNull
    @Column(name = "user_story", nullable = false)
    private int userStory;

    @Size(max = 100)
    @NotNull
    @Column(name = "description", nullable = false, length = 100)
    private String description;

    public ProjectHour() {
    }

    public ProjectHour(Integer id, User userName, int hoursSpent, int userStory, String description) {
        this.id = id;
        this.userName = userName;
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

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
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
                ", userName=" + userName +
                ", hoursSpent=" + hoursSpent +
                ", userStory=" + userStory +
                ", description='" + description + '\'' +
                '}';
    }
}