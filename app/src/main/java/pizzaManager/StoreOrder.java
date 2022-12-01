package pizzaManager;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

/**
 * StoreOrder Class is a blueprint for store order objects
 * Stores list of orders and can export them to a text file
 * @author Arya Shetty, John Greaney-Cheng
 */
public class StoreOrder implements Customizable{
    private ArrayList<Order> orderList;

    /**
     * Creates a StoreOrder object
     */
    public StoreOrder(){
        this.orderList = new ArrayList<Order>();
    }

    /**
     * Adds order to store order's list of orders
     * @param obj the order to add to the store order's order list
     * @return true if added, false otherwise
     */
    @Override
    public boolean add(Object obj) {
        if (obj instanceof Order){
            return orderList.add((Order) obj);
        }
        return false;
    }

    /**
     * Removes order from store order's list of orders
     * @param obj the order to remove from the store order's order list
     * @return true if removed, false otherwise
     */
    @Override
    public boolean remove(Object obj) {
        if (obj instanceof Order){
            return orderList.remove((Order) obj);
        }
        return false;
    }

    /**
     * Getter method for store order's list of orders
     * @return store order's list of orders
     */
    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    /**
     * Exports contents of an order in order list to a text file
     * @param serialNumber serial number of order to export to text file
     * @throws IOException deals with FileWrite IOException
     */
    public void export (int serialNumber) throws IOException {
        Order orderToExport = null;
        for (Order order: orderList){
            if (serialNumber == order.getSerialNumber()){
                orderToExport = order;
                break;
            }
        }
        if (orderToExport == null){
            return;
        }

        File export = new File("ExportOrders.txt");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(export, true)));
        pw.println("Order " + serialNumber + ":");

        for (Pizza pizza: orderToExport.getPizzaList()){
            pw.println(pizza.toString());
        }
        pw.println("Order Total: $" + orderToExport.getTotal() + "\n");
        pw.close();
    }
}
