package quizlang;

/**
 * Langue est une énumération représentant les différentes langues disponibles pour un exercice.
 *
 * Pour l'instant, les langues disponibles sont :
 * - FR : français
 * - JP : japonais
 */
public enum Langue {

    FR("Français"), JP("Japonais");
	
	private String string;

	private Langue(String string) {
		this.string = string;
	}
	
	public String getLangueString() {
		return string;
	}
	
    // Méthode statique pour obtenir une instance de Langue à partir d'une chaîne
    public static Langue fromString(String langue) {
        for (Langue l : Langue.values()) {
            if (l.string.equalsIgnoreCase(langue) || l.name().equalsIgnoreCase(langue)) {
                return l;
            }
        }
        // Gérer le cas où la chaîne ne correspond à aucune Langue
        throw new IllegalArgumentException("Langue non prise en charge : " + langue);
    }
}