package com.tenfe;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class GestioDBHR {
    // Com veurem, aquesta booleana controla si volem sortir de l'aplicació.
    static boolean sortirapp = false;
    static boolean DispOptions = true;

    public static void main(String[] args) {

        try (BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in))) {

            try {
                // Carregar propietats des de l'arxiu
                Properties properties = new Properties();
                try (InputStream input = GestioDBHR.class.getClassLoader().getResourceAsStream("config.properties")) {
                    // try (FileInputStream input = new FileInputStream(configFilePath)) {
                    properties.load(input);

                    // Obtenir les credencials com a part del fitxer de propietats
                    String dbUrl = properties.getProperty("db.url");
                    String dbUser = properties.getProperty("db.username");
                    String dbPassword = properties.getProperty("db.password");

                    // Conectar amb MariaDB
                    try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
                        System.out.println("Conexió exitosa");

                        String File_create_script = "db_scripts/tenfe.sql";

                        InputStream input_sch = GestioDBHR.class.getClassLoader()
                                .getResourceAsStream(File_create_script);

                        CRUDHR crudbhr = new CRUDHR();
                        // Aquí farem la creació de la database i de les taules, a més d'inserir dades
                        crudbhr.CreateDatabase(connection, input_sch);
                        while (sortirapp == false) {
                            MenuOptions(br1, crudbhr, connection);
                        }

                    } catch (Exception e) {
                        System.err.println("Error al conectar: " + e.getMessage());
                    }
                } catch (Exception e) {
                    System.err.println("Error al carregar fitxer de propietats: " + e.getMessage());
                }
            } finally {
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void MenuOptions(BufferedReader br, CRUDHR crudbhr, Connection connection)
            throws NumberFormatException, IOException, SQLException, InterruptedException {

        Terminal terminal = TerminalBuilder.builder().system(true).build();

        String message = "";
        message = "==================";
        printScreen(terminal, message);

        message = "CONSULTA BD HR";
        printScreen(terminal, message);

        message = "==================";
        printScreen(terminal, message);

        message = "OPCIONS";
        printScreen(terminal, message);

        message = "1. CARREGAR TAULA";
        printScreen(terminal, message);

        message = "2. CONSULTAR TOTES LES DADES";
        printScreen(terminal, message);

        message = "3. ALTRES CONSULTES";
        printScreen(terminal, message);

        message = "9. SORTIR";
        printScreen(terminal, message);

        message = "Introdueix l'opcio tot seguit >> ";
        for (char c : message.toCharArray()) {
            terminal.writer().print(c);
            terminal.flush();
            Thread.sleep(10);
        }

        int opcio = Integer.parseInt(br.readLine());

        switch (opcio) {
            case 1:
                String File_data_script = "db_scripts/inserts.sql";

                InputStream input_data = GestioDBHR.class.getClassLoader().getResourceAsStream(File_data_script);

                if (crudbhr.CreateDatabase(connection, input_data) == true) {
                    System.out.println("Registres duplicats");
                } else {
                    System.out.println("Registres inserits amb éxit");
                }

                break;
            case 2:
                // Preguntem de quina taula volem mostrar
                MenuSelect(br, crudbhr, connection);
                break;

            case 3:
                MenuSelectAltres(br, crudbhr, connection);
                break;

            case 9:
                // sortim
                System.out.println("Adéu!!");
                sortirapp = true;
                break;
            default:
                System.out.print("Opcio no vàlida");
                MenuOptions(br, crudbhr, connection);
        }

    }

    private static void printScreen(Terminal terminal, String message) throws InterruptedException {
        for (char c : message.toCharArray()) {
            terminal.writer().print(c);
            terminal.flush();
            Thread.sleep(10);
        }
        System.out.println();
    }

    public static void MenuSelect(BufferedReader br, CRUDHR crudbhr, Connection connection)
            throws SQLException, NumberFormatException, IOException {

        int opcio = 0;

        while (DispOptions) {

            System.out.println("De quina taula vols mostrar els seus registres?");
            System.out.println("1. Estació");
            System.out.println("2. Sortir");

            System.out.print("Introdueix l'opció tot seguit >> ");

            opcio = Integer.parseInt(br.readLine());

            switch (opcio) {
                case 1:
                    crudbhr.ReadAllDatabase(connection, "estacio");
                    break;
                case 2:
                    DispOptions = false;
                    break;
                default:
                    System.out.print("Opcio no vàlida");
            }

            if (DispOptions) {
                System.out.println("Vols fer altra consulta? (S o N): ");
                String opcioB = br.readLine();

                if (opcioB.equalsIgnoreCase("n")) {
                    System.out.println("No, no marxis si us plau!");
                    DispOptions = false;
                    break;
                }
            }
        }
    }

    public static void MenuSelectAltres(BufferedReader br, CRUDHR crudbhr, Connection connection)
            throws SQLException, NumberFormatException, IOException {

        int opcio = 0;

        while (DispOptions) {

            System.out.println("Quina consulta vols fer?");
            System.out.println("1. Estació per id");
            System.out.println("2. Estació per nom");
            System.out.println("3. Modificar nom estació");
            System.out.println("4. Esborrar estació");
            System.out.println("5. Inserir estació");
            System.out.println("6. Generar XML");
            System.out.println("9. Sortir");

            System.out.print("Introdueix l'opció tot seguit >> ");

            opcio = Integer.parseInt(br.readLine());

            switch (opcio) {
                case 1:
                    System.out.print("Introdueix la id de l'estació >> ");
                    int idEst = Integer.parseInt(br.readLine());
                    crudbhr.ReadEstacioId(connection, "estacio", idEst);
                    break;
                case 2:
                    System.out.print("Introdueix el nom de l'estacio a buscar >> ");
                    String nom = br.readLine();
                    crudbhr.ReadEstacioNom(connection, "estacio", nom);
                    break;
                case 3:
                    System.out.print("Introdueix el id de l'estacio a modificar >>");
                    idEst = Integer.parseInt(br.readLine());
                    System.out.print("Introdueix el nou nom de l'estacio a modificar >> ");
                    nom = br.readLine();
                    crudbhr.UpdateEstacio(connection, idEst, nom);
                    break;
                case 4:
                    System.out.print("Introdueix el id de l'estacio a eliminar >> ");
                    idEst = Integer.parseInt(br.readLine());
                    crudbhr.DeleteEstacio(connection, idEst);
                    break;
                case 5:
                    System.out.print("Introdueix el nom de la nova estacio >> ");
                    nom = br.readLine();
                    crudbhr.InsertEstacio(connection, nom);
                    break;
                case 6:
                    try {
                        generateEstacioXML(connection, "tenfe/src/main/resources/xml/estacions.xml");
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 9:
                    return;
            }
        }
    }

    public static void generateEstacioXML(Connection connection, String outputFilePath)
            throws SQLException, ParserConfigurationException, TransformerException {

        // Consulta per obtenir els registres de la taula estació
        String query = "SELECT * FROM estacio";

        try (PreparedStatement prepstat = connection.prepareStatement(query);
                ResultSet rset = prepstat.executeQuery()) {

            // Crear el document XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Crear l'element arrel
            Element rootElement = doc.createElement("estacions");
            doc.appendChild(rootElement);

            // Afegir registres de la taula com a elements al document XML
            while (rset.next()) {
                Element estacioElement = doc.createElement("estacio");

                Element idElement = doc.createElement("id");
                idElement.appendChild(doc.createTextNode(String.valueOf(rset.getInt("id"))));
                estacioElement.appendChild(idElement);

                Element nomElement = doc.createElement("nom");
                nomElement.appendChild(doc.createTextNode(rset.getString("nom")));
                estacioElement.appendChild(nomElement);

                rootElement.appendChild(estacioElement);
            }

            // Escriure el document XML al fitxer de sortida
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outputFilePath));

            transformer.transform(source, result);

            System.out.println("XML generat correctament a: " + outputFilePath);
        }
    }
}
