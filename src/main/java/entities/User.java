package entities;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@NamedQuery(name = "User.deleteAllRows", query = "DELETE from User")
@Table(name = "users")
public class User {
    @Id
    @Size(max = 25)
    @Column(name = "user_name", nullable = false, length = 25)
    private String userName;

    @Size(max = 255)
    @NotNull
    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Size(max = 255)
    @NotNull
    @Column(name = "user_pass", nullable = false)
    private String userPass;

    @NotNull
    @Column(name = "user_phone", nullable = false)
    private int userPhone;

    @NotNull
    @Column(name = "user_billingPrHour", nullable = false)
    private int userBillingPrHour;

    /*
    @OneToMany(mappedBy = "userName")
    private List<ProjectHour> projectHours = new ArrayList<>();
     */
    @ManyToMany
    @JoinTable(name = "developers_project",
            joinColumns = @JoinColumn(name = "user_name"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects = new ArrayList<>();

    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
            @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();

    public User() {
    }

    public User(String userName, String userEmail, String userPass, int userPhone, int userBillingPrHour) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
        this.userPhone = userPhone;
        this.userBillingPrHour = userBillingPrHour;
    }

    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    public List<String> getProjectsAsStrings(){
        if(projects.isEmpty()){
            return null;
        }
        List<String> projectsAsStrings = new ArrayList<>();
        projects.forEach((project) ->{
            projectsAsStrings.add(project.getProjectName());
        });
        return projectsAsStrings;
    }

    public boolean verifyPassword(String pw) {
        return (BCrypt.checkpw(pw, userPass));
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPass() {
        return this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public void setUserPass(String userPass) {
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public int getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(int userPhone) {
        this.userPhone = userPhone;
    }

    public int getUserBillingPrHour() {
        return userBillingPrHour;
    }

    public void setUserBillingPrHour(int userBillingPrHour) {
        this.userBillingPrHour = userBillingPrHour;
    }
/*
    public List<ProjectHour> getProjectHours() {
        return projectHours;
    }

    public void setProjectHours(List<ProjectHour> projectHours) {
        this.projectHours = projectHours;
    }

 */

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Role> getRoles() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void addRole(Role userRole) {
        roleList.add(userRole);
    }

    public void assignProject(Project project){
        projects.add(project);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserName().equals(user.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName());
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPass='" + userPass + '\'' +
                ", userPhone=" + userPhone +
                ", userBillingPrHour=" + userBillingPrHour +
                ", projects=" + projects +
                ", roleList=" + roleList +
                '}';
    }
}