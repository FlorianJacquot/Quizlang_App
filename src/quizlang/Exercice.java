//package quizlang;
//
//import java.util.List;
//
//public class Exercice {
//    private List<String> questions;
//    private List<String> reponses;
//    private String niveau;
//
//    public void generate() {
//        // Logique de génération du contenu de l'exercice
//    }
//
//    public void evaluate() {
//        // Logique d'évaluation des réponses soumises par l'élève
//    }
//}
//
//
//
//
//
package quizlang;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utilisateurs.Apprenant;

//
//import fake_quizlet.notation.BaremeNiveau;
//import fake_quizlet.reponse.ReponseEleve;
//import fake_quizlet.utilisateurs.Eleve;
//
/**
 * Classe abstraite représentant un exercice. 
 *
 * Cette classe contient plusieurs méthodes :
 * - afficheExercice() : permet d'afficher l'exercice à l'apprenant
 * - afficherCorrection() : permet d'afficher la correction
 */
public class Exercice {
	
    /** Langue de l'exercice */
    private Langue langue;

    /** Niveau de l'exercice :
     * - DEBUTANT
     * - INTERMEDIAIRE
     * - AVANCE
     * */
    private BaremeNiveau niveau;
    
    /**
     * ArrayList qui contient les objets {@link PhraseATrous} créés à partir de l'input donné par le professeur.
     */
    private ArrayList<PhraseATrous> listPhrases = new ArrayList<>();


    /**
     * Pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     */
    private Float pourcentageReussite;

    /**
     * Constructeur de la classe Exercice.
     *
     * @param langue langue de l'exercice
     * @param niveau niveau de l'exercice
     * @param pourcentage pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     * @param parseur parseur de phrases à trous
     * @param inputProf entrée du professeur contenant les phrases de l'exercice
     */
    public Exercice(Langue langue, BaremeNiveau niveau, Float pourcentage, ParseurPhraseATrous parseur, String inputProf) {
        this.langue = langue;
        this.niveau = niveau;
        this.pourcentageReussite = pourcentage;
        String afterPunct = "(?<=[?!.。])";
        String[] listPhrasesNotParsed = inputProf.split(afterPunct); //on split sur la ponctuation en la gardant
        for(String phraseNotParsed : listPhrasesNotParsed){ //pour chaque phrase non parsée dans la liste des phrases non parsées

            listPhrases.add(parseur.parse(phraseNotParsed)); //on parse avec le parseur de phrase à trous et on l'ajoute à la liste

        }
    }
    
    /**
     * Affiche un aperçu du texte de l'exercice à trous, en affichant seulement les 3 premières phrases.
     * Si le texte contient plus de 3 phrases, affiche "..." à la fin des 3 premières phrases.
     */
    public String previewText() {
        StringBuilder result = new StringBuilder();
        // Affiche le niveau et le résultat requis de l'exo
        result.append(niveau).append(" ").append(pourcentageReussite).append("\n");
        // Affiche seulement les 3 premières phrases du texte
        for (int i = 0; i < 3 && i < listPhrases.size(); i++) {
            result.append(listPhrases.get(i).getPhraseAvecTrous()).append("\n");
        }
        
        // Ajoute un message indiquant qu'il y a plus de phrases dans le texte
        if (listPhrases.size() > 3) {
            result.append("...\n");
        } else {
            result.append("\n"); // retour à la ligne pour que ça fasse plus clean
        }

        return result.toString();
    }
    public String previewTextApprenant() {
    	StringBuilder result = new StringBuilder();
    	result.append(niveau.name()+System.lineSeparator());
    	// Affiche seulement les 3 premières phrases du texte
    	for (int i = 0; i < 3 && i < listPhrases.size(); i++) {
    		result.append("\n"+listPhrases.get(i).getPhraseAvecTrous()).append("\n");
    	}
    	
    	// Ajoute un message indiquant qu'il y a plus de phrases dans le texte
    	if (listPhrases.size() > 3) {
    		result.append("...\n");
    	} else {
    		result.append("\n"); // retour à la ligne pour que ça fasse plus clean
    	}
    	
    	return result.toString();
    }
//    public void previewText() {
//        // Affiche seulement les 3 premières phrases du texte
//        for (int i = 0; i < 3 && i < listPhrases.size(); i++) {
//            System.out.println(listPhrases.get(i).getPhraseAvecTrous());
//        }
//        // Affiche un message indiquant qu'il y a plus de phrases dans le texte
//        if (listPhrases.size() > 3) {
//            System.out.println("...");
//        }
//        else {
//            System.out.println(""); //retour à la ligne pour que ça fasse plus clean
//        }
//    }

