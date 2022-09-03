package me.escoffier.quarkus.supes;

import java.util.List;

public class RankingResult {

    public List<Hero> heroes;

    public RankingResult() {

    }

    public RankingResult(List<Hero> heroes) {
        this.heroes = heroes;
    }
}
