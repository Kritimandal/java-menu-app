import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Coffee {

    //fields
    private final String name;
    private final long code;
    private final float price;
    private final int numberOfShots;
    private final Preference sugared;
    private final HotPreference iced;
    private final List<MilkType> milk;
    private final Set<String> extras;

    private final String description;

    private float minPrice;
    private float maxPrice;

    /**
     *
     * @param name the coffee's name
     * @param code the coffee's unique number code
     * @param price the coffee's price
     * @param numberOfShots the number of shots added to coffee
     * @param sugared the state of sugar as yes or no
     * @param iced the state of ice in coffee as hot or iced
     * @param milk the type of milk used for coffee making
     * @param extras the extras added to the coffee
     * @param description the description of the coffee
     */
    public Coffee(String name, long code, float price, int numberOfShots, Preference sugared, HotPreference iced, List<MilkType> milk, Set<String> extras, String description) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.numberOfShots = numberOfShots;
        this.sugared = sugared;
        this.iced = iced;
        this.milk = milk;
        this.extras = extras;
        this.description = description;
    }

    /**
     *
     * @return coffe's name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return coffee's code
     */
    public long getCode() {
        return code;
    }

    /**
     *
     * @return the price of the coffeee
     */
    public float getPrice() {
        return price;
    }

    /**
     *
     * @return coffee's number of shots added
     */
    public int getNumberOfShots() {
        return numberOfShots;
    }

    /**
     *
     * @return coffee's sugar state as yes or no
     */
    public Preference getSugared() {
        return sugared;
    }

    /**
     *
     * @return coffee's iced state as hot or iced
     */
    public HotPreference getIced() {
        return iced;
    }

    /**
     *
     * @return a 'dream' coffee's min price
     */
    public float getMinPrice() {
        return minPrice;
    }

    /**
     *
     * @param minPrice the min price a user is willing pay
     */
    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    /**
     *
     * @return a 'dream' coffee's max price
     */
    public float getMaxPrice() {
        return maxPrice;
    }

    /**
     *
     * @param maxPrice the maximum price user is willing to pay
     */
    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     *
     * @return the extras added to the coffee
     */
    public Set<String> getExtras() {
        return extras;
    }

    /**
     *
     * @return the milk type of the coffee
     */
    public List<MilkType> getMilk() {
        return milk;
    }

    /**
     *
     * @return the coffee's description
     */
    public String getDescription() {
        return description;
    }


    public String toStringMilk(){
        List<MilkType> milkList = getMilk();
        if (milkList.isEmpty()) {
            return "No Milk"; // Return a default message if milk list is empty or null
        }
        List<String> milkStrings = milkList.stream()
                .map(MilkType::toString)
                .collect(Collectors.toList());

        return String.join(", ", milkStrings);
    }

    /**
     *
     * @return string of extras
     */
    public String toStringExtras(){
        Set<String> extrasSet= getExtras();
        if(extrasSet.isEmpty()) return " ";
        List<String> extraString = extrasSet.stream().map(String::toString).collect(Collectors.toList());
        return String.join(", ", extraString);
    }
}
