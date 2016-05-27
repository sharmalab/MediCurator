import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.CacheMode;

public class Get {

    public static void main(String[] args) throws Exception {
	DefaultCacheManager cacheManager = new DefaultCacheManager("infinispan.xml");

	Cache<Integer, Integer> cache = cacheManager.getCache("replicatedCache");

	for (int i = 0; i < 100; ++i)
	{
	    while (cache.get(i) == null)
	    {
		System.out.println(i + " = " + cache.get(i));
	    }
	    System.out.println(i + " = " + cache.get(i));
	}
    }
}

