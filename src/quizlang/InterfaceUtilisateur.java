package quizlang;

import javax.swing.*;

import utilisateurs.Apprenant;
import utilisateurs.Professeur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * La classe InterfaceUtilisateur est utilisée pour l'interface graphique que
 * verra l'utilisateur
 */
public class InterfaceUtilisateur extends JFrame {

	/**
	 * La zone texte où l'utilisateur donnera son ID
	 */
	private JTextField idField;

	/**
	 * La zone texte où l'utilisateur écrira son mot de passe
	 */
	private JPasswordField passwordField;

	/**
	 * Case à cocher pour que l'utilisateur puisse voir et vérifier son mot de passe
	 */
	private JCheckBox showPasswordCheckBox; // Nouvelle case à cocher

	private GestionnaireDonnees gestionnaire;

	private Professeur professeur;
	private Apprenant apprenant;
	private ParseurPhraseATrous parseur;

	/**
	 * Constructeur de la classe InterfaceUtilisateur
	 * 
	 * @param gestionnaire le gestionnaire des données utilisateur
	 */
	public InterfaceUtilisateur(GestionnaireDonnees gestionnaire) {
		super("Page de Connexion à QuizLang");
		this.gestionnaire = gestionnaire;
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(500, 200);
		super.setResizable(false);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2)); // Ajout d'une ligne pour la case à cocher

		JLabel idLabel = new JLabel("ID:");
		idField = new JTextField();

		JLabel passwordLabel = new JLabel("Mot de Passe:");
		passwordField = new JPasswordField();

		showPasswordCheckBox = new JCheckBox("Afficher le mot de passe");
		showPasswordCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				afficherMasquerMotDePasse();
			}
		});

		JButton loginButton = new JButton("Se Connecter");
		JButton createAccountLearnerButton = new JButton("Créer un compte apprenant");
		JButton createAccountTeacherButton = new JButton("Créer un compte professeur");

		panel.add(idLabel);
		panel.add(idField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(new JLabel()); // Espacement
		panel.add(showPasswordCheckBox);
		panel.add(new JLabel()); // Espacement
		panel.add(loginButton);

		JPanel panelButton = new JPanel();
		panelButton.setLayout(new GridLayout(1, 3)); // Ajout d'une ligne pour la case à cocher

		panelButton.add(createAccountLearnerButton);
		panelButton.add(createAccountTeacherButton);

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				authentifierUtilisateur(idField.getText());
			}
		});

		createAccountLearnerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				menuCreateApprenant();
			}
		});

		createAccountTeacherButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				menuCreateProfesseur();
			}
		});

		add(panel, BorderLayout.NORTH);
		add(panelButton, BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Méthode pour identifier l'utilisateur.
	 * 
	 * @param id l'ID fourni par l'utilisateur
	 */
	private void authentifierUtilisateur(String id) {
		String motDePasse = new String(passwordField.getPassword());

		// Appel de la méthode de vérification de l'authentification
		boolean[] authentificationReussie = authentifierUtilisateur(id, motDePasse);

		// Vérification du résultat de l'authentification
		if (authentificationReussie[0] && authentificationReussie[1]) {
			JOptionPane.showMessageDialog(this, "Connexion réussie !");
			// Fermer la fenêtre de connexion
			dispose();
			// Appeler la méthode pour afficher le menu principal pour pprenant
			afficherMenuPrincipalApprenant(id);
		} else if (authentificationReussie[0]) {
			JOptionPane.showMessageDialog(this, "Connexion réussie !");
			// Fermer la fenêtre de connexion
			dispose();
			// Appeler la méthode pour afficher le menu principal pour professeur
			afficherMenuPrincipalProfesseur(id);
		} else {
			JOptionPane.showMessageDialog(this, "Échec de la connexion. Vérifiez vos identifiants.");
		}
	}

	/**
	 * Méthode pour identifier l'utilisateur
	 * 
	 * @param id l'id fournit par l'utilisateur
	 * @param motDePasse le mot de passe fournit par l'utilisateur
	 * @return un tableau de deux booléens qui permettent de savoir si
	 *         l'id est trouvé et si il a été trouvé dans le fichier des apprenants
	 *         ou celui des professeurs.
	 *         On a quatre possibilités : 
	 *         		- boolLearner[0] = true -> l'id correspond à un id enregistré dans un des fichiers utilisateur
	 *         		- boolLearner[0] = false -> l'id ne se trouve dans aucun fichier utilisateur
	 *         		- boolLearner[1] = true -> l'id a été trouvé dans le ficher apprenant
	 *         		- boolLearner[1] = false -> l'id n'a pas été trouvé dans le fichier apprenant
	 */
	private boolean[] authentifierUtilisateur(String id, String motDePasse) {
		boolean[] boolLearner = new boolean[2];
		boolLearner[1] = false;
		try (Scanner fileScanner = new Scanner(new File("../DATA/Apprenants.txt"))) {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String[] parts = line.split(";");
				if (parts.length == 7) {
					String idLearner = parts[0];
					String mdpLearner = parts[1];
					if (idLearner.equals(id) && mdpLearner.equals(motDePasse)) {
						boolLearner[0] = true;
						boolLearner[1] = true;

						Langue langueA = Langue.fromString(parts[4]);
						BaremeNiveau niveauA = BaremeNiveau.fromString(parts[5]);
						int scoreA = Integer.parseInt(parts[6]);
						apprenant = new Apprenant(idLearner, mdpLearner, parts[2], parts[3], langueA, niveauA, scoreA);
						return boolLearner;
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Fichier apprenants introuvable");
			e.printStackTrace();
		}

		try (Scanner fileScanner = new Scanner(new File("../DATA/Professeurs.txt"))) {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String[] parts = line.split(";");
				if (parts.length == 5) {
					String idTeacher = parts[0];
					String mdpTeacher = parts[1];
					if (idTeacher.equals(id) && mdpTeacher.equals(motDePasse)) {
						boolLearner[0] = true;
						boolLearner[1] = false;
						professeur = new Professeur(idTeacher, mdpTeacher, parts[2], parts[3],
								Langue.fromString(parts[4]));
						return boolLearner;
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Fichier professeurs introuvable");
			e.printStackTrace();
		}
		boolLearner[0] = false;
		return boolLearner;
	}

	/**
	 * Méthode pour afficher ou masquer le mot de passe en fonction de si la case
	 * correspondante est cochée ou non
	 */
	private void afficherMasquerMotDePasse() {
		// Affiche ou masque le mot de passe en fonction de l'état de la case à cocher
		if (showPasswordCheckBox.isSelected()) {
			passwordField.setEchoChar((char) 0); // Afficher le mot de passe
		} else {
			passwordField.setEchoChar('*'); // Masquer le mot de passe
		}
	}

	/**
	 * Méthode pour afficher le menu principal des apprenants si l'utilisateur
	 * connecté est un apprenant.
	 * 
	 * @param id l'ID de l'apprenant
	 */
	private void afficherMenuPrincipalApprenant(String id) {
		JFrame menuPrincipalFrame = new JFrame("Menu Principal");
		menuPrincipalFrame.setSize(600, 400);
		menuPrincipalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuPrincipalFrame.setLocationRelativeTo(null);

		JPanel menuPrincipalPanel = new JPanel();
		menuPrincipalPanel.setLayout(new GridLayout(4, 1));

		JLabel titre = new JLabel("Bonjour, que voulez-vous faire ?");
		JButton boutonExercice = new JButton("Faire un exercice");
		JButton boutonDeleteAccount = new JButton("Supprimer le compte");
		JButton boutonQuit = new JButton("Quitter");

		menuPrincipalPanel.add(titre);
		menuPrincipalPanel.add(boutonExercice);
		menuPrincipalPanel.add(boutonDeleteAccount);
		menuPrincipalPanel.add(boutonQuit);

		menuPrincipalFrame.add(menuPrincipalPanel);
		menuPrincipalFrame.setVisible(true);

		boutonExercice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					afficherExercices();

				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}

			private void afficherExercices() throws IOException {
				ArrayList<Exercice> exercicesAccessibles = apprenant.getExercicesAccessibles();

				JFrame menuExerciceFrame = new JFrame("Veuillez choisir un exercice :");
				menuExerciceFrame.setSize(600, 400);
				menuExerciceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				menuExerciceFrame.setLocationRelativeTo(null);

				JPanel menuExercicePanel = new JPanel();
				menuExercicePanel.setLayout(new GridLayout(0, 1));

				ArrayList<JButton> buttonsExo = new ArrayList<JButton>();

				for (Exercice exoA : exercicesAccessibles) {
					JButton boutonExercice = new JButton(
							"<html>" + exoA.previewTextApprenant().replaceAll("\n", "<br>") + "</html>");

					menuExercicePanel.add(boutonExercice);
					buttonsExo.add(boutonExercice);
				}
				JButton boutonQuit = new JButton("Quitter");
				menuExercicePanel.add(boutonQuit);

				menuExerciceFrame.add(menuExercicePanel);
				menuExerciceFrame.setVisible(true);
				int i = 0;
				for (JButton button : buttonsExo) {
					button.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							menuExerciceFrame.dispose();
							Exercice exerciceChoisi = exercicesAccessibles.get(buttonsExo.indexOf(button));

							// construction de la réponse de l'exercice
							ReponseApprenant reponseApprenant = exerciceChoisi.construireReponse((Apprenant) apprenant);
							afficherReponseApprenant(reponseApprenant);
						}
					});
					i++;
				}

				boutonQuit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						menuExerciceFrame.dispose();
					}
				});
			}
		});

		boutonDeleteAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choix = JOptionPane.showConfirmDialog(menuPrincipalFrame,
						"Êtes-vous sûr de vouloir supprimer votre compte?", "Confirmation de suppression",
						JOptionPane.YES_NO_OPTION);
				if (choix == JOptionPane.YES_OPTION) {
					supprimerCompteApprenant(id);
					menuPrincipalFrame.dispose();
				}
			}
		});
		
		boutonQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuPrincipalFrame.dispose();
				new InterfaceUtilisateur(gestionnaire);
			}
		});
	}

	/**
	 * Méthode pour afficher le menu principal des professeurs si l'utilisateur
	 * connecté est un professeur.
	 * 
	 * @param id l'ID du professeur
	 */
	private void afficherMenuPrincipalProfesseur(String id) {
		JFrame menuPrincipalFrame = new JFrame("Menu Principal");
		menuPrincipalFrame.setSize(600, 400);
		menuPrincipalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuPrincipalFrame.setLocationRelativeTo(null);

		JPanel menuPrincipalPanel = new JPanel();
		menuPrincipalPanel.setLayout(new GridLayout(5, 1));

		JLabel titre = new JLabel("Bonjour, que voulez-vous faire ?");
		JButton boutonCreateExercice = new JButton("Créer un exercice");
		JButton boutonManageExercice = new JButton("Afficher les exercices");
		JButton boutonQuit = new JButton("Quitter");

		JButton boutonDeleteAccount = new JButton("Supprimer le compte");

		menuPrincipalPanel.add(titre);
		menuPrincipalPanel.add(boutonCreateExercice);
		menuPrincipalPanel.add(boutonManageExercice);
		menuPrincipalPanel.add(boutonDeleteAccount);
		menuPrincipalPanel.add(boutonQuit);

		menuPrincipalFrame.add(menuPrincipalPanel);
		menuPrincipalFrame.setVisible(true);

		boutonCreateExercice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					professeur.createExercise();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		
		boutonManageExercice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String exoView = professeur.viewAvailableExercises();
//					professeur.viewAvailableExercises();
					JOptionPane.showMessageDialog(menuPrincipalFrame, exoView, "Les exercices disponibles :",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		boutonDeleteAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choix = JOptionPane.showConfirmDialog(menuPrincipalFrame,
						"Êtes-vous sûr de vouloir supprimer votre compte?", "Confirmation de suppression",
						JOptionPane.YES_NO_OPTION);
				if (choix == JOptionPane.YES_OPTION) {
					// Supprimez le compte (vous devrez implémenter cette fonction)
					supprimerCompteProfesseur(id);
					menuPrincipalFrame.dispose();
				}
			}
		});
		
		boutonQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuPrincipalFrame.dispose();
				new InterfaceUtilisateur(gestionnaire);
			}
		});
	}

	/**
	 * Méthode pour supprimer le compte professeur si ce dernier clique sur le bouton
	 * correspondant.
	 * 
	 * @param id l'ID du professeur
	 */
	private void supprimerCompteProfesseur(String id) {
		gestionnaire.deleteTeacher(id);
		new InterfaceUtilisateur(gestionnaire);
	}

	/**
	 * Méthode pour supprimer le compte apprenant si ce dernier clique sur le bouton
	 * correspondant.
	 * 
	 * @param id l'ID de l'apprenant
	 */
	private void supprimerCompteApprenant(String id) {
		gestionnaire.deleteLearner(id);
		new InterfaceUtilisateur(gestionnaire);
	}

	/**
	 * Méthode pour afficher le menu permettant de créer un compte apprenant.
	 * L'utilisateur peut saisir les informations nécessaires, telles que l'ID, le mot de passe, le nom,
	 * le prénom, la langue d'apprentissage et le niveau de l'apprenant. Une fois les informations valides, le compte est créé
	 * et l'utilisateur est redirigé vers l'interface principale.
	 */
	public void menuCreateApprenant() {
		JFrame menuFrame = new JFrame("Création d'un compte apprenant");
		menuFrame.setSize(500, 300);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setLocationRelativeTo(null);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(9, 1));
		menuFrame.add(infoPanel, BorderLayout.NORTH);

		JLabel lblId, lblNom, lblPrenom, lblMdp, lblLangue, lblLevel;
		JTextField txtNom, txtPrenom, txtId;
		passwordField = new JPasswordField();
		JComboBox<String> comboLangue, comboLevel;
		JButton btnRegister;

		lblId = new JLabel("ID");
		lblMdp = new JLabel("Mot de passe");
		lblNom = new JLabel("Nom");
		lblPrenom = new JLabel("Prenom");
		lblLangue = new JLabel("Langue");
		lblLevel = new JLabel("Niveau");

		txtId = new JTextField();
		txtNom = new JTextField();
		txtPrenom = new JTextField();

		showPasswordCheckBox = new JCheckBox("Afficher le mot de passe");
		showPasswordCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				afficherMasquerMotDePasse();
			}
		});

		comboLangue = new JComboBox<>();
		comboLangue.addItem("Français");
		comboLangue.addItem("Japonais");
		comboLevel = new JComboBox<>();
		comboLevel.addItem("DEBUTANT");
		comboLevel.addItem("INTERMEDIAIRE");
		comboLevel.addItem("AVANCE");

		btnRegister = new JButton("S'inscrire");

		infoPanel.add(lblId);
		infoPanel.add(txtId);
		infoPanel.add(lblMdp);
		infoPanel.add(passwordField);
		infoPanel.add(new JLabel());
		infoPanel.add(showPasswordCheckBox); // Ajout de la case à cocher
		infoPanel.add(lblNom);
		infoPanel.add(txtNom);
		infoPanel.add(lblPrenom);
		infoPanel.add(txtPrenom);
		infoPanel.add(lblLangue);
		infoPanel.add(comboLangue);
		infoPanel.add(lblLevel);
		infoPanel.add(comboLevel);

		menuFrame.add(btnRegister, BorderLayout.CENTER);
		menuFrame.setVisible(true);

		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (champsSontRemplis(txtId, passwordField, txtNom, txtPrenom)) {
					String id = txtId.getText();
					char[] mdp = passwordField.getPassword();
					String nom = txtNom.getText();
					String prenom = txtPrenom.getText();
					String langue = (String) comboLangue.getSelectedItem();
					String level = (String) comboLevel.getSelectedItem();

					String passwordString = new String(mdp);

					Langue langueA = Langue.fromString(langue);
					BaremeNiveau niveauA = BaremeNiveau.fromString(level);
					int scoreA = 0;
					if (niveauA == BaremeNiveau.INTERMEDIAIRE) {
						scoreA = 5;
					} else if (niveauA == BaremeNiveau.AVANCE) {
						scoreA = 10;
					}
					Apprenant nouvelApprenant = new Apprenant(id, passwordString, nom, prenom, langueA, niveauA,
							scoreA);
					gestionnaire.addLearner(nouvelApprenant, true);

					JOptionPane.showMessageDialog(menuFrame, "Création du compte réussi !");

					menuFrame.dispose();
					new InterfaceUtilisateur(gestionnaire);
				} else {
					JOptionPane.showMessageDialog(menuFrame, "Veuillez remplir tous les champs.");
				}
			}
		});
	}

	/**
	 * Méthode pour afficher le menu permettant de créer un compte professeur.
	 * L'utilisateur peut saisir les informations nécessaires, telles que l'ID, le mot de passe, le nom,
	 * le prénom et la langue du professeur. Une fois les informations valides, le compte est créé
	 * et l'utilisateur est redirigé vers l'interface principale.
	 */
	public void menuCreateProfesseur() {
		JFrame menuFrame = new JFrame("Création d'un compte professeur");
		menuFrame.setSize(500, 300);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setLocationRelativeTo(null);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(9, 1));
		menuFrame.add(infoPanel, BorderLayout.NORTH);

		JLabel lblId, lblNom, lblPrenom, lblMdp, lblLangue;
		JTextField txtNom, txtPrenom, txtId;
		passwordField = new JPasswordField();
		JComboBox<String> comboLangue;
		JButton btnRegister;

		lblId = new JLabel("ID");
		lblMdp = new JLabel("Mot de passe");
		lblNom = new JLabel("Nom");
		lblPrenom = new JLabel("Prenom");
		lblLangue = new JLabel("Langue");

		txtId = new JTextField();
		txtNom = new JTextField();
		txtPrenom = new JTextField();

		showPasswordCheckBox = new JCheckBox("Afficher le mot de passe");
		showPasswordCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				afficherMasquerMotDePasse();
			}
		});

		comboLangue = new JComboBox<>();
		comboLangue.addItem("Français");
		comboLangue.addItem("Japonais");

		btnRegister = new JButton("S'inscrire");

		infoPanel.add(lblId);
		infoPanel.add(txtId);
		infoPanel.add(lblMdp);
		infoPanel.add(passwordField);
		infoPanel.add(new JLabel());
		infoPanel.add(showPasswordCheckBox); // Ajout de la case à cocher
		infoPanel.add(lblNom);
		infoPanel.add(txtNom);
		infoPanel.add(lblPrenom);
		infoPanel.add(txtPrenom);
		infoPanel.add(lblLangue);
		infoPanel.add(comboLangue);

		menuFrame.add(btnRegister, BorderLayout.CENTER);
		menuFrame.setVisible(true);

		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Vérifier si les champs nécessaires sont remplis
				if (champsSontRemplis(txtId, passwordField, txtNom, txtPrenom)) {
					// Récupération des informations saisies
					String id = txtId.getText();
					char[] mdp = passwordField.getPassword();
					String nom = txtNom.getText();
					String prenom = txtPrenom.getText();
					String langue = (String) comboLangue.getSelectedItem();

					// Conversion du mot de passe en chaîne
					String passwordString = new String(mdp);

					// Conversion de la langue en enum Langue
					Langue langueProf = Langue.fromString(langue);

					// Création d'un nouveau professeur
					Professeur nouveauProfesseur = new Professeur(id, passwordString, nom, prenom, langueProf);
					
					// Ajout du professeur dans le gestionnaire de données
					gestionnaire.addTeacher(nouveauProfesseur, true);

					JOptionPane.showMessageDialog(menuFrame, "Création du compte réussi !");

					menuFrame.dispose();
					new InterfaceUtilisateur(gestionnaire);
				} else {
					// Affichage d'un message d'erreur si des champs sont manquants
					JOptionPane.showMessageDialog(menuFrame, "Veuillez remplir tous les champs.");
				}

			}
		});
	}

	/**
	 * Méthode pour vérifier si les champs nécessaires à l'inscription sont remplis.
	 *
	 * @param txtId            Champ de saisie de l'ID
	 * @param passwordField    Champ de saisie du mot de passe
	 * @param txtNom           Champ de saisie du nom
	 * @param txtPrenom        Champ de saisie du prénom
	 * @return un booléen 
	 * 		- true si tous les champs ont été remplis par l'utilisateur 
	 *      - false s'il manque quelque chose
	 */
	private boolean champsSontRemplis(JTextField txtId, JPasswordField passwordField, JTextField txtNom,
			JTextField txtPrenom) {
		return !txtId.getText().isEmpty() && passwordField.getPassword().length > 0 && !txtNom.getText().isEmpty()
				&& !txtPrenom.getText().isEmpty();
	}

	/**
	 * Affiche les réponses d'un apprenant à un exercice, permettant également la soumission des réponses
	 * pour correction. La méthode crée une fenêtre Swing avec une interface utilisateur contenant les réponses
	 * de l'apprenant, les phrases de l'exercice, des champs pour les mots à placer, un bouton de soumission,
	 * et une fenêtre de correction après soumission.
	 *
	 * @param reponse L'objet {@link ReponseApprenant} contenant les réponses de l'apprenant et les détails de l'exercice.
	 */
	public void afficherReponseApprenant(ReponseApprenant reponse) {
		JFrame frame = new JFrame("Réponses de l'apprenant");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Affiche les mots à placer pour l'exercice
		displayMotsAPlacer(reponse.getExercice(), panel);
		
		// Crée des panneaux pour chaque phrase de l'exercice avec des champs pour les mots à placer
		createPhrasePanels(reponse.getExercice(), panel);

		// Crée un bouton de soumission des réponses
		createSubmitButton(reponse, panel, frame);

		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(scrollPane);

		frame.setVisible(true);
	}

	/**
	 * Affiche les mots à placer pour un exercice dans un panneau Swing.
	 * Les mots à placer sont extraits des phrases de l'exercice, mélangés aléatoirement
	 * et affichés dans une JTextArea avec une police en gras.
	 *
	 * @param exercice L'objet Exercice contenant la liste des phrases avec des mots à placer.
	 * @param panel Le panneau Swing dans lequel afficher les mots à placer.
	 */
	private void displayMotsAPlacer(Exercice exercice, JPanel panel) {
		// Crée une liste de tous les mots à placer dans l'exercice
		ArrayList<String> allMotsAPlacer = new ArrayList<>();
		for (PhraseATrous phrase : exercice.getListPhrases()) {
			allMotsAPlacer.addAll(phrase.getMotsAPlacer());
		}
		
	    // Mélange aléatoirement la liste des mots à placer
		Collections.shuffle(allMotsAPlacer);

		// Crée une chaîne de texte affichant les mots à placer
		String motsAPlacerText = "Les mots à placer sont : " + String.join(", ", allMotsAPlacer);

		// Crée une zone de texte pour afficher les mots à placer
		JTextArea motsAPlacerTextArea = new JTextArea(motsAPlacerText);
		motsAPlacerTextArea.setFont(new Font("SansSerif", Font.BOLD, 14));
		motsAPlacerTextArea.setEditable(false);
		panel.add(motsAPlacerTextArea);
	}

	/**
	 * Crée des panneaux Swing pour chaque phrase d'un exercice et les ajoute à un panneau principal.
	 * Chaque panneau individuel est généré en utilisant la méthode {@link #createPhrasePanel(PhraseATrous, int)},
	 * puis ajouté au panneau principal fourni.
	 *
	 * @param exercice L'objet Exercice contenant la liste des phrases à traiter.
	 * @param panel Le panneau Swing principal dans lequel ajouter les panneaux de phrases.
	 */
	private void createPhrasePanels(Exercice exercice, JPanel panel) {
	    // Initialise un compteur pour numéroter les phrases
		int i = 1;
		for (PhraseATrous phrase : exercice.getListPhrases()) {
			JPanel phrasePanel = createPhrasePanel(phrase, i);
			i++;
			panel.add(phrasePanel);
		}
	}

	/**
	 * Crée un panneau Swing pour une phrase à trous donnée, avec une disposition verticale.
	 * Le panneau contient une JTextPane affichant le texte de la phrase formaté en HTML,
	 * suivi de panneaux individuels pour chaque mot à placer, générés à l'aide de la méthode
	 * {@link #createMotPanel(String, int)}.
	 *
	 * @param phrase L'objet PhraseATrous représentant la phrase pour laquelle créer le panneau.
	 * @param index L'index de la phrase, utilisé pour la numérotation dans l'affichage.
	 * @return Un JPanel représentant la phrase avec des emplacements pour les mots à placer.
	 */
	private JPanel createPhrasePanel(PhraseATrous phrase, int index) {
		JPanel phrasePanel = new JPanel();

		// Définit la disposition du panneau comme une disposition verticale
		phrasePanel.setLayout(new BoxLayout(phrasePanel, BoxLayout.Y_AXIS));

		// Crée une JTextPane pour afficher le texte de la phrase au format HTML
		JTextPane phraseTextPane = new JTextPane();
		phraseTextPane.setEditable(false);
		phraseTextPane.setContentType("text/html");
		phraseTextPane.setText("<html>Phrase " + index + " : " + phrase.getPhraseAvecTrous() + "</html>");

		phrasePanel.add(phraseTextPane);

	    // Initialise un compteur pour numéroter les mots
		int j = 1;
		for (String mot : phrase.getMotsAPlacer()) {
			JPanel motPanel = createMotPanel(mot, j);
			j++;
			phrasePanel.add(motPanel);
		}
		return phrasePanel;
	}

	/**
	 * Crée un panneau Swing pour afficher un mot à placer, avec une disposition horizontale.
	 * Le panneau contient un JLabel pour étiqueter le champ de texte, et un JTextField pour
	 * permettre à l'utilisateur de saisir le mot.
	 *
	 * @param mot Le mot à placer.
	 * @param index L'index du mot, utilisé pour la numérotation dans l'affichage.
	 * @return Un JPanel représentant un emplacement pour un mot à placer avec un champ de texte.
	 */
	private JPanel createMotPanel(String mot, int index) {
		JPanel motPanel = new JPanel();
		
		// Définit la disposition du panneau comme une disposition horizontale
		motPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));

		JLabel motLabel = new JLabel("Mot manquant " + index + ": ");
		motLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		JTextField motTextField = new JTextField(15);
		motTextField.setFont(new Font("SansSerif", Font.PLAIN, 14));

		motPanel.add(motLabel);
		motPanel.add(motTextField);
		return motPanel;
	}

	/**
	 * Crée un bouton Swing permettant à l'apprenant de soumettre ses réponses à l'exercice.
	 * Le bouton est configuré avec une police de caractères, un arrière-plan, et une couleur de texte spécifiques.
	 * Lorsque le bouton est cliqué, il déclenche une série d'actions, notamment la collecte des réponses de l'apprenant,
	 * la correction de l'exercice, le calcul de la note, l'affichage du résultat de l'exercice, la fermeture de la fenêtre
	 * principale, et l'affichage de la correction.
	 *
	 * @param reponse L'objet ReponseApprenant contenant les réponses et les informations sur l'exercice.
	 * @param panel Le JPanel sur lequel le bouton sera ajouté.
	 * @param frame La JFrame principale à fermer après la soumission des réponses.
	 */
	private void createSubmitButton(ReponseApprenant reponse, JPanel panel, JFrame frame) {
		JButton submitButton = new JButton("Soumettre les réponses");
		submitButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		submitButton.setBackground(new Color(50, 150, 50));
		submitButton.setForeground(Color.WHITE);
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Collecte les réponses de l'apprenant depuis le panneau
				reponse.collecterReponses(panel);
				
	            // Corrige les réponses collectées
				reponse.corrige();
				
	            // Calcule la note de l'apprenant
				reponse.calculNote();
				
	            // Affiche le résultat de l'exercice
				resultatExercice(reponse);
				
				frame.dispose();
				
	            // Affiche la correction de l'exercice
				afficherCorrection(reponse);
			}
		});
		panel.add(submitButton);
	}

	/**
	 * Affiche le résultat de l'exercice sous la forme d'une boîte de dialogue Swing. Le message de la boîte de dialogue
	 * dépend de la validité des réponses de l'apprenant. Si l'apprenant a réussi l'exercice, un message de félicitations
	 * est affiché, indiquant la note obtenue et le seuil de passage. Si l'apprenant n'a pas réussi, un message d'encouragement
	 * est affiché, indiquant la note obtenue et le seuil de passage.
	 * Après l'affichage du résultat, la méthode met à jour le score de l'apprenant, ajuste éventuellement son niveau, et
	 * actualise les données de l'apprenant dans le gestionnaire.
	 *
	 * @param reponse L'objet ReponseApprenant contenant les réponses et les informations sur l'exercice.
	 */
	private void resultatExercice(ReponseApprenant reponse) {
	    // Détermine si l'apprenant a réussi l'exercice
		Boolean eleveValide = reponse.valide();
		
		// Construit le message de la boîte de dialogue en fonction de la réussite de l'apprenant
		String message = (eleveValide)
				? "Félicitations, vous avez réussi l'exercice.\nVous deviez obtenir " + reponse.getSeuilPassation()
						+ " points pour valider et vous en avez obtenu " + reponse.getNoteDonnee() + "!"
				: "Dommage, vous n'avez pas réussi l'exercice.\nVous deviez obtenir " + reponse.getSeuilPassation()
						+ " points pour valider et vous en avez obtenu " + reponse.getNoteDonnee() + "...";

		// Afficher le message dans une boîte de dialogue
		JOptionPane.showMessageDialog(null, message, "Résultat de l'exercice", JOptionPane.INFORMATION_MESSAGE);
		
		// Met à jour le score de l'apprenant et ajuste son niveau si nécessaire
		reponse.updateScore(eleveValide);
		reponse.updateNiveau();
		
		// Actualise les données de l'apprenant dans le gestionnaire
		gestionnaire.updateApprenant(apprenant);
	}

	/**
	 * Affiche la correction de l'exercice sous la forme d'une fenêtre Swing. La fenêtre contient une liste des mots à
	 * placer et des phrases avec des trous. Chaque phrase est affichée avec des champs de texte pour les réponses fournies
	 * par l'apprenant et leur correction. Les champs de texte ont une couleur de fond différente en fonction de la
	 * correction : vert pour les réponses correctes, rouge pour les réponses incorrectes, et jaune pour les mots non
	 * répondues.
	 *
	 * @param reponse L'objet ReponseApprenant contenant les réponses et les informations sur l'exercice.
	 */
	private void afficherCorrection(ReponseApprenant reponse) {
		// Création de la fenêtre Swing
		JFrame frame = new JFrame("Correction de l'apprenant");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 400); 
		frame.setLocationRelativeTo(null);

		// Création d'un panneau pour afficher la correction
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Encadrez le panneau principal dans un JScrollPane
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		frame.getContentPane().add(scrollPane);

		// Affichage de l'exercice complet (liste des mots à placer et phrases avec
		// trous)
		displayMotsAPlacer(reponse.getExercice(), panel);

		// Création d'un panneau pour chaque phrase avec des champs de texte pour les
		// réponses et leur correction
		int i = 1;
		for (PhraseATrous phrase : reponse.getExercice().getListPhrases()) {
			JPanel phrasePanel = new JPanel();
			phrasePanel.setLayout(new BoxLayout(phrasePanel, BoxLayout.Y_AXIS));

			JTextPane phraseTextPane = new JTextPane();
			phraseTextPane.setEditable(false);
			phraseTextPane.setContentType("text/html"); // j'ai utiliser le format HTML pour permettre le retour automatique à la ligne
			phraseTextPane.setText("<html>Phrase " + i + " : " + phrase.getPhraseAvecTrous() + "</html>");

			phrasePanel.add(phraseTextPane);

			int j = 1;
			for (String mot : phrase.getMotsAPlacer()) {
				JPanel motPanel = new JPanel();
				motPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
				JLabel motLabel = new JLabel("Mot manquant " + j + ": ");

				JTextField motTextField = new JTextField(15);
				motTextField.setFont(new Font("SansSerif", Font.PLAIN, 14));
				motTextField.setEditable(false); // Le champ de texte est en lecture seule
				motTextField.setBackground(Color.YELLOW); // Couleur par défaut pour "non répondu"

				ValeurReponse correction = reponse.getReponsesCorrection().get(i - 1).get(j - 1);

				switch (correction) {
				case VRAI:
					motTextField.setBackground(Color.GREEN);
					break;
				case FAUX:
					motTextField.setBackground(Color.RED);
					break;
				}

				motTextField.setText(reponse.reponsesFournies.get(i - 1).get(j - 1));
				motPanel.add(motLabel);
				motPanel.add(motTextField);
				phrasePanel.add(motPanel);
				j++;
			}
			i++;

			panel.add(phrasePanel);
		}

		frame.setVisible(true);
	}

}
