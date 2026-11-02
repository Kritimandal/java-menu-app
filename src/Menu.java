import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Menu {

    //field
    private final List<Coffee> coffeeList = new ArrayList<>();

    /**
     * @param coffeeList the list of the coffee from the menu
     */
    public void addMenu(Coffee coffeeList) {
        this.coffeeList.add(coffeeList);
    }

    /**
     * @return the set of all extras from the menu
     */
    public Set<String> getAllExtra() {
        Set<String> allExtra = new HashSet<>();
        for (Coffee coffee : coffeeList) {
            allExtra.addAll(coffee.getExtras());  // Adding all extras from each Coffee object to the allCoffee set
        }
        return allExtra;
    }

    /**
     * a method to compare coffees in the field to a user's 'dream' coffee
     *
     * @param geekOrder the customer's dream coffee inputs
     * @return the match of dream coffee found in menu
     */
    public List<Coffee> findMatch(Coffee geekOrder) {
        // a collection to store compatible Coffee objects
        List<Coffee> compatibleOrders = new ArrayList<>();

        for (Coffee coffee : this.coffeeList) {
            //  Milk comparison: Ensure there's an intersection of milk types
           if (geekOrder.getMilk().contains(MilkType.NO_MILK) && coffee.getMilk().isEmpty()) {
               System.out.println("Null milk type");
           } else if (coffee.getMilk().stream().noneMatch(geekOrder.getMilk()::contains)) {
               continue; // Skip if no milk type matches
           }
              
            //  Shots comparison: Check if the number of shots is the same
            if (coffee.getNumberOfShots() != geekOrder.getNumberOfShots()) {
                System.out.println("Shots don't match: " + coffee.getNumberOfShots() + " and " + geekOrder.getNumberOfShots());
                continue;
            }
          
            //  Price range check: check for coffee match within the min /max coffee price range
            if (coffee.getPrice() < geekOrder.getMinPrice() || coffee.getPrice() > geekOrder.getMaxPrice()) {
                continue; // Skip if the price is outside the range
            }
          

            //  Sugar comparison: Compare if the sugar type matches
            if (!coffee.getSugared().equals(geekOrder.getSugared())) {
                continue; // Skip if sugar doesn't match
            }


            //  Ice comparison: Compare if iced status matches
            if (!coffee.getIced().equals(geekOrder.getIced())) {
                continue; // Skip if iced status doesn't match
            }

          
           // Extra comparison: Compare if any extra matches
            if (geekOrder.getExtras().isEmpty() ) {
               System.out.println("Both are null, so they match");

           } else if (!coffee.getExtras().stream().anyMatch(geekOrder.getExtras()::contains)) {
                continue; // Skip if no extras match
            }
            

            // Adding the matched coffee to the compatible orders list
            compatibleOrders.add(coffee);
        }

        return compatibleOrders;
    }


}
