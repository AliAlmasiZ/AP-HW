package models.enums;

public enum Leader {
    ADENAUER(Ideology.DEMOCRACY, CountryType.GERMAN_REICH),
    PIECK(Ideology.COMMUNISM, CountryType.GERMAN_REICH),
    HITLER(Ideology.FASCISM, CountryType.GERMAN_REICH),

    ZOMBIE_LENIN(Ideology.DEMOCRACY, CountryType.SOVIET_UNION),
    STALIN(Ideology.COMMUNISM, CountryType.SOVIET_UNION),
    TROTSKY(Ideology.FASCISM, CountryType.SOVIET_UNION),

    ROOSEVELT(Ideology.DEMOCRACY, CountryType.UNITED_STATES),
    BROWDER(Ideology.COMMUNISM, CountryType.UNITED_STATES),
    PELLEY(Ideology.FASCISM, CountryType.UNITED_STATES),

    CHURCHILL(Ideology.DEMOCRACY, CountryType.UNITED_KINGDOM),
    MOSLEY(Ideology.FASCISM, CountryType.UNITED_KINGDOM),

    HIROHITO(Ideology.FASCISM, CountryType.JAPAN),
    ;

    private final Ideology ideology;
    private final CountryType country;


    Leader(Ideology ideology, CountryType country) {
        this.ideology = ideology;
        this.country = country;
    }

    public Ideology getIdeology() {
        return ideology;
    }

    public CountryType getCountry() {
        return country;
    }
}
