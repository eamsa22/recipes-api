package recipeWebService.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import communs.ResponseAPI;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class AbstractRecipeService implements RecipeService {

    private boolean isMenu= false;

    private String EDAMAM_API_URL = "https://api.edamam.com/search";
    private String APP_ID = "ce5437e5";
    private String APP_KEY = "b9ed058e8cc35d94f4d989d9b480fc29";

    private int numAppEdamamCalling = 0;
    private int idOfEdamamInfo= 0;
    List<String> appIds = List.of("ce5437e5","25f98b30");
    List<String> appKeys = List.of("b9ed058e8cc35d94f4d989d9b480fc29","1bb7e09c4ffbbd7af9de362517322f9a");

    private static Client client = ClientBuilder.newClient();

    private WebTarget target ;
    private Response response ;

    private ObjectMapper objectMapper = new ObjectMapper();

    public boolean isMenu() {
        return isMenu;
    }

    public void setMenu(boolean menu) {
        isMenu = menu;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public void setAPP_KEY(String APP_KEY) {
        this.APP_KEY = APP_KEY;
    }

    public Response getRandomRecipe(String dishType, String cuisineType, List<String> healthLabels, int time) {
        try {
            ResponseAPI jsonResponse = getRandomRecipeFromEDAMAM(cuisineType, dishType, healthLabels, time);
            return handleResponse(jsonResponse);
        } catch (Exception e){
            return handleErrorResponse(e);
        }
    }

    public Response getRandomRecipe(String cuisineType) {
        return getRandomRecipe(null,cuisineType, List.of(),0);
    }

    protected ResponseAPI getRandomRecipeFromEDAMAM(String cuisineType, String dishType, List<String> healthLabels, int time) throws Exception {
        int maxResults = 99;
        int randomStart = new Random().nextInt(maxResults);
        List<String> dishTypes;
        if (dishType == null) {
            dishTypes = Arrays.asList("Biscuits and cookies", "Bread", "Cereals", "Condiments and sauces",
                    "Desserts", "Egg", "Main course", "Omelet", "Pancake", "Preps", "Preserve",
                    "Salad", "Sandwiches", "Soup", "Starter");
        } else {
            dishTypes = List.of(dishType);
        }
        UriBuilder uriBuilder = UriBuilder.fromUri(EDAMAM_API_URL)
                .queryParam("q", "")
                .queryParam("cuisineType", cuisineType)
                .queryParam("dishType", dishTypes.toArray())
                .queryParam("app_id", APP_ID)
                .queryParam("app_key", APP_KEY)
                .queryParam("from", randomStart)
                .queryParam("to", randomStart + 1);
        if (time != 0) {
            uriBuilder.queryParam("time", time);
        }

        if (!healthLabels.isEmpty()) {
            uriBuilder.queryParam("health", healthLabels.toArray());
        }
        if(numAppEdamamCalling >= 10 ){
            idOfEdamamInfo = 1*(idOfEdamamInfo % 1);
            setAPP_ID(appIds.get(idOfEdamamInfo));
            setAPP_KEY(appKeys.get(idOfEdamamInfo));
            numAppEdamamCalling =0 ;
        }
        numAppEdamamCalling ++ ;
        target = client.target(uriBuilder.build());
        response = target.request(MediaType.APPLICATION_JSON).get();
        return getResponseAPI(response, cuisineType);
    }


    protected abstract Response handleResponse(ResponseAPI responseAPI)throws Exception;
    protected abstract Response handleErrorResponse(Exception e) ;



    private ResponseAPI getResponseAPI(Response response , String cuisineType) throws Exception {
        ResponseAPI responseAPI =null;
        String jasonResult = response.readEntity(String.class);
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            int hitNodeSize = getHitNodeSize(jasonResult);
            if (hitNodeSize>0){
                responseAPI= ResponseAPI.OK;
                responseAPI.setResponse(jasonResult);
            }
            else if (hitNodeSize == 0){
                responseAPI= ResponseAPI.BAD_REQUEST;
                String errorMsg = "Error the value of the parameter cuisineType  : "+ cuisineType +" is invalid" ;
                responseAPI.setResponse(errorMsg);
                responseAPI.setApiFailed("recipes-api");
            }
        }
        else {
            String errorMsg = "Error from the external API with status code: " + response.getStatus() +
                    " and error message: " + jasonResult;
            responseAPI= ResponseAPI.INTERNAL_SERVER_ERROR;
            responseAPI.setResponse(errorMsg);
            responseAPI.setApiFailed("Edamam");
        }
        return responseAPI;
    }

    private int getHitNodeSize(String jsonResult) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResult);
        JsonNode hitsNode = rootNode.get("hits");
        return hitsNode.size();
    }


}
