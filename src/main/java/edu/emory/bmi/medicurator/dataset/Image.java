import java.util.UUID;

public abstract class Image
{
    private UUID imageID = UUID.randomUUID();
    public UUID getID { return imageID; }

    public abstract Metadata getMetadata();
    public abstract byte[] getImage();
}

