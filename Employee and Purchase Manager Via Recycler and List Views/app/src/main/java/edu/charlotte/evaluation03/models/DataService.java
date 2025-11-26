package edu.charlotte.evaluation03.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

public class DataService {

    private static final ArrayList<String> EMPLOYEE_NAMES = new ArrayList<>(Arrays.asList(
            "Alice Johnson", "Bob Smith", "Charlie Evans", "Diana Lee", "Ethan Davis",
            "Fiona Clark", "George Baker", "Hannah Scott", "Ian Wright", "Jenna Hall",
            "Kyle Adams", "Laura Turner", "Mike Nelson", "Nora Perez", "Oscar Hughes",
            "Paula Ramirez", "Quinn Foster", "Rita Cooper", "Sam Green", "Tina Brooks",
            "Uma Patel", "Victor Ward", "Wendy Cox", "Xander Reed", "Yara Sullivan"
    ));

    private static final ArrayList<String> DEPARTMENTS = new ArrayList<>(Arrays.asList(
            "IT", "Finance", "HR", "Marketing", "Sales", "Operations"
    ));

    // ✅ Fixed prices per item for consistency
    private static final Map<String, Double> ITEM_CATALOG = new HashMap<>() {{
        put("Laptop", 899.99);
        put("Monitor", 249.99);
        put("Keyboard", 49.99);
        put("Mouse", 29.99);
        put("Desk Chair", 159.99);
        put("Printer", 199.99);
        put("USB Drive", 19.99);
        put("Headphones", 89.99);
        put("Notebook", 7.99);
        put("Pen Set", 12.49);
        put("Coffee Mug", 14.99);
        put("Phone Charger", 24.99);
        put("Backpack", 59.99);
        put("Tablet", 499.99);
        put("Desk Lamp", 39.99);
    }};

    private static final long randomSeed = 12345L; // Fixed seed for reproducibility

    private static final Random random = new Random(randomSeed);

    public static ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            String name = EMPLOYEE_NAMES.get(i);
            String department = DEPARTMENTS.get(random.nextInt(DEPARTMENTS.size()));
            ArrayList<Purchase> purchases = generatePurchases();
            employees.add(new Employee(name, department, purchases));
        }

        return employees;
    }

    private static ArrayList<Purchase> generatePurchases() {
        ArrayList<Purchase> purchases = new ArrayList<>();
        int numberOfPurchases = random.nextInt(3) + 2; // 2–4 purchases per employee

        for (int i = 0; i < numberOfPurchases; i++) {
            Date purchaseDate = randomDate();
            ArrayList<Item> items = generateItems();
            purchases.add(new Purchase(purchaseDate, items));
        }

        return purchases;
    }

    private static ArrayList<Item> generateItems() {
        ArrayList<Item> items = new ArrayList<>();
        ArrayList<String> itemNames = new ArrayList<>(ITEM_CATALOG.keySet());
        int itemCount = random.nextInt(9) + 1; // 1–9 items per purchase
        HashSet<String> selectedItems = new HashSet<>();

        for (int i = 0; i < itemCount; i++) {
            String itemName = itemNames.get(random.nextInt(itemNames.size()));
            if (!selectedItems.contains(itemName)) {
                selectedItems.add(itemName);
                double price = ITEM_CATALOG.get(itemName);
                int quantity = random.nextInt(5) + 1; // 1–5 quantity
                items.add(new Item(itemName, price, quantity));
            }
        }

        return items;
    }

    private static Date randomDate() {
        Calendar cal = Calendar.getInstance();
        int daysAgo = random.nextInt(365); // within last year
        cal.add(Calendar.DAY_OF_YEAR, -daysAgo);
        return cal.getTime();
    }
}
