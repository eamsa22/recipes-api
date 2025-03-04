package recipeWebService.services;

import communs.ResponseAPI;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import model.version1.meal.Error;
import model.version1.meal.*;

import java.io.IOException;;
import java.util.ArrayList;
import java.util.List;

public class RecipeV1Service  extends AbstractRecipeService {


    protected Response handleResponse(ResponseAPI jsonResponse) throws Exception {
        if (jsonResponse == ResponseAPI.OK){
            Meal meal = convertJsonToXml(jsonResponse.getResponse());
            return Response.ok(meal).build();
        }
        else if (jsonResponse == ResponseAPI.BAD_REQUEST) {
            return  Response.status(Response.Status.BAD_REQUEST)
                    .entity(createErrorXml(jsonResponse.getResponse(),jsonResponse.getApiFailed()))
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(createErrorXml(jsonResponse.getResponse(),jsonResponse.getApiFailed()))
                    .build();
        }
    }

    protected Response handleErrorResponse(Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(createErrorXml("Error while executing the web service: " + e.getMessage(),"recipes-api"))
                .build();
    }

    private Meal convertJsonToXml(String json) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode recipeNode = jsonNode.path("hits").path(0).path("recipe");
            Meal meal = buildMeal(recipeNode);
            return meal;
        } catch (IOException e) {
            throw new Exception();
        }
    }

    private Meal buildMeal(JsonNode jsonMeal){
        Meal meal = new Meal();

        RecipeDescription recipeDescription = new RecipeDescription();
        recipeDescription.setCuisineType(convertArrayToString(jsonMeal.get("cuisineType")));
        recipeDescription.setMealType(convertArrayToString(jsonMeal.get("mealType")));
        recipeDescription.setDishType(convertArrayToString(jsonMeal.get("dishType")));

        RecipeSource recipeSource= new RecipeSource();
        recipeSource.setSource(jsonMeal.get("source").asText());
        recipeSource.setUrl(jsonMeal.get("url").asText());

        IngredientLines ingredientLines = getIngredientsLines(jsonMeal.get("ingredientLines"));
        IngredientsDetails ingredientDetails = getIngredientsDetails(jsonMeal.get("ingredients"));
        HealthLabels healthLabels = getHealthLabels(jsonMeal.get("healthLabels"));
        meal.setRecipeName(jsonMeal.get("label").asText());
        meal.setRecipeDescription(recipeDescription);
        meal.setImage(jsonMeal.get("image").asText());
        meal.setRecipeSource(recipeSource);
        meal.setCalories(jsonMeal.get("calories").asText());
        meal.setTotalTime(jsonMeal.get("totalTime").asText());
        meal.setIngredientLines(ingredientLines);
        meal.setIngredientsDetails(ingredientDetails);
        meal.setHealthLabels(healthLabels);
        return meal;
    }

    private HealthLabels getHealthLabels(JsonNode healthLabels) {
        List<String> healthLabelsList = new ArrayList<>();
        HealthLabels h = new HealthLabels();
        for (JsonNode healthLabel : healthLabels) {
            healthLabelsList.add(healthLabel.asText());
        }
        h.setHealthLabelsList(healthLabelsList);
        return h;
    }

    private IngredientsDetails getIngredientsDetails(JsonNode ingredients) {
        List<IngredientDetails> listIngDetails = new ArrayList<>();
        IngredientsDetails iD = new IngredientsDetails();
        for (JsonNode ingredient : ingredients) {
            IngredientDetails i = new IngredientDetails();
            i.setText(ingredient.get("text").asText());
            i.setQuantity(ingredient.get("quantity").asText());
            i.setMeasure(ingredient.get("measure").asText().equals("<unit>") ? "unit" : ingredient.get("measure").asText());
            i.setFood(ingredient.get("food").asText());
            i.setWeight(ingredient.get("weight").asText());
            i.setFoodCategory(ingredient.get("foodCategory").asText());
            i.setFoodId(ingredient.get("foodId").asText());
            i.setImage(ingredient.get("image").asText());

            listIngDetails.add(i);
        }
        iD.setIngredients(listIngDetails);
        return iD;
    }

    private IngredientLines getIngredientsLines(JsonNode ingredientLines) {
        List<String> listeIng = new ArrayList<>();
        for (JsonNode ingredientLine : ingredientLines) {
            listeIng.add(ingredientLine.asText());
        }
        IngredientLines iL = new IngredientLines();
        iL.setIngredients(listeIng);
        return iL;
    }


    private String convertArrayToString(JsonNode arrayNode) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arrayNode.size(); i++) {
            stringBuilder.append(arrayNode.get(i).asText());
            if (i < arrayNode.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    public Error createErrorXml(String errorMessage,String apiFailed)  {
        Error error = new Error();
        error.setError(errorMessage);
        error.setApiFailed(apiFailed);

        return error;
    }



}
