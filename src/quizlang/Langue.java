package quizlang;

/**
 * Langue est une énumération représentant les différentes langues disponibles pour un exercice.
 *
 * Pour l'instant, les langues disponibles sont :
 * - FR : français
 * - JP : japonais
 */
public enum Langue {

    FR, JP;
	
	public Langue getLanguageFromString(String langue) {
		switch(langue) {
		case "Français":
			return Langue.FR;
			
		case "Japonais":
			return Langue.JP;
		
		default:
			return null;
		
		}
	}
}