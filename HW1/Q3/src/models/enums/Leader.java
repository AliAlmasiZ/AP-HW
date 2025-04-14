package models.enums;

public enum Leader {
    ADENAUER(Ideology.DEMOCRACY, Country.GERMAN_REICH),
    PIECK(Ideology.COMMUNISM, Country.GERMAN_REICH),
    HITLER(Ideology.FASCISM, Country.GERMAN_REICH),

    ZOMBIE_LENIN(Ideology.DEMOCRACY, Country.SOVIET_UNION),
    STALIN(Ideology.COMMUNISM, Country.SOVIET_UNION),
    TROTSKY(Ideology.FASCISM, Country.SOVIET_UNION),

    ROOSEVELT(Ideology.DEMOCRACY, Country.UNITED_STATES),
    BROWDER(Ideology.COMMUNISM, Country.UNITED_STATES),
    PELLEY(Ideology.FASCISM, Country.UNITED_STATES),

    CHURCHILL(Ideology.DEMOCRACY, Country.UNITED_KINGDOM),
    MOSLEY(Ideology.FASCISM, Country.UNITED_KINGDOM),

    HIROHITO(Ideology.FASCISM, Country.JAPAN),
    ;

    private final Ideology ideology;
    private final Country country;


    Leader(Ideology ideology, Country country) {
        this.ideology = ideology;
        this.country = country;
    }

    public Ideology getIdeology() {
        return ideology;
    }

    public Country getCountry() {
        return country;
    }
}
