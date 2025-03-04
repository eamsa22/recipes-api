package recipeWebService.controllers;


import communs.Version;

import jakarta.ws.rs.*;

import jakarta.xml.bind.JAXBException;
import model.version1.meal.Error;
import model.version1.meal.Meal;
import model.version2.MealDrinkError;
import model.version2.MealSuccess;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/")
public class RecipeController {

    private RecipeDispatcher recipeDispatcher ;

    @GET
    @Path("recipe/meal/{cuisineType}")
    @Produces(MediaType.APPLICATION_XML)
    @Operation(summary = "Get a random cuisine recipe with xml response")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "A cuisine recipe is successfully retrieved",content = @Content(
                    mediaType = "application/xml",schema = @Schema(implementation = Meal.class) ,examples = @ExampleObject(value = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<Recipe xmlns=\"http://www.example.com/cuisine\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.com/cuisine  cuisine_recipe.xsd\">\n" +
                    "    <RecipeName>Pizza Dough From 'The Kitchn Cookbook'</RecipeName>\n" +
                    "    <RecipeDescription>\n" +
                    "        <cuisineType>italian</cuisineType>\n" +
                    "        <mealType>lunch/dinner</mealType>\n" +
                    "        <dishType>main course</dishType>\n" +
                    "    </RecipeDescription>\n" +
                    "    <image>\n" +
                    "        https://edamam-product-images.s3.amazonaws.com/web-img/85e/85ee0dadf80fb207641908fd80d02901.jpg\n" +
                    "    </image>\n" +
                    "    <RecipeSource>\n" +
                    "        <source>Serious Eats</source>\n" +
                    "        <url>https://www.seriouseats.com/pizza-dough-from-the-kitchn-cookbook</url>\n" +
                    "    </RecipeSource>\n" +
                    "    <calories>923.0</calories>\n" +
                    "    <totalTime>100.0</totalTime>\n" +
                    "    <ingredientLines>\n" +
                    "        <ingredient>3/4 cup (6 ounces) lukewarm water</ingredient>\n" +
                    "        <ingredient>1 teaspoon active dry yeast or instant yeast</ingredient>\n" +
                    "        <ingredient>2 cups unbleached all-purpose flour, plus more as needed</ingredient>\n" +
                    "        <ingredient>1 1/2 teaspoons salt</ingredient>\n" +
                    "    </ingredientLines>\n" +
                    "    <ingredientsDetails>\n" +
                    "        <ingredientDetails>\n" +
                    "            <text>3/4 cup (6 ounces) lukewarm water</text>\n" +
                    "            <quantity>6.0</quantity>\n" +
                    "            <measure>ounce</measure>\n" +
                    "            <food>water</food>\n" +
                    "            <weight>170.09713875</weight>\n" +
                    "            <foodCategory>water</foodCategory>\n" +
                    "            <foodId>food_a99vzubbk1ayrsad318rvbzr3dh0</foodId>\n" +
                    "            <image>https://www.edamam.com/food-img/5dd/5dd9d1361847b2ca53c4b19a8f92627e.jpg</image>\n" +
                    "        </ingredientDetails>\n" +
                    "        <ingredientDetails>\n" +
                    "            <text>1 teaspoon active dry yeast or instant yeast</text>\n" +
                    "            <quantity>1.0</quantity>\n" +
                    "            <measure>teaspoon</measure>\n" +
                    "            <food>yeast</food>\n" +
                    "            <weight>4.0</weight>\n" +
                    "            <foodCategory>condiments and sauces</foodCategory>\n" +
                    "            <foodId>food_a2xisx4br72i19btvvxgzayoelqj</foodId>\n" +
                    "            <image>https://www.edamam.com/food-img/433/433749733fd8a22560cdb451b1317be1.jpg</image>\n" +
                    "        </ingredientDetails>\n" +
                    "        <ingredientDetails>\n" +
                    "            <text>2 cups unbleached all-purpose flour, plus more as needed</text>\n" +
                    "            <quantity>2.0</quantity>\n" +
                    "            <measure>cup</measure>\n" +
                    "            <food>all-purpose flour</food>\n" +
                    "            <weight>250.0</weight>\n" +
                    "            <foodCategory>grains</foodCategory>\n" +
                    "            <foodId>food_ar3x97tbq9o9p6b6gzwj0am0c81l</foodId>\n" +
                    "            <image>https://www.edamam.com/food-img/368/368077bbcab62f862a8c766a56ea5dd1.jpg</image>\n" +
                    "        </ingredientDetails>\n" +
                    "        <ingredientDetails>\n" +
                    "            <text>1 1/2 teaspoons salt</text>\n" +
                    "            <quantity>1.5</quantity>\n" +
                    "            <measure>teaspoon</measure>\n" +
                    "            <food>salt</food>\n" +
                    "            <weight>9.0</weight>\n" +
                    "            <foodCategory>Condiments and sauces</foodCategory>\n" +
                    "            <foodId>food_btxz81db72hwbra2pncvebzzzum9</foodId>\n" +
                    "            <image>https://www.edamam.com/food-img/694/6943ea510918c6025795e8dc6e6eaaeb.jpg</image>\n" +
                    "        </ingredientDetails>\n" +
                    "    </ingredientsDetails>\n" +
                    "    <healthLabels>\n" +
                    "        <healthLabel>Sugar-Conscious</healthLabel>\n" +
                    "        <healthLabel>Vegan</healthLabel>\n" +
                    "    </healthLabels>\n" +
                    "</Recipe>"))),
            @ApiResponse(responseCode = "400", description = "the cuisine type is invalid", content = @Content(schema = @Schema(implementation = Error.class),examples = @ExampleObject("<Recipe xmlns=\"http://www.example.com/cuisine\"\n" +
                    "          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "          xsi:schemaLocation=\"http://www.example.com/cuisine  cuisine_recipe.xsd\">\n" +
                    "    <error>Error the value of the parameter cuisineType  : Moroccan is invalid</error>\n" +
                    "    <api_failed>recipes-api</api_failed>\n" +
                    "</Recipe>"))),
            @ApiResponse(responseCode = "500", description = "problem with the external or internal api",content = @Content(schema = @Schema(implementation = Error.class),examples = @ExampleObject("<Recipe xmlns=\"http://www.example.com/cuisine\"\n" +
                    "          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "          xsi:schemaLocation=\"http://www.example.com/cuisine  cuisine_recipe.xsd\">\n" +
                    "    <error>Error from the external API</error>\n" +
                    "    <api_failed>Edamam</api_failed>\n" +
                    "</Recipe>")))
    })
    public Response handleRecipeRequestV1(
            @Parameter(name = "cuisineType", required = true)
            @PathParam("cuisineType") String cuisineType) throws JAXBException {
        this.recipeDispatcher = new RecipeDispatcher(Version.VERSION1);
            return recipeDispatcher.getRecipe(cuisineType);
    }

    @GET
    @Path("v2/recipe/meal/{cuisineType}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get a random cuisine recipe with Json response")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "A cuisine recipe is successfully retrieved",content = @Content(
                    mediaType = "application/json",schema = @Schema(implementation = MealSuccess.class),examples = @ExampleObject(value = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"name\": \"Pasta al Limone Recipe\",\n" +
                    "    \"type\": \"main course\",\n" +
                    "    \"country\": \"italian\",\n" +
                    "    \"preparationTime\": \"15.0\",\n" +
                    "    \"imageURL\": \"https://edamam-product-images.s3.amazonaws.com/web-img\",\n" +
                    "    \"source\": \"Serious Eats\",\n" +
                    "    \"ingredients\": \"5 tablespoons (70g) unsalted butter,Finely grated zest of 1 lemon, plus fresh juice to taste and additional grated zest for serving,1 medium clove garlic, minced,Kosher salt,1 pound (450g) fresh spaghetti,1 ounce (30g) grated Parmigiano-Reggiano cheese, plus more for serving,Freshly ground black pepper,\",\n" +
                    "    \"detailedIngredients\": [\n" +
                    "        {\n" +
                    "            \"name\": \"5 tablespoons (70g) unsalted butter\",\n" +
                    "            \"quantity\": \"70.0\",\n" +
                    "            \"image\": \"https://www.edamam.com/food-img/713/71397239b670d88c04faa8d05035cab4.jpg\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"name\": \"Finely grated zest of 1 lemon, plus fresh juice to taste and additional grated zest for serving\",\n" +
                    "            \"quantity\": \"1.0\",\n" +
                    "            \"image\": \"https://www.edamam.com/food-img/70a/70acba3d4c734d7c70ef4efeed85dc8f.jpg\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"name\": \"1 medium clove garlic, minced\",\n" +
                    "            \"quantity\": \"1.0\",\n" +
                    "            \"image\": \"https://www.edamam.com/food-img/6ee/6ee142951f48aaf94f4312409f8d133d.jpg\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"name\": \"Kosher salt\",\n" +
                    "            \"quantity\": \"0.0\",\n" +
                    "            \"image\": \"https://www.edamam.com/food-img/694/6943ea510918c6025795e8dc6e6eaaeb.jpg\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"name\": \"1 pound (450g) fresh spaghetti\",\n" +
                    "            \"quantity\": \"1.0\",\n" +
                    "            \"image\": \"https://www.edamam.com/food-img/882/8825533f89f0fde6397f43b22ef20cfe.jpg\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"name\": \"1 ounce (30g) grated Parmigiano-Reggiano cheese, plus more for serving\",\n" +
                    "            \"quantity\": \"1.0\",\n" +
                    "            \"image\": \"https://www.edamam.com/food-img/f58/f588658627c59d5041e4664119829aa9.jpg\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"name\": \"Freshly ground black pepper\",\n" +
                    "            \"quantity\": \"0.0\",\n" +
                    "            \"image\": \"https://www.edamam.com/food-img/c6e/c6e5c3bd8d3bc15175d9766971a4d1b2.jpg\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"instructions\": []\n" +
                    "}"))
                    ),
            @ApiResponse(responseCode = "400", description = "the cuisine type is invalid",content = @Content(
                    mediaType = "application/json",schema = @Schema(implementation = MealDrinkError.class),examples = @ExampleObject(value ="{\n" +
                    "    \"api_failed\": \"recipes-api\",\n" +
                    "    \"success\": false,\n" +
                    "    \"api_status\": \"400\"\n" +
                    "}"))),
            @ApiResponse(responseCode = "500", description = "problem with the external or internal api",content = @Content(
                    mediaType = "application/json",schema = @Schema(implementation = MealDrinkError.class),examples = @ExampleObject(value ="{\n" +
                    "    \"api_failed\": \"Edamam\",\n" +
                    "    \"success\": false,\n" +
                    "    \"api_status\": \"500\"\n" +
                    "}")))
    })
    public Response handleRecipeRequestV2(
            @Parameter(name = "cuisineType", required = true)
            @PathParam("cuisineType") String cuisineType) throws JAXBException {
        this.recipeDispatcher = new RecipeDispatcher(Version.VERSION2);
        return recipeDispatcher.getRecipe(cuisineType);
    }

}
