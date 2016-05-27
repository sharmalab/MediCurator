import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.CacheMode;

public class Put {

    public static void main(String[] args) throws Exception {
	DefaultCacheManager cacheManager = new DefaultCacheManager("infinispan.xml");

	Cache<Integer, Integer> cache = cacheManager.getCache("replicatedCache");

	int start = Integer.parseInt(args[0]);
	int step = Integer.parseInt(args[1]);

	for (int i = start; i < start + 100; i += step)
	{
	    cache.put(i, i * i);
	}
    }
}

