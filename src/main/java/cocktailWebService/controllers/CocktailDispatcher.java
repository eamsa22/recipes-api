package cocktailWebService.controllers;

import cocktailWebService.services.CocktailService;
import cocktailWebService.services.CocktailV1Service;
import cocktailWebService.services.CocktailV2Service;
import communs.ResponseAPI;
import communs.Version;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import static java.lang.Boolean.parseBoolean;

public class CocktailDispatcher {

    private CocktailService cocktailService;
    public CocktailDispatcher(Version version) {
        switch (version) {
            case VERSION1:
                this.cocktailService = new CocktailV1Service();
                break;

            case VERSION2:
                this.cocktailService = new CocktailV2Service();
                break;
        }
    }
    public Response getRecipeCocktail(String alcoholic, UriInfo uriInfo) {

        if (uriInfo.getQueryParameters().containsKey("alcoholic")) {
            alcoholic =alcoholic.toLowerCase();
            if (alcoholic.equals("true") || alcoholic.equals("false")) {
                return cocktailService.getRandomCocktailFilteredByAlcoholic(parseBoolean(alcoholic));
            }
            else if(alcoholic.equals("")){
                ResponseAPI r = ResponseAPI.BAD_REQUEST;
                r.setResponse("Error the parameter Alcoholic is empty");
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(cocktailService.createErrorMessage(r,"recipes-api"))
                        .build();
            }
            else {
                ResponseAPI r = ResponseAPI.BAD_REQUEST;
                r.setResponse("Error the value of the parameter Alcoholic : " + alcoholic + " is invalid");
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(cocktailService.createErrorMessage(r,"recipes-api"))
                        .build();
            }
        }
        else {
            if (uriInfo.getQueryParameters().isEmpty()) {
                return cocktailService.getRandomCocktailWithNoFilter();
            }
            else {
                ResponseAPI r = ResponseAPI.BAD_REQUEST;
                r.setResponse("Error the parameter entered for drink  is invalid");
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(cocktailService.createErrorMessage(r,"recipes-api"))
                        .build();
            }
        }
    }

}
