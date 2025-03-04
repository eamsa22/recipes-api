package recipeWebService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.JsonReader;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import model.version2.MealSuccess;
import org.junit.jupiter.api.Test;
import org.leadpony.justify.api.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipeV2ControllerTest {
    private static final String json_schema = "src/main/resources/meal.schema.json";
    private static final String json_response_file = "src/main/resources/meal.response.json";
    private final JsonValidationService service = JsonValidationService.newInstance();
    private final JsonSchemaReaderFactory readerFactory = service.createSchemaReaderFactoryBuilder().build();

    private static final String BASE_URL = "http://localhost:8080/v2/recipe/meal/";
    private Client client = ClientBuilder.newClient();
    private  WebTarget target;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetRandomRecipeWithValidCuisineType() {
        // When: performing a get request on the v2 API to get a recipe with valid cuisine type
        target = client.target(BASE_URL + "italian");
        Response response = target.request().get();
        // Then: response status is OK
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetRandomRecipeWithInvalidCuisineType() throws JsonProcessingException {
        // When: performing a get request on the v2 API to get a recipe with invalid cuisine type
        WebTarget target = client.target(BASE_URL +"marocaine");
        Response response = target.request().get();

        // Then: response status is INTERNAL_SERVER_ERROR
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        String jsonResponse = response.readEntity(String.class);
        JsonNode rootNode = mapper.readTree(jsonResponse);
        assertEquals("recipes-api",rootNode.get("api_failed").asText());

    }
    @Test
    public void testJsonResponseStructureNominalCase() throws JsonProcessingException {
        // When: performing a get request on the v2 API to get a cocktail with JSON response
        target = client.target(BASE_URL + "italian");
        Response response = target.request().get();

        // Then: response status is OK and the content is JSON
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Object meal =response.readEntity(MealSuccess.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(meal);
        boolean validStructure = validateJsonResponseStructure(jsonString);
        assertTrue(validStructure);
    }

    @Test
    public void testJsonResponseStructureErrorCase() {
        // When: performing a get request on the v2 API with no valid parameter name
        target = client.target(BASE_URL + "marocaine");
        Response response = target.request().get();

        // Then: response status is OK and the content is JSON
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        boolean validStructure = validateJsonResponseStructure(response.readEntity(String.class));
        assertTrue(validStructure);
    }
    private boolean validateJsonResponseStructure(String jsonResponse) {
        System.out.println(jsonResponse);
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
