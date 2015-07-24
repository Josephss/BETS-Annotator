package edu.usd.btl.tests;

import edu.usd.btl.entities.ToolBets;
import edu.usd.btl.entities.ToolBridge;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Joseph
 */
public class annotate {

    public static void main(String[] args) {
        
        //Importing stuff ...
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceUnitPU");
        EntityManager em = emf.createEntityManager(); // The mother load :)
        
        /**
         * 
         * Insersion ...
         * 
         */
        
        em.getTransaction().begin();
        
        // Inserting a new bridge
        
        ToolBridge tb = new ToolBridge();
        
        /// Inserting a valid data ...
        
                // Add this in order to ensure that the whole proccess when smoothly 
//        try{
//            
//        } catch {
//            
//        }
        
        tb.setOntologyId("Testing-ID");
        
        // Setting a toolID
        ToolBets tbs = new ToolBets();
        tbs.setId(4);
        
        // Inorder to have a valid bridge ..
        tb.setToolId(tbs);
        
        //Persist ... (Save it in the database ...)
        em.persist(tb);
        

        // Commit tb to the database 
        em.getTransaction().commit();
        
        
        // CLose em and emf ....
        em.close();
        emf.close();
    }
}
