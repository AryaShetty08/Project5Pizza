package pizzaManager;

import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * Order Class is a blueprint for order objects
 * Stores serial number as identifier,
 * list of pizzas to order, and
 * can calculate necessary prices
 * @author Arya Shetty, John Greaney-Cheng
 */
public class Order implements Customizable{
    private int serialNumber;
    private ArrayList <Pizza> pizzaList;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Creates an Order object
     * @param serialNumber serial number of order
     */
    public Order (int serialNumber){
        this.serialNumber = serialNumber;
        this.pizzaList = new ArrayList<Pizza>();
    }

    /**
     * Method to calculate and return subtotal
     * Subtotal is equal to price of all pizzas in pizza list
     * @return order subtotal
     */
    public double getSubtotal(){
        double subTotal = 0;
        for (Pizza pizza: pizzaList){
            subTotal += pizza.price();
        }
        return subTotal;
    }

    /**
     * Method to calculate and return tax
     * Tax is equal to subtotal multiplied by Tax Rate
     * @return order tax
     */
    public double getTax(){
        double tax = getSubtotal() * Constant.TAX_RATE.getValue();
        return Double.parseDouble(df.format(tax));
    }

    /**
     * Method to calculate and return total price
     * Total price is equal to sum of subtotal and tax
     * @return order tax
     */
    public double getTotal(){
        return getTax() + getSubtotal();
    }

    /**
     * Adds pizza to order's list of pizzas
     * @param obj the pizza to add to the order's pizza list
     * @return true if added, false otherwise
     */
    @Override
    public boolean add(Object obj) {
        if (obj instanceof Pizza){
            return pizzaList.add((Pizza) obj);
        }
        return false;
    }

    /**
     * Removes pizza from order's list of pizzas
     * @param obj the pizza to remove from the order's pizza list
     * @return true if removed, false otherwise
     */
    @Override
    public boolean remove(Object obj) {
        if (obj instanceof Pizza){
            return pizzaList.remove((Pizza) obj);
        }
        return false;
    }

    /**
     * Returns if Order is empty
     * @return true if Order is empty, false otherwise
     */
    public boolean isEmpty(){
        return pizzaList.isEmpty();
    }

    /**
     * Getter method for order's list of pizzas
     * @return order's list of pizzas
     */
    public ArrayList<Pizza> getPizzaList(){
        return pizzaList;
    }

    /**
     * Getter method for order's serial number
     * @return order's serial number
     */
    public int getSerialNumber() {
        return serialNumber;
    }
}
