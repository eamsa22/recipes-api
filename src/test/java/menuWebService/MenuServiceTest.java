package menuWebService;

import jakarta.ws.rs.core.Response;
import menuWebService.services.menuService;
import model.version2.MealDrinkError;
import model.version3.ErrorMenu;
import model.version3.Menu;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MenuServiceTest {

    private menuService menuService = new menuService();


    @Test
    public void testGetMenuWithValidCuisineType() {
        Response response = menuService.getRandomMenu("italian", List.of(),0,null,null);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Menu menu = (Menu) response.getEntity();
        assertEquals("italian",menu.getMainCourse().getCountry());
        assertEquals("italian",menu.getDessert().getCountry());
        assertEquals("italian",menu.getStarter().getCountry());
    }

    @Test
    public void testGetMenuWithInvalidCuisineType() {
        Response response = menuService.getRandomMenu("moroccan", List.of(),0,null,null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        ErrorMenu error = (ErrorMenu) response.getEntity();
        assertEquals("recipes-api",error.getApi_failed());
    }

    @Test
    public void testGetMenuWithValidHealthLabels(){
        Response response = menuService.getRandomMenu("italian", List.of("vegan"),0,null,null);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetMenuWithInvalidHealthLabels(){
        Response response = menuService.getRandomMenu("italian", List.of("Vegan"),0,null,null);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        ErrorMenu error = (ErrorMenu) response.getEntity();
        assertEquals("Edamam",error.getApi_failed());
    }

    @Test
    public void testGetMenuWithValidDrinkIngredientFilter(){
        Response response = menuService.getRandomMenu("italian", List.of(),0,null,"vodka");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Menu menu = (Menu) response.getEntity();
        assertTrue(menu.getDrink().getIngredients().contains("Vodka"));
    }
    @Test
    public void testGetMenuWithInvalidIngredientFilter(){
        Response response = menuService.getRandomMenu("italian", List.of(),0,null,"bread");
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        ErrorMenu error = (ErrorMenu) response.getEntity();
        assertEquals("recipes-api",error.getApi_failed());
    }

    @Test
    public void testGetMenuWithValidAlcoholicFilter(){
        Response response = menuService.getRandomMenu("italian", List.of(),0,"false",null);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Menu menu = (Menu) response.getEntity();
        assertFalse(menu.getDrink().isAlcoholic());
    }
    @Test
    public void testGetMenuWithInvalidAlcoholicFilter(){
        Response response = menuService.getRandomMenu("italian", List.of(),0,"noAlcohol",null);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        ErrorMenu error = (ErrorMenu) response.getEntity();
        assertEquals("recipes-api",error.getApi_failed());
    }

    @Test
    public void testGetMenuWithAllFiltersAsValid(){
        Response response = menuService.getRandomMenu("italian", List.of("vegan"),60,"true","vodka");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Menu menu = (Menu) response.getEntity();
        assertTrue(menu.getDrink().getIngredients().contains("Vodka"));
        assertTrue(menu.getDrink().isAlcoholic());
        assertEquals("italian",menu.getMainCourse().getCountry());
        assertEquals("italian",menu.getDessert().getCountry());
        assertEquals("italian",menu.getStarter().getCountry());
        float totalPreparationTime = Float.parseFloat(menu.getStarter().getPreparationTime()) +
                Float.parseFloat(menu.getMainCourse().getPreparationTime()) +
                Float.parseFloat(menu.getDessert().getPreparationTime());
        assertTrue(totalPreparationTime < 60 || totalPreparationTime == 60);
    }

}
