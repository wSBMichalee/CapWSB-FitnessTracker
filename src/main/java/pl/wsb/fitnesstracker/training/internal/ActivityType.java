package pl.wsb.fitnesstracker.training.internal;

/**
 * Enum reprezentujący dostępne typy aktywności fizycznej
 * wspierane przez system Fitness Tracker.
 * Każdy typ aktywności posiada nazwę wyświetlaną (displayName),
 * która może być używana do prezentacji użytkownikowi w interfejsie.
 * Przykładowe użycie:
 * <pre>{@code
 * ActivityType activity = ActivityType.RUNNING;
 * System.out.println(activity.getDisplayName()); // "Running"
 * }</pre>
 */
// TODO : JavaDoc
public enum ActivityType {

    /**
     * Bieganie
     */
    RUNNING("Running"),
    /**
     * Jazda na rowerze.
     */
    CYCLING("Cycling"),
    /**
     * Spacer.
     */
    WALKING("Walking"),
    /**
     * Pływanie.
     */
    SWIMMING("Swimming"),
    /**
     * Gra w tenisa.
     */
    TENNIS("Tenis");

    private final String displayName;

    /**
     * Konstruktor przypisujący nazwę wyświetlaną do typu aktywności.
     *
     * @param displayName czytelna nazwa aktywności
     */
    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Zwraca nazwę wyświetlaną danego typu aktywności.
     *
     * @return nazwa aktywności (np. "Running", "Cycling")
     */
    public String getDisplayName() {
        return displayName;
    }

}
