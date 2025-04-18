package it.rubricaTuring.view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import it.rubricaTuring.model.Persona;
import it.rubricaTuring.persistenza.GestioneDB;

public class RubricaUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<Persona> listaPersone;

	public RubricaUI(ArrayList<Persona> listaPersone) {
        this.listaPersone = listaPersone;
        this.initUI();
    }

    private void initUI() {
        setTitle("Rubrica Persone");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Qui il codice relativo alla toolbar
        JToolBar bar = new JToolBar();
        
        ImageIcon originalNewIcon = new ImageIcon(getClass().getResource("/icons/new.png"));
        Image scaledNewImage = originalNewIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton nuovo = new JButton("Nuovo", new ImageIcon(scaledNewImage));
        
        ImageIcon originalModifyIcon = new ImageIcon(getClass().getResource("/icons/edit.png"));
        Image scaledModifyImage = originalModifyIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton modifica = new JButton("Modifica", new ImageIcon(scaledModifyImage));
        
        ImageIcon originalDeleteIcon = new ImageIcon(getClass().getResource("/icons/delete.png"));
        Image scaledDeleteImage = originalDeleteIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton elimina = new JButton("Elimina", new ImageIcon(scaledDeleteImage));
        
        bar.add(nuovo);
        bar.add(modifica);
        bar.add(elimina);
        add(bar, BorderLayout.NORTH);

        // Creazione table model con colonne specificate
        String[] colonne = {"Nome", "Cognome", "Telefono"};
        this.tableModel = new DefaultTableModel(colonne, 0);
        this.table = new JTable(this.tableModel);
        this.aggiornaTabella(); // Carica i dati dalla lista

        add(new JScrollPane(this.table), BorderLayout.CENTER);

        // Aggiungi listeners
        nuovo.addActionListener(e -> this.apriEditor(null));
        modifica.addActionListener(e -> {
            int rigaSelezionata = this.table.getSelectedRow();
            if(rigaSelezionata == -1) {
                JOptionPane.showMessageDialog(this, "Selezionare prima una persona da modificare");
            } else {
                Persona p = this.getListaPersone().get(rigaSelezionata);
                this.apriEditor(p);
            }
        });
        elimina.addActionListener(e -> {
            int rigaSelezionata = this.table.getSelectedRow();
            if(rigaSelezionata == -1) {
                JOptionPane.showMessageDialog(this, "Selezionare prima una persona da eliminare");
            } else {
                int conferma = JOptionPane.showConfirmDialog(this, "Eliminare la persona " +
                        this.getListaPersone().get(rigaSelezionata) + "?", "Conferma", JOptionPane.YES_NO_OPTION);
                if(conferma == JOptionPane.YES_OPTION) {
                	GestioneDB.eliminaPersona(this.getListaPersone().get(rigaSelezionata));
                    this.getListaPersone().remove(rigaSelezionata);
                    this.aggiornaTabella();
                }
            }
        });
    }

    // Metodo per aggiornare il contenuto della JTable dalla lista
    public void aggiornaTabella() {
        this.tableModel.setRowCount(0);
        for (Persona p : this.getListaPersone()) {
            this.tableModel.addRow(new Object[]{p.getNome(), p.getCognome(), p.getTelefono()});
        }
    }

    // Metodo per aprire la finestra editor (passa null per creare una nuova persona)
    private void apriEditor(Persona p) {
        EditorPersona editor = new EditorPersona(this, p);
        editor.setVisible(true);
    }
    
    // Getter e setter
    public ArrayList<Persona> getListaPersone() {
		return this.listaPersone;
	}

	public void setListaPersone(ArrayList<Persona> listaPersone) {
		this.listaPersone = listaPersone;
	}
}
