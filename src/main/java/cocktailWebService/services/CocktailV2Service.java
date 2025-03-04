package cocktailWebService.services;

import jakarta.ws.rs.core.Response;
import model.version3.ErrorMenu;
import model.version2.DetailedIngredient;
import model.version2.DrinkSuccess;
import model.version2.MealDrinkError;
import org.json.JSONObject;
import communs.ResponseAPI;

import java.util.ArrayList;
import java.util.List;

public class CocktailV2Service extends AbstractCocktailService {

    protected Response handleResponse(ResponseAPI jsonResponse){
        ResponseAPI r = null;
        if (jsonResponse == ResponseAPI.OK) {
            JSONObject originalJson = new JSONObject(jsonResponse.getResponse());

            if (originalJson.has("drinks") && originalJson.getJSONArray("drinks").length() > 0) {
                JSONObject drink = originalJson.getJSONArray("drinks").getJSONObject(0);
                return Response.ok(buildCocktail(drink)).build();
            }
            else {
                r= ResponseAPI.INTERNAL_SERVER_ERROR;
                r.setResponse("no drink can be retrieved");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(createErrorMessage(r,"cocktailDB"))
                        .build();
            }
        }
        else {
            if(jsonResponse== ResponseAPI.INTERNAL_SERVER_ERROR){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(createErrorMessage( jsonResponse ,jsonResponse.getApiFailed()))
                        .build();
            }
            else{
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(createErrorMessage( jsonResponse ,jsonResponse.getApiFailed()))
                        .build();
            }

        }
    }
    protected Response handleErrorResponse(Exception e) {
        ResponseAPI r = ResponseAPI.INTERNAL_SERVER_ERROR;
                r.setResponse("error while executing the web service drink : "+e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(createErrorMessage(r,"recipes-api"))
                .build();
    }

    private DrinkSuccess buildCocktail(JSONObject drinkJson){
        List<DetailedIngredient> detailedIngredients = getDetailedIngredients(drinkJson);
        List<String> ingredients = getIngredientsLines(detailedIngredients);
        DrinkSuccess drink = new DrinkSuccess();

        drink.setName(drinkJson.getString("strDrink"));
        drink.setType(drinkJson.getString("strCategory"));
        drink.setAlcoholic("Alcoholic".equalsIgnoreCase(drinkJson.getString("strAlcoholic")));
        drink.setImageURL(drinkJson.getString("strDrinkThumb"));
        drink.setIngredients(ingredients);
        drink.setDetailedIngredients(detailedIngredients);
        drink.setInstructions(drinkJson.getString("strInstructions"));
        return drink;
    }

    private List<String> getIngredientsLines(List<DetailedIngredient> ingredientsDetails) {
        List<String> ingredients= new ArrayList<>();
        for(DetailedIngredient ingredient : ingredientsDetails){
            ingredients.add(ingredient.getName());
        }
        return ingredients;
    }
    private List<DetailedIngredient> getDetailedIngredients(JSONObject drink){
        List<DetailedIngredient> detailedIngredients=new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            String ingredient = drink.optString("strIngredient" + i);
            String measure = drink.optString("strMeasure" + i);
            DetailedIngredient detailedIngredient = new DetailedIngredient();
            if (!ingredient.isEmpty()) {
                detailedIngredient.setName(ingredient);
                detailedIngredient.setQuantity(measure);
                if (drink.has("strIngredient" + i + "Thumb")) {
                    detailedIngredient.setImage( drink.getString("strIngredient" + i + "Thumb"));
                }else{
                    detailedIngredient.setImage( "");
                }
                detailedIngredients.add(detailedIngredient);
            }
        }
        return detailedIngredients;
    }
    public Object createErrorMessage(ResponseAPI responseAPI, String apiFailed) {
        if(isMenu()){
            ErrorMenu error = new ErrorMenu();
            error.setApi_failed(apiFailed);
            error.setApi_status(String.valueOf(ResponseAPI.getStatusCode(responseAPI)));
            error.setError(responseAPI.getResponse());
            return error;
        }else{
            MealDrinkError error = new MealDrinkError();
            error.setApi_failed(apiFailed);
            error.setApi_status(String.valueOf(ResponseAPI.getStatusCode(responseAPI)));
            error.setError(responseAPI.getResponse());
            return error;
        }
    }


}
