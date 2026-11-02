import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MenuSearcher class
 * static constants:
 * - appName: The name of the application.
 * - iconPath: The path to the application's icon.
 * - filePath: The path to the menu file that contains the menu data.
 * menus: a static Menu object for storing the loaded menu data.
 */
public class MenuSearcher {


    // the constant static fields

    private final static String appName = "Brewed Brilliance";
    private final static String iconPath = "./brewed_brilliance.jpg";
    private final static String filePath = "./menu.txt";
    private static Menu menus;


    private static Geek getGeekDetals() {
        String fullName;
        do {
            fullName = JOptionPane.showInputDialog("Please enter your full name (in format firstname surname): ");
            if (fullName == null) System.exit(0);
        } while (!isValidFullName(fullName));
        String phoneNumber;
        do {
            phoneNumber = JOptionPane.showInputDialog("Please enter your phone number (10-digit number in the format 0412345678): ");
            if (phoneNumber == null) System.exit(0);
        } while (!isValidPhoneNumber(phoneNumber));

        String email;
        do {
            email = JOptionPane.showInputDialog(null, "Please enter your email address.", appName, JOptionPane.QUESTION_MESSAGE);
            if (email == null) System.exit(0);
        } while (!isValidEmail(email));    //replace with regex


        return new Geek(fullName, phoneNumber, email);

    }

    /**
     * a very simple regex for full name in Firstname Surname format
     *
     * @param fullName the candidate full name entered by the user
     * @return true if name matches regex/false if not
     */
    public static boolean isValidFullName(String fullName) {
        String regex = "^[A-Z][a-z]+\\s[A-Z][a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fullName);
        return matcher.matches();
    }



    /**
     * a regex matcher that ensures that the user's entry starts with a 0 and is followed by 9 digits
     *
     * @param phoneNumber the candidate phone number entered by the user
     * @return true if phone number matches regex/false if not
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^0\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    /**
     * a regex matcher that ensures that the user's entry complies with RFC 5322
     *
     * @param email the candidate email entered by the user
     * @return true if email matches regex/false if not
     */
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * main static method for running the program
     *
     * @param args
     */
    public static void main(String[] args) {

        ImageIcon icon = new ImageIcon(iconPath);
        menus = loadmenus();
        Coffee geekOrder = getUserOrder(icon);

        List<Coffee> potentialMatches = menus.findMatch(geekOrder);
        System.out.printf("the potentialMatches %s", potentialMatches);
        if (!potentialMatches.isEmpty()) {
            Map<String, Coffee> options = new HashMap<>();
            StringBuilder infoToShow = new StringBuilder("Matches found!! The following coffees meet your criteria: \n");
            for (Coffee match : potentialMatches) {

                infoToShow.append("\n ********************************************* \n");
                options.put(match.getName() + " (" + match.getCode() + ")", match);
                infoToShow.append(match.getName()).append("(").append(match.getCode()).append(")").append("\n").append(match.getDescription()).append("\n").append("Ingredients: \n").append("Milk:").append(match.toStringMilk()).append("\n Number of Shots:").append(match.getNumberOfShots()).append("\n Sugar: ").append(match.getSugared()).append("\nExtras: ").append(match.toStringExtras()).append("\nPrice:").append(match.getPrice());
            }

            String order = (String) JOptionPane.showInputDialog(null,infoToShow+"\n\nPlease select which coffee you'd like to order:",appName, JOptionPane.QUESTION_MESSAGE,null,options.keySet().toArray(), "");
            if(order==null) System.exit(0);
            else{
                Coffee orderChoice = options.get(order);
                Geek customer = getGeekDetals();
                writeCustomerOrderRequestToFile(customer, orderChoice, geekOrder);
                JOptionPane.showMessageDialog(null, "Thank you! Your order has been submitted", appName, JOptionPane.INFORMATION_MESSAGE);
                           }
        } else JOptionPane.showMessageDialog(null, "Unfortunately, we don't have your preferred coffee :(" +
                "\n\tTo exit, click OK.", appName, JOptionPane.QUESTION_MESSAGE, icon);
        System.exit(0);
    }


