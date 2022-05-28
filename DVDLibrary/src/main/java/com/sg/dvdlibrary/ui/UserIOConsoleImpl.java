/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/02/21
 * purpose: M3 Assignment "DVD Library-Adding Lambdas and Streams"
 */
package com.sg.dvdlibrary.ui;

import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {

    final private Scanner sc = new Scanner(System.in);

    @Override   //Simply prints a message to console
    public void print(String message) {
        System.out.println(message);
    }

    @Override //Displays message, gets user input, then returns user input as String
    public String readString(String prompt) {
        System.out.println(prompt);
        String userInput = sc.nextLine();
        return userInput;
    }

    @Override //Displays message, gets user input, then returns user input as String if not blank 
    public String mustReadString(String prompt) {
        System.out.println(prompt);
        String userInput = sc.nextLine();
        while (userInput.isBlank()) {
            System.out.println("This field cannot be blank!");
            System.out.println(prompt);
            userInput = sc.nextLine();
        }

        return userInput;
    }

    @Override //Displays message, gets user input, then returns user input as int
    public int readInt(String prompt) {
        boolean invalidInput = true;
        int userInput = 0;
        while (invalidInput) {
            try {
                System.out.println(prompt);
                userInput = Integer.parseInt(sc.nextLine());
                invalidInput = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number..");
            }
        }

        return userInput;
    }

    @Override //Displays message, gets user input in range, then returns user input as int
    public int readInt(String prompt, int min, int max) {
        boolean invalidInput = true;
        int userInput = 0;
        while (invalidInput) {
            try {
                System.out.println(prompt);
                userInput = Integer.parseInt(sc.nextLine());
                for (int i = 0; userInput > max || userInput < min; i++) {
                    System.out.println(prompt);
                    userInput = Integer.parseInt(sc.nextLine());
                }
                invalidInput = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number..");
            }
        }
        return userInput;
    }

    @Override //Displays message, gets user input, then returns user input as double
    public double readDouble(String prompt) {
        boolean invalidInput = true;
        double userInput = 0;
        while (invalidInput) {
            try {
                System.out.println(prompt);
                userInput = Double.parseDouble(sc.nextLine());
                invalidInput = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid input");
            }
        }
        return userInput;
    }

    @Override //Displays message, get user input in range, then returns user input as double
    public double readDouble(String prompt, double min, double max) {
        boolean invalidInput = true;
        double userInput = 0;
        while (invalidInput) {
            try {
                System.out.println(prompt);
                userInput = Double.parseDouble(sc.nextLine());
                for (int i = 0; userInput > max || userInput < min; i++) {
                    System.out.println(prompt);
                    userInput = Double.parseDouble(sc.nextLine());
                }
                invalidInput = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number..");
            }
        }
        return userInput;
    }

    @Override //Displays message, get user input, then returns user input as float
    public float readFloat(String prompt) {
        boolean invalidInput = true;
        float userInput = 0;
        while (invalidInput) {
            try {
                System.out.println(prompt);
                userInput = Float.parseFloat(sc.nextLine());
                invalidInput = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
        return userInput;
    }

    @Override  //Displays message, gets user input in range, then returns user input as float
    public float readFloat(String prompt, float min, float max) {
        boolean invalidInput = true;
        float userInput = 0;
        while (invalidInput) {
            try {
                System.out.println(prompt);
                userInput = Float.parseFloat(sc.nextLine());
                for (int i = 0; userInput > max || userInput < min; i++) {
                    System.out.println(prompt);
                    userInput = Float.parseFloat(sc.nextLine());
                }
                invalidInput = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number..");
            }
        }
        return userInput;
    }

    @Override  //Displays message, gets user input, then returns user input as long
    public long readLong(String prompt) {
        boolean invalidInput = true;
        long userInput = 0;
        while (invalidInput) {
            try {
                System.out.println(prompt);
                userInput = Long.parseLong(sc.nextLine());
                invalidInput = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
        return userInput;
    }

    @Override  //Displays message, gets user input in range, then returns user input as long
    public long readLong(String prompt, long min, long max) {
        boolean invalidInput = true;
        long userInput = 0;
        while (invalidInput) {
            try {
                System.out.println(prompt);
                userInput = Long.parseLong(sc.nextLine());
                for (int i = 0; userInput > max || userInput < min; i++) {
                    System.out.println(prompt);
                    userInput = Long.parseLong(sc.nextLine());
                }
                invalidInput = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number..");
            }
        }
        return userInput;
    }
}
