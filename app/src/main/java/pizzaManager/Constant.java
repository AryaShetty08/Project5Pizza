package pizzaManager;

/**
 * Constant Enum Class holds all variables with constant values
 * Stores the value corresponding to each constant
 * @author Arya Shetty, John Greaney-Cheng
 */
public enum Constant {
    BBQ_CHICKEN_SMALL_PRICE(13.99),
    BBQ_CHICKEN_MEDIUM_PRICE(15.99),
    BBQ_CHICKEN_LARGE_PRICE(17.99),
    MEATZZA_SMALL_PRICE(15.99),
    MEATZZA_MEDIUM_PRICE(17.99),
    MEATZZA_LARGE_PRICE(19.99),
    DELUXE_SMALL_PRICE(14.99),
    DELUXE_MEDIUM_PRICE(16.99),
    DELUXE_LARGE_PRICE(18.99),
    BUILD_YOUR_OWN_SMALL_PRICE(8.99),
    BUILD_YOUR_OWN_MEDIUM_PRICE(10.99),
    BUILD_YOUR_OWN_LARGE_PRICE(12.99),
    PRICE_PER_TOPPING(1.59),
    TAX_RATE(0.06625);

    private final double value;

    /**
     * Creates a Constant
     * @param value the corresponding value of the constant
     */
    Constant(double value){
        this.value = value;
    }

    /**
     * Getter method for value of constant
     * @return constant's value
     */
    public double getValue(){
        return this.value;
    }
}
