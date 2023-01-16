package datafacades;

import entities.ProjectHour;
import entities.Role;
import entities.User;
import errorhandling.API_Exception;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class DeveloperFacade {
    private static EntityManagerFactory emf;
    private static DeveloperFacade instance;

    private DeveloperFacade() {
    }

    public static DeveloperFacade getDeveloperFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DeveloperFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ProjectHour recordProjectHours (ProjectHour projectHour) throws API_Exception {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(projectHour);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return projectHour;

    }


    public ProjectHour editProjectHours (ProjectHour projectHour) throws API_Exception{
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            ProjectHour p = em.merge(projectHour);
            em.getTransaction().commit();
            return p;
        } catch (Exception e) {
            throw new API_Exception("Can't find a project hour with the id: "+projectHour.getId(),404,e);
        } finally {
            em.close();
        }
    }

    public ProjectHour deleteMyOwnSpecificRecording (int projectHourId) throws API_Exception{
        EntityManager em = getEntityManager();
        ProjectHour ph = em.find(ProjectHour.class, projectHourId);

        try {
            em.getTransaction().begin();
            em.remove(ph);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (ph == null) {
                throw new API_Exception("Can't find a recording with the id: " + projectHourId,404,e);
            }
        } finally {
            em.close();
        }
        return ph;

    }



    public List<ProjectHour> timeSpentOnSpecificProject (String userName, int projectId) throws API_Exception {
        EntityManager em = getEntityManager();

        TypedQuery<ProjectHour> query = em.createQuery("SELECT p FROM ProjectHour p where p.projectId =:projectId and p.userName =:userName",ProjectHour.class);
        query.setParameter("projectId",projectId);
        query.setParameter("userName",userName);
        List<ProjectHour> list = query.getResultList();
        return list;
    }





}
