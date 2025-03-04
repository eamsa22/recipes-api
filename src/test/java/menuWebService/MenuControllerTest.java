package menuWebService;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.JsonReader;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import model.version3.MenuFilters;
import model.version2.MealDrinkError;
import model.version3.Menu;
import org.junit.jupiter.api.Test;
import org.leadpony.justify.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static jakarta.ws.rs.client.Entity.json;
import static org.junit.jupiter.api.Assertions.*;

public class MenuControllerTest {
    private static final String jsonSuccess_schema = "src/main/resources/menuSuccess.schema.json";
    private static final String jsonError_schema = "src/main/resources/menuError.schema.json";
    private static final String jsonRequest_schema = "src/main/resources/menu.bodyRequest.schema.json";

    private static final String json_generated_file = "src/main/resources/menu.generated.json";
    private final JsonValidationService service = JsonValidationService.newInstance();
    private final JsonSchemaReaderFactory readerFactory = service.createSchemaReaderFactoryBuilder().build();
    private static final String Base_URL = "http://localhost:8080/menu";
    private final Client client = ClientBuilder.newClient();
    private WebTarget target;

    private  ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testGetMenuWithNullBodyRequest(){
        target = client.target(Base_URL);
        Response response = target.request().post(null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Object error = response.readEntity(MealDrinkError.class);
        try {
            String jsonString = objectMapper.writeValueAsString(error);
            assertTrue(jsonString.contains("400"));
            assertTrue(jsonString.contains("recipes-api"));
            boolean validStructure = validateJsonStructure(jsonString,jsonError_schema);
            assertTrue(validStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetMenuWithValidCuisineType() {
        target = client.target(Base_URL);
        MenuFilters filters = new MenuFilters();
        filters.setCuisineType("french");
        Response response = target.request().post(json(filters));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Object menu = response.readEntity(Menu.class);
        try {
            String jsonString = objectMapper.writeValueAsString(menu);
            assertTrue(jsonString.contains("french"));
            boolean validStructure = validateJsonStructure(jsonString,jsonSuccess_schema);
            assertTrue(validStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetMenuWithInvalidCuisineType() {
        target = client.target(Base_URL);
        MenuFilters filters = new MenuFilters();
        filters.setCuisineType("moroccan");
        Response response = target.request().post(json(filters));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Object error = response.readEntity(MealDrinkError.class);
        try {
            String jsonString = objectMapper.writeValueAsString(error);
            assertTrue(jsonString.contains("400"));
            assertTrue(jsonString.contains("recipes-api"));
            boolean validStructure = validateJsonStructure(jsonString,jsonError_schema);
            assertTrue(validStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetMenuWithValidHealthLabels(){
        target = client.target(Base_URL);
        MenuFilters filters = new MenuFilters();
        filters.setCuisineType("french");
        filters.setHealthLabels(List.of("pork-free","gluten-free"));
        Response response = target.request().post(json(filters));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Object menu = response.readEntity(Menu.class);
        try {
            String jsonString = objectMapper.writeValueAsString(menu);
            assertTrue(jsonString.contains("french"));
            boolean validStructure = validateJsonStructure(jsonString,jsonSuccess_schema);
            assertTrue(validStructure);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetMenuWithInvalidHealthLabels() {
        target = client.target(Base_URL);
        MenuFilters filters = new MenuFilters();
        filters.setCuisineType("italian");
        filters.setHealthLabels(List.of("Pork-free","Gluten-free"));
        Response response = target.request().post(json(filters));
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        Object error = response.readEntity(MealDrinkError.class);
        try {
            String jsonString = objectMapper.writeValueAsString(error);
            assertTrue(jsonString.contains("500"));
            assertTrue(jsonString.contains("Edamam"));
            boolean validStructure = validateJsonStructure(jsonString,jsonError_schema);
            assertTrue(validStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetMenuWithValidDrinkIngredientFilter(){
        target = client.target(Base_URL);
        MenuFilters filters = new MenuFilters();
        filters.setCuisineType("french");
        filters.setIngredient("vodka");
        Response response = target.request().post(json(filters));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Object menu = response.readEntity(Menu.class);
        try {
            String jsonString = objectMapper.writeValueAsString(menu);
            assertTrue(jsonString.contains("french"));
            assertTrue(jsonString.contains("Vodka"));
            boolean validStructure = validateJsonStructure(jsonString,jsonSuccess_schema);
            assertTrue(validStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetMenuWithInvalidIngredientFilter(){
        target = client.target(Base_URL);
        MenuFilters filters = new MenuFilters();
        filters.setCuisineType("french");
        filters.setIngredient("no");
        Response response = target.request().post(json(filters));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Object error = response.readEntity(MealDrinkError.class);
        try {
            String jsonString = objectMapper.writeValueAsString(error);
            assertTrue(jsonString.contains("400"));
            assertTrue(jsonString.contains("recipes-api"));
            boolean validStructure = validateJsonStructure(jsonString,jsonError_schema);
            assertTrue(validStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetMenuWithValidAlcoholicFilter(){
        target = client.target(Base_URL);
        MenuFilters filters = new MenuFilters();
        filters.setCuisineType("french");
        filters.setAlcohol("true");
        Response response = target.request().post(json(filters));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Object menu = response.readEntity(Menu.class);
        try {
            String jsonString = objectMapper.writeValueAsString(menu);
            assertTrue(jsonString.contains("french"));
            assertTrue(jsonString.contains("true"));
            boolean validStructure = validateJsonStructure(jsonString,jsonSuccess_schema);
            assertTrue(validStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetMenuWithInvalidAlcoholicFilter(){
        target = client.target(Base_URL);
        MenuFilters filters = new MenuFilters();
        filters.setCuisineType("french");
        filters.setAlcohol("noAlcohol");
        Response response = target.request().post(json(filters));
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        Object error = response.readEntity(MealDrinkError.class);
        try {
            String jsonString = objectMapper.writeValueAsString(error);
            assertTrue(jsonString.contains("recipes-api"));
            boolean validStructure = validateJsonStructure(jsonString,jsonError_schema);
            assertTrue(validStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetMenuWithNoCuisineTypeFilter(){
        target = client.target(Base_URL);
        MenuFilters filters = new MenuFilters();
        filters.setAlcohol("true");
        filters.setHealthLabels(List.of("pork-free","gluten-free"));
        filters.setIngredient("vodka");
        filters.setTotalTime(60);
        Response response = target.request().post(json(filters));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Object error = response.readEntity(MealDrinkError.class);
        try {
            String jsonString = objectMapper.writeValueAsString(error);
            assertTrue(jsonString.contains("recipes-api"));
            boolean validStructure = validateJsonStructure(jsonString,jsonError_schema);
            assertTrue(validStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetMenuWithAllFiltersAsValid(){
        target = client.target(Base_URL);
        MenuFilters filters = new MenuFilters();
        filters.setCuisineType("french");
        filters.setHealthLabels(List.of("pork-free","gluten-free"));
        filters.setIngredient("vodka");
        filters.setAlcohol("true");
        filters.setTotalTime(60);
        Response response = target.request().post(json(filters));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Menu menu = response.readEntity(Menu.class);
        try {
            String jsonString = objectMapper.writeValueAsString(menu);
            assertTrue(jsonString.contains("french"));
            assertTrue(jsonString.contains("true"));
            assertTrue(jsonString.contains("Vodka"));
           float totalPreparationTime = Float.parseFloat(menu.getStarter().getPreparationTime()) +
                   Float.parseFloat(menu.getMainCourse().getPreparationTime()) +
                   Float.parseFloat(menu.getDessert().getPreparationTime());
            assertTrue(totalPreparationTime < 60 || totalPreparationTime == 60);
            boolean validStructure = validateJsonStructure(jsonString,jsonSuccess_schema);
            assertTrue(validStructure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateJsonStructure(String stringJson , String jsonSchema) {
        try {
            JsonSchema schema = readSchema(Paths.get(jsonSchema));
            List<Problem> problems = new ArrayList<>();
            ProblemHandler handler = list -> {
                problems.addAll(list);
            };
            writeStringToFile(stringJson, json_generated_file);
            java.nio.file.Path pathToInstance = Paths.get(json_generated_file);
            JsonReader reader = service.createReader(pathToInstance, schema, handler);
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