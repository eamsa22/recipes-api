package cocktailWebService.controllers;


import communs.Version;
import model.version1.cocktail.Drink;
import model.version1.cocktail.Error;
import model.version2.DrinkSuccess;
import model.version2.MealDrinkError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;


@Path("/")
public class CocktailController {

    private CocktailDispatcher cocktailDispatcher;

    @GET
    @Path("recipe/drink")
    @Produces(MediaType.APPLICATION_XML)
    @Operation(summary = "Get cocktail recipe filtered eventually by alcohol presence with xml response")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "a drink is successfully retrieved",
                    content = @Content(mediaType = "application/xml",
                            schema = @Schema(implementation = Drink.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                                    "<Cocktail xmlns=\"http://www.example.com/cocktail\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.example.com/cocktail  cocktail_recipe.xsd\">\n" +
                                    "    <CocktailName>Ace</CocktailName>\n" +
                                    "    <RecipeType>\n" +
                                    "        <Alcoholic>Alcoholic</Alcoholic>\n" +
                                    "        <Category>Cocktail</Category>\n" +
                                    "        <GlassType>Martini Glass</GlassType>\n" +
                                    "    </RecipeType>\n" +
                                    "    <Image>https://www.thecocktaildb.com/images/media/drink/l3cd7f1504818306.jpg</Image>\n" +
                                    "    <IngredientsList>\n" +
                                    "        <Ingredient>Gin</Ingredient>\n" +
                                    "        <Ingredient>Grenadine</Ingredient>\n" +
                                    "        <Ingredient>Heavy cream</Ingredient>\n" +
                                    "        <Ingredient>Milk</Ingredient>\n" +
                                    "        <Ingredient>Egg White</Ingredient>\n" +
                                    "    </IngredientsList>\n" +
                                    "    <IngredientsDetails>\n" +
                                    "        <IngredientDetails>\n" +
                                    "            <Text>2 shots  Gin</Text>\n" +
                                    "            <Name>Gin</Name>\n" +
                                    "            <Measure>2 shots </Measure>\n" +
                                    "        </IngredientDetails>\n" +
                                    "        <IngredientDetails>\n" +
                                    "            <Text>1/2 shot  Grenadine</Text>\n" +
                                    "            <Name>Grenadine</Name>\n" +
                                    "            <Measure>1/2 shot </Measure>\n" +
                                    "        </IngredientDetails>\n" +
                                    "        <IngredientDetails>\n" +
                                    "            <Text>1/2 shot  Heavy cream</Text>\n" +
                                    "            <Name>Heavy cream</Name>\n" +
                                    "            <Measure>1/2 shot </Measure>\n" +
                                    "        </IngredientDetails>\n" +
                                    "        <IngredientDetails>\n" +
                                    "            <Text>1/2 shot Milk</Text>\n" +
                                    "            <Name>Milk</Name>\n" +
                                    "            <Measure>1/2 shot</Measure>\n" +
                                    "        </IngredientDetails>\n" +
                                    "        <IngredientDetails>\n" +
                                    "            <Text>1/2 Fresh Egg White</Text>\n" +
                                    "            <Name>Egg White</Name>\n" +
                                    "            <Measure>1/2 Fresh</Measure>\n" +
                                    "        </IngredientDetails>\n" +
                                    "    </IngredientsDetails>\n" +
                                    "    <Instructions>Shake all the ingredients in a cocktail shaker and ice then strain in a cold glass.</Instructions>\n" +
                                    "</Cocktail>")
                    )
            ),@ApiResponse(responseCode = "500", description = "Invalid parameter name or invalid parameter value or problem with the external or internal api",
                content = @Content(mediaType = "application/xml", schema = @Schema(implementation = Error.class), examples = @ExampleObject("<Cocktail xmlns=\"http://www.example.com/cocktail\"\n" +
                        "          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                        "          xsi:schemaLocation=\"http://www.example.com/cocktail  cocktail_recipe.xsd\">\n" +
                        "    <error>Error the value of the parameter Alcoholic : eeeeeeee is invalid</error>\n" +
                        "    <api_failed>recipes-api</api_failed>\n" +
                        "</Cocktail>")
                ))
    })
    public Response handleDrinkRequestV1(
            @Parameter(name = "alcoholic", description = "Filter cocktails by alcohol presence (true/false).", required = false)
            @QueryParam("alcoholic")String alcoholic, @Context UriInfo uriInfo) {
        this.cocktailDispatcher = new CocktailDispatcher(Version.VERSION1);
        return cocktailDispatcher.getRecipeCocktail(alcoholic,uriInfo);
    }
    @GET
    @Path("v2/recipe/drink")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get cocktail recipe filtered eventually by alcohol presence Json response")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "a drink is successfully retrieved",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation= DrinkSuccess.class),examples = @ExampleObject(value = "{\n" +
                            "    \"success\": true,\n" +
                            "    \"name\": \"A Night In Old Mandalay\",\n" +
                            "    \"type\": \"Ordinary Drink\",\n" +
                            "    \"alcoholic\": true,\n" +
                            "    \"imageURL\": \"https://www.thecocktaildb.com/images/media/drink/vyrvxt1461919380.jpg\",\n" +
                            "    \"ingredients\": [\n" +
                            "        \"Light rum\",\n" +
                            "        \"Añejo rum\",\n" +
                            "        \"Orange juice\",\n" +
                            "        \"Lemon juice\",\n" +
                            "        \"Ginger ale\",\n" +
                            "        \"Lemon peel\"\n" +
                            "    ],\n" +
                            "    \"detailedIngredients\": [\n" +
                            "        {\n" +
                            "            \"name\": \"Light rum\",\n" +
                            "            \"quantity\": \"1 oz \"\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"name\": \"Añejo rum\",\n" +
                            "            \"quantity\": \"1 oz \"\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"name\": \"Orange juice\",\n" +
                            "            \"quantity\": \"1 oz \"\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"name\": \"Lemon juice\",\n" +
                            "            \"quantity\": \"1/2 oz \"\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"name\": \"Ginger ale\",\n" +
                            "            \"quantity\": \"3 oz \"\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"name\": \"Lemon peel\",\n" +
                            "            \"quantity\": \"1 twist of \"\n" +
                            "        }\n" +
                            "    ],\n" +
                            "    \"instructions\": \"In a shaker half-filled with ice cubes, combine the light rum, añejo rum, orange juice, and lemon juice. Shake well. Strain into a highball glass almost filled with ice cubes. Top with the ginger ale. Garnish with the lemon twist.\"\n" +
                            "}"))),
            @ApiResponse(responseCode = "500", description = "Invalid parameter name or invalid parameter value or problem with the external or internal api",content = @Content(mediaType = "application/json",schema = @Schema(implementation = MealDrinkError.class),examples = @ExampleObject(value ="{\n" +
                    "    \"api_failed\": \"recipes-api\",\n" +
                    "    \"success\": false,\n" +
                    "    \"api_status\": \"500\"\n" +
                    "}" ))),
    })
    public Response handleDrinkRequestV2(
            @Parameter(name = "alcoholic", description = "Filter cocktails by alcohol presence (true/false).", required = false)
            @QueryParam("alcoholic")String alcoholic, @Context UriInfo uriInfo){
        this.cocktailDispatcher = new CocktailDispatcher(Version.VERSION2);
        return cocktailDispatcher.getRecipeCocktail(alcoholic,uriInfo);
    }
}
