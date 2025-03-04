package recipeWebService;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

public class RecipeV1ControllerTest {
    private static final String TESTFILE_XML = "src/main/resources/recipe_test.xml";
    private static final String TESTFILE_XSD = "src/main/resources/cuisine_recipe.xsd";

    private Client client;
    private WebTarget target;
    // Scenario 1
    @Test
    public void testGetRecipeWithValidCuisineType() {
        // Given: A valid cuisine type
        // When: performing a get request on our API
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/recipe/meal/italian");
        Response response = target.request().get();
        // Then: response status is OK
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }


    // Scenario 2
    @Test
    public void testGetRecipeWithInvalidCuisineType() {
        // Given: An invalid cuisine type
        // When: performing a get request on our API
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/recipe/meal/marocaine");
        Response response = target.request().get();
        String responseString= response.readEntity(String.class).toString();

        // Then: response status is BAD REQUEST
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(responseString.contains("Error the value of the parameter cuisineType  : marocaine is invalid"));
        assertTrue(responseString.contains("recipes-api"));
    }

    // Scenario 3
    @Test
    public void testInternalAPIProblem() {
        assertThrows(IllegalStateException.class, () -> {
            // Given: An invalid cuisine type like {}
            // When: creating a recipe service and performing a GET request on our API
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:8080/recipe/meal/{}");
            Response response = target.request().get();
            // Then: response status is INTERNAL SERVER ERROR
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        }, "Unexpected exception in testInternalAPIProblem");
    }

    // Scenario 4
    @Test
    public void testValidXmlContentInNominalCase() throws Exception {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/recipe/meal/italian");
        Response response = target.request().get();

        String xmlString = prettyPrintByTransformer(response.readEntity(String.class));

        // Écriture de la chaîne XML dans un fichier
        FileWriter writer = new FileWriter(TESTFILE_XML);
        writer.write(xmlString);
        writer.close();

        // Ouverture du fichier XML en mode lecture et écriture
        RandomAccessFile file = new RandomAccessFile(TESTFILE_XML, "rw");
        byte[] bytes = new byte[(int) file.length()];
        file.readFully(bytes);
        file.seek(0);

        // Suppression des deux premières lignes (en-tête XML et tag recipe)
        String content = new String(bytes);
        content = content.replaceFirst("<\\?xml(.+?)\\?>", "");
        content = content.replaceFirst("<Recipe.*?>", "");
        // Ajout de la ligne <Recipe ...> après les deux premières lignes
        String newContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Recipe xmlns=\"http://www.example.com/cuisine\"\n" +
                "          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "          xsi:schemaLocation=\"http://www.example.com/cuisine  cuisine_recipe.xsd\">" + content;

        // Écriture du nouveau contenu dans le fichier
        file.setLength(0);
        file.write(newContent.getBytes());
        file.close();

        // Validation du fichier XML par rapport au schéma XSD
        boolean res = isValid(TESTFILE_XML, TESTFILE_XSD);
        System.out.println("the XML content" + (res ? " is valid " : " is not valid ") + " according to the XSD file " + TESTFILE_XSD);

        assertTrue(res);

        // Suppression du fichier XML après la validation
        File fileToDelete = new File(TESTFILE_XML);
        fileToDelete.delete();

    }
    // Scenario 5
    @Test
    public void testValidXmlContentInErrorCase() throws Exception {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/recipe/meal/moroccan");
        Response response = target.request().get();

        String xmlString = response.readEntity(String.class);
        // Écriture de la chaîne XML dans un fichier
        FileWriter writer = new FileWriter(TESTFILE_XML);
        writer.write(xmlString);
        writer.close();

        // Ouverture du fichier XML en mode lecture et écriture
        RandomAccessFile file = new RandomAccessFile(TESTFILE_XML, "rw");
        byte[] bytes = new byte[(int) file.length()];
        file.readFully(bytes);
        file.seek(0);

        // Suppression des deux premières lignes (en-tête XML et tag recipe)
        String content = new String(bytes);
        content = content.replaceFirst("<\\?xml(.+?)\\?>", "");
        content = content.replaceFirst("<Recipe.*?>", "");
        // Ajout de la ligne <Recipe ...> après les deux premières lignes
        String newContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Recipe xmlns=\"http://www.example.com/cuisine\"\n" +
                "          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "          xsi:schemaLocation=\"http://www.example.com/cuisine  cuisine_recipe.xsd\">" + content;

        // Écriture du nouveau contenu dans le fichier
        file.setLength(0);
        file.write(newContent.getBytes());
        file.close();

        // Validation du fichier XML par rapport au schéma XSD
        boolean res = isValid(TESTFILE_XML, TESTFILE_XSD);
        System.out.println("the XML content" + (res ? " is valid " : " is not valid ") + " according to the XSD file " + TESTFILE_XSD);

        assertTrue(res);

        // Suppression du fichier XML après la validation
        File fileToDelete = new File(TESTFILE_XML);
        fileToDelete.delete();
    }

    /**
     * Function to validate xml with xsd
     * @Given: String path to xsd file , String path to xml file where the content is stored
     * @Return: boolean
     * */
    private static boolean isValid(String xmlPath, String xsdPath) {
        Source xmlDoc = new StreamSource(new File(xmlPath));
        SchemaFactory factory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(xmlDoc);
            return true;
        } catch (SAXException | IOException e) {
            System.err.println(xmlDoc.getSystemId() + " is NOT valid. "
                    + "[Reason: " + e.getLocalizedMessage() + "]");
            return false;
        }
    }

    private String prettyPrintByTransformer(String xmlString) throws Exception {
        try{
            return getString(xmlString);
        } catch (Exception e) {
            throw new Exception();
        }
    }


    public static String getString(String xmlString) throws SAXException, IOException, ParserConfigurationException, TransformerException {
        InputSource src = new InputSource(new StringReader(xmlString));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        Writer out = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(out));
        return out.toString();
    }
}
