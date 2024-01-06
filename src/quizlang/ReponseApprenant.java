package quizlang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import fake_quizlet.exercice.Exercice;
//import fake_quizlet.notation.ValeurReponse;
//import fake_quizlet.notation.Correction;
//import fake_quizlet.notation.Notation;
//import fake_quizlet.utilisateurs.Eleve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import utilisateurs.Apprenant;

/**
*  La classe ReponseEleve est une classe abstraite qui représente la réponse d'un élève à un exercice.
* Elle contient :
* - l'exercice que l'élève a fait,
* - l'élève qui a répondu,
* - les réponses fournies par l'élève,
* - la correction de ses réponses : chaque réponse fournie est attribuée une {@link ValeurReponse} : VRAI, FAUX ou NR (non répondu)
* - la note calculée
* - et le seuil de passation : la note qu'il faut atteindre pour que l'execice soit considéré comme réussi
* Cette classe implémente les interfaces {@link Correction} et {@link Notation}.
*
* La classe {@link ReponseEleveExoATrous} étend cette classe abstraite.
*
* @see Correction
* @see Notation
* @see ReponseEleveExoATrous
*/
public class ReponseApprenant {

  /**
   * L'exercice auquel appartiennent ces réponses
   */
  Exercice exercice;

  /**
   * L'élève qui a fait l'exercice.
   */
  Apprenant apprenant; // l'élève qui fait l'exercice

  /**
   * Les réponses que l'élève a founies
   */
  ArrayList<ArrayList<String>> reponsesFournies = new ArrayList<>();

  /**
   * La correction des réponses fournies par l'élève (la correction est faite avec l'interface {@link Correction}.
   * On attribue à chaque réponse fournie une {@link ValeurReponse}.
   */
  private ArrayList<ArrayList<ValeurReponse>> reponsesCorrection = new ArrayList<>();

  /**
   * La note calculée avec l'interface {@link Notation}.
   */
  protected Float noteDonnee = 0.0F;

  /**
   *  La note qu'il faut atteindre (fixée par le professeur) pour que l'exercice soit considéré comme réussi
   */
  protected Float seuilPassation = 0.0F;

