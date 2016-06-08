package edu.emory.bmi.medicurator.general;

import edu.emory.bmi.medicurator.infinispan.ID;
import java.util.UUID;
import java.util.HashSet;
import java.io.Serializable;

public class User implements Serializable
{
    private UUID userID = UUID.randomUUID();
    public UUID getID() { return userID; }

    private String username;
    private String password;

    private HashSet<UUID> replicaSets;
    
    public User(String username, String password)
    {
	this.username = username;
	this.password = password;
	replicaSets = new HashSet<UUID>();
	store();
    }

    public void setUsername(String username)
    {
	this.username = username;
	store();
    }

    public String getUsername()
    {
	return username;
    }
    
    public void setPassword(String password)
    {
	this.password = password;
	store();
    }

    public boolean checkPassword(String password)
    {
	return password.equals(this.password);
    }

    public UUID[] getReplicaSets()
    {
	return (UUID[])replicaSets.toArray();
    }

    public void addReplicaSet(ReplicaSet rs)
    {
	replicaSets.add(rs.getID());
	store();
    }

    public void removeReplicaSet(UUID replicaSetID)
    {
	replicaSets.remove(replicaSetID);
	store();
    }

    public void store()
    {
	ID.setUser(userID, this);
    }
}

