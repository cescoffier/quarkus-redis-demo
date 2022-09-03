package me.escoffier.quarkus.supes;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.stream.Collectors;

@ApplicationScoped
public class HeroService {

    @Inject
    MyRedisCache cache;

    public RankingResult getTopHeroes() {
        return cache.getOrSetIfAbsent("top", () -> {
                    // Dumb approach, don't do this
                    return new RankingResult(Hero.<Hero>listAll()
                            .stream()
                            .sorted((o1, o2) -> Integer.compare(o2.level, o1.level))
                            .peek(h -> {
                                // do something very long...
                                nap();
                            })
                            .limit(3)
                            .collect(Collectors.toList()));
                });
    }

    private void nap() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
