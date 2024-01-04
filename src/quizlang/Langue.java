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
	
	public String getLangue() {
		return string;
	}
	
//	public Langue getLanguageFromString(String langue) {
//		switch(langue) {
//		case "Français":
//			return Langue.FR;
//			
//		case "Japonais":
//			return Langue.JP;
//		
//		default:
//			return null;
//		
//		}
//	}
}