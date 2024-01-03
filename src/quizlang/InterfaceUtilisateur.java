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
import java.util.Scanner;

public class InterfaceUtilisateur extends JFrame {

    private JTextField idField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;  // Nouvelle case à cocher
    private GestionnaireDonnees gestionnaire;

    public InterfaceUtilisateur(GestionnaireDonnees gestionnaire) {
        super("Page de Connexion à QuizLang");
        this.gestionnaire = gestionnaire;
//        super.setTitle("Page de connexion à QuizLang");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(500, 200);
        super.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));  // Ajout d'une ligne pour la case à cocher

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
        panelButton.setLayout(new GridLayout(1, 3));  // Ajout d'une ligne pour la case à cocher

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

    private boolean[] authentifierUtilisateur(String id, String motDePasse) {
        boolean[] boolLearner = new boolean[2];
        boolLearner[1] = false;
        try (Scanner fileScanner = new Scanner(new File("../DATA/Apprenants.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String idLearner = parts[0];
                    String mdpLearner = parts[1];
                    if (idLearner.equals(id) && mdpLearner.equals(motDePasse)) {
                        boolLearner[0] = true;
                        boolLearner[1] = true;
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
                String[] parts = line.split(",");
                if (parts.length == 5) {
                	String idTeacher = parts[0];
                    String mdpTeacher = parts[1];
                    if (idTeacher.equals(id) && mdpTeacher.equals(motDePasse)) {
                        boolLearner[0] = true;
                        boolLearner[1] = false;
                        return boolLearner;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier apprenants introuvable");
            e.printStackTrace();
        }
        boolLearner[0] = false;
        return boolLearner;
    }

    private void afficherMasquerMotDePasse() {
        // Affiche ou masque le mot de passe en fonction de l'état de la case à cocher
        if (showPasswordCheckBox.isSelected()) {
            passwordField.setEchoChar((char) 0);  // Afficher le mot de passe
        } else {
            passwordField.setEchoChar('*');  // Masquer le mot de passe
        }
    }

    private void afficherMenuPrincipalApprenant(String id) {
        JFrame menuPrincipalFrame = new JFrame("Menu Principal");
        menuPrincipalFrame.setSize(600, 400);
        menuPrincipalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuPrincipalFrame.setLocationRelativeTo(null);

        JPanel menuPrincipalPanel = new JPanel();
        menuPrincipalPanel.setLayout(new GridLayout(4, 1));

        JLabel titre = new JLabel("Bonjour, que voulez-vous faire ?");
        JButton boutonExercice = new JButton("Faire un exercice");
        JButton boutonResults = new JButton("Voir résultats");
        JButton boutonDeleteAccount = new JButton("Supprimer le compte");
        JButton boutonQuit = new JButton("Quitter");

        menuPrincipalPanel.add(titre);
        menuPrincipalPanel.add(boutonExercice);
        menuPrincipalPanel.add(boutonResults);
        menuPrincipalPanel.add(boutonDeleteAccount);
        menuPrincipalPanel.add(boutonQuit);

        menuPrincipalFrame.add(menuPrincipalPanel);
        menuPrincipalFrame.setVisible(true);

        boutonExercice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(menuPrincipalFrame, "Option 1 sélectionnée");
            }
        });
        boutonResults.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(menuPrincipalFrame, "Option 2 sélectionnée");
            }
        });
        boutonDeleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choix = JOptionPane.showConfirmDialog(menuPrincipalFrame, "Êtes-vous sûr de vouloir supprimer votre compte?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                if (choix == JOptionPane.YES_OPTION) {
                    // Supprimez le compte (vous devrez implémenter cette fonction)
                    supprimerCompteApprenant(id);
                    menuPrincipalFrame.dispose();
                }
            }
        });
        boutonQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void afficherMenuPrincipalProfesseur(String id) {
        JFrame menuPrincipalFrame = new JFrame("Menu Principal");
        menuPrincipalFrame.setSize(600, 400);
        menuPrincipalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuPrincipalFrame.setLocationRelativeTo(null);

        JPanel menuPrincipalPanel = new JPanel();
        menuPrincipalPanel.setLayout(new GridLayout(5, 1));

        JLabel titre = new JLabel("Bonjour, que voulez-vous faire ?");
        JButton boutonCreateExercice = new JButton("Créer un exercice");
        JButton boutonManageExercice = new JButton("Gérer les exercices");
        JButton boutonResults = new JButton("Voir les résultats des apprenants");
        JButton boutonQuit = new JButton("Quitter");
        
        JButton boutonDeleteAccount = new JButton("Supprimer le compte");

        menuPrincipalPanel.add(titre);
        menuPrincipalPanel.add(boutonCreateExercice);
        menuPrincipalPanel.add(boutonManageExercice);
        menuPrincipalPanel.add(boutonResults);
        menuPrincipalPanel.add(boutonDeleteAccount);
        menuPrincipalPanel.add(boutonQuit);

        menuPrincipalFrame.add(menuPrincipalPanel);
        menuPrincipalFrame.setVisible(true);

        boutonCreateExercice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(menuPrincipalFrame, "Option 1 sélectionnée");
            }
        });
        boutonResults.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(menuPrincipalFrame, "Option 2 sélectionnée");
            }
        });
        boutonDeleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choix = JOptionPane.showConfirmDialog(menuPrincipalFrame, "Êtes-vous sûr de vouloir supprimer votre compte?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
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
                System.exit(0);
            }
        });
    }
    
    private void supprimerCompteProfesseur(String id) {
        gestionnaire.deleteTeacher(id);
//        System.out.println("prof censé être supprimé");
        // Fermez la session et revenez à la page de connexion
        new InterfaceUtilisateur(gestionnaire);
    }
    
    private void supprimerCompteApprenant(String id) {
    	gestionnaire.deleteLearner(id);
//        System.out.println("prof censé être supprimé");
    	// Fermez la session et revenez à la page de connexion
    	new InterfaceUtilisateur(gestionnaire);
    }

    public void menuCreateApprenant() {
//		GestionnaireDonnees gestionnaire = new GestionnaireDonnees();

        JFrame menuFrame = new JFrame("Création d'un compte apprenant");
        menuFrame.setSize(500, 300);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(9,1));
        menuFrame.add(infoPanel, BorderLayout.NORTH);

        JLabel lblId, lblNom, lblPrenom, lblMdp, lblLangue, lblLevel;
        JTextField txtNom, txtPrenom, txtId;
