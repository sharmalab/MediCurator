import java.util.List;
import java.util.UUID;

public abstract class ImageSet
{
    private UUID imageSetID = UUID.randomUUID();

    public UUID getID() { return imageSetID; }

    public abstract Metadata getMetadata();
    public abstract ImageSet[] getSubset();
    public abstract Image[] getImages();
}