    /**
     * generates JOptionPanes requesting user input for coffee, milk type, number of shots, extras, iced and sugared status and price
     *
     * @return a coffee object representing the user's dream coffee order
     */
    private static Coffee getUserOrder(ImageIcon icon) {
        List<MilkType> milkType = new ArrayList<>();
        MilkType milkTypeValue = (MilkType) JOptionPane.showInputDialog(null, "Please select your preferred type of milk", appName, JOptionPane.QUESTION_MESSAGE, icon, MilkType.values(), MilkType.FULL_CREAM);
        if (milkTypeValue == null) System.exit(0);

        milkType.add(milkTypeValue);
        int numberOfShots = -1;
        do {
            String nos = JOptionPane.showInputDialog(null, "Please enter the number of shorts you desire:", appName, JOptionPane.QUESTION_MESSAGE);

            if (nos == null) System.exit(0);
            numberOfShots = Integer.parseInt(nos);
        }while (numberOfShots<0);

        int sugarResponse = JOptionPane.showConfirmDialog(null, "Do you want sugar?", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
        Preference sugared = (sugarResponse == JOptionPane.YES_OPTION) ? Preference.YES : Preference.NO;
        System.out.println(sugared);


        if (sugarResponse == JOptionPane.CANCEL_OPTION) {
            System.exit(0);
        }


        int icedResponse = JOptionPane.showConfirmDialog(null, "Would you like iced coffee?", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
        HotPreference iced = (icedResponse == JOptionPane.YES_OPTION) ? HotPreference.YES : HotPreference.NO;

       // System.out.println("Iced preference:1 " + iced);
        if (icedResponse == JOptionPane.CANCEL_OPTION) {
            System.exit(0);
        }
        float minPrice = -1.00F;
        float maxPrice = -1.00F;
        while (minPrice == -1.00F) {
            try {
             String valuePrice = JOptionPane.showInputDialog(null, "Please enter the lowest price. The cheapest item is $3.00", appName, JOptionPane.QUESTION_MESSAGE);
             if(valuePrice == null) System.exit(0);
             minPrice = Float.parseFloat(valuePrice);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
            }
        }

        //max age cannot be less than min age
        while (maxPrice < minPrice) {
            try {
                String valuePrice = JOptionPane.showInputDialog(null, "Please enter the highest price. The priciest item is $8.10", appName, JOptionPane.QUESTION_MESSAGE);
                if(valuePrice == null) System.exit(0);
                maxPrice=Float.parseFloat(valuePrice);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
            }
            if (maxPrice < minPrice) JOptionPane.showMessageDialog(null, "Max price must be >= min price.");
        }
        //flag for checking another extra requirement
        boolean isAnotherExtra = true;
        Set<String> extras = new HashSet<>();
        while (isAnotherExtra) {

            List<String> extraOptions = new ArrayList<>(menus.getAllExtra());
            extraOptions.add("Skip");
            String extraValue = (String) JOptionPane.showInputDialog(null, "Please select an extra?", appName, JOptionPane.QUESTION_MESSAGE, icon, extraOptions.toArray(), extraOptions.getFirst());
            if (extraValue != null && !extraValue.isEmpty()) {
                if(!extraValue.equals("Skip")) {
                    extras.add(extraValue);
                    int option = JOptionPane.showConfirmDialog(null, "Would you like another extra?", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
                    if (option == JOptionPane.NO_OPTION) {
                        isAnotherExtra = false;
                    }
                }
                else {
                    isAnotherExtra = false;
                }
            }else{
                System.exit(0);
            }

        }

        Coffee coffeeOrder = new Coffee("", 0L, 0.00F, numberOfShots, sugared, iced, milkType, extras, "");
        coffeeOrder.setMinPrice(minPrice);
        coffeeOrder.setMaxPrice(maxPrice);
        return coffeeOrder;
    }


    /**
     * method to load all coffee data from file, storing it as coffee objects in an instance of Menu
     *
     * @return a menuItems object - functions as database of coffee, with associated methods
     */
    private static Menu loadmenus() {
        Menu menuItems = new Menu();
        Path path = Path.of(filePath);

        List<String> menuData = null;
        try {
            menuData = Files.readAllLines(path);
        } catch (IOException io) {
            System.out.println("Could not load the file. \nError message: " + io.getMessage());
            System.exit(0);
        }
        String regex = ",(?![^\\[]*\\])";
        for (int i = 1; i < menuData.size(); i++) {
            String[] items = menuData.get(i).split(regex);
            long code = 0L;
            try {
                code = Long.parseLong(items[0]);
            } catch (NumberFormatException n) {
                System.out.println("Error in file. Coffee code number could not be parsed for menu on line " + (i + 1) + ". Terminating. \nError message: " + n.getMessage());
                System.exit(0);
            }

            String name = items[1];


            float price = 0F;
            try {
                price = Float.parseFloat(items[2]);
            } catch (NumberFormatException n) {
                System.out.println("Error in file. price could not be parsed for order on line " + (i + 1) + ". Terminating. \nError message: " + n.getMessage());
                System.exit(0);
            }
            int numberOfShots = 0;
            try {
                numberOfShots = Integer.parseInt(items[3]);
            } catch (NumberFormatException n) {
                System.out.println("Error in file. number of shots could not be parsed for order on line " + (i + 1) + ". Terminating. \nError message: " + n.getMessage());
                System.exit(0);
            }
            Preference sugared = Preference.valueOf(items[4].toUpperCase());
            HotPreference iced = HotPreference.valueOf(items[5].toUpperCase());


            List<MilkType> milkTypelist = new ArrayList<>();

            String[] allItem = items[6].replaceAll("[\\[\\]]", "").split(",");

            for(String singleItem : allItem) {
                String cleanedItem = singleItem.replaceAll("[\\[\\]]", "").trim().replace("-", "_").toUpperCase();

                try {
                    MilkType milk = MilkType.valueOf(cleanedItem);
                    milkTypelist.add(milk);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error in file. Invalid milk type '" + cleanedItem + "' on line " + (i + 1) + ". Skipping.");
                }
            }
            Set<String> extras = new HashSet<>(Arrays.asList(( items[7]).replaceAll("[\\[\\]]", "").trim().split(", ")));
            extras.removeIf(String::isEmpty);
            System.out.println(extras);
            String description = items[8].replaceAll("[\\[\\]]","");

            Coffee coffee = new Coffee(name, code, price, numberOfShots, sugared, iced, milkTypelist, extras, description);
            System.out.println(coffee);
            menuItems.addMenu(coffee);
        }
        return menuItems;
    }

    /**
     * a method to write the Geek user's order information to a text file in the format specified by the Greek Geek.
     * @param geek the geek object
     * @param coffee the dream coffee object
     *
     */
    private static void writeCustomerOrderRequestToFile(Geek geek, Coffee coffee, Coffee geekOrder) {
        String filePath = geek.name().replace(" ","_")+"_Order_details_" + geek.phoneNumber() +"_.txt";
        Path path = Path.of(filePath);
        String lineToWrite = "Order details:\nName: " + geek.name()+" ("+geek.phoneNumber() +")\nEmail: "+geek.emailAddress()+"\nItem: "+coffee.getName()+" ("+coffee.getCode()+
                ") - "+ geekOrder.getIced()+"\nMIlk: "+geekOrder.toStringMilk()+"\nExtras: "+geekOrder.toStringExtras();
        try {
            Files.writeString(path, lineToWrite);
        }catch (IOException io){
            System.out.println("File could not be written. \nError message: "+io.getMessage());
            System.exit(0);
        }
    }

}
