package it.rubricaTuring.persistenza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import it.rubricaTuring.model.Persona;
import it.rubricaTuring.model.Utente;

public class GestioneDB {
    private static Connection conn;

    public static void init(Properties props) {
        String url = String.format("jdbc:mysql://%s:%s/%s",
            props.getProperty("db.host"),
            props.getProperty("db.port"),
            props.getProperty("db.name")
        );
        try {
            conn = DriverManager.getConnection(url,
                props.getProperty("db.user"),
                props.getProperty("db.password")
            );
        } catch (SQLException e) {
            throw new RuntimeException("Impossibile connettersi al DB: " + e.getMessage(), e);
        }
    }

    public static ArrayList<Persona> caricaPersone() {
    	ArrayList<Persona> lista = new ArrayList<>();
        String query = "SELECT id, nome, cognome, indirizzo, telefono, eta FROM persone";
        try (Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Persona p = new Persona(
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("indirizzo"),
                    rs.getString("telefono"),
                    rs.getInt("eta")
                );
                p.setId(rs.getInt("id"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public static ArrayList<Utente> caricaUtenti() {
    	ArrayList<Utente> lista = new ArrayList<>();
        String query = "SELECT id, username, password FROM utenti";
        try (Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Utente u = new Utente(
                    rs.getString("username"),
                    rs.getString("password")
                );
                u.setId(rs.getInt("id"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void salvaPersona(Persona p) {
        String sqlInsert = "INSERT INTO persone(nome,cognome,indirizzo,telefono,eta) VALUES(?,?,?,?,?)";
        String sqlUpdate = "UPDATE persone SET nome=?,cognome=?,indirizzo=?,telefono=?,eta=? WHERE id=?";
        try {
            if (p.getId() <= 0) {
                try (PreparedStatement ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, p.getNome());
                    ps.setString(2, p.getCognome());
                    ps.setString(3, p.getIndirizzo());
                    ps.setString(4, p.getTelefono());
                    ps.setInt(5, p.getEta());
                    ps.executeUpdate();
                    ResultSet keys = ps.getGeneratedKeys();
                    if (keys.next()) {
                    	p.setId(keys.getInt(1));
                    }
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                    ps.setString(1, p.getNome());
                    ps.setString(2, p.getCognome());
                    ps.setString(3, p.getIndirizzo());
                    ps.setString(4, p.getTelefono());
                    ps.setInt(5, p.getEta());
                    ps.setInt(6, p.getId());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void salvaUtente(Utente u) {
        String sqlInsert = "INSERT INTO utenti(username,password) VALUES(?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    u.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminaPersona(Persona p) {
        if (p.getId() <= 0) return;
        String query = "DELETE FROM persone WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        if (conn != null) 
        	try { conn.close(); 
        	} catch (SQLException ignored) {
        		System.out.print(ignored);
        	}
    }

}
