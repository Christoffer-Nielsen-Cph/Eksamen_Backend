package datafacades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.Project;
import entities.Role;
import entities.User;
import utils.EMF_Creator;

public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        User user = new User("user", "user@gmail.com","test123",20103040,200);
        User admin = new User("admin", "admin@gmail.com","test123",30103940,180);
        User both = new User("user_admin", "user_admin@gmail.com","test123",40491021,250);
        Project p1 = new Project("Just Eat","Fullstack application for food delivery service");
        Project p2 = new Project("Uber","Mobile app for Uber, which is a taxi service");


        if(admin.getUserPass().equals("test123")||user.getUserPass().equals("test123")||both.getUserPass().equals("test123"))
            throw new UnsupportedOperationException("You have not changed the passwords");

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();
        System.out.println("PW: " + user.getUserPass());
        System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
        System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
        System.out.println("Created TEST Users");

    }
    
    public static void main(String[] args) {
        populate();
    }
}
