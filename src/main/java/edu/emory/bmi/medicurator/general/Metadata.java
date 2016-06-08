package edu.emory.bmi.medicurator.general;

import edu.emory.bmi.medicurator.infinispan.ID;
import java.util.HashMap;
import java.io.Serializable;
import java.util.UUID;

public class Metadata implements Serializable
{
    private UUID metaID = UUID.randomUUID();
    public UUID getID() { return metaID; }

    public HashMap<String, String> keyValues;

    public Metadata()
    {
	keyValues = new HashMap<String, String>();
    }

    public Metadata(Metadata another)
    {
	keyValues = new HashMap<String, String>(another.keyValues);
    }

    public void put(String word)
    {
	keyValues.put(word, "");
    }

    public void put(String key, String value)
    { 
	if (value == null) value = ""; 
	keyValues.put(key, value); 
    }

    public boolean contains(String key) 
    {
	return keyValues.containsKey(key);
    }

    public String get(String key)
    {
	return keyValues.get(key);
    }

    public String[] getKeys()
    {
	return (String[])keyValues.keySet().toArray(new String[0]); 
    }

    public void store()
    {
	ID.setMetadata(metaID, this);
    }
}

