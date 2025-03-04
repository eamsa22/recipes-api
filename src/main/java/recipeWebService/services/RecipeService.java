package recipeWebService.services;


import jakarta.ws.rs.core.Response;

import java.util.List;

public interface RecipeService {
    Response getRandomRecipe(String cuisineType);

    void setAPP_ID(String APP_ID) ;

    Response getRandomRecipe(String dishType, String cuisineType, List<String> healthLabels, int time);


    public boolean isMenu();

    public void setMenu(boolean menu);}

