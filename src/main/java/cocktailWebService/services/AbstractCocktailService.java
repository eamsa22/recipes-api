package cocktailWebService.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import communs.ResponseAPI;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractCocktailService implements CocktailService {

    boolean isMenu =false;
    private String COCKTAIL_API_BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/";
    private String apiUrl;
    private static Client client = ClientBuilder.newClient();
    private WebTarget target;
    private Response response ;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void setCOCKTAIL_API_BASE_URL(String COCKTAIL_API_BASE_URL) {
        this.COCKTAIL_API_BASE_URL = COCKTAIL_API_BASE_URL;
    }

    public Response getRandomCocktailFilteredByAlcoholic(boolean alcoholic) {
        try {
            ResponseAPI jsonResponse = getRandomFilteredCocktailFromTheCocktailDb(alcoholic);
            return handleResponse(jsonResponse);
        } catch (Exception e) {
            return handleErrorResponse(e);
        }
    }
    public Response getRandomCocktailFilteredByIngredient(String ingredient) {
        try {
            ResponseAPI jsonResponse = getRandomCocktailFilteredByIngredientFromCocktailDB(ingredient);
            return handleResponse(jsonResponse);
        } catch (Exception e) {
            return handleErrorResponse(e);
        }
    }
    public Response getRandomCocktailFilteredByIngredientAndAlcoholic(String ingredient, boolean alcoholic) {
        try {
            ResponseAPI jsonResponse = getRandomCocktailFilteredByIngredientAndAlcoholicFromCocktailDB(ingredient,alcoholic);
            return handleResponse(jsonResponse);
        } catch (Exception e) {
            return handleErrorResponse(e);
        }
    }

    private ResponseAPI getRandomCocktailFilteredByIngredientAndAlcoholicFromCocktailDB(String ingredient,boolean alcoholic) throws Exception {
        ResponseAPI responseAPI;
        apiUrl = COCKTAIL_API_BASE_URL+"filter.php?a="+ (alcoholic ? "Alcoholic" : "Non_Alcoholic");
        target = client.target(apiUrl);
        response = target.request(MediaType.APPLICATION_JSON).get();
        String responseEntity =response.readEntity(String.class );
        if (response.getStatus() == Response.Status.OK.getStatusCode() & (! responseEntity.isEmpty())) {
            String jsonResult1 = responseEntity;
            apiUrl = COCKTAIL_API_BASE_URL+"filter.php?i="+ ingredient;
            target = client.target(apiUrl);
            response = target.request(MediaType.APPLICATION_JSON).get();
            responseEntity =response.readEntity(String.class );
            if (response.getStatus() == Response.Status.OK.getStatusCode()& (! responseEntity.isEmpty())) {
                String jsonResult2= responseEntity;
                List<String> listDrinkIds= getCommonDrinkIds(jsonResult1,jsonResult2);
                String randomId = getRandomDrinkId(listDrinkIds);
                return getRandomCocktailFilteredById(randomId);
            }else{
                String errorMsg = "" ;

                if(!responseEntity.isEmpty()){
                    // Extraire les messages d'erreur h3 et h4 à l'aide d'expressions régulières
                    String htmlContent = responseEntity;
                    String h3Pattern = "<h3>(.*?)<\\/h3>";
                    String h4Pattern = "<h4>(.*?)<\\/h4>";

                    String h3Error = extractPattern(htmlContent, h3Pattern);
                    String h4Error = extractPattern(htmlContent, h4Pattern);

                    if (h3Error != null && h4Error != null) {
                        errorMsg += h3Error + " " + h4Error;
                    } else {
                        errorMsg += "Unknown error message.";
                    }
                }
                else{
                    errorMsg+= " no drink can be retrieved with the specified ingredient "+ingredient;
                }

                responseAPI = ResponseAPI.BAD_REQUEST;
                responseAPI.setResponse(errorMsg);
                responseAPI.setApiFailed("recipes-api");
                return responseAPI;
            }
        }
        else {
            String errorMsg = "" ;

            if(!responseEntity.isEmpty()){
                // Extraire les messages d'erreur h3 et h4 à l'aide d'expressions régulières
                String htmlContent = responseEntity;
                String h3Pattern = "<h3>(.*?)<\\/h3>";
                String h4Pattern = "<h4>(.*?)<\\/h4>";

                String h3Error = extractPattern(htmlContent, h3Pattern);
                String h4Error = extractPattern(htmlContent, h4Pattern);

                if (h3Error != null && h4Error != null) {
                    errorMsg += h3Error + " " + h4Error;
                } else {
                    errorMsg += "Unknown error message.";
                }
            }
            else{
                errorMsg+= " no drink can be retrieved with the specified  alcoholic filter as "+alcoholic;
            }

            responseAPI = ResponseAPI.BAD_REQUEST;
            responseAPI.setResponse(errorMsg);
            responseAPI.setApiFailed("recipes-api");
            return responseAPI;
        }
    }


    public static List<String> getCommonDrinkIds(String jsonResult1, String jsonResult2) throws Exception {
        List<String> drinkIds1 = extractDrinkIds(jsonResult1);
        List<String> drinkIds2 = extractDrinkIds(jsonResult2);

        // Créer une liste contenant les identifiants de boisson communs
        List<String> commonDrinkIds = new ArrayList<>();
        for (String id : drinkIds1) {
            if (drinkIds2.contains(id)) {
                commonDrinkIds.add(id);
            }
        }

        return commonDrinkIds;
    }

    private static List<String> extractDrinkIds(String jsonString) throws Exception {
        List<String> drinkIds = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        JsonNode drinksArray = rootNode.get("drinks");
        if (drinksArray != null) {
            for (JsonNode element : drinksArray) {
                String drinkId = element.get("idDrink").asText();
                drinkIds.add(drinkId);
            }
        }
        return drinkIds;
    }

    public static String getRandomDrinkId(List<String> drinkIds) {
        if (drinkIds.isEmpty()) {
            return null;
        }
        int randomIndex = (int) (Math.random() * drinkIds.size());
        return drinkIds.get(randomIndex);
    }

    public Response getRandomCocktailWithNoFilter() {
        try {
            ResponseAPI jsonResponse = getRandomCocktailFromTheCocktailDb();
            return handleResponse(jsonResponse);
        } catch (Exception e) {
            return handleErrorResponse(e);
        }
    }
    protected abstract Response handleResponse(ResponseAPI responseAPI)throws Exception;
    protected abstract Response handleErrorResponse(Exception e) ;

    protected ResponseAPI getRandomCocktailFromTheCocktailDb() throws Exception {
        apiUrl = COCKTAIL_API_BASE_URL + "random.php";
        target = client.target(apiUrl);
        response = target.request(MediaType.APPLICATION_JSON).get();
        return getResponseAPI(response);
    }

    protected ResponseAPI getRandomFilteredCocktailFromTheCocktailDb(boolean alcoholic) throws Exception{
        ResponseAPI responseAPI;
        apiUrl = COCKTAIL_API_BASE_URL+"filter.php?a="+ (alcoholic ? "Alcoholic" : "Non_Alcoholic");
        target = client.target(apiUrl);
        response = target.request(MediaType.APPLICATION_JSON).get();
        String responseEntity =response.readEntity(String.class);
        if (response.getStatus() == Response.Status.OK.getStatusCode() & (! responseEntity.isEmpty())) {
            String jsonResult = responseEntity;
            String randomId = getRandomIdFromJsonString(jsonResult);
            return getRandomCocktailFilteredById(randomId);
        }
        else {
            String errorMsg = "Error from the external API : " ;

            if(!responseEntity.isEmpty()){
                // Extraire les messages d'erreur h3 et h4 à l'aide d'expressions régulières
                String htmlContent = responseEntity;
                String h3Pattern = "<h3>(.*?)<\\/h3>";
                String h4Pattern = "<h4>(.*?)<\\/h4>";

                String h3Error = extractPattern(htmlContent, h3Pattern);
                String h4Error = extractPattern(htmlContent, h4Pattern);

                if (h3Error != null && h4Error != null) {
                    errorMsg += h3Error + " " + h4Error;
                } else {
                    errorMsg += "Unknown error message.";
                }
            }
            else{
                errorMsg+= " no drink can be retrieved with the specified alcoholic filter as "+alcoholic;
            }

            responseAPI = ResponseAPI.INTERNAL_SERVER_ERROR;
            responseAPI.setResponse(errorMsg);
            responseAPI.setApiFailed("cocktailDB");
            return responseAPI;
        }
    }

    private ResponseAPI getRandomCocktailFilteredByIngredientFromCocktailDB(String ingredient) throws Exception {
        ResponseAPI responseAPI;
        apiUrl = COCKTAIL_API_BASE_URL+"filter.php?i="+ ingredient;
        target = client.target(apiUrl);
        response = target.request(MediaType.APPLICATION_JSON).get();
        String responseEntity =response.readEntity(String.class);
        if (response.getStatus() == Response.Status.OK.getStatusCode() & (! responseEntity.isEmpty())) {
            String randomId = getRandomIdFromJsonString(responseEntity);
            return getRandomCocktailFilteredById(randomId);
        }
        else {
            String errorMsg = "" ;

            if(!responseEntity.isEmpty()){
                // Extraire les messages d'erreur h3 et h4 à l'aide d'expressions régulières
                String htmlContent = responseEntity;
                String h3Pattern = "<h3>(.*?)<\\/h3>";
                String h4Pattern = "<h4>(.*?)<\\/h4>";

                String h3Error = extractPattern(htmlContent, h3Pattern);
                String h4Error = extractPattern(htmlContent, h4Pattern);

                if (h3Error != null && h4Error != null) {
                    errorMsg += h3Error + " " + h4Error;
                } else {
                    errorMsg += "Unknown error message.";
                }
            }
            else{
                errorMsg+= " no drink can be retrieved with the specified ingredient "+ingredient;
            }

            responseAPI = ResponseAPI.BAD_REQUEST;
            responseAPI.setResponse(errorMsg);
            responseAPI.setApiFailed("recipes-api");
            return responseAPI;
        }
    }
    private ResponseAPI getRandomCocktailFilteredById(String recipeId){
        apiUrl = COCKTAIL_API_BASE_URL+"lookup.php?i="+ recipeId;
        target = client.target(apiUrl);
        response = target.request(MediaType.APPLICATION_JSON).get();
        return getResponseAPI(response);
    }
    private ResponseAPI getResponseAPI(Response response)  {
        ResponseAPI responseAPI;
        String responseEntity =response.readEntity(String.class);
        if (response.getStatus() == Response.Status.OK.getStatusCode() &  responseEntity!= null) {
            responseAPI= ResponseAPI.OK;
            responseAPI.setResponse(responseEntity);
        }
        else {
            String errorMsg = "Error from the external API with , no drink can be retrieved";
            responseAPI= ResponseAPI.INTERNAL_SERVER_ERROR;
            responseAPI.setResponse(errorMsg);
            responseAPI.setApiFailed("cocktailDB");
        }
        return responseAPI;
    }

    private String getRandomIdFromJsonString(String jsonString) throws Exception {
        JsonNode rootNode = objectMapper.readTree(jsonString);
        JsonNode drinksArray = rootNode.get("drinks");
        int randomIndex = new Random().nextInt(drinksArray.size());
        JsonNode randomObject = drinksArray.get(randomIndex);
        return randomObject.get("idDrink").asText();
    }

    private String extractPattern(String input, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }
    @Override
    public void setIsMenu(boolean isMenu) {
        this.isMenu=isMenu;
    }

    @Override
    public boolean isMenu() {
        return this.isMenu;
    }

}