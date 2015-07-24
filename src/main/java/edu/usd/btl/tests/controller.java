/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.tests;

import edu.usd.btl.controllers.ToolBetsJpaController;
import edu.usd.btl.entities.ToolBets;
import edu.usd.btl.entities.ToolBridge;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Joseph
 */
public class controller {

    private static ToolBetsJpaController tbController;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceUnitPU");
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
            for (ToolBridge TIB : bridgeCollection){
                System.out.println("\tBridge" + TIB.getOntologyId());
            }
        } 
        
      
    }
}
