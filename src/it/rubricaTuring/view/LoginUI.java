package it.rubricaTuring.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import it.rubricaTuring.model.Persona;
import it.rubricaTuring.model.Utente;

public class LoginUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField userField;
    private JPasswordField passField;
    private ArrayList<Utente> utenti;
    private ArrayList<Persona> listaPersone;

    public LoginUI(ArrayList<Utente> utenti, ArrayList<Persona> listaPersone) {
        this.utenti = utenti;
        this.listaPersone = listaPersone;
        this.initUI();
    }

    private void initUI() {
        setTitle("Login Rubrica");
        setSize(250, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Qui il codice relativo alla toolbar
        JToolBar bar = new JToolBar();
        
        ImageIcon originalLoginIcon = new ImageIcon(getClass().getResource("/icons/login.png"));
        Image scaledLoginImage = originalLoginIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton bottoneLogin = new JButton("Login", new ImageIcon(scaledLoginImage));

        ImageIcon originalExitIcon = new ImageIcon(getClass().getResource("/icons/exit.png"));
        Image scaledExitImage = originalExitIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton bottoneExit = new JButton("Esci", new ImageIcon(scaledExitImage));
        bar.add(bottoneLogin);
        bar.add(bottoneExit);
        bar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        add(bar, BorderLayout.NORTH);

        // Qui il codice relativo al form
        JPanel form = new JPanel(new GridLayout(2,2,5,5));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Utente:"));
        this.userField = new JTextField();
        form.add(this.userField);
        form.add(new JLabel("Password:"));
        this.passField = new JPasswordField();
        form.add(this.passField);
        add(form, BorderLayout.CENTER);
        

        bottoneLogin.addActionListener(e -> this.eseguiLogin());
        bottoneExit.addActionListener(e -> System.exit(0));
    }

    private void eseguiLogin() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());
        boolean autenticato = false;
        for (Utente u : utenti) {
            if (u.getUsername().equals(user) && u.getPassword().equals(pass)) {
                autenticato = true;
                break;
            }
        }
        if (autenticato) {
            // Chiudi login e apri finestra principale
            dispose();
            RubricaUI ui = new RubricaUI(this.listaPersone);
            ui.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Login errato. Riprova.",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            passField.setText("");
        }
    }

}