    /**
     * Implémentation de la méthode abstraite afficheExercice().
     * Elle affiche :
     *
     * - la liste des mots à placer arrangés de manière aléatoire
     * - le texte avec des "___" à la place des mots à placer
     */
//    public void afficheExercice() {
//
//        //on récupère tous les mots à placer de chaque phrase de l'exercice
//        ArrayList<String> allMotsAPlacer = new ArrayList<>();
//
//        for (PhraseATrous phrase : listPhrases) {
//            allMotsAPlacer.addAll(phrase.getMotsAPlacer());
//        }
//
//        // on randomise la liste des des mots à placer
//        Collections.shuffle(allMotsAPlacer);
//
//        // on affiche la liste des mots à placer
//        System.out.println("Les mots à placer sont : " + String.join(", ", allMotsAPlacer) + "\n"); //on affiche la liste de tous les mots à placer
//
//        // on affiche la phrase avec les trousS.
//        for (PhraseATrous phrase : listPhrases) {
//            System.out.println(phrase.getPhraseAvecTrous());
//        }
//    }
//    public void afficheExerciceSwing() {
//        // Créer la fenêtre Swing
//        JFrame frame = new JFrame("Exercice");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(600, 400); 
//
//        // Créer un panneau pour afficher l'exercice
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//
//        // Créer un composant Swing pour afficher la liste des mots à placer
//        ArrayList<String> allMotsAPlacer = new ArrayList<>();
//        for (PhraseATrous phrase : listPhrases) {
//            allMotsAPlacer.addAll(phrase.getMotsAPlacer());
//        }
//        Collections.shuffle(allMotsAPlacer);
//        String motsAPlacerText = "Les mots à placer sont : " + String.join(", ", allMotsAPlacer) + "\n";
//        JLabel motsAPlacerLabel = new JLabel(motsAPlacerText);
//        Font font = motsAPlacerLabel.getFont();
//        motsAPlacerLabel.setFont(new Font(font.getName(), Font.BOLD, 25));
//        panel.add(motsAPlacerLabel);
//
//        // Créer des composants Swing pour afficher chaque phrase avec les trous
//        for (PhraseATrous phrase : listPhrases) {
//            String phraseText = phrase.getPhraseAvecTrous();
//            JLabel phraseLabel = new JLabel(phraseText);
//            Font phraseFont = phraseLabel.getFont();
//            phraseLabel.setFont(new Font(phraseFont.getName(), Font.PLAIN, 25));
//            panel.add(phraseLabel);
//        }
//
//        // Ajouter le panneau à la fenêtre
//        frame.getContentPane().add(panel);
//
//        // Rendre la fenêtre visible
//        frame.setVisible(true);
//    }
    
    
    
//    public void afficheExerciceSwing() {
//        // Créer la fenêtre Swing
//        JFrame frame = new JFrame("Exercice");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600); // Ajustez la taille selon vos besoins
//
//        // Créer un panneau pour afficher l'exercice
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setBackground(Color.WHITE); // Couleur de fond du panneau
//
//        // Créer un composant Swing pour afficher la liste des mots à placer
//        ArrayList<String> allMotsAPlacer = new ArrayList<>();
//        for (PhraseATrous phrase : listPhrases) {
//            allMotsAPlacer.addAll(phrase.getMotsAPlacer());
//        }
//        Collections.shuffle(allMotsAPlacer);
//        String motsAPlacerText = "Les mots à placer sont : " + String.join(", ", allMotsAPlacer) + "\n";
//        JLabel motsAPlacerLabel = new JLabel(motsAPlacerText);
//        Font font = motsAPlacerLabel.getFont();
//        motsAPlacerLabel.setFont(new Font(font.getName(), Font.BOLD, 25));
//        panel.add(motsAPlacerLabel);
//
//        // Ajouter une marge après le label des mots à placer
//        panel.add(Box.createRigidArea(new Dimension(0, 10)));
//
//        // Créer des composants Swing pour afficher chaque phrase avec les trous
//        for (PhraseATrous phrase : listPhrases) {
//            String phraseText = phrase.getPhraseAvecTrous();
//            JLabel phraseLabel = new JLabel(phraseText);
//            Font phraseFont = phraseLabel.getFont();
//            phraseLabel.setFont(new Font(phraseFont.getName(), Font.PLAIN, 20)); // Réduire la taille de la police
//            panel.add(phraseLabel);
//
//            // Ajouter une marge après chaque label de phrase
//            panel.add(Box.createRigidArea(new Dimension(0, 5)));
//        }
//
//        // Ajouter un espace en bas du panneau
//        panel.add(Box.createVerticalGlue());
//
//        // Ajouter le panneau à la fenêtre
//        frame.getContentPane().add(panel);
//
//        // Centrer la fenêtre sur l'écran
//        frame.setLocationRelativeTo(null);
//
//        // Rendre la fenêtre visible
//        frame.setVisible(true);
//    }

    
    
    
    
    
    
    