  /**
   * Constructeur pour la classe ReponseEleve.
   * @param exercice l'exercice que l'élève a fait
   * @param eleve l'élève qui a répondu à l'exercice
   */
  public ReponseApprenant(Exercice exercice, Apprenant apprenant) {
      this.exercice = exercice;
      this.apprenant = apprenant;

      // Définition du seuil de passation pour l'exercice
      this.setSeuilPassation();

      // Initialisation des réponses fournies
      this.reponsesFournies = new ArrayList<>();

      // Création de la fenêtre Swing
      JFrame frame = new JFrame("Réponses de l'apprenant");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setSize(600, 400); // Ajustez la taille selon vos besoins
      frame.setLocationRelativeTo(null);

      // Création d'un panneau pour afficher l'exercice et les réponses
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

      // Affichage de l'exercice complet (liste des mots à placer et phrases avec trous)
      ArrayList<String> allMotsAPlacer = new ArrayList<>();
      for (PhraseATrous phrase : exercice.getListPhrases()) {
          allMotsAPlacer.addAll(phrase.getMotsAPlacer());
      }
      Collections.shuffle(allMotsAPlacer);
      String motsAPlacerText = "Les mots à placer sont : " + String.join(", ", allMotsAPlacer) + "\n";
      JLabel motsAPlacerLabel = new JLabel(motsAPlacerText);
      panel.add(motsAPlacerLabel);

      // Création d'un panneau pour chaque phrase avec des champs de texte pour les réponses
      int i = 1;
      for (PhraseATrous phrase : exercice.getListPhrases()) {
          JPanel phrasePanel = new JPanel();
          phrasePanel.setLayout(new BoxLayout(phrasePanel, BoxLayout.Y_AXIS));

          JLabel phraseLabel = new JLabel("Phrase " + i + " : " + phrase.getPhraseAvecTrous());
          phrasePanel.add(phraseLabel);

          int j = 1;
          for (String mot : phrase.getMotsAPlacer()) {
              JPanel motPanel = new JPanel();
              motPanel.setLayout(new FlowLayout());
              JLabel motLabel = new JLabel("Mot manquant " + j + ": ");
              JTextField motTextField = new JTextField(15);
              motPanel.add(motLabel);
              motPanel.add(motTextField);
              phrasePanel.add(motPanel);
              j++;
          }
          i++;
          panel.add(phrasePanel);
      }

      // Création d'un bouton pour soumettre les réponses
      JButton submitButton = new JButton("Soumettre les réponses");
      submitButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              // Collecter les réponses des champs de texte
              collecterReponses(panel);

              // On corrige les réponses de l'élève
              corrige();
              // On calcule la note de l'élève pour l'exercice
              calculNote();
              
              String message;
              if (valide()) {
                  message = "Félicitations, vous avez réussi l'exercice.\n"
                          + "Vous deviez obtenir " + getSeuilPassation() + " points pour valider et vous en avez obtenu " + getNoteDonnee() + "!";
              } else {
                  message = "Dommage, vous n'avez pas réussi l'exercice.\n"
                          + "Vous deviez obtenir " + getSeuilPassation() + " points pour valider et vous en avez obtenu " + getNoteDonnee() + "...";
              }

              // Afficher le message dans une boîte de dialogue
              JOptionPane.showMessageDialog(null, message, "Résultat de l'exercice", JOptionPane.INFORMATION_MESSAGE);

              // Afficher la correction
              frame.dispose();
              afficheCorrectionSwing();
          }
      });
      panel.add(submitButton);

      // Ajouter le panneau à la fenêtre
      frame.getContentPane().add(panel);

      // Rendre la fenêtre visible
      frame.setVisible(true);
  }

  private void collecterReponses(JPanel panel) {
//      int phraseIndex = 0;
      ArrayList<String> listeTampon = new ArrayList<>();
      for (Component component : panel.getComponents()) {
    	  listeTampon.clear(); // on vide la liste tampon
          if (component instanceof JPanel) {
              JPanel phrasePanel = (JPanel) component;
//              int motIndex = 0;
              for (Component innerComponent : phrasePanel.getComponents()) {
                  if (innerComponent instanceof JPanel) {
                      JPanel motPanel = (JPanel) innerComponent;
                      for (Component subComponent : motPanel.getComponents()) {
                          if (subComponent instanceof JTextField) {
                              JTextField motTextField = (JTextField) subComponent;
                              String reponse = motTextField.getText();
                              listeTampon.add(reponse);
//                              System.out.println(reponse);
//                              System.out.println(phraseIndex);
//                              System.out.println(reponsesFournies);
                              
//                              motIndex++;
                          }
                      }
                  }
              }
              this.reponsesFournies.add(new ArrayList<String>(listeTampon));
//              phraseIndex++;
          }
      }
  }
  
  public void afficheCorrectionSwing() {
      // Création de la fenêtre Swing
      JFrame frame = new JFrame("Correction de l'apprenant");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setSize(600, 400); // Ajustez la taille selon vos besoins
      frame.setLocationRelativeTo(null);

      // Création d'un panneau pour afficher la correction
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

      // Affichage de l'exercice complet (liste des mots à placer et phrases avec trous)
      ArrayList<String> allMotsAPlacer = new ArrayList<>();
      for (PhraseATrous phrase : exercice.getListPhrases()) {
          allMotsAPlacer.addAll(phrase.getMotsAPlacer());
      }
      Collections.shuffle(allMotsAPlacer);
      String motsAPlacerText = "Les mots à placer sont : " + String.join(", ", allMotsAPlacer) + "\n";
      JLabel motsAPlacerLabel = new JLabel(motsAPlacerText);
      panel.add(motsAPlacerLabel);

      // Création d'un panneau pour chaque phrase avec des champs de texte pour les réponses et leur correction
      int i = 1;
      for (PhraseATrous phrase : exercice.getListPhrases()) {
          JPanel phrasePanel = new JPanel();
          phrasePanel.setLayout(new BoxLayout(phrasePanel, BoxLayout.Y_AXIS));

          JLabel phraseLabel = new JLabel("Phrase " + i + " : " + phrase.getPhraseAvecTrous());
          phrasePanel.add(phraseLabel);

          int j = 1;
          for (String mot : phrase.getMotsAPlacer()) {
              JPanel motPanel = new JPanel();
              motPanel.setLayout(new FlowLayout());
              JLabel motLabel = new JLabel("Mot manquant " + j + ": ");
              JTextField motTextField = new JTextField(15);
              motTextField.setEditable(false); // Le champ de texte est en lecture seule
              motTextField.setBackground(Color.YELLOW); // Couleur par défaut pour "non répondu"
              ValeurReponse correction = this.getReponsesCorrection().get(i - 1).get(j - 1);

              switch (correction) {
                  case VRAI:
                      motTextField.setBackground(Color.GREEN);
                      break;
                  case FAUX:
                      motTextField.setBackground(Color.RED);
                      break;
              }

              motTextField.setText(reponsesFournies.get(i - 1).get(j - 1));
              motPanel.add(motLabel);
              motPanel.add(motTextField);
              phrasePanel.add(motPanel);
              j++;
          }
          i++;

          panel.add(phrasePanel);
      }

      // Ajouter le panneau à la fenêtre
      frame.getContentPane().add(panel);

      // Rendre la fenêtre visible
      frame.setVisible(true);
  }

  /**
   * Retourne l'exercice correspondant à cette réponse.
   * @return l'exercice correspondant à cette réponse.
   */
  public Exercice getExercice(){
      return this.exercice;
  }

  /**
   * Retourne l'élève ayant répondu à l'exercice.
   * @return l'élève ayant répondu à l'exercice.
   */
  public  Apprenant getApprenant(){
      return this.apprenant;
  }

  /**
   * Retourne les réponses fournies par l'élève.
   * @return les réponses fournies par l'élève.
   */
  public ArrayList<ArrayList<String>> getReponsesFournies(){
      return this.reponsesFournies;
  }

  /**
   * Retourne la correction des réponses fournies : chaque réponse est attribuée une {@link ValeurReponse}.
   * @return la correction des réponses fournies.
   */
  public ArrayList<ArrayList<ValeurReponse>> getReponsesCorrection() {
      return reponsesCorrection;
  }

  /**
   * Instancie la correction des réponses fournies.
   * @param reponsesCorrection la correction des réponses fournies.
   */
  public void setReponsesCorrection(ArrayList<ArrayList<ValeurReponse>> reponsesCorrection) {
      this.reponsesCorrection = reponsesCorrection;
  }

  /**
   * Retourne la note calculée pour cette réponse.
   * @return la note calculée pour cette réponse.
   */
  public Float getNoteDonnee() {
      return noteDonnee;
  }

  /**
   * Instancie la note donnée à cette réponse.
   * @param noteDonnee la nouvelle note donnée à cette réponse.
   */
  public void setNoteDonnee(Float noteDonnee) {
      this.noteDonnee = noteDonnee;
  }

  /**
   * Retourne vrai si l'élève valide (si la note calculée est supérieure ou égale au seuil de passation), faux sinon.
   * @return vrai si l'élève valide, faux sinon.
   */
  public Boolean valide() {
      return this.noteDonnee >= this.seuilPassation;
  }

  /**
   *  Méthode setSeuilPassation qui définit le seuil de réussite d'un exercice en fonction de son pourcentage de réussite et du nombre de réponses à fournir.
   *  Elle calcule le nombre total de réponses à fournir en comptant le nombre de mots à placer dans chaque phrase de l'exercice, puis définit le seuil de réussite en multipliant ce nombre par le pourcentage de réussite de l'exercice.
   */
  public void setSeuilPassation() {
      Exercice exo = (Exercice) this.exercice;
      Float totalReponsesAFournir = 0.0F;
      for (PhraseATrous phrase : exo.getListPhrases()) {
          totalReponsesAFournir += phrase.getMotsAPlacer().size();
      }
      this.seuilPassation = exo.getPourcentage() * totalReponsesAFournir;
  }

  /**
   * Getter qui permet de récupérer la note seuil que l'élève doit atteindre pour que l'exercice soit considéré comme réussi, validé.
   * @return la note seuil que l'élève doit atteindre pour que l'exercice soit considéré comme réussi, validé.
   */
  public Float getSeuilPassation(){
      return this.seuilPassation;
  }

