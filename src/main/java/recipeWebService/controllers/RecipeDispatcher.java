package recipeWebService.controllers;

import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.JAXBException;
import recipeWebService.services.RecipeService;
import recipeWebService.services.RecipeV1Service;
import recipeWebService.services.RecipeV2Service;
import communs.Version;

public class RecipeDispatcher {

    private RecipeService recipeService ;
    public RecipeDispatcher(Version version){
        switch(version) {
            case VERSION1:
                this.recipeService = new RecipeV1Service() ;
                break;
            case VERSION2:
                this.recipeService = new RecipeV2Service() ;
                break;
        }
    }


    public Response getRecipe(String cuisineType) throws JAXBException {
        return recipeService.getRandomRecipe(cuisineType);
    }
}
