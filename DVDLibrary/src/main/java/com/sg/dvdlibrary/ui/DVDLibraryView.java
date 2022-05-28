/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/02/21
 * purpose: M3 Assignment "DVD Library-Adding Lambdas and Streams"
 */
package com.sg.dvdlibrary.ui;

import com.sg.dvdlibrary.dto.DVD;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DVDLibraryView {

    private UserIO io;

    //Dependency Injection
    public DVDLibraryView(UserIO io) {
        this.io = io;
    }
    //Displays main menu and get user choice within range

    public int printMenuAndGetSelection() {
        io.print("===== Main Menu =====");
        io.print("1. Add DVD");  //Add dvd
        io.print("2. Remove DVD"); //Remove dvd
        io.print("3. Edit DVD Info"); //Search by title and edit info
        io.print("4. List All DVDs"); //List all dvds from list
        io.print("5. Search DVDs"); //Search for dvd by title and display info about it
        io.print("6. Exit ");

        return io.readInt("Please select from the above choices.", 1, 6);

    }

    //Getting input from user and assigning input as values to object fields in list using setters
    //adds new DVD to HashMap
    public DVD getNewDvdInfo() {
        String title = io.mustReadString("Please enter the DVD title");
        String releaseDate = io.mustReadString("Please enter the release date(MM/DD/YYYY)");
        LocalDate rd = LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String mpaaRating = io.mustReadString("Please enter MPAA rating");
        String directorName = io.mustReadString("Please enter the name of the director");
        String studio = io.mustReadString("Please enter the studio name");
        String userRating = io.mustReadString("Please enter your own rating or note(s) about this title");
        DVD currentDvd = new DVD(title);
        currentDvd.setReleaseDate(rd);
        currentDvd.setMpaaRating(mpaaRating);
        currentDvd.setDirectorName(directorName);
        currentDvd.setStudio(studio);
        currentDvd.setUserRating(userRating);

        return currentDvd;
    }

    public void displayDvdExistsError() {
        io.print("  !!!!Failed to add DVD!!!!");
        io.readString("A DVD with that title already exists. Please press enter to return to main menu...");
    }

    public void displayDvdDoesntExistError() {
        io.readString("A DVD with that title does not exist in library. Press enter to return to main menu...");
    }

    public void displayAddDvdBanner() {
        io.print("=== Add DVD === \n ");
    }

    public void displayAddSuccessBanner() {
        io.readString("DVD successfully added. Please press enter to continue...");
    }

    public void displayDvdList(List<DVD> dvdList) {
        if (dvdList.isEmpty()) {
            io.print("");
            io.print("No Results Found!!!");
            io.print("");
        } else {
            for (DVD currentDvd : dvdList) {
                io.print("            TITLE:  " + currentDvd.getTitle());
                io.print("     RELEASE DATE:  " + currentDvd.getReleaseDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                io.print("      MPAA RATING:  " + currentDvd.getMpaaRating());
                io.print("         DIRECTOR:  " + currentDvd.getDirectorName());
                io.print("           STUDIO:  " + currentDvd.getStudio());
                io.print("USER RATING/NOTES:  " + currentDvd.getUserRating());
                io.print("___________________________");
                io.print(" \n ");
            }
            io.readString("Please press enter to continue...");
        }
    }

    public void displayDisplayAllBanner() {
        io.print("=== Showing All DVDs === \n ");
    }

    public void displayDisplayDvdBanner() {
        io.print("=== Display DVD === \n ");
    }

    //Simply prompts user for DVD title 
    public String getTitleChoice() {
        return io.mustReadString("Please enter DVD title.");
    }

    //Displays info specific to DVD title
    public void displayDvd(DVD dvd) {
        if (dvd != null) {
            io.print("            TITLE:  " + dvd.getTitle());
            io.print("     RELEASE DATE:  " + dvd.getReleaseDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            io.print("      MPAA RATING:  " + dvd.getMpaaRating());
            io.print("         DIRECTOR:  " + dvd.getDirectorName());
            io.print("           STUDIO:  " + dvd.getStudio());
            io.print("USER RATING/NOTES:  " + dvd.getUserRating());
            io.print("___________________________");
            io.print(" \n ");
        } else {
            io.print("No such DVD exists in library.");
        }
        io.readString("Please press enter to continue...");
    }

    public void displayRemoveDvdBanner() {
        io.print("=== Remove DVD === \n ");
    }

    public void displayRemoveResult(DVD dvdRecord) {
        if (dvdRecord != null) {
            io.print("DVD successfully removed from library.");
        } else {
            io.print("No such DVD exists in library");
        }
        io.readString("Please press enter to continue...");
    }

    public void displayExitBanner() {
        io.print("Good Bye!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!");
    }

    public void displayEditDvdBanner() {
        io.print("=== Edit DVD Menu === \n ");
    }

    public void displayEditSuccessMsg() {
        io.print("The selected DVD has been updated.");
        io.readString("Please press enter to return to the main menu..");
    }

    //Gets existing DVD and allows user to edit values
    public DVD editDvdInfo(DVD editDvd) {

        String editedInfo;
        int editSelection;

        DVD newDvd = new DVD(editDvd.getTitle());
        newDvd.setReleaseDate(editDvd.getReleaseDate());
        newDvd.setMpaaRating(editDvd.getMpaaRating());
        newDvd.setDirectorName(editDvd.getDirectorName());
        newDvd.setStudio(editDvd.getStudio());
        newDvd.setUserRating(editDvd.getUserRating());

        if (editDvd != null) {
            io.print("            TITLE:  " + editDvd.getTitle());
            io.print("");
            io.print("     RELEASE DATE:  " + editDvd.getReleaseDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            io.print("      MPAA RATING:  " + editDvd.getMpaaRating());
            io.print("         DIRECTOR:  " + editDvd.getDirectorName());
            io.print("           STUDIO:  " + editDvd.getStudio());
            io.print("USER RATING/NOTES:  " + editDvd.getUserRating());
            io.print(" \n ");
            io.print("1. Edit Title");
            io.print("2. Edit Release Date");
            io.print("3. Edit MPAA Rating");
            io.print("4. Edit Director");
            io.print("5. Edit Studio");
            io.print("6. Edit User Rating/Notes");
            io.print("7. Exit to Main Menu");

            editSelection = io.readInt(" \nWhat info about " + editDvd.getTitle() + " would you like to edit?", 1, 7);
            io.print("");

            switch (editSelection) {

                case 1:
                    editedInfo = io.mustReadString("Please enter the new title");
                    newDvd = new DVD(editedInfo);
                    newDvd.setReleaseDate(editDvd.getReleaseDate());
                    newDvd.setMpaaRating(editDvd.getMpaaRating());
                    newDvd.setDirectorName(editDvd.getDirectorName());
                    newDvd.setStudio(editDvd.getStudio());
                    newDvd.setUserRating(editDvd.getUserRating());
                    io.print("Title successfully updated.");
                    break;
                case 2:
                    editedInfo = io.mustReadString("Enter new 'Release Date': ");
                    LocalDate ld = LocalDate.parse(editedInfo, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    newDvd.setReleaseDate(ld);
                    io.print("Release Date successfully updated.");
                    break;
                case 3:
                    editedInfo = io.mustReadString("Enter new 'MPAA Rating':");
                    newDvd.setMpaaRating(editedInfo);
                    io.print("MPAA Rating successfully updated.");
                    break;
                case 4:
                    editedInfo = io.mustReadString("Enter new 'Director':");
                    newDvd.setDirectorName(editedInfo);
                    io.print("Director successfully updated.");
                    break;
                case 5:
                    editedInfo = io.mustReadString("Enter new 'Studio':");
                    newDvd.setStudio(editedInfo);
                    io.print("Studio successfully updated.");
                    break;
                case 6:
                    editedInfo = io.mustReadString("Enter new 'User Rating/Notes':");
                    newDvd.setUserRating(editedInfo);
                    io.print("User Rating/Notes successfully updated.");
                    break;
                case 7:
                    io.print("Exiting to Main Menu..");
                    break;
                default:
                    io.print("Unknown Command");
            }
        } else {
            io.print(" \nNo such DVD exists.");
        }
        return newDvd;
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void displaySearchMenu(DVD dvd) {
        if (dvd != null) {
            io.print("            TITLE:  " + dvd.getTitle());
            io.print("     RELEASE DATE:  " + dvd.getReleaseDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            io.print("      MPAA RATING:  " + dvd.getMpaaRating());
            io.print("         DIRECTOR:  " + dvd.getDirectorName());
            io.print("           STUDIO:  " + dvd.getStudio());
            io.print("USER RATING/NOTES:  " + dvd.getUserRating());
            io.print("___________________________");
            io.print(" \n ");
        } else {
            io.print("No such DVD exists in library.");
        }
        io.readString("Please press enter to continue...");
    }

    public int displaySearchMenu() {
        io.print("===== Search Menu ======");
        io.print("");
        io.print("How would you like to search?");
        io.print("1. Search by title.");
        io.print("2. Search by release date in the last x years.");
        io.print("3. Search by MPAA Rating.");
        io.print("4. Search by director.");
        io.print("5. Search by Studio.");
        io.print("6. view average age of all DVDs.");
        io.print("7. view the newest DVD.");
        io.print("8. view the oldest DVD");
        io.print("9. view average number of notes associated with DVDs.");
        io.print("10. Exit to main menu.");

        return io.readInt("Please select from the above choices.", 1, 10);

    }

    public int displaySearchByReleaseDatePrompt() {
        return io.readInt("Please enter the number of years we should go back from today: ", 0, LocalDate.now().getYear());
    }

    public void displaySearchResultsBanner() {
        io.print("=== Search Results ===");
        io.print("");
        io.print("");
    }

    public String displayMpaaRatingPrompt() {
        return io.readString("Please enter an MPAA Rating:");
    }

    public String displayDirectorPrompt() {
        return io.readString("Please enter a director:");
    }

    public String displayStudioPrompt() {
        return io.readString("Please enter a studio:");
    }

    public void displayAverageAgeMessage(double averageAge) {
        io.print("The average age of all DVDS in the library is " + averageAge + " years old.");
    }

    public void displayAverageAgeBanner() {
        io.print("=== Average Age of all DVDs === ");
        io.print("");
        io.print("");
    }

    public void displayNewestDvdBanner() {
        io.print("=== Display Newest DVD ===");
        io.print("");
        io.print("");
    }

    public void displayOldestDvdBanner() {
        io.print("=== Display Oldest DVD ===");
        io.print("");
        io.print("");
    }

    public void displayAverageNumOfNotesBanner() {
        io.print("=== Average Amount of DVDs that Contain User Notes ===");
        io.print("");
        io.print("");
    }

    public void displayAverageNotesMessage(double averageNum) {
        io.print("The average number of DVDs with User Ratings/Notes is " + averageNum + ".");
        io.print("");
        io.print("");
    }
}
