package recipeWebService.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.ws.rs.core.Response;
import model.version2.DetailedIngredient;
import model.version2.MealDrinkError;
import model.version2.MealSuccess;
import model.version3.ErrorMenu;
import model.version3.MealSuccessV3;
import org.json.JSONArray;
import org.json.JSONObject;
import communs.ResponseAPI;

import java.util.ArrayList;
import java.util.List;

import static communs.ResponseAPI.INTERNAL_SERVER_ERROR;

public class RecipeV2Service extends AbstractRecipeService {

    private final ObjectMapper objectMapper;

    public RecipeV2Service(){
        this.objectMapper = new ObjectMapper() ;
        this.objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    }

    protected Response handleResponse(ResponseAPI jsonResponse){

        if (jsonResponse == ResponseAPI.OK) {
            JSONObject originalJson = new JSONObject(jsonResponse.getResponse());

            if (originalJson.has("hits") && originalJson.getJSONArray("hits").length() > 0) {
                JSONObject meal = originalJson.getJSONArray("hits").getJSONObject(0).getJSONObject("recipe");
                if(isMenu()){
                    return  Response.ok(buildMealMenu(meal)).build();
                }

                return Response.ok(buildMeal(meal)).build();
            }
            else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(createErrorJson(jsonResponse,jsonResponse.getApiFailed()))
                        .build();
            }
        }
        else {
            return Response.status(jsonResponse.getStatusCode(jsonResponse))
                    .entity(createErrorJson(jsonResponse, jsonResponse.getApiFailed()))
                    .build();
        }
    }


    private MealSuccessV3 buildMealMenu(JSONObject mealJson) {
        MealSuccess meal = buildMeal(mealJson);
        MealSuccessV3 m = new MealSuccessV3();
        m.setMeal(meal);
        m.setCalories(mealJson.getNumber("calories").toString());
        return  m;
    }

    protected Response handleErrorResponse(Exception e) {
        ResponseAPI r =INTERNAL_SERVER_ERROR;
        r.setResponse(e.getMessage() == null ? "error while executing Recipe web service" : "error in recipes web service "+e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(createErrorJson(r,"recipes-api"))
                .build();
    }

    private Object createErrorJson(ResponseAPI jsonResponse,String apiFailed) {
        if(isMenu()){
            ErrorMenu error = new ErrorMenu();
            error.setApi_failed(apiFailed);
            error.setApi_status(String.valueOf(ResponseAPI.getStatusCode(jsonResponse)));
            error.setError(jsonResponse.getResponse());
            return error;
        }
        else{
            MealDrinkError error = new MealDrinkError();
            error.setApi_failed(apiFailed);
            error.setApi_status(String.valueOf(ResponseAPI.getStatusCode(jsonResponse)));
            return error;
        }
    }


    private MealSuccess buildMeal(JSONObject mealJson){

        StringBuilder type= new StringBuilder();
        JSONArray mealType = mealJson.getJSONArray("dishType") ;
        for (int i = 0; i < mealType.length(); i++) {
            type.append(mealType.get(i));
        }
        StringBuilder country= new StringBuilder();
        JSONArray cuisineType = mealJson.getJSONArray("cuisineType") ;
        for (int i = 0; i < cuisineType.length(); i++) {
            country.append(cuisineType.get(i));
        }
        List<DetailedIngredient> ingredientsDetails = getDetailedIngredients(mealJson);
        String ingredients = getIngredientsLine(ingredientsDetails);
        JSONArray instructionsArray = mealJson.optJSONArray("instructions");
        List<String> instructionsList = new ArrayList<>();
        if (instructionsArray != null) {
            for (int i = 0; i < instructionsArray.length(); i++) {
                String instruction = instructionsArray.getString(i);
                instructionsList.add(instruction);
            }
        }
        MealSuccess meal = new MealSuccess();
        meal.setName(mealJson.getString("label"));
        meal.setType(type.toString());
        meal.setCountry(country.toString());
        meal.setPreparationTime(mealJson.getNumber("totalTime").toString());
        meal.setImageURL(mealJson.has("image") && mealJson.get("image") instanceof String ? mealJson.getString("image"): "");
        meal.setSource(mealJson.getString("source"));
        meal.setIngredients(ingredients);
        meal.setDetailedIngredients(ingredientsDetails);
        meal.setInstructions(instructionsList);
        return meal;
    }

    private String getIngredientsLine(List<DetailedIngredient> ingredientsDetails) {
        StringBuilder ingredients= new StringBuilder();
        for(DetailedIngredient ingredient : ingredientsDetails){
            ingredients.append(ingredient.getName()+", ");
        }
        return ingredients.toString();
    }

    private List<DetailedIngredient> getDetailedIngredients(JSONObject mealJson){
        JSONArray ingredientsArray = mealJson.getJSONArray("ingredients");
        List<DetailedIngredient> detailedIngredientsArray = new ArrayList<>();
        for (int i = 0; i < ingredientsArray.length(); i++) {
            DetailedIngredient ingredient = new DetailedIngredient();
            JSONObject ingredientObject = ingredientsArray.getJSONObject(i) ;
            ingredient.setName(ingredientObject.getString("text"));
            ingredient.setQuantity(ingredientObject.getNumber("quantity").toString());
            ingredient.setImage(ingredientObject.has("image") ?ingredientObject.optString("image"): "" );
            detailedIngredientsArray.add(ingredient);
        }
        return detailedIngredientsArray;
    }


}
