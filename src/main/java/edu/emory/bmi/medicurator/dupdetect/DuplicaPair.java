package edu.emory.bmi.medicurator.dupdetect;

import java.util.UUID;
import java.io.Serializable;

public class DuplicatePair implements Serializable
{
    public UUID first;
    public UUID second;

    public DuplicatePair(UUID a, UUID b)
    {
	first = a;
	second = b;
    }
}

