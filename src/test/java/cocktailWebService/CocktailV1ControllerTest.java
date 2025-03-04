package cocktailWebService;

import jakarta.validation.constraints.AssertTrue;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CocktailV1ControllerTest {
    private static final String TESTFILE_XML = "src/main/resources/cocktail_test.xml";
    private static final String TESTFILE_XSD = "src/main/resources/cocktail_recipe.xsd";
   private WebTarget target;
   private Client client;
    @Test
    public void TestGetCocktailWithNoParameter(){
        //when: performing a get request on our API to get a cocktail with no filter
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/recipe/drink");
        Response response = target.request().get();
        // Then: response status is OK
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

    @Test
    public void TestGetCocktailWithValidParameterAndValidValue(){
        //when: performing a get request on our API to get a cocktail with alcoholic filter
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/recipe/drink?alcoholic=true");
        Response response = target.request().get();
        // Then: response status ok
        System.out.println(response.readEntity(String.class));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void TestGetCocktailWithInvalidParameterName() {
        //when: performing a get request on our API to get a cocktail with invalid parameter name
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/recipe/drink?alcoholi=true");
        Response response = target.request().get();
        String responseString= response.readEntity(String.class).toString();
        // Then: response status is INTERNAL_SERVER_ERROR 500
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(responseString.contains("Error the parameter entered for drink  is invalid"));
        assertTrue(responseString.contains("recipes-api"));
    }


    @Test
    public void TestGetCocktailWithInvalidValueParameter(){
        //when: performing a get request on our API to get a cocktail with invalid parameter value
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/recipe/drink?alcoholic=0");
        Response response = target.request().get();
        String responseString= response.readEntity(String.class).toString();
        // Then: response status is INTERNAL_SERVER_ERROR
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(responseString.contains("Error the value of the parameter Alcoholic : 0 is invalid"));
        assertTrue(responseString.contains("recipes-api"));
    }

    @Test
    public void TestGetCocktailWithNoValueParameter(){
        //when: performing a get request on our API to get a cocktail with empty parameter value
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/recipe/drink?alcoholic=");
        Response response = target.request().get();
        String responseString= response.readEntity(String.class).toString();

        // Then: response status is INTERNAL_SERVER_ERROR
        assertTrue(responseString.contains("Error the parameter Alcoholic is empty"));
        assertTrue(responseString.contains("recipes-api"));
    }

    @Test
    public void testXmlValidWithInNominalCase() throws Exception {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/recipe/drink");
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

        String content = new String(bytes);
        content = content.replaceFirst("<\\?xml(.+?)\\?>", "");
        content = content.replaceFirst("<Cocktail.*?>", "");
        // Ajout de la ligne <Recipe ...> après les deux premières lignes
        String newContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Cocktail xmlns=\"http://www.example.com/cocktail\"\n" +
                "          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "          xsi:schemaLocation=\"http://www.example.com/cocktail cocktail_recipe.xsd\">" + content;

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
    @Test
    public void testXmlValidWithInErrorCase() throws Exception {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/recipe/drink/?alcohoc=true");
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

        String content = new String(bytes);
        content = content.replaceFirst("<\\?xml(.+?)\\?>", "");
        content = content.replaceFirst("<Cocktail.*?>", "");
        // Ajout de la ligne <Recipe ...> après les deux premières lignes
        String newContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Cocktail xmlns=\"http://www.example.com/cocktail\"\n" +
                "          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "          xsi:schemaLocation=\"http://www.example.com/cocktail cocktail_recipe.xsd\">" + content;

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


}
