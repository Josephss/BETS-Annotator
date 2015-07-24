package edu.usd.btl.entities;

import edu.usd.btl.entities.ToolBridge;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-03T11:38:36")
@StaticMetamodel(ToolBets.class)
public class ToolBets_ { 

    public static volatile SingularAttribute<ToolBets, String> summary;
    public static volatile SingularAttribute<ToolBets, String> name;
    public static volatile SingularAttribute<ToolBets, String> icon;
    public static volatile SingularAttribute<ToolBets, Integer> id;
    public static volatile SingularAttribute<ToolBets, byte[]> bets;
    public static volatile SingularAttribute<ToolBets, String> version;
    public static volatile CollectionAttribute<ToolBets, ToolBridge> toolBridgeCollection;
    public static volatile SingularAttribute<ToolBets, Date> dateAdded;
    public static volatile SingularAttribute<ToolBets, Date> dateUpdated;

}