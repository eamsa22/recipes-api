package recipeWebService ;

import jakarta.ws.rs.core.Response;
import model.version1.meal.Error;
import org.junit.jupiter.api.Test;

import recipeWebService.services.RecipeService;
import recipeWebService.services.RecipeV1Service;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeV1ServiceTest {

    private RecipeService recipeService = new RecipeV1Service() ;

    //scenario 1
    @Test
    public void testGetRecipeWithValidCuisineType() {
        //Given: A valid cuisine type
        //When: performing a get request on Edamam
        Response response = recipeService.getRandomRecipe("french");
        //then: response status is OK
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    //scenario2
    @Test
    public void testGetRecipeWithInvalidCuisineType() {
        //Given: An invalid cuisine type
        //When: performing a get request on Edamam
        Response response = recipeService.getRandomRecipe("marocaine");
        String responseString= response.getEntity().toString();

        Error error= (Error) response.getEntity();

        //then: response status is BAD REQUEST
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        assertTrue(error.getError().contains("Error the value of the parameter cuisineType  : marocaine is invalid"));
        assertEquals("recipes-api",error.getApiFailed());    }

    //scenario3
    //problem from API Edamam
    @Test
    public void testExternalAPIProblem() {
        //Given: An invalid id
        String invalidID = "ce5437e";
        //When: creating a recipe service and setting its ID with an invalid one and performing a GET request on Edamam
        recipeService.setAPP_ID(invalidID);
        Response response = recipeService.getRandomRecipe("italian");
        Error error= (Error) response.getEntity();

        //then: response status is internal server error 500
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertTrue(error.getError().contains("Unauthorized app_id = ce5437e"));
        assertEquals("Edamam",error.getApiFailed());
    }

    //scenario4
    //problem from our API
    @Test
    public void testInternalAPIProblem() {
        //Given: An invalid cuisine type like {}
        //When: creating a recipe service and performing a GET request on Edamam
        Response response = recipeService.getRandomRecipe("{}");
        //then: response status is internal server error 500
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals(500,response.getStatus());
    }

}
