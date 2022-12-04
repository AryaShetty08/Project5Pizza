package pizzaManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Meatzza class is a blueprint for Meatzza Pizza objects
 * Subclass of Pizza Class
 * @author Arya Shetty, John Greaney-Cheng
 */
public class Meatzza extends Pizza{
    public Meatzza (PizzaFactory pizzaFactory){
        if (pizzaFactory instanceof NYPizza){
            crust = Crust.HAND_TOSSED;
        }
        else if (pizzaFactory instanceof ChicagoPizza){
            crust = Crust.STUFFED;
        }
        else {
            crust = null;
        }
        size = Size.SMALL;
        toppings = new ArrayList<Topping>(Arrays.asList(Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM));
    }

    /**
     * No need to add/remove toppings, so method always returns false
     * @param obj topping to add to Topping List (can't in this class)
     * @return false
     */
    @Override
    public boolean add(Object obj) {
        return false;
    }

    /**
     * No need to add/remove toppings, so method always returns false
     * @param obj topping to add to Topping List (can't in this class)
     * @return false
     */
    @Override
    public boolean remove(Object obj) {
        return false;
    }

    /**
     * Method to calculate and return this Meatzza pizza price
     * Price is based solely off of size (see below)
     * @return price of pizza
     */
    @Override
    public double price() {
        switch (size) {
            case SMALL:
                return Constant.MEATZZA_SMALL_PRICE.getValue();
            case MEDIUM:
                return Constant.MEATZZA_MEDIUM_PRICE.getValue();
            case LARGE:
                return Constant.MEATZZA_LARGE_PRICE.getValue();
            default:
                return 0;
        }
    }

    /**
     * Returns String representation of this Meatzza pizza
     * @return String representation of this Meatzza pizza
     */
    @Override
    public String toString() {
        String toReturn = "Meatzza (";
        if (crust == Crust.HAND_TOSSED){
            toReturn = toReturn + "New York Style - Hand Tossed), ";
        }
        else {
            toReturn = toReturn + "Chicago Style - Stuffed), ";
        }
        for (Topping topping: toppings){
            toReturn = toReturn + (topping.name() + ", ");
        }
        toReturn = toReturn + (size.name()) + ", $" + (price());
        return toReturn;
    }
}
