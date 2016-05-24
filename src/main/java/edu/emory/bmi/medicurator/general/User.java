package edu.emory.bmi.medicurator.general;

import java.util.UUID;
import java.util.HashSet;
import java.io.Serializable;

public class User implements Serializable
{
    private UUID userid = UUID.randomUUID();
    public UUID getID() { return userid; }

    private String username;
    private String password;

    private HashSet<UUID> replicaSets;
    
    public User(String username, String password)
    {
	this.username = username;
	this.password = password;
	replicaSets = new HashSet<UUID>();
	ID.setUser(this.userid, this);
    }

    public boolean setUsername(String username)
    {
	this.username = username;
    }

    public String getUsername()
    {
	return username;
    }
    
    public boolean setPassword(String password)
    {
	this.password = password;
    }

    public boolean checkPassword(String password)
    {
	return password.equals(this.password);
    }

    public UUID[] getReplicaSets()
    {
	return replicaSets.toArray();
    }

    public boolean addReplicaSet(ReplicaSet rs)
    {
	return replicaSets.add(rs.getID());
    }

    public boolean removeReplicaSet(UUID replicaSetID)
    {
	return replicaSets.remove(replicaSetID);
    }
}

