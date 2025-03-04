package menuWebService.services;

import cocktailWebService.services.CocktailService;
import cocktailWebService.services.CocktailV2Service;
import jakarta.ws.rs.core.Response;
import model.version2.DrinkSuccess;
import model.version2.MealDrinkError;
import model.version3.ErrorMenu;
import model.version3.MealSuccessV3;
import model.version3.Menu;
import recipeWebService.services.RecipeService;
import recipeWebService.services.RecipeV2Service;

import java.util.ArrayList;
import java.util.List;


public class menuService {

    private RecipeService recipeService = new RecipeV2Service();
    private CocktailService cocktailService = new CocktailV2Service();
    private Menu menu = new Menu();

    public menuService(){
        this.cocktailService.setIsMenu(true);
        this.recipeService.setMenu(true);
    }
    public Response getRandomMenu(String cuisineType, List<String> healthLabels,int totalTime , String alcoholic, String ingredient) {

            int timeStarter= (int) (0.25 * totalTime);
            int timeMainCourse= (int) (0.5 * totalTime);
            int timeDessert= (int) (0.25 * totalTime);

            Response mealsResponse= getMeals(cuisineType,healthLabels,timeStarter,timeMainCourse,timeDessert);
            if(mealsResponse.getStatus()!= Response.Status.OK.getStatusCode()){
                return Response.status(mealsResponse.getStatus()).entity(mealsResponse.getEntity())
                        .build();

            }

            Response drink = getDrink(alcoholic,ingredient);
            if(drink.getStatus() != Response.Status.OK.getStatusCode()){
                return Response.status(drink.getStatus()).entity(drink.getEntity())
                        .build();
            }
            List<MealSuccessV3> meals = (List<MealSuccessV3>) mealsResponse.getEntity();
            float preparationTime = Float.parseFloat(meals.get(0).getMeal().getPreparationTime())+Float.parseFloat(meals.get(1).getMeal().getPreparationTime())+Float.parseFloat(meals.get(2).getMeal().getPreparationTime());
            float calories = Float.parseFloat(meals.get(0).getCalories())+Float.parseFloat(meals.get(1).getCalories())+Float.parseFloat(meals.get(2).getCalories());
            menu.setStarter(meals.get(0).getMeal());
            menu.setMainCourse(meals.get(1).getMeal());
            menu.setDessert(meals.get(2).getMeal());
            menu.setDrink((DrinkSuccess) drink.getEntity());
            menu.setMenuPreparationTime(preparationTime);
            menu.setMenuTotalCalories(calories);
            return Response.status(Response.Status.OK).entity(menu).build();



    }



    private Response getMeals(String cuisineType,List<String> healthLabels, int timeStarter, int timeMainCourse, int timeDessert){
        List<MealSuccessV3> mealsList = new ArrayList<>();
        Response starter = recipeService.getRandomRecipe("Starter", cuisineType,healthLabels,timeStarter);
        if(starter.getStatus() != Response.Status.OK.getStatusCode()){
            return Response.status(starter.getStatus()).entity(starter.getEntity())
                    .build();
        }
        Response mainCourse = recipeService.getRandomRecipe( "main course",cuisineType,healthLabels,timeMainCourse);
        if(mainCourse.getStatus() != Response.Status.OK.getStatusCode()){
            return Response.status(mainCourse.getStatus()).entity(mainCourse.getEntity())
                    .build();
        }
        Response dessert = recipeService.getRandomRecipe("Desserts",cuisineType,healthLabels,timeDessert);
        if(dessert.getStatus() != Response.Status.OK.getStatusCode()){
            return Response.status(dessert.getStatus()).entity(dessert.getEntity())
                    .build();
        }
        mealsList.add((MealSuccessV3) starter.getEntity());
        mealsList.add((MealSuccessV3) mainCourse.getEntity());
        mealsList.add((MealSuccessV3) dessert.getEntity());

        return Response.status(Response.Status.OK)
                .entity(mealsList)
                .build();

    }
    private Response getDrink(String alcoholic , String ingredient){
        Response drink;
        if(ingredient != null){
            if(alcoholic == null){
                drink = cocktailService.getRandomCocktailFilteredByIngredient(ingredient);
            }
            else if(alcoholic.equals("true")){
                drink = cocktailService.getRandomCocktailFilteredByIngredientAndAlcoholic(ingredient,true);
            }
            else if(alcoholic.equals("false")){
                drink = cocktailService.getRandomCocktailFilteredByIngredientAndAlcoholic(ingredient,false);
            }
            else{
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(createErrorMessage(" the alcohol parameter "+alcoholic+" is invalid","recipes-api","400"))
                        .build();
            }
        }
        else{
            if(alcoholic == null){
                drink = cocktailService.getRandomCocktailWithNoFilter();
            }
            else if(alcoholic.equals("true")){
                drink = cocktailService.getRandomCocktailFilteredByAlcoholic(true);
            }
            else if(alcoholic.equals("false")){
                drink = cocktailService.getRandomCocktailFilteredByAlcoholic(false);
            }
            else{
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(createErrorMessage(" the alcohol parameter "+alcoholic+" is invalid","recipes-api","400"))
                        .build();
            }
        }
        return drink;
    }

    public ErrorMenu createErrorMessage(String message, String apiFailed, String codeStatus) {
        ErrorMenu error = new ErrorMenu();
        error.setApi_failed(apiFailed);
        error.setError(message);
        error.setApi_status(codeStatus);
        return error;
    }

}
