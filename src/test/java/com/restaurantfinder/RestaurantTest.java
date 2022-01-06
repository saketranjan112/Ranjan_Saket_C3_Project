package com.restaurantfinder;


import com.restaurantfinder.itemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    public void addTestRestaurant(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        addTestRestaurant();
        Restaurant spiedRestaurant = Mockito.spy(restaurant);

        LocalTime time1 = LocalTime.now().plusMinutes(10);
        LocalTime time2 = LocalTime.now().minusMinutes(10);

        Mockito.when(spiedRestaurant.getClosingTime()).thenReturn(time1);
        Mockito.when(spiedRestaurant.getOpeningTime()).thenReturn(time2);

        assertTrue(spiedRestaurant.isRestaurantOpen());

//        Mockito.verify(restaurant1).getClosingTime();
//        Mockito.verify(restaurant1).getOpeningTime();
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        addTestRestaurant();
        Restaurant spiedRestaurant = Mockito.spy(restaurant);

        LocalTime time1 = LocalTime.now().plusMinutes(10);
//        LocalTime time2 = LocalTime.now().minusMinutes(10);

//        Mockito.when(spiedRestaurant.getClosingTime()).thenReturn(time2);
        Mockito.when(spiedRestaurant.getOpeningTime()).thenReturn(time1);

        assertFalse(spiedRestaurant.isRestaurantOpen());

//        Mockito.verify(restaurant1).getClosingTime();
//        Mockito.verify(restaurant1).getOpeningTime();

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        addTestRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        addTestRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        addTestRestaurant();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER TOTAL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void getOrderValue_should_return_sum_of_prices_of_all_items_selected(){
        addTestRestaurant();

        List<String> selectedItems = new ArrayList<>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");

        assertEquals(388,restaurant.getOrderValue(selectedItems));
    }
    @Test
    public void getOrderValue_should_return_reduced_price_after_item_is_removed(){
        addTestRestaurant();

        List<String> selectedItems = new ArrayList<>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");

        int orderValue = restaurant.getOrderValue(selectedItems);
        int newOrderValue = restaurant.findItemByName(selectedItems.get(1)).getPrice();
        selectedItems.remove(1);
        assertEquals(orderValue - newOrderValue, restaurant.getOrderValue(selectedItems));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}