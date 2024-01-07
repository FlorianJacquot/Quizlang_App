package quizlang;

/**
 * La classe enum `BaremeNiveau` représente un barème de niveaux pour évaluer
 * les réponses d'un élève.
 *
 * Chaque niveau est défini par les points attribués pour chaque réponse de
 * l'élève : 
 * - vrai : nombre de points attribués pour une réponse vraie. 
 * - faux : nombre de points attribués pour une réponse fausse. 
 * - nr : nombre de points attribués pour une réponse non répondue.
 *
 * Les niveaux disponibles sont : 
 * - DEBUTANT : 1 point pour une réponse vraie, 0 point pour une réponse fausse et 0 point pour une réponse non répondue. 
 * - INTERMEDIAIRE : 1 point pour une réponse vraie, -1 point pour une réponse fausse et 0 point pour une réponse non répondue. 
 * - AVANCE : 1 point pour une réponse vraie, -1 point pour une réponse fausse et -1 point pour une réponse non répondue.
 */
public enum BaremeNiveau {

	/**
	 * Le barème de notation pour les élèves de niveau DEBUTANT
	 */
	DEBUTANT(1, 0, 0),

	/**
	 * Le barème de notation pour les élèves de niveau INTERMEDIAIRE
	 */
	INTERMEDIAIRE(1, -1, 0),

	/**
	 * Le barème de notation pour les élèves de niveau AVANCE
	 */
	AVANCE(1, -1, -1);

	/**
	 * Nombre de points attribués par réponse vraie de l'élève.
	 */
	private int vrai;

	/**
	 * Nombre de points attribués par réponse fausse de l'élève.
	 */
	private int faux;

	/**
	 * Nombre de points attributs par réponse non répondue de l'élève.
	 */
	private int nr;

	/**
	 * Constructeur de la classe `BaremeNiveau`
	 *
	 * @param vrai nombre de points attribués par réponse vraie de l'élève
	 * @param faux nombre de points attribués par réponse fausse de l'élève
	 * @param nr   nombre de points attributs par réponse non répondue de l'élève
	 */
	BaremeNiveau(int vrai, int faux, int nr) {
		this.vrai = vrai;
		this.faux = faux;
		this.nr = nr;
	}

	/**
	 * Getter pour le nombre de points attribués pour une réponse vraie.
	 *
	 * @return nombre de points attribués pour une réponse vraie.
	 */
	public int getVrai() {
		return vrai;
	}

	/**
	 * Getter pour le nombre de points attribués pour une réponse fausse.
	 *
	 * @return nombre de points attribués pour une réponse fausse.
	 */
	public int getFaux() {
		return faux;
	}

	/**
	 * Getter pour le nombre de points attribués pour une question non-répondue.
	 *
	 * @return nombre de points attribués pour une question non-répondue.
	 */
	public int getNr() {
		return nr;
	}

	/**
	 * Méthode statique pour obtenir une instance de BaremeNiveau à partir d'une
	 * chaîne de caractères
	 * 
	 * @param niveau
	 * @return le niveau
	 */
	public static BaremeNiveau fromString(String niveau) {
		for (BaremeNiveau bn : BaremeNiveau.values()) {
			if (bn.name().equalsIgnoreCase(niveau)) {
				return bn;
			}
		}
		// Pour gérer le cas où la chaîne ne correspond à aucune Langue
		throw new IllegalArgumentException("Niveau non prise en charge : " + niveau);
	}

	/**
	 * Méthode pour comparer deux niveaux en utilisant le nombre de points qui leur
	 * sont attribués en fonction des réponses. On veut avoir l'ordre suivant :
	 * DEBUTANT < INTERMEDIAIRE < AVANCE
	 *
	 * @param autreNiveau le niveau à comparer.
	 * @return la valeur de la comparaison : 
	 * 		- une valeur < 0 si le niveau actuel est inférieur à l'autre niveau. 
	 * 		- une valeur > 0 si le niveau actuel est supérieur à l'autre niveau. 
	 * 		- une valeur == 0 si les deux niveaux sont équivalents.
	 */
	public int compare(BaremeNiveau autreNiveau) {
		int compareVrai = Integer.compare(this.vrai, autreNiveau.vrai);
		if (compareVrai != 0) {
			return compareVrai;
		}

		int compareFaux = Integer.compare(this.faux, autreNiveau.faux);
		if (compareFaux != 0) {
			return compareFaux;
		}

		return Integer.compare(this.nr, autreNiveau.nr);
	}

	/**
	 * Méthode pour déterminer si le niveau actuel est inférieur ou équivalent à un
	 * autre niveau, en utilisant le nombre de points qui leur sont attribués en
	 * fonction des réponses. On veut avoir l'ordre suivant : DEBUTANT <
	 * INTERMEDIAIRE < AVANCE
	 *
	 * @param autreNiveau le niveau à comparer.
	 * @return true si le niveau actuel est inférieur ou équivalent à l'autre
	 *         niveau, false sinon.
	 */
	public boolean plusPetitQue(BaremeNiveau autreNiveau) {
		return this.compare(autreNiveau) >= 0;
	}

}
