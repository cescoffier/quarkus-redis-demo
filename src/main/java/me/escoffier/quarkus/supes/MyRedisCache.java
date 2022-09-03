package me.escoffier.quarkus.supes;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.string.SetArgs;
import io.quarkus.redis.datasource.string.StringCommands;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.function.Supplier;

@ApplicationScoped
public class MyRedisCache {

    private final StringCommands<String, RankingResult> commands;

    public MyRedisCache(RedisDataSource ds) {
        this.commands = ds.string(RankingResult.class);
    }

    public RankingResult get(String key) {
        return commands.get(key);
    }

    public void set(String key, RankingResult result) {
        commands.set(key, result, new SetArgs().ex(Duration.ofSeconds(10)));
    }

    public void evict(String key) {
        commands.getdel(key);
    }

    public RankingResult getOrSetIfAbsent(String key, Supplier<RankingResult> computation) {
        var cached = get(key);
        if (cached != null) {
            return cached;
        } else {
            var result = computation.get();
            set(key, result);
            return result;
        }
    }
}
