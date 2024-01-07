//package quizlang;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.StandardCopyOption;
//import java.util.Scanner;
//
//import javax.swing.JOptionPane;
//
//import utilisateurs.Apprenant;
//import utilisateurs.Professeur;
//
////import com.j256.ormlite.dao.Dao;
////import com.j256.ormlite.field.DatabaseField;
////import fake_quizlet.exercice.Exercice;
////import fake_quizlet.exercice.Langue;
////import fake_quizlet.notation.BaremeNiveau;
////import fake_quizlet.notation.Notation;
////import fake_quizlet.reponse.ReponseEleve;
////import fake_quizlet.utilisateurs.Eleve;
////import fake_quizlet.utilisateurs.Professeur;
//
//
///**
// *  La classe NiveauxEleves représente le niveau d'un apprenant dans la langue qu'il étudie.
// *  Elle est utilisée pour enregistrer et mettre à jour le score de l'élève dans cette langue.
// *
// *  Il est important de comprendre que chaque méthode est appelée sur un objet de classe NiveauxEleves,
// *  et non sur un objet de classe Eleve. C'est pour cela que l'on a pas besoin de fournir en argument la langue ou le pseudo de l'élève.
// *  Il faut voir ça comme une table où chaque ligne possède un identifiant unique (la clé primaire).
// *  Un même élève peut être sur plusieurs lignes car il étudie plusieurs langues.
// *
// *  Par exemple, si Marie étudie le français avec Jean et l'allemand avec Claude, on pourrait avoir dans notre table les deux lignes suivantes :
// *  1 -- Marie -- français -- Jean -- DEBUTANT -- 10
// *  2 -- Marie -- allemand -- Claude -- AVANCE -- 41
// */
//public class NiveauApprenant{
//	
//    private String id;
//
//    private Langue langue;
//
//    /**
//     * Le niveau de l'élève dans la langue étudiée.
//     * Les niveaux posibles sont : DEBUTANT, INTERMEDIAIRE, AVANCE
//     */
//    private BaremeNiveau niveau;
//
//    /**
//     * Le score de l'élève dans cette langue.
//     */
//    private float score;
//
//    /**
//     * Constructeur de la classe NiveauxEleves. Initialise les attributs eleve, professeur, langue et niveau en fonction de l'objet Eleve et Professeur passés en paramètres. Le score est initialisé à 0 et le niveau à DEBUTANT ({@link Eleve#ajouterProf(Professeur prof) ajouterProf})
//     *
//     * @param eleve l'objet Eleve pour lequel on crée un niveau dans une langue
//     */
//    public NiveauApprenant(Apprenant apprenant) {
//        this.id = apprenant.getId();
//        this.niveau = apprenant.getBaremeNiveau();
//        this.langue = apprenant.getLangue();
//        this.setScore(0);
//    }
//
//    /**
//     * Retourne le niveau de l'élève dans la langue enseignée par le professeur après l'avoir mis à jour.
//     *
//     * @return le niveau de l'élève
//     */
//    public BaremeNiveau getNiveau() {
//        updateNiveau();
//        return this.niveau;
//    }
//
//    /**
//     * Modifie le niveau de l'élève dans la langue enseignée par le professeur.
//     *
//     * @param newNiveau le nouveau niveau de l'élève
//     */
//    public void setNiveau(BaremeNiveau newNiveau) {
//        this.niveau = newNiveau;
//    }
//
//    /**
//     * Met à jour le niveau de l'élève en fonction de sa performance dans la langue.
//     */
//    public void updateNiveau() {
////        this.niveau = this.apprenant.getBaremeNiveau();
//    }
//
//    /**
//     * Retourne le pseudo de l'élève.
//     *
//     * @return le pseudo de l'élève
//     */
//    public String getIdApprenant() {
//        return id;
//    }
//
//    /**
//     * Modifie l'élève.
//     *
//     * @param eleve
//     */
//    public void setApprenant(Apprenant apprenant) {
//        this.id = apprenant.getId();
//    }
//
//    /**
//     * Retourne le score de l'élève dans cette langue.
//     *
//     * @return le score de l'élève
//     */
//    public float getScore(){
//        return this.score;
//    }
//
//    /**
//     * Retourne la langue de la ligne.
//     *
//     * @return la langue
//     */
//    public Langue getLangue() {
//        return langue;
//    }
//
//    /**
//     * Modifie la langue de la ligne unique
//     *
//     * @param langue la nouvelle langue
//     */
//    public void setLangue(Langue langue) {
//        this.langue = langue;
//    }
//
//    /**
//     * Modifie le score de l'élève dans la langue.
//     *
//     * @param score le nouveau score de l'élève
//     */
//    public void setScore(float score) {
//        this.score = score;
//    }
//
//    /**
//     * Cette méthode met à jour le niveau de l'utilisateur actif (un élève) dans une langue donnée, en fonction de son score dans cette langue.
//     * Si le score est inférieur à 20, le niveau est défini comme "débutant"
//     * Si le score est compris entre 20 et 40, le niveau est défini comme "intermédiaire".
//     * Si le score est compris entre 40 et 60, le niveau est défini comme "avancé".
//     * La régression d'un niveau supérieur à un niveau inférieur est possible.
//     * @param lang la langue pour laquelle mettre à jour le niveau de l'utilisateur actif
//     * @param niveauElevesDao l'objet Dao pour accéder à la table des enregistrements de niveaux des élèves dans la base de données
//     * @see NiveauxEleves#updateScore(Boolean, Dao)
//     * @throws SQLException
//     */
//    public void updateNiveau(Langue lang) {
//        if (this.getScore() < 20) {
//            this.setNiveau(BaremeNiveau.DEBUTANT);
//        } else if (this.getScore() >= 20 && this.getScore() < 40) {
//            this.setNiveau(BaremeNiveau.INTERMEDIAIRE);
//        } else if (this.getScore() >= 40 && this.getScore() < 60) {
//            this.setNiveau(BaremeNiveau.AVANCE);
//        }
//    }
//    
//    public void updateNiveauApprenant(Apprenant apprenant) {
//    	try {
//    		File inputFile = new File("../DATA/Apprenants.txt");
//    		File tempFile = new File("../DATA/tempFile.txt");
//    		
//    		try (Scanner scanner = new Scanner(inputFile);
//    				FileWriter writer = new FileWriter(tempFile)) {
//    			
//    			while (scanner.hasNextLine()) {
//    				String line = scanner.nextLine();
//    				String[] parts = line.split(";");
//    				if (parts.length == 7) {
//    					String id = parts[0];
//    					if (!id.equals(apprenant.getId())) {
//    						writer.write(line + System.lineSeparator());
//    					} else {
//    						String mdp = parts[1];
//    						String nom = parts[2];
//    						String prenom = parts[3];
//    						String langue = parts[4];
//    						String niveau = apprenant.getBaremeNiveau().name();
//    						String score = parts[6];
//    						writer.write(id + ";" + mdp + ";" + nom + ";" + prenom + ";" + langue + ";" + niveau + ";" + score + System.lineSeparator());
//    					}
//    				}
//    			}
//    			scanner.close();
//    		}
//    		Files.move(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//    		
//    		JOptionPane.showMessageDialog(null, "Votre compte a bien été supprimé.");
//    	} catch (FileNotFoundException e) {
//    		System.out.println("Fichier introuvable: " + e.getMessage());
//    	} catch (IOException e) {
//    		System.out.println("Erreur lors de la manipulation du fichier: " + e.getMessage());
//    	}
//    }
//    
//
//    /**
//     *  Cette méthode permet de mettre à jour le score d'un élève pour un enregistrement de niveau donné.
//     *  Si l'élève a réussi l'exercice, son score est incrémenté de 1.
//     *  Si l'élève a échoué l'exercice, son score est décrémenté de 1.
//     *  La réussite de l'élève dépend de si son score obtenu est supérieur ou non à la note de passation (fixée par rapport au {@link Exercice#pourcentage} de réussite fixé par le professeur lors de la création de l'exercice.
//     *  @param eleveValide booléen indiquant si l'élève a réussi ou échoué l'exercice
//     *  @param niveauElevesDao Dao permettant la mise à jour de l'enregistrement de niveau dans la base de données
//     * @see Notation
//     * @see ReponseEleve#valide()
//     *  @throws SQLException
//     */
//    public void updateScore(Boolean eleveValide) {
//        // Mise à jour du score de l'enregistrement
//        if(eleveValide){
//            this.score++;
//        }
//        else {
//            this.score--;
//        }
//    }
//}
//
