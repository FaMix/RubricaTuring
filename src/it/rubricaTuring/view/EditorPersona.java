package it.rubricaTuring.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import it.rubricaTuring.model.Persona;

public class EditorPersona extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nome;
    private JTextField cognome;
    private JTextField indirizzo;
    private JTextField telefono;
    private JTextField eta;

    private RubricaUI parent;
    private Persona persona;

    public EditorPersona(RubricaUI parent, Persona persona) {
        this.parent = parent;
        this.persona = persona;
        this.initUI();
    }

    private void initUI() {
        setTitle("editor-persona");
        setSize(300, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        // Toolbar
        JToolBar bar = new JToolBar();
        
        ImageIcon originalSaveIcon = new ImageIcon(getClass().getResource("/icons/save.png"));
        Image scaledSaveImage = originalSaveIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton salva = new JButton("Salva", new ImageIcon(scaledSaveImage));
        
        ImageIcon originaCancelIcon = new ImageIcon(getClass().getResource("/icons/cancel.png"));
        Image scaledCancelImage = originaCancelIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton annulla = new JButton("Annulla", new ImageIcon(scaledCancelImage));
        
        bar.add(salva);
        bar.add(annulla);
        add(bar, BorderLayout.NORTH);

        // Qui il codice relativo al form
        JPanel form = new JPanel(new GridLayout(5,2,5,5));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Nome:")); 
        this.nome = new JTextField();
        form.add(this.nome);
        form.add(new JLabel("Cognome:"));
        this.cognome = new JTextField();
        form.add(this.cognome);
        form.add(new JLabel("Indirizzo:"));
        this.indirizzo = new JTextField();
        form.add(this.indirizzo);
        form.add(new JLabel("Telefono:"));
        this.telefono = new JTextField();
        form.add(this.telefono);
        form.add(new JLabel("Età:"));
        this.eta = new JTextField();
        form.add(this.eta);
        add(form, BorderLayout.CENTER);
        
        // Se stiamo modificando, precompila i campi
        if (this.persona != null) {
        	this.nome.setText(this.persona.getNome());
        	this.cognome.setText(this.persona.getCognome());
        	this.indirizzo.setText(this.persona.getIndirizzo());
        	this.telefono.setText(this.persona.getTelefono());
        	this.eta.setText(String.valueOf(this.persona.getEta()));
        }

        salva.addActionListener(e -> this.salva());
        annulla.addActionListener(e -> dispose());
    }

    private void salva() {
        // Validazione dei campis
        if(this.nome.getText().trim().isEmpty() || this.cognome.getText().trim().isEmpty() || this.eta.getText().trim().isEmpty()
        		|| this.indirizzo.getText().trim().isEmpty() || this.telefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ogni campo e' obbligatorio!");
            return;
        }
        try {
            int eta = Integer.parseInt(this.eta.getText().trim());
            if(this.persona == null) { // Nuova persona
                this.persona = new Persona(
                        this.nome.getText().trim(), 
                        this.cognome.getText().trim(),
                        this.indirizzo.getText().trim(), 
                        this.telefono.getText().trim(), 
                        eta
                );
                // Aggiungi alla lista
                this.parent.getListaPersone().add(this.persona);
            } else { // Modifica di una persona esistente
            	this.persona.setNome(this.nome.getText().trim());
                this.persona.setCognome(this.cognome.getText().trim());
                this.persona.setIndirizzo(this.indirizzo.getText().trim());
                this.persona.setTelefono(this.telefono.getText().trim());
                this.persona.setEta(eta);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Inserire un'età valida");
            return;
        }
        this.parent.aggiornaTabella();
        dispose();
    }
}
