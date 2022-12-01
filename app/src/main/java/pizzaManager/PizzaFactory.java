package pizzaManager;

/**
 * PizzaFactory interface describes pizza factory (class that can create pizzas)
 * "Abstract Factory" design pattern
 * @author Arya Shetty, John Greaney-Cheng
 */
public interface PizzaFactory {

    /**
     * Creates Deluxe Pizza
     * @return Deluxe Pizza that was created
     */
    Pizza createDeluxe();

    /**
     * Creates Meatzza Pizza
     * @return Meatzza Pizza that was created
     */
    Pizza createMeatzza();

    /**
     * Creates BBQ Chicken Pizza
     * @return BBQ Chicken Pizza that was created
     */
    Pizza createBBQChicken();

    /**
     * Creates Build Your Own Pizza
     * @return Build Your Own Pizza that was created
     */
    Pizza createBuildYourOwn();
}
