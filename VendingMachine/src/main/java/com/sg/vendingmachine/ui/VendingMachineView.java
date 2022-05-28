/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/06/22
 * purpose: M3-Summative - Vending Machine
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public int displayMenuBeforeCoins(List<Item> items) {
        io.print("***************************");
        io.print("***** THE SNACK SHACK *****");
        io.print("***************************");
        io.print("*   ITEM    |     PRICE   *");
        io.print("*___________|_____________*");

        for (Item item : items) {
            io.print(item.getName() + "        $" + item.getCost());
        }
        io.print("");
        io.print("-Press 1 to INSERT COINS-");
        io.print("-Press 2 to ACCESS ADMIN MENU-");
        io.print("-Press 3 to EXIT-");
        return io.readInt("Select an option", 1, 3);
    }

    public Change getCoinsAndDisplayAmount(Change existingCredits) {

        int dol = io.readInt("Please enter the amount of DOLLAR BILLS to insert: ", 0, Integer.MAX_VALUE);
        int q = io.readInt("Please enter the amount of QUARTERS to insert:", 0, Integer.MAX_VALUE);
        int d = io.readInt("Please enter the amount of DIMES to insert", 0, Integer.MAX_VALUE);
        int n = io.readInt("Please enter the amount of NICKELS to insert", 0, Integer.MAX_VALUE);
        int p = io.readInt("Please enter the amount of PENNIES to insert", 0, Integer.MAX_VALUE);
        Change change = new Change(dol, q, d, n, p);

        BigDecimal amount = change.getAmount().add(existingCredits.getAmount())
                .setScale(2, RoundingMode.HALF_UP);
        io.print("TOTAL CREDITS: $" + amount.toString());
        Change total = new Change(amount);
        return total;
    }

    public String displayMenuAndGetSelection(List<Item> items) {
        int selection;
        Item selectedItem;
        String id = "";
        int counter = 1;

        io.print("***************************");
        io.print("***** THE SNACK SHACK *****");
        io.print("***************************");
        io.print("*   ITEM    |     PRICE   *");
        io.print("*___________|_____________*");

        for (Item item : items) {
            io.print(counter + ". " + item.getName() + "        $" + item.getCost());
            counter++;
        }
        selection = io.readInt("Choose from the above options", 1, items.size() + 1);
        if (selection <= items.size()) {
            selectedItem = items.get(selection - 1);
            id = selectedItem.getId();
        }
        return id;
    }

    public int displaySelectAddExitPrompt() {
        io.print("");
        io.print("1. SELECT AN ITEM");
        io.print("2. ADD MORE COINS");
        io.print("3. REFUND & EXIT");
        io.print("");
        return io.readInt("Select an option", 1, 3);
    }

    public void displayExitMessage() {
        io.print("");
        io.print("GOOD BYE..");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayRefund(Change change) {
        io.print("");
        io.print("=== PLEASE TAKE YOUR CHANGE ===\n ");
        io.print("Quarters: " + change.getQuarters());
        io.print("Dimes: " + change.getDimes());
        io.print("Nickels: " + change.getNickels());
        io.print("Pennies: " + change.getPennies());

        io.print("");
        io.print("Total Refund: $" + change.getAmount()
                .setScale(2, RoundingMode.HALF_UP).toString());
        io.print("");
        io.print("Thank you!");
    }

    public void displaySaleRefund(Item item, Change change) {
        io.print("=== ITEM VENDED ===");
        io.print("");
        io.print("Enjoy your " + item.getName());
        io.print("");
        io.print("=== PLEASE TAKE YOUR CHANGE ===\n ");
        io.print("Quarters: " + change.getQuarters());
        io.print("Dimes: " + change.getDimes());
        io.print("Nickels: " + change.getNickels());
        io.print("Pennies: " + change.getPennies());

        io.print("");
        io.print("Total Refund: $" + change.getAmount().setScale(2, RoundingMode.HALF_UP).toString());
        io.print("");
        io.print("Thank you!");
    }

    public int displayAdminMenu() {
        io.print("=== ADMIN MENU ===");
        io.print("");
        io.print("1. View all items.");
        io.print("2. Add item to inventory.");
        io.print("3. Edit Item.");
        io.print("4. Remove item from inventory.");
        io.print("5. Exit admin menu");
        io.print("");
        return io.readInt("Select an option.", 1, 5);
    }

    public Item displayAddItem() {
        io.print("=== ADD ITEM ===");
        io.print("");
        String id = io.readString("ENTER ITEM ID: ");
        String name = io.readString("ENTER ITEM NAME");
        double dPrice = io.readDouble("ENTER ITEM PRICE: ", 0, 100);
        BigDecimal price = new BigDecimal(dPrice).setScale(2, RoundingMode.HALF_UP);
        int total = io.readInt("ENTER ITEM INVENTORY TOTAL: ", 0, 999);
        Item item = new Item(id);
        item.setName(name);
        item.setCost(price);
        item.setTotal(total);
        return item;
    }

    public Item displayRemoveItem(List<Item> items) {
        Item deletedItem = new Item("0000");
        io.print("=== REMOVE ITEM ===");
        io.print("");
        for (Item item : items) {
            io.print(item.getId() + " " + item.getName() + " " + item.getCost().toString() + " " + item.getTotal());
        }
        io.print("");
        String id = io.mustReadString("ENTER ITEM ID TO DELETE: ");
        for (Item itemToDelete : items) {
            if (itemToDelete.getId().equals(id)) {
                deletedItem = itemToDelete;
            } else {
                io.print("The id entered does not match any items in inventory.");
                io.readString("Please press enter to continue.");
            }

        }
        return deletedItem;

    }

    public void displayAllItems(List<Item> items) {
        io.print("=== INVENTORY LIST ===");
        io.print("");
        if (items.isEmpty()) {
            io.print("");
            io.print("No Inventory Available");
            io.print("");
        } else {
            for (Item item : items) {
                io.print("   ID#: " + item.getId());
                io.print("  Name: " + item.getName());
                io.print(" Price: " + item.getCost().setScale(2, RoundingMode.HALF_UP));
                io.print(" Count: " + item.getTotal());
                io.print("_____________________________");
                io.print("");

            }
            io.readString("Press ENTER to continue");
        }
    }

    public Item displayEditItemMenu(Item item) {
        int editInfo;
        String editedInfo;
        int selection;
        io.print("=== EDIT ITEM ===");

        Item newItem = new Item(item.getId());
        newItem.setName(item.getName());
        newItem.setCost(item.getCost());
        newItem.setTotal(item.getTotal());

        if (item != null) {
            io.print("     ID#: " + item.getId());
            io.print("   Price: " + item.getCost());
            io.print("    Name: " + item.getName());
            io.print("   Count: " + item.getTotal());
            io.print("");
            io.print("1. Edit ID");
            io.print("2. Edit Price");
            io.print("3. Edit Name");
            io.print("4. Edit Count");
            io.print("5. Exit to Main Menu");

            selection = io.readInt("Select an option above", 1, 5);
            io.print("");

            switch (selection) {
                case 1:
                    editedInfo = io.mustReadString("Enter new ID#:");
                    newItem = new Item(editedInfo);
                    newItem.setName(item.getName());
                    newItem.setCost(item.getCost());
                    newItem.setTotal(item.getTotal());
                    io.print("ID successfully updated.");
                    io.readString("Press ENTER to continue..");
                    break;
                case 2:
                    editedInfo = io.mustReadString("Enter new Price: ");
                    newItem.setCost(new BigDecimal(editedInfo));
                    io.print("Price successfully updated.");
                    io.readString("press ENTER to continue..");
                    break;
                case 3:
                    editedInfo = io.mustReadString("Enter new Name: ");
                    newItem.setName(editedInfo);
                    io.print("Name successfully updated.");
                    io.readString("press ENTER to continue..");
                    break;
                case 4:
                    editInfo = io.readInt("Enter new Count: ", 0, 999);
                    newItem.setTotal(editInfo);
                    io.print("Count successfully updated.");
                    io.readString("press ENTER to continue..");
                    break;
                case 5:
                    io.print("Exiting to Main Menu");
                    break;
                default:
                    io.print("Unknown Command");
            }

        } else {
            io.print("No such Item exists in inventory");
        }
        return newItem;

    }

    public String displayItemsAndGetChoice(List<Item> items) {
        io.print("=== INVENTORY LIST ===");
        io.print("");
        if (items.isEmpty()) {
            io.print("");
            io.print("No Inventory Available");
            io.print("");
        } else {
            for (Item item : items) {
                io.print("   ID#: " + item.getId());
                io.print("  Name: " + item.getName());
                io.print(" Price: " + item.getCost().setScale(2, RoundingMode.HALF_UP));
                io.print(" Count: " + item.getTotal());
                io.print("_____________________________");
                io.print("");
            }
        }
        return io.readString("Plese enter item ID to edit.");
    }

    public void displayItemDoesntExistError() {
        io.print("This item does not exist in inventory..");
    }

    public boolean displayPasswordPrompt() {
        boolean noAccess = true;
        boolean access = false;
        while (noAccess) {
            io.print("Press 1 to exit to main menu");
            String entered = io.mustReadString("ENTER PASSWORD: ");

            if (entered.equals("password")) {
                noAccess = false;
                access = true;
            } else if (entered.equals("1")) {
                noAccess = false;
            } else {
                io.print("Incorrect Password!!");
            }
        }
        return access;
    }
}
