package pizzaManager;

/**
 * Customizable interface describes class that can add and remove something
 * All classes that implement customizable maintain a collection (w/ ArrayList)
 * @author Arya Shetty, John Greaney-Cheng
 */
public interface Customizable {

    /**
     * Adds something to class's collection
     * @param obj the thing to add to the class's collection
     * @return true if added, false otherwise
     */
    boolean add(Object obj);

    /**
     * Removes something from class's collection
     * @param obj the thing to remove from the class's collection
     * @return true if removed, false otherwise
     */
    boolean remove(Object obj);
}
