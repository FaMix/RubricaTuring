package it.rubricaTuring.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.SwingUtilities;

import it.rubricaTuring.model.Persona;
import it.rubricaTuring.model.Utente;
import it.rubricaTuring.persistenza.GestioneDB;
import it.rubricaTuring.view.LoginUI;

public class Main {

	public static void main(String[] args) {
		Properties props = new Properties();

        try (FileInputStream fs = new FileInputStream("config_database.properties")) {
            props.load(fs);
        } catch (IOException e) {
            System.err.println("Errore caricamento configurazione DB: " + e.getMessage());
            return;
        }
        
        GestioneDB.init(props);
        
        /* Gli utenti vengono hardcodati */
        ArrayList<Utente> listaUtenti = GestioneDB.caricaUtenti();
        
        if (listaUtenti.isEmpty()) {
        	Utente admin = new Utente("admin", "admin");
            GestioneDB.salvaUtente(admin);
            Utente user  = new Utente("user",  "password");
            GestioneDB.salvaUtente(user);
            // Ricarica la lista con gli ID popolati
            listaUtenti = GestioneDB.caricaUtenti();
        }
        
        final ArrayList<Utente> listaUtentiFinal = listaUtenti;
        ArrayList<Persona> listaPersone = GestioneDB.caricaPersone();
        
        
        /* Codice relativo a caricamento da file
         * 
		// Carica la lista di persone dal file (se esiste)
		ArrayList<Persona> listaPersone = GestioneFile.caricaPersone();
		
		// Setta utenti
		ArrayList<Utente> listaUtenti = new ArrayList<>();
        listaUtenti.add(new Utente("admin", "admin"));
        listaUtenti.add(new Utente("user", "password"));
        *
        */

		// Avvia l'interfaccia grafica
		SwingUtilities.invokeLater(() -> {
			LoginUI ui = new LoginUI(listaUtentiFinal, listaPersone);
			ui.setVisible(true);
		});

		// Hook per salvare automaticamente le modifiche al termine dell'applicazione
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			//GestioneFile.salvaPersone(listaPersone);
			GestioneDB.close();
		}));
	}

}