//  /**
//   * Affiche les phrases de l'exercice avec les trous remplis par les réponses de l'élève.
//   * Si la réponse de l'élève est correcte, le mot est affiché en vert.
//   * Si la réponse est incorrecte, le mot est affiché en rouge.
//   * Si la réponse n'a pas été fournie, "___" est affiché en jaune.
//   *
//   * @param pattern le motif de l'expression régulière utilisé pour détecter les trous dans
//   *                les phrases
//   */
//  public void affichePhrasesRempliesAvecCouleurs(Pattern pattern) {
//      // Crée une liste qui contient l'attribut phraseAvecTrous de chaque objet Phrase de l'exo
//      ArrayList<String> allPhraseAvecTrous = new ArrayList<>();
//      for (PhraseATrous phrase : ((Exercice) exercice).getListPhrases()) {
//          allPhraseAvecTrous.add(phrase.getPhraseAvecTrous());
//      }
//
//      // Crée un StringBuffer qui contiendra les phrases dans lesquelles on a rempli les trous par les réponses de l'élève
//      StringBuffer phrasesRemplies = new StringBuffer();
//      for (int i = 0; i < allPhraseAvecTrous.size(); i++) {
//          // Récupère la phrase et les réponses de l'élève pour cette phrase
//          String phrase = allPhraseAvecTrous.get(i);
//          ArrayList<String> response = reponsesFournies.get(i);
//          Matcher matcher = pattern.matcher(phrase);
//
//          int j = 0;
//          while (matcher.find()) { // tant qu'on trouve des trous dans la phrase
//              ValeurReponse correction = this.getReponsesCorrection().get(i).get(j);
//              String replacement = null;
//              switch (correction) { // en fonction de la correction, détermine la couleur de la réponse de l'élève
//                  case VRAI:
////                      replacement = Ansi.ansi().bg(Ansi.Color.GREEN).a(response.get(j)).reset().toString();
//                      replacement = colorize(response.get(j), Color.GREEN);
//;
//                      break;
//                  case FAUX:
////                      replacement = Ansi.ansi().bg(Ansi.Color.RED).a(response.get(j)).reset().toString();
//                      replacement = colorize(response.get(j), Color.RED);
//
//                      break;
//                  case NR:
//                      replacement = colorize("___", Color.YELLOW);
//
//                      break;
//                  default:
//                      replacement = response.get(j);
//                      break;
//              }
//              // Remplace le trou par la réponse de l'élève dans le StringBuffer
//              matcher.appendReplacement(phrasesRemplies, replacement);
//              j++;
//          }
//          // Ajoute la fin de la phrase au StringBuffer
//          matcher.appendTail(phrasesRemplies);
//          phrasesRemplies.append("\n");
//      }
//
//      // Affiche le StringBuffer qui contient les phrases remplies
//      System.out.println(phrasesRemplies);
//  }
  private String colorize(String text, Color backgroundColor) {
	    return "\u001B[" + backgroundColor + "m" + text + "\u001B[0m";
  }
  
  /**
   * Méthode qui corrige les réponses de l'élève à l'exercice à trous.
   * Pour chaque phrase de l'exercice, la méthode compare chaque réponse de l'élève à la réponse attendue et attribue la valeur VRAI si la réponse de l'élève est correcte, FAUX si elle est incorrecte et NR si elle est vide.
   */
  public void corrige() {
      ArrayList<ValeurReponse> listTampon = new ArrayList<>();
      Exercice exo = (Exercice) exercice;
      ArrayList<PhraseATrous> listPhrases = exo.getListPhrases();

      for(int i=0; i < listPhrases.size(); i++) { //pour i allant de 0 au nombre de phrases dans l'exo

          listTampon.clear(); // on vide la liste tampon après chaque phrase
          PhraseATrous phrase = listPhrases.get(i);

          for(int j=0; j < phrase.getMotsAPlacer().size(); j++){ //pour j allant de 0 au nombre de mots à placer dans une phrase
              String reponseElevePourLaPhraseIEtLeMotJ = this.reponsesFournies.get(i).get(j);

              if(reponseElevePourLaPhraseIEtLeMotJ.equals(phrase.getMotsAPlacer().get(j))){ //si la réponse de l'élève correspond à la bonne réponse
                  listTampon.add(ValeurReponse.VRAI);
              }
              else if (reponseElevePourLaPhraseIEtLeMotJ.isEmpty()) { //si l'élève n'a pas répondu
                  listTampon.add(ValeurReponse.NR);
              }
              else{ //si la réponse de l'élève est fausse
                  listTampon.add(ValeurReponse.FAUX);
              }

          }

          this.getReponsesCorrection().add(new ArrayList<ValeurReponse>(listTampon));
      }
  }
  
  /**
   * Méthode qui définit le seuil de passation de l'exercice à trous,
   * en somme la note qu'il faut atteindre pour que l'exercice soit considéré comme réussi
   * Le seuil de passation est égal au pourcentage d'exigence de l'exercice (renseigné par le professeur lors de la création des exercices) multiplié par le nombre de réponses à fournir en tout, multiplié par le nombre de points attribués à une réponse correcte selon le {@link BaremeNiveau} de l'élève.
   */
  public void calculNote() {
      BaremeNiveau niveauExercice = this.getExercice().getNiveau();
      for (ArrayList<ValeurReponse> phraseCorrigee : this.getReponsesCorrection()) {
          for (ValeurReponse v : phraseCorrigee) {
              switch (v) {
                  case NR:
                      this.noteDonnee += niveauExercice.getNr();
                      break;
                  case FAUX:
                      this.noteDonnee += niveauExercice.getFaux();
                      break;
                  case VRAI:
                      this.noteDonnee += niveauExercice.getVrai();
                      break;
                  default:
                      System.out.println("Valeur incorrecte");
                      break;
              }
          }
      }
  }
}
