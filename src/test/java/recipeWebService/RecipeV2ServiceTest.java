package recipeWebService;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import recipeWebService.services.RecipeService;
import recipeWebService.services.RecipeV2Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipeV2ServiceTest {
    private RecipeService recipeService = new RecipeV2Service();


    @Test
    public void testGetRandomRecipeFilteredByValidCuisineType() {
        // Given: a valid cuisine type
        String validCuisineType = "italian";
        // When: trying to get a random recipe with the valid cuisine type
        Response response = recipeService.getRandomRecipe(validCuisineType);
        // Then: response status is OK (200)
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetRandomRecipeFilteredByInvalidCuisineType() {
        // Given: an invalid cuisine type
        String invalidCuisineType = "marocaine";
        // When: trying to get a random recipe with the invalid cuisine type
        Response response = recipeService.getRandomRecipe(invalidCuisineType);
        //then: response status is BAD REQUEST
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("recipes-api"));
    }

    @Test
    public void testGetRandomRecipeFiltered(){
        // Given: a valid dish type, cuisine type, healthLabels list , time max of preparation
        String validDishType = "Starter";
        String validCuisineType= "italian";
        List<String> healthLabels = List.of("vegan","gluten-free");
        int timeMax = 30;
        // When: trying to get a random recipe filtered by the  previous parameters
        Response response = recipeService.getRandomRecipe(validDishType, validCuisineType,healthLabels,timeMax);
        // Then: response status is OK (200)
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        String stringResponse = response.getEntity().toString();
        System.out.println(stringResponse);

        assertTrue(stringResponse.contains("starter"));
    }
    @Test
    public void testExternalAPIProblem() {
        //Given: An invalid id
        String invalidID = "ce5437e";
        //When: creating a recipe service and setting its ID with an invalid one and performing a GET request on Edamam
        recipeService.setAPP_ID(invalidID);
        Response response = recipeService.getRandomRecipe("italian");
        //then: response status is internal server error 500
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("Edamam"));
    }
}
