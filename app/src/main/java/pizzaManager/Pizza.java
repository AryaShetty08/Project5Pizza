package pizzaManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Pizza Abstract Class is a blueprint for Pizza objects
 * Stores the following pizza data:
 *  - Size
 *  - Crust type
 *  - List of Toppings
 * @author Arya Shetty, John Greaney-Cheng
 */
public abstract class Pizza implements Customizable {
    // Note: All instance variables are protected for inheritance
    protected static final DecimalFormat df = new DecimalFormat("0.00");
    protected ArrayList<Topping> toppings;
    protected Crust crust;
    protected Size size;

    /**
     * Method to calculate and return pizza's price
     * @return price of pizza
     */
    public abstract double price();

    /**
     * Setter method for pizza's size
     * @param size size to set pizza to
     */
    public void setSize(Size size){
        this.size = size;
    }

    /**
     * Getter method for pizza's topping list
     * @return pizza's topping list
     */
    public ArrayList<Topping> getToppings(){
        return toppings;
    }

    /**
     * Getter method for pizza's crust
     * @return pizza's crust
     */
    public Crust getCrust(){
        return crust;
    }

    /**
     * Returns String representation of this pizza
     * @return String representation of this pizza
     */
    @Override
    public abstract String toString();

    public static Pizza stringToPizza(String s){
        Pizza pizza = null;
        if (s.substring(0, 13).equals("BBQ Chicken (")){
            if (s.substring(13, 37).equals("New York Style - Thin), ")){
                pizza = new BBQ_Chicken(new NYPizza());
            }
            else {
                pizza = new BBQ_Chicken(new ChicagoPizza());
            }
        }
        else if (s.substring(0, 8).equals("Deluxe (")){
            if (s.substring(8, 36).equals("New York Style - Brooklyn), ")){
                pizza = new Deluxe(new NYPizza());
            }
            else {
                pizza = new Deluxe(new ChicagoPizza());
            }
        }
        else if (s.substring(0, 9).equals("Meatzza (")){
            if (s.substring(9, 40).equals("New York Style - Hand Tossed), ")){
                pizza = new Meatzza(new NYPizza());
            }
            else {
                pizza = new Meatzza(new ChicagoPizza());
            }
        }
        else if (s.substring(0, 16).equals("Build Your Own (")){
            if (s.substring(16, 47).equals("New York Style - Hand Tossed), ")){
                pizza = new BuildYourOwn(new NYPizza());
            }
            else {
                pizza = new BuildYourOwn(new ChicagoPizza());
            }
            addToppingsFromString(pizza, s);
        }
        else {
            return null;
        }
        pizza.setSize(getSizeFromString(s));
        return pizza;
    }

    private static Size getSizeFromString(String s){
        switch (s.substring(s.length() - 9, s.length() - 8)){
            case "L":
                return Size.SMALL;
            case "M":
                return Size.MEDIUM;
            case "E":
                return Size.LARGE;
            default:
                return null;
        }
    }

    private static void addToppingsFromString(Pizza pizza, String pizzaString){
        String[] splitPizzaString = pizzaString.split(", ");
        for (String s: splitPizzaString){
            Topping topping = Topping.stringToTopping(s);
            if (topping != null){
                pizza.add(topping);
            }
        }
    }
}
