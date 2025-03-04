package cocktailWebService;

import cocktailWebService.services.CocktailService;
import cocktailWebService.services.CocktailV2Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import model.version2.DrinkSuccess;
import model.version2.MealDrinkError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CocktailV2ServiceTest {

    private CocktailService cocktailService = new CocktailV2Service();
    private  ObjectMapper mapper = new ObjectMapper();


    @Test
    public void testGetCocktailWithoutFilter() {
        Response response = cocktailService.getRandomCocktailWithNoFilter();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetAlcoholicCocktail() {
        Response response = cocktailService.getRandomCocktailFilteredByAlcoholic(true);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        DrinkSuccess drink = (DrinkSuccess) response.getEntity();
        assertTrue(drink.isAlcoholic());
    }

    @Test
    public void testGetNonAlcoholicCocktail(){
        Response response = cocktailService.getRandomCocktailFilteredByAlcoholic(false);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        DrinkSuccess drink = (DrinkSuccess) response.getEntity();
        assertTrue(!drink.isAlcoholic());
    }

    @Test
    public void getCocktailFilteredByIngredient(){
        Response response = cocktailService.getRandomCocktailFilteredByIngredient("Vodka");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        DrinkSuccess drink = (DrinkSuccess) response.getEntity();
        assertTrue(drink.toString().contains("Vodka"));
    }
    @Test
    public void getCocktailFilteredByIngredientAndAlcoholic(){
        Response response = cocktailService.getRandomCocktailFilteredByIngredientAndAlcoholic("Vodka",true);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        DrinkSuccess drink = (DrinkSuccess) response.getEntity();
        assertTrue(drink.toString().contains("Vodka"));
        assertTrue(drink.isAlcoholic());

    }
    @Test
    public void testExternalAPIProblem(){
        // Given: wrong path
        cocktailService.setCOCKTAIL_API_BASE_URL("https://www.thecocktaildb.com/api/json/v1/");
        Response response = cocktailService.getRandomCocktailFilteredByAlcoholic(false);
        // Then: response status is Internal server error
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        MealDrinkError drinkError = (MealDrinkError) response.getEntity();
        assertEquals("cocktailDB",drinkError.getApi_failed());

    }
}

