package it.rubricaTuring.persistenza;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import it.rubricaTuring.model.Persona;

public class GestioneFile {
	private static final String DIR_PATH = "informazioni";

	public static void salvaPersone(ArrayList<Persona> lista) {
		File dir = new File(DIR_PATH);
		if (!dir.exists()) {
			dir.mkdir();
		}

		// Pulisce la cartella
		for (File file : dir.listFiles()) {
			file.delete();
		}

		int count = 1;
		for (Persona p : lista) {
			String filename = String.format("Persona%d.txt", count++);
			File file = new File(dir, filename);
			try (PrintStream ps = new PrintStream(file)) {
				String linea = p.getNome() + ";" + p.getCognome() + ";" + p.getIndirizzo() + ";" + p.getTelefono() + ";" + p.getEta();
				ps.println(linea);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	public static ArrayList<Persona> caricaPersone() {
		ArrayList<Persona> lista = new ArrayList<>();
		File dir = new File(DIR_PATH);
		if (dir.exists()) {
			File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
			if (files == null) return lista;
			for (File file : files) {
				try (Scanner scanner = new Scanner(file)) {
					String riga = scanner.nextLine();
					String[] dati = riga.split(";");
					if(dati.length == 5) {
						Persona p = new Persona(
								dati[0], dati[1], dati[2], dati[3], Integer.parseInt(dati[4])
								);
						lista.add(p);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return lista;
		}
		return lista;
	}
}

