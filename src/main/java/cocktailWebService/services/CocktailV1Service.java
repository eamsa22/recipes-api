package cocktailWebService.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import communs.ResponseAPI;
import jakarta.ws.rs.core.Response;
import model.version1.cocktail.*;
import model.version1.cocktail.Error;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class CocktailV1Service extends AbstractCocktailService {

    protected Response handleResponse(ResponseAPI jsonResponse) throws Exception {
        if (jsonResponse == ResponseAPI.OK) {
            Drink drink = getDrink(jsonResponse.getResponse());
            return Response.ok(drink).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(createErrorXml(jsonResponse.getResponse(), jsonResponse.getApiFailed()))
                    .build();
        }
    }

    protected Response handleErrorResponse(Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(createErrorXml("Error while executing the web service: " + e.getMessage(),"recipes-api"))
                .build();
    }

    private Drink getDrink(String json) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode drinksArray = jsonNode.get("drinks");
            Drink drink = buildDrink(drinksArray.get(0));
            return drink;
        } catch (IOException e) {
            throw new Exception();
        }
    }

    private Drink buildDrink(JsonNode cocktailNode){
        RecipeType recipeType = new RecipeType();
        recipeType.setAlcoholic(cocktailNode.get("strAlcoholic").asText());
        recipeType.setGlassType(cocktailNode.get("strDrink").asText());
        recipeType.setCategory(cocktailNode.get("strCategory").asText());

        IngredientsList ingredientsList = getIngredientsList(cocktailNode);
        CocktailIngredientsDetails ingredientsDetails = getIngredientsDetails(cocktailNode);

        Drink drink = new Drink();
        drink.setCocktailName(cocktailNode.get("strAlcoholic").asText());
        drink.setRecipeType(recipeType);
        drink.setImage(cocktailNode.get("strDrinkThumb").asText());
        drink.setIngredientsList(ingredientsList);
        drink.setIngredientsDetails(ingredientsDetails);
        drink.setInstructions(cocktailNode.get("strInstructions").asText());
        return drink;
    }

    private CocktailIngredientsDetails getIngredientsDetails(JsonNode cocktailNode) {

        CocktailIngredientsDetails cocktailIngredientsDetails = new CocktailIngredientsDetails();
        List<CocktailIngredientDetails> cocktailIngredientDetails = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            CocktailIngredientDetails ing = new CocktailIngredientDetails();
            String ingredientKey = "strIngredient" + i;
            String measureKey = "strMeasure" + i;
            JsonNode ingredientNode = cocktailNode.get(ingredientKey);
            JsonNode measureNode = cocktailNode.get(measureKey);
            if (!ingredientNode.isNull()) {
                ing.setText(measureNode != null ? measureNode.asText() : "" +" " + ingredientNode.asText());
                ing.setName(ingredientNode.asText());
                ing.setMeasure(measureNode != null ? measureNode.asText() : "");

                cocktailIngredientDetails.add(ing);
            }
        }

        cocktailIngredientsDetails.setIngredientDetails(cocktailIngredientDetails);
        return cocktailIngredientsDetails;
    }

    private IngredientsList getIngredientsList(JsonNode cocktailNode) {
        IngredientsList ingredientsList = new IngredientsList();
        List<String> ingredients = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            String ingredientKey = "strIngredient" + i;
            JsonNode ingredient = cocktailNode.get(ingredientKey);
            if (!ingredient.isNull()) {
                ingredients.add(ingredient.asText());
            }
        }
        ingredientsList.setIngredient(ingredients);
        return ingredientsList;
    }


    public String createErrorXml(String errorMessage,String apiFailed) {
        StringBuilder xmlBuilder = new StringBuilder("<Cocktail xmlns=\"http://www.example.com/cocktail\"\n" +
                "          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "          xsi:schemaLocation=\"http://www.example.com/cocktail  cocktail_recipe.xsd\">");
        xmlBuilder.append("<error>" + errorMessage + "</error>"
                +"<api_failed>"+apiFailed+"</api_failed>"+
                "</Cocktail>");
        return xmlBuilder.toString();
    }

    public Object createErrorMessage(ResponseAPI r, String apiFailed){
        Error error = new Error();
        error.setError(r.getResponse());
        error.setApiFailed(apiFailed);
        return error;
    }

}
