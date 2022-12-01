package pizzaManager;

import java.util.ArrayList;

/**
 * BuildYourOwn class is a blueprint for BuildYourOwn Pizza objects
 * Subclass of Pizza Class
 * @author Arya Shetty, John Greaney-Cheng
 */
public class BuildYourOwn extends Pizza{
    private int numToppings;

    /**
     * Creates a BuildYourOwn Pizza object
     * Initializes empty Topping List, Size to Small, and Crust Type based on Parameter
     * @param pizzaFactory NY or Chicago Factory determines crust type
     */
    public BuildYourOwn (PizzaFactory pizzaFactory){
        if (pizzaFactory instanceof NYPizza){
            crust = Crust.HAND_TOSSED;
        }
        else if (pizzaFactory instanceof ChicagoPizza){
            crust = Crust.PAN;
        }
        else {
            crust = null;
        }
        size = Size.SMALL;
        toppings = new ArrayList<Topping>();
        numToppings = 0;
    }

    /**
     * Adds topping to topping list
     * Max Number of Pizza Toppings is 7
     * @param obj topping to add to topping list
     * @return true if added, false otherwise
     */
    @Override
    public boolean add(Object obj) {
        if (numToppings == 7){
            return false;
        }
        if (!toppings.add((Topping) obj)){
            return false;
        }
        numToppings++;
        return true;
    }

    /**
     * Removes topping from topping list
     * @param obj topping to remove from topping list
     * @return true if removed, false otherwise
     */
    @Override
    public boolean remove(Object obj) {
        if (toppings.remove((Topping) obj)){
            numToppings--;
            return true;
        }
        return false;
    }

    /**
     * Method to calculate and return this Build Your Own pizza price
     * Price is based off of size and toppings
     *  - Base Small costs 8.99
     *  - Base Medium costs 10.99
     *  - Base Large costs 12.99
     *  - 1.59 per topping
     * @return price of pizza
     */
    @Override
    public double price() {
        switch (size) {
            case SMALL:
                return Double.parseDouble(df.format(Constant.BUILD_YOUR_OWN_SMALL_PRICE.getValue()
                        + (Constant.PRICE_PER_TOPPING.getValue()*numToppings)));
            case MEDIUM:
                return Double.parseDouble(df.format(Constant.BUILD_YOUR_OWN_MEDIUM_PRICE.getValue()
                        + (Constant.PRICE_PER_TOPPING.getValue()*numToppings)));
            case LARGE:
                return Double.parseDouble(df.format(Constant.BUILD_YOUR_OWN_LARGE_PRICE.getValue()
                        + (Constant.PRICE_PER_TOPPING.getValue()*numToppings)));
            default:
                return 0;
        }
    }

    /**
     * Returns String representation of this Build Your Own pizza
     * @return String representation of this Build Your Own pizza
     */
    @Override
    public String toString() {
        String toReturn = "Build Your Own (";
        if (crust == Crust.HAND_TOSSED){
            toReturn = toReturn + "New York Style - Hand Tossed), ";
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
