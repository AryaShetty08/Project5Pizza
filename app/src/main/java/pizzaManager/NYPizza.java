package pizzaManager;

/**
 * New York Pizza Factory creates New York Style Pizzas
 * @author Arya Shetty, John Greaney-Cheng
 */
public class NYPizza implements PizzaFactory{

    /**
     * Creates Deluxe New York Style Pizza
     * @return Deluxe Pizza that was created
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(this);
    }

    /**
     * Creates Meatzza New York Style Pizza
     * @return Meatzza Pizza that was created
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(this);
    }

    /**
     * Creates BBQ Chicken New York Style Pizza
     * @return BBQ Chicken Pizza that was created
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQ_Chicken(this);
    }

    /**
     * Creates Build Your Own New York Style Pizza
     * @return Build Your Own Pizza that was created
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn(this);
    }
}
