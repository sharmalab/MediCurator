package edu.emory.bmi.medicurator.general;

import java.util.HashSet;
import java.util.HashMap;
import java.io.Serializable;
import java.util.UUID;

public class Metadata implements Serializable
{
    private UUID metaID = UUID.randomUUID();
    public UUID getID() { return metaID; }

    private HashSet<String> words;
    private HashMap<String, String> keyValues;

    public Metadata()
    {
	words = new HashSet<String>();
	keyValues = new HashMap<String, String>();
    }

    public Metadata(Metadata another)
    {
	words = new HashSet<String>(another.words);
	keyValues = new HashMap<String, String>(another.keyValues);
    }

    public boolean put(String word) { return words.add(word); }

    public String put(String key, String value) { return keyValues.put(key, value); }

    public boolean contains(String word) { return words.contains(word); }

    public boolean containsKey(String key) { return keyValues.containsKey(key); }

    public String get(String key) { return keyValues.get(key); }

    public String[] getWords() { return (String[])words.toArray(); }

    public String[] getKeys() { return (String[])keyValues.keySet().toArray(); }
}

