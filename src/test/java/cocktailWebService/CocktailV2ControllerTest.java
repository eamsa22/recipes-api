package cocktailWebService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.JsonReader;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import model.version2.DrinkSuccess;
import model.version2.MealDrinkError;
import org.junit.jupiter.api.Test;
import org.leadpony.justify.api.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CocktailV2ControllerTest {

    private static final String json_schema = "src/main/resources/drink.schema.json";
    private static final String json_response_file = "src/main/resources/drink.response.json";
    private final JsonValidationService service = JsonValidationService.newInstance();
    private final JsonSchemaReaderFactory readerFactory = service.createSchemaReaderFactoryBuilder().build();

    private static final String BASE_URL = "http://localhost:8080/v2/recipe/drink";
    private final Client client = ClientBuilder.newClient();
    private WebTarget target;
    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void testGetCocktailWithNoParameter() {
        // When: performing a get request on the v2 API to get a cocktail with no filter
        target = client.target(BASE_URL);
        Response response = target.request().get();
        // Then: response status is OK
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetCocktailWithValidParameterAndValidValue() throws JsonProcessingException {
        // When: performing a get request on the v2 API to get a cocktail with alcoholic filter
        target = client.target(BASE_URL + "?alcoholic=true");
        Response response = target.request().get();
        // Then: response status is OK
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        //then: the field alcoholic is true
        String jsonResponse = response.readEntity(String.class);
        JsonNode rootNode = mapper.readTree(jsonResponse);
        boolean isAlcoholic = rootNode.get("alcoholic").asBoolean();
        assertTrue(isAlcoholic);
    }

    @Test
    public void testGetCocktailWithInvalidParameterName() throws JsonProcessingException {
        // When: performing a get request on the v2 API to get a cocktail with invalid parameter name
        target = client.target(BASE_URL + "?alcoholi=true");
        Response response = target.request().get();

        // Then: response status is INTERNAL_SERVER_ERROR 500 with error message
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        MealDrinkError drinkError = response.readEntity(MealDrinkError.class);
        assertEquals("recipes-api",drinkError.getApi_failed());

    }

    @Test
    public void testGetCocktailWithInvalidValueParameter() throws JsonProcessingException {
        // When: performing a get request on the v2 API to get a cocktail with invalid parameter value
        target = client.target(BASE_URL + "?alcoholic=0");
        Response response = target.request().get();

        // Then: response status is INTERNAL_SERVER_ERRORwith error message
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        MealDrinkError drinkError = response.readEntity(MealDrinkError.class);
        assertEquals("recipes-api",drinkError.getApi_failed());

    }

    @Test
    public void testGetCocktailWithNoValueParameter() throws JsonProcessingException {
        // When: performing a get request on the v2 API to get a cocktail with empty parameter value
        target = client.target(BASE_URL + "?alcoholic=");
        Response response = target.request().get();

        // Then: response status is INTERNAL_SERVER_ERROR
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        MealDrinkError drinkError = response.readEntity(MealDrinkError.class);
        assertEquals("recipes-api",drinkError.getApi_failed());
    }


    @Test
    public void testJsonResponseStructureNominalCase() {
        // When: performing a get request on the v2 API to get a cocktail with JSON response
        target = client.target(BASE_URL + "?alcoholic=true");
        Response response = target.request().get();

        // Then: response status is OK and the content is JSON
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Object drinkObject =response.readEntity(DrinkSuccess.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(drinkObject);
            boolean validStructure = validateJsonResponseStructure(jsonString);
            assertTrue(validStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJsonResponseStructureErrorCase() {
        // When: performing a get request on the v2 API with no valid parameter name
        target = client.target(BASE_URL + "?alcohoc=true");
        Response response = target.request().get();

        // Then: response status is OK and the content is JSON
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Object drinkObject =response.readEntity(MealDrinkError.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(drinkObject);
            boolean validStructure = validateJsonResponseStructure(jsonString);
            assertTrue(validStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateJsonResponseStructure(String jsonResponse) {
        try {
            JsonSchema schema = readSchema(Paths.get(json_schema));
            List<Problem> problems = new ArrayList<>();
            ProblemHandler handler = list -> {
                problems.addAll(list);
            };
            writeStringToFile(jsonResponse, json_response_file);
            java.nio.file.Path pathToInstance = Paths.get(json_response_file);
            JsonReader reader = service.createReader(pathToInstance, schema, handler);
            reader.readValue();
            if ( !problems.isEmpty()) {
                problems.forEach(System.out::println);
            }
            Files.delete(pathToInstance);
            return problems.isEmpty();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private JsonSchema readSchema(java.nio.file.Path path) {
        try (JsonSchemaReader reader = readerFactory.createSchemaReader(path)) {
            return reader.read();
        }
    }
    private void writeStringToFile(String content, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath)))) {
            writer.write(content);
        }
    }


}
