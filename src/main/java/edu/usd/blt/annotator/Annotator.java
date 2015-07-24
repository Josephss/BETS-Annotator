package edu.usd.blt.annotator;

import edu.usd.btl.controllers.ToolBetsJpaController;
import edu.usd.btl.entities.ToolBets;
import edu.usd.btl.entities.ToolBridge;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Joseph Mammo
 */
public class Annotator {

    //Importing stuff ...
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceUnitPU");
    EntityManager em = emf.createEntityManager(); // The mother load :)
    private static ToolBetsJpaController tbController;

    /**
     * Importing the necessary persistence and entity managers
     *
     *
     */
    public void annotate() {

        //Insertion ...
        em.getTransaction().begin();
        // Inserting a new bridge
        ToolBridge tb = new ToolBridge();
        // Inserting a valid data ...
        tb.setOntologyId("Testing-ID");
        // Setting a toolID
        ToolBets tbs = new ToolBets();
        tbs.setId(2);
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

    // Get a list of tool Bets
    public void getTools() {
        tbController = new ToolBetsJpaController(emf);

        // List of all the BETS ...
        List<ToolBets> toolsList = tbController.findToolBetsEntities();

        System.out.println("Count: " + toolsList.size());

        for (int i = 0; i < 10; i++) {
            ToolBets temp = toolsList.get(i);
            System.out.println("Name: " + temp.getName());
            System.out.println("Summary: " + temp.getSummary());
            // Because of the one to many relationship .. (We have to nest it ....)
            Collection<ToolBridge> bridgeCollection = temp.getToolBridgeCollection();
            for (ToolBridge TIB : bridgeCollection) {
                System.out.println("\tBridge" + TIB.getOntologyId());
            }
        }
    }

    // Get all Bridges
    public void getAllBridges() {

    }

    // Get all Briges for a Tool Bets id (Integer)
    public void getBridgesById(int id) {

    }

    // Get all LIST Bets that match a name (String)
    public void getBetsByName(String name) {

    }

    // Save LIST of tool Bets
    public void setToolsListBets() {

    }

    // Save individual Tool Bets
    public void setToolBets() {

    }

    // Save Individual Tool Bridge 
    public void setToolBridge() {

    }

// ------------------------------------------------- // 
    // Fetching keywords from the published documents.
    // PDF --> Txt 
}
