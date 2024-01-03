package utilisateurs;

/*
 * La classe abstraite qui représente un utilisateur de l'application.
 * Un utilisateur peut être un professeur ou un élève. 
 * Il est définit par un login unique.
 */
public abstract class Utilisateur {
	
	protected String id;
	protected String motDePasse;
	protected String nom;
	protected String prenom;

	/*
	 * Constructeur de la classe Utilisateur.
	 * @param login le login de l'utilisateur.
	 */
	public Utilisateur(String id, String motDePasse, String nom, String prenom){
		this.id = id;
		this.motDePasse = motDePasse;
		this.nom = nom;
		this.prenom = prenom;
	}

	/*
	 * Retourne le login de l'utilisateur.
	 * @return le login de l'utilisateur.
	 */
	public String getId(){
		return id;
	}
	
	public String getMdp(){
		return motDePasse;
	}
	public String getName(){
		return nom;
	}
	public String getSurname(){
		return prenom;
	}
	
	public void login() {
		//TODO
	}
	
	public void logout() {
		//TODO
	}
	
	public void UpdateProfil() {
		//TODO
	}
}
