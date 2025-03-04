package menuWebService.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import menuWebService.services.menuService;
import model.version3.ErrorMenu;
import model.version3.MenuFilters;
import model.version3.Menu;

import java.util.ArrayList;
import java.util.List;

@Path("/")
public class MenuController {

    private menuService menuService;

    public MenuController() {
        this.menuService = new menuService();
    }

    @POST
    @Path("menu")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = " Get a random Menu returning a json format response, filtering it based on parameters provided in the request body, also in a json format")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "a menu is successfully generated",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation= Menu.class),examples = @ExampleObject(value = "{\n" +
                            "    \"starter\": {\n" +
                            "        \"success\": true,\n" +
                            "        \"name\": \"Quick Crostini\",\n" +
                            "        \"type\": \"starter\",\n" +
                            "        \"country\": \"italian\",\n" +
                            "        \"preparationTime\": \"0.0\",\n" +
                            "        \"imageURL\": \"https://edamam-product-images.s3.amazonaws.com/web-img/f45/f4592c4dbe7eb62460fc612a1df36a7a?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEAwaCXVzLWVhc3QtMSJHMEUCIDVCt%2BiYPgEZeEKnUjA7X01lq1Fe2pkDSaXVoDyCXH9fAiEAvinWq0R6ksaPG0gLsYM7laInNhuiQUgYyECN5QcSeVkquAUIZRAAGgwxODcwMTcxNTA5ODYiDPYhODrSkDkQeUP%2BuyqVBc8CHf8IbGK7r9u8XNPXCnaXo85z%2BFRZLGkoO1VblQqloyed3AYBaTB0ufsi3zz7ZmRXHHHhpgeDBV9D3Ws30MoLda0Hg1WX5ZKmZ30BObIb%2F5BgNHpEEziW5GaQG1M56i1B%2FlYO6hFwpobiGNXAHus0VdHZjXXzumDlmKOdhKcVhoGg4xQlkXvA%2BvCkFJwbsF4aCH316UcaO2BXwYVkXhO%2FJ6uBfGPttPRoQS9Hh9qr%2FH5WUdhwRt2066GynfZhaVfx%2FisV1loEWtt1nUp4DMqSh%2BvhszUTzY4BHPndfgitFy5ePj9VQYc4rfHXS9Ih3DUfUJ8zGPjIB92S6g1LzW%2BQzkuCdEGJQ5yJrhgU6Neo%2BwQfL1kBICdDo%2B5GYAgRdU3CvAW%2BF7c7IbBlopo1HXeEpXO%2BOawcXCBb8jJX2UEqitV%2F5ATZyDzf4cDBvUOUND%2BVxooRj4%2F5s2J36nXE3X4YuFp25ZfRjfm69OXd8%2BJLAd38cbSNrMPSwdgY%2BJ%2B%2Belyvnk0PGAVuuKJR79%2FWIKRqJG9C4IM%2Bzz29TgMY3efFaToJAOz1S3y%2FQreGCQYFkr2%2FaNEc8UUGSGag4T9k4Z7A5YooDGgdHxCD1Wb%2B2oDdaztE7cIT3w6KV%2BTRG2NyNYIt7SbvbQOOFjMk%2FEbO0K49nBqVkSDQsKcRdIX4yO9kJ1HfNi4Vk%2BYWzuV2JilMOZ2RrCfoIhNlhoaNOu38AX22NGi3PMBHrZ%2Bz5JZPUmnHWopLCuauPoLlP3cqir0mxPXY%2FZB3FOsTD8Akm5Z431B0YrMG2DU6SaiytzLTtsQ0Oe7dyCVA%2FovoPJa%2Fbejocqg%2FhXV7UzdTeZx3H%2BmgzrqNdVQDK0N2BX5OfCNplh8cw0uiWmYwst3PsQY6sQHPBOXOFNxyXCTn%2B8p57EyaMqGjbrE7No9i2t9JVc8OGcUwQpJXvoavoAVpGjTr95zvlPd%2BSB7wfrmZ%2FrhRSKZk6rI%2BuNqqY1ux3p7hvqY6gNUpgvul6BrXn%2FBgu9rkaifUYsEpfgykf3WCJdF9Chw6B8pXn9x8OjJ3u2N3og9%2FP23%2B4xvEj01RkR0NRpbQe5QdBY8unDMALEMvjL7Z9HKzQzhyxunkcZqyM%2FFidMajSwU%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240502T200837Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFBNM3PVPO%2F20240502%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=0cdd2a2b14a45c30ff0e144999e2cb4bdb6a5143ba1b04dd194667074fab03d2\",\n" +
                            "        \"source\": \"Martha Stewart\",\n" +
                            "        \"ingredients\": \"1 five- to six-ounce baguette, sliced on the diagonal, 1/4 inch thick, 2 tablespoons olive oil, \",\n" +
                            "        \"detailedIngredients\": [\n" +
                            "            {\n" +
                            "                \"name\": \"1 five- to six-ounce baguette, sliced on the diagonal, 1/4 inch thick\",\n" +
                            "                \"quantity\": \"5.5\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/470/47053c77e167539c64fef3f2a3249bb2.jpg\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"2 tablespoons olive oil\",\n" +
                            "                \"quantity\": \"2.0\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/4d6/4d651eaa8a353647746290c7a9b29d84.jpg\"\n" +
                            "            }\n" +
                            "        ],\n" +
                            "        \"instructions\": []\n" +
                            "    },\n" +
                            "    \"mainCourse\": {\n" +
                            "        \"success\": true,\n" +
                            "        \"name\": \"Baked Pasta Casserole Recipe\",\n" +
                            "        \"type\": \"main course\",\n" +
                            "        \"country\": \"italian\",\n" +
                            "        \"preparationTime\": \"0.0\",\n" +
                            "        \"imageURL\": \"https://edamam-product-images.s3.amazonaws.com/web-img/1f6/1f61e9e5189c75cd1e9f2996011f18cd.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEAwaCXVzLWVhc3QtMSJHMEUCIQDRIh7N61hZ646CngKiIY%2Fo8PoRsATKUp1mah%2BoEfqHHQIgVgtpG%2Bg5%2BlkOZhzI6f9kGB%2F8ADhPtZWgUOXRh1gVUQ0quQUIZRAAGgwxODcwMTcxNTA5ODYiDLa8cQpVVLWVcbBzVCqWBfdJi%2BfdEQc0Mhxi%2FiJphK4BbkBol3jPYXycon6DXTeLyN9%2Bx1Od71j1YfkbodDb2HfWoaAeBKGchX76lDC913hORlR0Uiv9jAm0WeJq1lAHCW2AOV5G2X8YFkhXOnnm3xPx0W3tAlMevxh2EXSSKcSSmet385RTqTANb1NtSHIEPGuYmpS4LoX5VARLlDT5DP8uSzO1Nlp3GxuvOG0vL9m%2BlWT2TK86nHgtwvs7oDhWiOmMj3lwG0mbW6KhfqhnzVKpVLxwpV5Wxjug%2BoVtSaaMZQG334dAIjlUT0fIAka2saTJCffm41kIRdM9QsSZZFt4r%2BUPJyEv6N%2B2GL37161dMxQpa00PK2ifKAYPx6OyX0jrnj5xY0NjF%2FUOh04%2BTDAEJcYWqC5F%2B89%2FdmZ4mG5LXJDXO5QnnwCPwFsNqEOJjP6VsaR8m%2FYjzP2VNYbAy1hbjfL5KlcCt6yExUCOq4wNW7SAIXqiFTaDB2WeVYkSOYzzeRzRz96S7YiuCldCap%2B5i%2Blu%2BF%2FJmdCqshzfBjplASI6kbzPpmb5XbHX2XzbVFNnFTBOFc6i7axrLzO1cTEFhtMI%2Fp7TfXSaOgR7d6e408QfLL9EjGUnRa5JOpCo%2FOUc9keLJzh30H8RTmQnr64TdcVImDlgyKWv1cWUG%2FdBbY3rsajhn55WKcxF32xyHPI4rZwroGyO26RitC8pL7Ia7Xcq74yjHgkVZq5kRC5Uzn1N3lGiDc1%2By9SOELFqP%2FGT%2F%2F7SMoKJSzmoGW1hp3CEyv7RFTavYAscJNNE%2BHM5Nr48rO1nm5LLN%2FILbBjdZxkjigObBj9%2BZGsEJ99arYIg5kY3kVQDtCvsObd%2FKfb6kO8VLzU2PL0Vp3RSQgha16b0hHkqMOvWz7EGOrEB14VBXp8A5Vy%2B%2Ffh0vtu%2BcuTWHUrqxUSIEonRs0sT%2B7ZPvIxfLpAPuJlMzjRYeGtE77GOgPs3OoAkjqUkM4T6I03jl1k9ypfUvxUIzXCg4qp%2BzxMMSV6EmGw6p53Kv5%2BwQptaWJSlcP1RYlJyHtgGtAdqjbPeCYRPO0jWQ6WEfZyG2dcK%2B72ZER9HfRO9Hyxj1fAZXtXyd0dQf%2BkOX2vI2gd2cgwiPFaLeWcdUVFRW%2Bqq&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240502T200838Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFDWLYUK4V%2F20240502%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=e3752571072d38d14d2b9145d7d3327c7ede329e43875b0e3a9bf113724bd587\",\n" +
                            "        \"source\": \"101 Cookbooks\",\n" +
                            "        \"ingredients\": \"extra-virgin olive oil, 3/4 pound whole wheat pasta shells, sea salt, 1 large yellow onion, chopped, 2 cloves garlic, chopped, 4 cups well-chopped fresh spinach, 1 1/2 cups sliced almonds, lightly toasted, zest of 2 lemons, 8 ounces mozzerella, shredded or torn into small pieces, \",\n" +
                            "        \"detailedIngredients\": [\n" +
                            "            {\n" +
                            "                \"name\": \"extra-virgin olive oil\",\n" +
                            "                \"quantity\": \"0.0\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/4d6/4d651eaa8a353647746290c7a9b29d84.jpg\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"3/4 pound whole wheat pasta shells\",\n" +
                            "                \"quantity\": \"0.75\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/296/296ff2b02ef3822928c3c923e22c7d19.jpg\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"sea salt\",\n" +
                            "                \"quantity\": \"0.0\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/694/6943ea510918c6025795e8dc6e6eaaeb.jpg\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"1 large yellow onion, chopped\",\n" +
                            "                \"quantity\": \"1.0\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/205/205e6bf2399b85d34741892ef91cc603.jpg\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"2 cloves garlic, chopped\",\n" +
                            "                \"quantity\": \"2.0\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/6ee/6ee142951f48aaf94f4312409f8d133d.jpg\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"4 cups well-chopped fresh spinach\",\n" +
                            "                \"quantity\": \"4.0\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/e6e/e6e4be375c4554ce01c8ea75232efaa6.jpg\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"1 1/2 cups sliced almonds, lightly toasted\",\n" +
                            "                \"quantity\": \"1.5\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/6c2/6c2dc21adf11afc4c8d390ee2f651e56.jpg\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"zest of 2 lemons\",\n" +
                            "                \"quantity\": \"2.0\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/70a/70acba3d4c734d7c70ef4efeed85dc8f.jpg\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"8 ounces mozzerella, shredded or torn into small pieces\",\n" +
                            "                \"quantity\": \"8.0\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/03e/03ec3a4d46bec5634dc8a352804e4e68.jpg\"\n" +
                            "            }\n" +
                            "        ],\n" +
                            "        \"instructions\": []\n" +
                            "    },\n" +
                            "    \"dessert\": {\n" +
                            "        \"success\": true,\n" +
                            "        \"name\": \"Panna Cotta\",\n" +
                            "        \"type\": \"desserts\",\n" +
                            "        \"country\": \"italian\",\n" +
                            "        \"preparationTime\": \"0.0\",\n" +
                            "        \"imageURL\": \"https://edamam-product-images.s3.amazonaws.com/web-img/8d6/8d61c45b03d4a52538f6680a9ba68986.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEAwaCXVzLWVhc3QtMSJHMEUCIDVCt%2BiYPgEZeEKnUjA7X01lq1Fe2pkDSaXVoDyCXH9fAiEAvinWq0R6ksaPG0gLsYM7laInNhuiQUgYyECN5QcSeVkquAUIZRAAGgwxODcwMTcxNTA5ODYiDPYhODrSkDkQeUP%2BuyqVBc8CHf8IbGK7r9u8XNPXCnaXo85z%2BFRZLGkoO1VblQqloyed3AYBaTB0ufsi3zz7ZmRXHHHhpgeDBV9D3Ws30MoLda0Hg1WX5ZKmZ30BObIb%2F5BgNHpEEziW5GaQG1M56i1B%2FlYO6hFwpobiGNXAHus0VdHZjXXzumDlmKOdhKcVhoGg4xQlkXvA%2BvCkFJwbsF4aCH316UcaO2BXwYVkXhO%2FJ6uBfGPttPRoQS9Hh9qr%2FH5WUdhwRt2066GynfZhaVfx%2FisV1loEWtt1nUp4DMqSh%2BvhszUTzY4BHPndfgitFy5ePj9VQYc4rfHXS9Ih3DUfUJ8zGPjIB92S6g1LzW%2BQzkuCdEGJQ5yJrhgU6Neo%2BwQfL1kBICdDo%2B5GYAgRdU3CvAW%2BF7c7IbBlopo1HXeEpXO%2BOawcXCBb8jJX2UEqitV%2F5ATZyDzf4cDBvUOUND%2BVxooRj4%2F5s2J36nXE3X4YuFp25ZfRjfm69OXd8%2BJLAd38cbSNrMPSwdgY%2BJ%2B%2Belyvnk0PGAVuuKJR79%2FWIKRqJG9C4IM%2Bzz29TgMY3efFaToJAOz1S3y%2FQreGCQYFkr2%2FaNEc8UUGSGag4T9k4Z7A5YooDGgdHxCD1Wb%2B2oDdaztE7cIT3w6KV%2BTRG2NyNYIt7SbvbQOOFjMk%2FEbO0K49nBqVkSDQsKcRdIX4yO9kJ1HfNi4Vk%2BYWzuV2JilMOZ2RrCfoIhNlhoaNOu38AX22NGi3PMBHrZ%2Bz5JZPUmnHWopLCuauPoLlP3cqir0mxPXY%2FZB3FOsTD8Akm5Z431B0YrMG2DU6SaiytzLTtsQ0Oe7dyCVA%2FovoPJa%2Fbejocqg%2FhXV7UzdTeZx3H%2BmgzrqNdVQDK0N2BX5OfCNplh8cw0uiWmYwst3PsQY6sQHPBOXOFNxyXCTn%2B8p57EyaMqGjbrE7No9i2t9JVc8OGcUwQpJXvoavoAVpGjTr95zvlPd%2BSB7wfrmZ%2FrhRSKZk6rI%2BuNqqY1ux3p7hvqY6gNUpgvul6BrXn%2FBgu9rkaifUYsEpfgykf3WCJdF9Chw6B8pXn9x8OjJ3u2N3og9%2FP23%2B4xvEj01RkR0NRpbQe5QdBY8unDMALEMvjL7Z9HKzQzhyxunkcZqyM%2FFidMajSwU%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240502T200839Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFBNM3PVPO%2F20240502%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=9fc21abc97fddf65d2502a81645623f30f5e8eb98ad95f122166a35e9c8fd127\",\n" +
                            "        \"source\": \"David Lebovitz\",\n" +
                            "        \"ingredients\": \"4 cups (1l) heavy cream (or half-and-half), 1/2 cup (100g) sugar, 2 teaspoons of vanilla extract, or 1 vanilla bean, split lengthwise, 2 packets powdered gelatin (about 4 1/2 teaspoons), 6 tablespoons (90ml) cold water, \",\n" +
                            "        \"detailedIngredients\": [\n" +
                            "            {\n" +
                            "                \"name\": \"4 cups (1l) heavy cream (or half-and-half)\",\n" +
                            "                \"quantity\": \"1.0\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/484/4848d71f6a14dd5076083f5e17925420.jpg\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"1/2 cup (100g) sugar\",\n" +
                            "                \"quantity\": \"100.0\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/ecb/ecb3f5aaed96d0188c21b8369be07765.jpg\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"2 teaspoons of vanilla extract, or 1 vanilla bean, split lengthwise\",\n" +
                            "                \"quantity\": \"2.0\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/90f/90f910b0bf82750d4f6528263e014cca.jpg\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"2 packets powdered gelatin (about 4 1/2 teaspoons)\",\n" +
                            "                \"quantity\": \"4.5\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/47a/47a5b5c20c3cbfaf7332d572a5bfddbe.jpg\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"6 tablespoons (90ml) cold water\",\n" +
                            "                \"quantity\": \"90.0\",\n" +
                            "                \"image\": \"https://www.edamam.com/food-img/5dd/5dd9d1361847b2ca53c4b19a8f92627e.jpg\"\n" +
                            "            }\n" +
                            "        ],\n" +
                            "        \"instructions\": []\n" +
                            "    },\n" +
                            "    \"drink\": {\n" +
                            "        \"success\": true,\n" +
                            "        \"name\": \"Tequila Sour\",\n" +
                            "        \"type\": \"Ordinary Drink\",\n" +
                            "        \"alcoholic\": true,\n" +
                            "        \"imageURL\": \"https://www.thecocktaildb.com/images/media/drink/ek0mlq1504820601.jpg\",\n" +
                            "        \"ingredients\": [\n" +
                            "            \"Tequila\",\n" +
                            "            \"Lemon\",\n" +
                            "            \"Powdered sugar\",\n" +
                            "            \"Lemon\",\n" +
                            "            \"Cherry\"\n" +
                            "        ],\n" +
                            "        \"detailedIngredients\": [\n" +
                            "            {\n" +
                            "                \"name\": \"Tequila\",\n" +
                            "                \"quantity\": \"2 oz \",\n" +
                            "                \"image\": \"\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"Lemon\",\n" +
                            "                \"quantity\": \"Juice of 1/2 \",\n" +
                            "                \"image\": \"\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"Powdered sugar\",\n" +
                            "                \"quantity\": \"1 tsp \",\n" +
                            "                \"image\": \"\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"Lemon\",\n" +
                            "                \"quantity\": \"1/2 slice \",\n" +
                            "                \"image\": \"\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"name\": \"Cherry\",\n" +
                            "                \"quantity\": \"1 \",\n" +
                            "                \"image\": \"\"\n" +
                            "            }\n" +
                            "        ],\n" +
                            "        \"instructions\": \"Shake tequila, juice of lemon, and powdered sugar with ice and strain into a whiskey sour glass. Add the half-slice of lemon, top with the cherry, and serve.\"\n" +
                            "    }\n" +
                            "}"))),
            @ApiResponse(responseCode = "400", description = "Invalid filters",content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMenu.class),examples = @ExampleObject(value ="{\n" +
                    "    \"success\": false,\n" +
                    "    \"api_failed\": \"recipes-api\",\n" +
                    "    \"api_status\": \"400\"\n" +
                    "}" ))),
            @ApiResponse(responseCode = "500", description = "problem with the external or internal api",content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMenu.class),examples = @ExampleObject(value ="{\n" +
                    "    \"success\": false,\n" +
                    "    \"api_failed\": \"EDAMAM\",\n" +
                    "    \"api_status\": \"500\"\n" +
                    "}" ))),

    })
    public Response generateRandomMenu(
            @RequestBody(
                    description = "JSON structure of the Menu filters" ,
                    required = true,
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = MenuFilters.class), examples = {@ExampleObject("{\n" + "  \"cuisineType\": \"italian\",\n" + "  \"alcohol\": \"true\",\n" + "  \"healthLabels\": [\"vegan\"],\n" + "  \"ingredient\": \"vodka\",\n" + "  \"totalTime\": 60\n" + "}\n")} ))

            MenuFilters menuFilters) {
            List<String> healthLabels = new ArrayList<>() ;
            int time = 0;
            String alcohol = null;
            String ingredient = null ;

            if(menuFilters == null || menuFilters.getCuisineType() == null){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(menuService.createErrorMessage(" the cuisineType parameter is missing","recipes-api","400"))
                        .build();
            }
            else {
                String cuisineType = menuFilters.getCuisineType();
                if (menuFilters.getHealthLabels() != null) {
                    healthLabels = menuFilters.getHealthLabels();
                }
                if (menuFilters.getTotalTime() != 0) {
                    time=menuFilters.getTotalTime();
                }
                if (menuFilters.getAlcohol() != null) {
                    alcohol = menuFilters.getAlcohol();
                }
                if (menuFilters.getIngredient() != null) {
                    ingredient = menuFilters.getIngredient();
                }
                return menuService.getRandomMenu(cuisineType,healthLabels,time, alcohol,ingredient);
            }




    }
}
