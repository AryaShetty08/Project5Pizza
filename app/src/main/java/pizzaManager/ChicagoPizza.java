package pizzaManager;

/**
 * Chicago Pizza Factory creates Chicago Style Pizzas
 * @author Arya Shetty, John Greaney-Cheng
 */
public class ChicagoPizza implements PizzaFactory {

    /**
     * Creates Deluxe Chicago Style Pizza
     * @return Deluxe Pizza that was created
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(this);
    }

    /**
     * Creates Meatzza Chicago Style Pizza
     * @return Meatzza Pizza that was created
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(this);
    }


    /**
     * Creates BBQ Chicken Chicago Style Pizza
     * @return BBQ Chicken Pizza that was created
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQ_Chicken(this);
    }


    /**
     * Creates Build Your Own Chicago Style Pizza
     * @return Build Your Own Pizza that was created
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn(this);
    }
}
