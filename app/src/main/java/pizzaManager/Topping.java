package pizzaManager;

/**
 * Topping Enum Class holds all potential pizza toppings
 * @author Arya Shetty, John Greaney-Cheng
 */
public enum Topping {
    SAUSAGE,
    PEPPERONI,
    GREEN_PEPPER,
    ONION,
    MUSHROOM,
    BBQ_CHICKEN,
    PROVOLONE,
    CHEDDAR,
    BEEF,
    HAM,
    BACON,
    OLIVES,
    PINEAPPLE,
    SPINACH;

    /**
     * Method takes in topping name and returns a topping enum
     * @param str, string that represents topping name
     * @return topping string was referring to if it exists,
     *         null otherwise
     */
    public static Topping stringToTopping(String str){
        for (Topping topping: Topping.values()){
            if (str.equalsIgnoreCase(topping.name())){
                return topping;
            }
        }
        return null;
    }
}
