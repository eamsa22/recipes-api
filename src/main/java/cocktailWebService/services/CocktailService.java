package cocktailWebService.services;

import communs.ResponseAPI;
import jakarta.ws.rs.core.Response;

public interface CocktailService {

    public Response getRandomCocktailWithNoFilter();

    public Response getRandomCocktailFilteredByAlcoholic(boolean alcoholic);
    public Response getRandomCocktailFilteredByIngredient(String ingredient);
    public Response getRandomCocktailFilteredByIngredientAndAlcoholic(String ingredient, boolean alcoholic);
    public Object createErrorMessage(ResponseAPI r, String apiFailed);


    public void setCOCKTAIL_API_BASE_URL(String COCKTAIL_API_BASE_URL);
    public void setIsMenu(boolean isMenu);
    public boolean isMenu();

}