    public String textExercice() {
    	StringBuilder result = new StringBuilder();
    	//on récupère tous les mots à placer de chaque phrase de l'exercice
    	ArrayList<String> allMotsAPlacer = new ArrayList<>();
    	
    	for (PhraseATrous phrase : listPhrases) {
    		allMotsAPlacer.addAll(phrase.getMotsAPlacer());
    	}
    	
    	// on randomise la liste des des mots à placer
    	Collections.shuffle(allMotsAPlacer);
    	
    	// on affiche la liste des mots à placer
    	result.append("\nLes mots à placer sont : " + String.join(", ", allMotsAPlacer) + "\n"); //on affiche la liste de tous les mots à placer
    	
    	// on affiche la phrase avec les trousS.
    	for (PhraseATrous phrase : listPhrases) {
    		result.append(phrase.getPhraseAvecTrous()+"\n");
    	}
    	return result.toString();
    }
    public String viewAvailableExercises() throws IOException {
    	ImportExercice ie = new ImportExercice();
    	StringBuilder result = new StringBuilder();
    	
        System.out.println("Exercices disponibles :");
        List<Exercice> listeExercices = ie.importDossier("../EXO");
        for (Exercice exercice : listeExercices) {
        	// n'affiche que les exercices associés à la langue du professeur
        	if (exercice.getLangue() == langue) {
        		String exoView = exercice.previewText();
        		result.append("- ").append(exoView).append("\n");
        	}
        }
        return result.toString();
    }

    /**
     * Getter permettant de récupérer la langue de l'exercice.
     *
     * @return langue de l'exercice
     */
    public Langue getLangue(){
        return this.langue;
    }

    /**
     * Getter permettant de récupérer le niveau de l'exercice :
     *
     * Liste des niveaux possibles :
     * - DEBUTANT
     * - INTERMEDIAIRE
     * - AVANCE
     *
     * @return niveau de l'exercice
     */
    public BaremeNiveau getNiveau() {
        return niveau;
    }

    /**
     * Getter permettant de récupérer le pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit
     * considéré comme réussi.
     *
     * @return pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     */
    public Float getPourcentage() {
        return pourcentageReussite;
    }

    /**
     * Setter permettant de modifier le pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
     * @param pourcentage le pourcentage de points qu'un élève doit obtenir en faisant l'exercice pour que l'exercice soit considéré comme réussi.
    */
    public void setPourcentage(Float pourcentage) {
        this.pourcentageReussite = pourcentage;
    }
    
    /**
     * Getter de l'attribut listPhrases. Il contient la liste de tous les objets {@link PhraseATrous} qui composent l'exercice
     * @return listPhrase
     */
    public ArrayList<PhraseATrous> getListPhrases(){
        return listPhrases;
    }

//    /**
//     * Affiche un aperçu de l'exercice. Elle est utilisée pour que les professeurs puissent voir les exercices déjà enregistrés et que les élèves puissent choisir l'exercice qu'ils veulent faire.
//     * Elle est à définir dans les classe filles de `Exercice`
//     */
//    public abstract void previewText();

    /**
     *  Cette méthode permet de construire la réponse d'un élève à un exercice.
     *  @param apprenant l'apprenant dont on souhaite construire la réponse
     *  @return La réponse de l'élève à l'exercice
     */
    public ReponseApprenant construireReponse(Apprenant apprenant) {
    	return new ReponseApprenant(this, apprenant);
    }

    /**
     * Affiche la correction de l'exercice, c'est-à-dire les vraies réponses qu'il fallait fournir.
     */
    public void afficherCorrection() {
        System.out.println("\n");
        for(PhraseATrous phrase: listPhrases){
            System.out.println(phrase.getPhraseCorrecte());
        }
        System.out.println("\n");
    }
    
    /**
     * Redéfinition de la méthode toString() de la classe Object.
     * @return (String) Une chaîne de caractères qui représente l'objet ExoATrous.
     */
    @Override
    public String toString() {
        return "Exo{" +
                "listPhrases=" + listPhrases +
                '}';
    }

}