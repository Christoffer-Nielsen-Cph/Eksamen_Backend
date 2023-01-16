package datafacades;

import entities.Project;
import entities.User;
import errorhandling.API_Exception;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
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

    public void collectInvoice(int projectId) throws API_Exception {

    }




}
