package edu.emory.bmi.medicurator.dupdetect;

import java.util.UUID;
import java.io.Serializable;

/*
 * The near-duplicate pair data type. Consists of two image IDs.
 */
public class DuplicatePair implements Serializable
{
    public UUID first;
    public UUID second;

    public DuplicatePair(UUID a, UUID b)
    {
	if (a.compareTo(b) <= 0)
	{
	    first = a;
	    second = b;
	}
	else
	{
	    first = b;
	    second = a;
	}
    }

    public boolean equals(Object obj)
    {
	DuplicatePair another = (DuplicatePair)obj;
	return first.equals(another.first) && second.equals(another.second);
    }
    
    public int hashCode()
    {
	return first.hashCode() ^ second.hashCode();
    }
}

