package quizlang;


/**
 * La classe enum `BaremeNiveau` représente un barème de niveaux pour évaluer les réponses d'un élève.
 *
 * Chaque niveau est défini par les points attribués pour chaque réponse de l'élève :
 * - vrai : nombre de points attribués pour une réponse vraie.
 * - faux : nombre de points attribués pour une réponse fausse.
 * - nr : nombre de points attribués pour une réponse non répondue.
 *
 * Les niveaux disponibles sont :
 * - DEBUTANT : 1 point pour une réponse vraie, 0 point pour une réponse fausse et 0 point pour une réponse non répondue.
 * - INTERMEDIAIRE : 1 point pour une réponse vraie, -1 point pour une réponse fausse et 0 point pour une réponse non répondue.
 * - AVANCE : 1 point pour une réponse vraie, -1 point pour une réponse fausse et -1 point pour une réponse non répondue.
 *
 * Elle est utilisée dans l'interface {@link Notation}, notamment dans la classe {@link ReponseEleve} qui implémente l'interface {@link Notation}.
 *
 * @see Notation
 * @see ReponseEleve
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
	
//	private String string;

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
     * @param nr nombre de points attributs par réponse non répondue de l'élève
     */
    BaremeNiveau(int vrai, int faux, int nr) {
        this.vrai = vrai;
        this.faux = faux;
        this.nr = nr;
    }

    /**
     * Retourne le nombre de points attribués pour une réponse vraie.
     *
     * @return nombre de points attribués pour une réponse vraie.
     */
    public int getVrai(){
        return vrai;
    }

    /**
     * Retourne le nombre de points attribués pour une réponse fausse.
     *
     * @return nombre de points attribués pour une réponse fausse.
     */
    public int getFaux(){
        return faux;
    }

    /**
     * Retourne le nombre de points attribués pour une question non-répondue.
     *
     * @return nombre de points attribués pour une question non-répondue.
     */
    public int getNr(){
        return nr;
    }
    
    //  Méthode statique pour obtenir une instance de BaremeNiveau à partir d'une chaîne
    public static BaremeNiveau fromString(String niveau) {
        for (BaremeNiveau bn : BaremeNiveau.values()) {
            if (bn.name().equalsIgnoreCase(niveau)) {
                return bn;
            }
        }
        // Gérer le cas où la chaîne ne correspond à aucune Langue
        throw new IllegalArgumentException("Niveau non prise en charge : " + niveau);
    }
}
