package quizlang;

/**
 * Langue est une énumération représentant les différentes langues disponibles
 * pour un exercice.
 *
 * Pour l'instant, les langues disponibles sont : 
 * 		- FR : français 
 * 		- JP : japonais
 */
public enum Langue {

	FR("Français"), JP("Japonais");

	private String string;

	/**
     * Constructeur de l'énumération Langue.
     *
     * @param string La chaîne représentant la langue.
     */
	private Langue(String string) {
		this.string = string;
	}

	/**
     * Getter pour la représentation textuelle de la langue.
     *
     * @return La représentation textuelle de la langue.
     */
	public String getLangueString() {
		return string;
	}

	/**
     * Méthode statique pour obtenir une instance de Langue à partir d'une chaîne.
     *
     * @param langue La chaîne représentant la langue.
     * @return L'instance de Langue correspondante.
     * @throws IllegalArgumentException Si la chaîne ne correspond à aucune Langue.
     */
	public static Langue fromString(String langue) {
		for (Langue l : Langue.values()) {
			if (l.string.equalsIgnoreCase(langue) || l.name().equalsIgnoreCase(langue)) {
				return l;
			}
		}
		// Pour gérer le cas où la chaîne ne correspond à aucune Langue
		throw new IllegalArgumentException("Langue non prise en charge : " + langue);
	}
}