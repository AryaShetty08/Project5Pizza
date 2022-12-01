package pizzaManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * BBQ_Chicken class is a blueprint for BBQ_Chicken Pizza objects
 * Subclass of Pizza Class
 * @author Arya Shetty, John Greaney-Cheng
 */
public class BBQ_Chicken extends Pizza {

    /**
     * Creates a BBQ_Chicken Pizza object
     * Initializes predetermined Topping List, Size to Small, and Crust Type based on Parameter
     * @param pizzaFactory NY or Chicago Factory determines crust type
     */
    public BBQ_Chicken (PizzaFactory pizzaFactory){
        if (pizzaFactory instanceof NYPizza){
            crust = Crust.THIN;
        }
        else if (pizzaFactory instanceof ChicagoPizza){
            crust = Crust.PAN;
        }
        else {
            crust = null;
        }
        size = Size.SMALL;
        toppings = new ArrayList<Topping>(Arrays.asList(Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.PROVOLONE, Topping.CHEDDAR));
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
     * Method to calculate and return this BBQ Chicken pizza price
     * Price is based solely off of size (see below)
     * @return price of pizza
     */
    @Override
    public double price() {
        switch (size) {
            case SMALL -> {
                return Constant.BBQ_CHICKEN_SMALL_PRICE.getValue();
            }
            case MEDIUM -> {
                return Constant.BBQ_CHICKEN_MEDIUM_PRICE.getValue();
            }
            case LARGE -> {
                return Constant.BBQ_CHICKEN_LARGE_PRICE.getValue();
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * Returns String representation of this BBQ Chicken pizza
     * @return String representation of this BBQ Chicken pizza
     */
    @Override
    public String toString() {
        String toReturn = "BBQ Chicken (";
        if (crust == Crust.THIN){
            toReturn = toReturn + "New York Style - Thin), ";
        }
        else {
            toReturn = toReturn + "Chicago Style - Pan), ";
        }
        for (Topping topping: toppings){
            toReturn = toReturn + (topping.name() + ", ");
        }
        toReturn = toReturn + (size.name()) + ", $" + (price());
        return toReturn;
    }
}
