package edu.emory.bmi.medicurator.general;

import edu.emory.bmi.medicurator.infinispan.ID;
import java.util.HashMap;
import java.io.Serializable;
import java.util.UUID;

/*
 * The uniform way to represent the meta information/attributes
 * A Metadata is a collection of String key-value pairs
 * Metadata is also able to store flags with key equals the flag and value equals ""
 */
public class Metadata implements Serializable
{
    //the unique ID used to retrieve the Metadata inside MediCurator
    private UUID metaID = UUID.randomUUID();
    public UUID getID() { return metaID; }

    //store the String key-value pairs
    public HashMap<String, String> keyValues;

    //create a new empty Metadata
    public Metadata()
    {
	keyValues = new HashMap<String, String>();
    }

    //make a copy from another Metadata
    public Metadata(Metadata another)
    {
	keyValues = new HashMap<String, String>(another.keyValues);
    }

    //store a flag
    public void put(String word)
    {
	keyValues.put(word, "");
    }

    //store a key-value pair
    public void put(String key, String value)
    { 
	if (value == null) value = ""; 
	keyValues.put(key, value); 
    }

    //check if the key exists in the Metadata
    public boolean contains(String key) 
    {
	return keyValues.containsKey(key);
    }

    //get the corresponding value of a key
    public String get(String key)
    {
	return keyValues.get(key);
    }

    //get a String array consists of all the keys in the Metadata
    public String[] getKeys()
    {
	return (String[])keyValues.keySet().toArray(new String[0]); 
    }

    //store the Metadata to Infinispan
    public void store()
    {
	ID.setMetadata(metaID, this);
    }
}

