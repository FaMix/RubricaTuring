package it.rubricaTuring.persistenza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import it.rubricaTuring.model.Persona;

public class GestioneFileTest {
    private static final String DIR_PATH = "informazioni";

    @BeforeEach
    public void setup() {
        // Pulizia file prima di ogni test
    	this.cleanDir();
    }

    @AfterEach
    public void delete() {
        // Rimuove il file creato durante i test
    	this.cleanDir();
    }

    @Test
    public void testSalvaPersone() {
        ArrayList<Persona> lista = new ArrayList<>();
        lista.add(new Persona("Steve", "Jobs", "via Cupertino 13", "0612344", 56));
        lista.add(new Persona("Bill", "Gates", "via Redmond 10", "06688989", 60));

        GestioneFile.salvaPersone(lista);
        ArrayList<Persona> loaded = GestioneFile.caricaPersone();

        assertEquals(2, loaded.size(), "Il numero di persone caricate dovrebbe essere 2");
        assertEquals("Steve", loaded.get(0).getNome(), "Nome della prima persona");
        assertEquals("Gates", loaded.get(1).getCognome(), "Cognome della seconda persona");
    }

    @Test
    public void testModificaPersona() {
    	ArrayList<Persona> lista = new ArrayList<>();
        lista.add(new Persona("Steve", "Jobs", "via Cupertino 13", "0612344", 56));

        GestioneFile.salvaPersone(lista);

        // Modifica del telefono e salvataggio
        lista.get(0).setTelefono("999");
        GestioneFile.salvaPersone(lista);

        ArrayList<Persona> loaded = GestioneFile.caricaPersone();
        assertEquals(1, loaded.size(), "Dovrebbe esserci una sola persona");
        assertEquals("999", loaded.get(0).getTelefono(), "Telefono modificato");
    }

    @Test
    public void testCancellaPersona() {
        ArrayList<Persona> lista = new ArrayList<>();
        lista.add(new Persona("Steve", "Jobs", "via Cupertino 13", "0612344", 56));
        lista.add(new Persona("Bill", "Gates", "via Redmond 10", "06688989", 60));

        GestioneFile.salvaPersone(lista);

        // Rimozione del primo elemento
        lista.remove(0);
        GestioneFile.salvaPersone(lista);

        ArrayList<Persona> loaded = GestioneFile.caricaPersone();
        assertEquals(1, loaded.size(), "Dopo l'eliminazione deve restare una sola persona");
        assertEquals("Bill", loaded.get(0).getNome(), "Nome della persona rimanente");
    }
    
    public void cleanDir() {
    	File dir = new File(DIR_PATH);
        if (dir.exists()) {
            for (File f : dir.listFiles()) {
                f.delete();
            }
            dir.delete();
        }
    }
}