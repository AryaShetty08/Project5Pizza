package pizzaManager;

/**
 * Topping Enum Class holds all potential pizza toppings
 * @author Arya Shetty, John Greaney-Cheng
 */
public enum Topping {
    SAUSAGE(0),
    PEPPERONI(1),
    GREEN_PEPPER(2),
    ONION(3),
    MUSHROOM(4),
    BBQ_CHICKEN(5),
    PROVOLONE(6),
    CHEDDAR(7),
    BEEF(8),
    HAM(9),
    BACON(10),
    OLIVES(11),
    PINEAPPLE(12),
    SPINACH(13);

    private int order;

    Topping(int order) {
        this.order = order;
    }

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

    public int getOrder() {
        return order;
    }
}
