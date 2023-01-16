package datafacades;

import entities.Project;
import entities.ProjectHour;
import entities.User;
import errorhandling.API_Exception;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class AdminFacade {

    private static EntityManagerFactory emf;
    private static AdminFacade instance;

    private AdminFacade() {
    }

    public static AdminFacade getAdminFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AdminFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Project createProject(Project project) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(project);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new API_Exception("There's already a project with the name: " + project.getProjectName() + " in the system!",400,e);
        } finally {
            em.close();
        }
        return project;
    }

    public List<Project> listAllProjects() throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p", Project.class);
            return query.getResultList();
        } catch (Exception e){
            throw new API_Exception("Can't find any projects in the system",404,e);
        }
    }

    public User assignDeveloperToProject(String userName,int projectId) throws API_Exception{
        EntityManager em = getEntityManager();
        User user = em.find(User.class,userName);
        Project project = em.find(Project.class,projectId);
        try {
            em.getTransaction().begin();
            project.getUsers().add(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (user == null) {
                throw new API_Exception("Can't find a user with the username: " + userName,400,e);
            }
            if (project == null) {
                throw  new API_Exception("Can't find a project with the id: "+projectId,400,e);
            }
        } finally {
            em.close();
        }
        return user;
    }

    public List<Project> collectInvoice (int projectId) throws API_Exception {
        EntityManager em = getEntityManager();
        List<Project> projectList = new ArrayList<>();
        try{
            TypedQuery<Project> projects = em.createQuery("select p from Project p where p.id =:projectId",Project.class);
            projectList = projects.setParameter("projectId",projectId).getResultList();

        } catch (Exception e) {
            if(projectList == null) {
                throw new API_Exception("Can't find a project with the id: "+projectId,400,e);
            }
        }
        return projectList;
    }

    /*public List<ProjectHour> invoiceDetails (int projectId) throws API_Exception {
        EntityManager em = getEntityManager();
        List<ProjectHour> recordingList;
        TypedQuery<ProjectHour> details = em.createQuery("select p from ProjectHour p where p.projectId =:projectId",ProjectHour.class);
        recordingList = details.setParameter("projectId",projectId).getResultList();

        return recordingList;
    }

     */

    public List<ProjectHour> invoiceDetails (int projectId) throws API_Exception {
        EntityManager em = getEntityManager();
        List<ProjectHour> recordingList;

        Query details = em.createQuery("select u.userBillingPrHour,p.hoursSpent from ProjectHour p join User u on p.userName = u.userName where p.projectId =:projectId");
        details.setParameter("projectId",projectId);
        recordingList = details.getResultList();

        return recordingList;
    }

}
