/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author dr304
 */
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
                do {
                    System.out.println(prompt);
                    userInput = Integer.parseInt(sc.nextLine());
                } while (userInput > max || userInput < min);
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
                do {
                    System.out.println(prompt);
                    userInput = Double.parseDouble(sc.nextLine());
                } while (userInput > max || userInput < min);
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
                do {
                    System.out.println(prompt);
                    userInput = Float.parseFloat(sc.nextLine());
                } while (userInput > max || userInput < min);
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
                do {
                    System.out.println(prompt);
                    userInput = Long.parseLong(sc.nextLine());
                } while (userInput > max || userInput < min);
                invalidInput = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number..");
            }
        }
        return userInput;
    }

    @Override
    public boolean readBoolean(String prompt) {
        boolean invalidInput = true;
        String userInput;
        boolean input = false;
        while (invalidInput) {
            System.out.println(prompt + " (Y/N)");
            userInput = sc.nextLine();
            if (userInput.equalsIgnoreCase("y")) {
                input = true;
                invalidInput = false;
            } else if (userInput.equalsIgnoreCase("n")) {
                input = false;
                invalidInput = false;
            }
        }
        return input;
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        LocalDate userInput = null;
        Boolean invalid = true;
        do {
            try {
                System.out.println(prompt);
                userInput = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                invalid = false;
            } catch (DateTimeParseException e) {
                System.out.println("You must enter a date using the correct format. (MM/dd/yyyy)");
            }
        } while (invalid);
        return userInput;
    }

    @Override
    public LocalDate readLocalDate(String prompt, LocalDate min, LocalDate max) {
        LocalDate userInput = null;
        Boolean invalid = true;
        do {
            try {
                System.out.println(prompt);
                userInput = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                if (userInput.isAfter(max) || userInput.isBefore(min)) {
                    System.out.println("Date must not be in the past");
                } else {
                    invalid = false;
                }
            } catch (DateTimeParseException e) {
                System.out.println("You must enter a date using the correct format. (MM/dd/yyyy)");
            }
        } while (invalid);
        return userInput;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        boolean invalidInput = true;
        BigDecimal userInput = new BigDecimal("0");
        while (invalidInput) {
            try {
                System.out.println(prompt);
                userInput = new BigDecimal(sc.nextLine()).setScale(2, RoundingMode.HALF_UP);
                invalidInput = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number...");
            }
        }
        return userInput;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        boolean invalidInput = true;
        BigDecimal userInput = new BigDecimal("0");
        int maxRes;
        int minRes;
        while (invalidInput) {
            try {
                do {
                    System.out.println(prompt);
                    userInput = new BigDecimal(sc.nextLine()).setScale(2, RoundingMode.HALF_UP);
                    maxRes = userInput.compareTo(max);
                    minRes = userInput.compareTo(min);
                } while (maxRes > 0 || minRes < 0);
                invalidInput = false;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number above " + min);
            }
        }
        return userInput;
    }

}
