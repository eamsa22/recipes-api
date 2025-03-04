package cocktailWebService;

import cocktailWebService.services.CocktailService;
import cocktailWebService.services.CocktailV1Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import model.version1.cocktail.Drink;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CocktailV1ServiceTest {

    private CocktailService cocktailService = new CocktailV1Service();
    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void testGetCocktailWithoutFilter() {
        //Given: path with no parameter
        Response response = cocktailService.getRandomCocktailWithNoFilter();
        //then: response status is OK
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetAlcoholicCocktail() {
        //Given: path with parameter alcoholic as true
        Response response = cocktailService.getRandomCocktailFilteredByAlcoholic(true);
        //then: response status is OK and contains the word alcoholic
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Alcoholic", ((Drink) response.getEntity()).getRecipeType().getAlcoholic());
    }


    @Test
    public void testGetNonAlcoholicCocktail() {
        //Given: path with parameter alcoholic as false
        Response response = cocktailService.getRandomCocktailFilteredByAlcoholic(false);
        //then: response status is OK and contains the word "Non alcoholic"
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Non alcoholic", ((Drink) response.getEntity()).getRecipeType().getAlcoholic());

    }

    @Test
    public void testExternalAPIProblem() throws JsonProcessingException {
        //Given: wrong path
        cocktailService.setCOCKTAIL_API_BASE_URL("https://www.thecocktaildb.com/api/json/v1/");
        Response response = cocktailService.getRandomCocktailFilteredByAlcoholic(false);
        //then: response status is Internal server error
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertTrue( response.getEntity().toString().contains("cocktailDB"));
    }

}
