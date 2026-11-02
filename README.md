# Brewed Brilliance Kiosk

A Java Swing practice project: a self-service coffee kiosk. The customer describes the coffee they want, the app searches the menu for matching drinks, and the chosen order is saved to a file.

## Features

- Loads the menu from `menu.txt` (ID, name, price, shots, sugar, iced, milk types, extras, description)
- Asks the customer for their preferences in dialog boxes: milk type, number of shots, sugar, iced, price range and extras
- Finds every menu item that matches and lets the customer pick one
- Validates input: full name, 10-digit phone number, email address, numbers and a minimum price that is not above the maximum
- Saves the order details to a text file

## Concepts practised

- Object-oriented design: `Coffee` and `Menu` classes, `Geek` (customer) as a record, and `MilkType`, `Preference` and `HotPreference` as enums
- Reading and parsing a CSV-like file with `java.nio.file`
- Regular expressions for input validation
- Collections and filtering
- Java Swing dialogs (`JOptionPane`)

## Run

Requires **Java 21 or newer**. Run from the project folder so the app can find `menu.txt` and the image:

```bash
javac -d out src/*.java
java -cp out MenuSearcher
```

## Files

```
src/
├── MenuSearcher.java   main program: dialogs, search and saving the order
├── Menu.java           holds the menu and finds matching coffees
├── Coffee.java         one menu item
├── Preference.java     yes/no and hot/iced enums
├── MilkType.java       milk options enum
└── Geek.java           customer details
menu.txt                menu data
```