//        JPasswordField txtMdp;
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
//        txtMdp = new JPasswordField();
        
        showPasswordCheckBox = new JCheckBox("Afficher le mot de passe");
        showPasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                afficherMasquerMotDePasse();
            }
        });
        
        comboLangue = new JComboBox<>();
        comboLangue.addItem("Anglais");
        comboLangue.addItem("Japonais");
        comboLevel = new JComboBox<>();
        comboLevel.addItem("Débutant");
        comboLevel.addItem("Intermédiaire");
        comboLevel.addItem("Avancé");

        btnRegister = new JButton("S'inscrire");

        infoPanel.add(lblId);
        infoPanel.add(txtId);
        infoPanel.add(lblMdp);
//        infoPanel.add(txtMdp);
        infoPanel.add(passwordField);
        infoPanel.add(new JLabel());
        infoPanel.add(showPasswordCheckBox);  // Ajout de la case à cocher
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
	//            	char[] mdp = txtMdp.getPassword();
	            	char[] mdp = passwordField.getPassword();
	            	String nom = txtNom.getText();
	            	String prenom = txtPrenom.getText();
	            	String langue = (String) comboLangue.getSelectedItem();
	            	String level = (String) comboLevel.getSelectedItem();
	            	
	            	String passwordString = new String(mdp);
	            	if (langue.equals("Japonais")) {
	            		langue = "JP";
	            	} else {
	            		langue = "EN";
	            	}
	            	
	            	if (level.equals("Débutant")) {
	            		level = "1";
	            	} else if (level.equals("Intermédiaire")) {
	            		level = "2";
	            	} else {
	            		level = "3";
	            	}
	            	String niveauLangue = langue + ":" + level;
	            	Apprenant nouvelApprenant = new Apprenant(id, passwordString, nom, prenom, niveauLangue);
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
    
    
    public void menuCreateProfesseur() {
//    	GestionnaireDonnees gestionnaire = new GestionnaireDonnees();
    	
    	JFrame menuFrame = new JFrame("Création d'un compte professeur");
    	menuFrame.setSize(500, 300);
    	menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	menuFrame.setLocationRelativeTo(null);
    	
    	JPanel infoPanel = new JPanel();
    	infoPanel.setLayout(new GridLayout(9,1));
    	menuFrame.add(infoPanel, BorderLayout.NORTH);
    	
    	JLabel lblId, lblNom, lblPrenom, lblMdp, lblLangue;
    	JTextField txtNom, txtPrenom, txtId;
//        JPasswordField txtMdp;
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
    	comboLangue.addItem("Anglais");
    	comboLangue.addItem("Japonais");
    	
    	btnRegister = new JButton("S'inscrire");
    	
    	infoPanel.add(lblId);
    	infoPanel.add(txtId);
    	infoPanel.add(lblMdp);
    	infoPanel.add(passwordField);
    	infoPanel.add(new JLabel());
    	infoPanel.add(showPasswordCheckBox);  // Ajout de la case à cocher
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
                    String id = txtId.getText();
                    char[] mdp = passwordField.getPassword();
                    String nom = txtNom.getText();
                    String prenom = txtPrenom.getText();
                    String langue = (String) comboLangue.getSelectedItem();

                    String passwordString = new String(mdp);

                    Professeur nouveauProfesseur = new Professeur(id, passwordString, nom, prenom, langue);
                    gestionnaire.addTeacher(nouveauProfesseur, true);
                    
                    JOptionPane.showMessageDialog(menuFrame, "Création du compte réussi !");

                    menuFrame.dispose();
                    new InterfaceUtilisateur(gestionnaire);
                } else {
                    JOptionPane.showMessageDialog(menuFrame, "Veuillez remplir tous les champs.");
                }
    			
    		}
    	});
    }
    
 // Méthode pour vérifier si les champs nécessaires à l'inscription sont remplis
    private boolean champsSontRemplis(JTextField txtId, JPasswordField passwordField, JTextField txtNom, JTextField txtPrenom) {
        return !txtId.getText().isEmpty() && passwordField.getPassword().length > 0 &&
                !txtNom.getText().isEmpty() && !txtPrenom.getText().isEmpty();
    }
}

