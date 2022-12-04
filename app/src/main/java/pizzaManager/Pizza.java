package pizzaManager;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
}
