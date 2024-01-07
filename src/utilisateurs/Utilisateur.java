package utilisateurs;

/**
 * La classe abstraite qui représente un utilisateur de l'application. Un
 * utilisateur peut être un professeur ou un apprenant.
 */
public abstract class Utilisateur {

	protected String id;
	protected String motDePasse;
	protected String nom;
	protected String prenom;

	/**
     * Constructeur de la classe Utilisateur.
     *
     * @param id         Le login de l'utilisateur.
     * @param motDePasse Le mot de passe de l'utilisateur.
     * @param nom        Le nom de l'utilisateur.
     * @param prenom     Le prénom de l'utilisateur.
     */
	public Utilisateur(String id, String motDePasse, String nom, String prenom) {
		this.id = id;
		this.motDePasse = motDePasse;
		this.nom = nom;
		this.prenom = prenom;
	}

	/*
	 * Retourne le login de l'utilisateur.
	 * 
	 * @return le login de l'utilisateur.
	 */
	public String getId() {
		return id;
	}

	/**
     * Retourne le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe de l'utilisateur.
     */
	public String getMdp() {
		return motDePasse;
	}

	/**
     * Retourne le nom de l'utilisateur.
     *
     * @return Le nom de l'utilisateur.
     */
	public String getName() {
		return nom;
	}

	/**
     * Retourne le prénom de l'utilisateur.
     *
     * @return Le prénom de l'utilisateur.
     */
	public String getSurname() {
		return prenom;
	}
}
