package it.rubricaTuring.main;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import it.rubricaTuring.model.Persona;
import it.rubricaTuring.model.Utente;
import it.rubricaTuring.persistenza.GestioneFile;
import it.rubricaTuring.view.LoginUI;

public class Main {

	public static void main(String[] args) {
		
		// Carica la lista di persone dal file (se esiste)
		ArrayList<Persona> listaPersone = GestioneFile.caricaPersone();
		
		// Setta utenti
		ArrayList<Utente> listaUtenti = new ArrayList<>();
        listaUtenti.add(new Utente("admin", "admin"));
        listaUtenti.add(new Utente("user", "password"));

		// Avvia l'interfaccia grafica
		SwingUtilities.invokeLater(() -> {
			LoginUI ui = new LoginUI(listaUtenti, listaPersone);
			ui.setVisible(true);
		});

		// Hook per salvare automaticamente le modifiche al termine dell'applicazione
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			GestioneFile.salvaPersone(listaPersone);
		}));
	}

}
