# Rubrica Java

**Rubrica ** è un'applicazione desktop Java che permette di gestire una lista di contatti (nome, cognome, indirizzo, telefono, età) con autenticazione utenti e persistenza su database MySQL.

## Caratteristiche principali

- **Login**: autenticazione semplice con utenti salvati in tabella `utenti`.
- **Gestione Contatti**: CRUD di contatti via interfaccia Swing.
- **Interfaccia Grafica**:
  - Toolbar in ogni finestra (Login, Rubrica, Editor Contatto).
  - Tabelle (`JTable`) e form di inserimento/modifica.
- **Persistenza**:
  - Dati salvati in database MySQL tramite JDBC.
  - Configurabile via file `config_database.properties` nella stessa cartella del JAR.
  - Viene mantenuto tutto il codice relativo alla gestione su file anche se non più usato

## Struttura del progetto

```
Rubrica/                   # root del progetto
├── src/                   # sorgenti Java
│   ├── it.rubricaTuring.main    # Main, avvio applicazione
│   ├── it.rubricaTuring.model   # classi di dominio (Persona, Utente)
│   ├── it.rubricaTuring.view      # finestre Swing (LoginUI, RubricaUI, EditorPersona)
│   └── it.rubricaTuring.persistenza
│       └── GestioneDB.java # gestione JDBC/MySQL
│       └── GestioneFile.java # gestione persistenza su file
├── icons/             # per le icone sulla toolbar
├── schema_database.sql    # script SQL per creare DB e tabelle
└── credenziali_database.properties
```

## Prerequisiti

- Java 17
- MySQL Server (≥ 5.7).
- MySQL Connector/J (driver JDBC). Incluso nel JAR esportato.

## Configurazione

1. **Creazione database e tabelle**
   ```bash
   mysql -u <utente_admin> -p < schema_database.sql
   ```
   Oppure in MySQL Workbench:
   - Apri `schema_database.sql`
   - Esegui script

2. **Configura file `credenziali_database.properties`**
   Colloca questo file accanto al JAR finale. Contenuto minimo:
   ```properties
   db.host=localhost
   db.port=3306
   db.name=rubrica_db
   db.user=tuo_utente
   db.password=tua_password
   ```

## Esecuzione

Posizionati nella cartella contentente `Rubrica.jar` e `credenziali_database.properties`, quindi:

```bash
java -jar Rubrica.jar
```

- Al primo avvio, se la tabella `utenti` è vuota, crea automaticamente gli account di default:
  - **admin / admin**
  - **user  / password**
- Dopo login, puoi aggiungere, modificare o eliminare contatti.
