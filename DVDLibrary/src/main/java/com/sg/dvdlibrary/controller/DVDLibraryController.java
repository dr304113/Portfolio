/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/02/21
 * purpose: M3 Assignment "DVD Library-Adding Lambdas and Streams"
 */
package com.sg.dvdlibrary.controller;

import com.sg.dvdlibrary.dao.DVDLibraryDao;
import com.sg.dvdlibrary.dao.DVDLibraryDaoException;
import com.sg.dvdlibrary.dao.DVDLibraryLambdasAndStreamsDao;
import com.sg.dvdlibrary.dto.DVD;
import com.sg.dvdlibrary.ui.DVDLibraryView;
import java.util.List;
import java.util.Map;

public class DVDLibraryController {

    private DVDLibraryView view;
    private DVDLibraryDao dao;
    private DVDLibraryLambdasAndStreamsDao LSDao;

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        addDvd();
                        break;
                    case 2:
                        removeDvd();
                        break;
                    case 3:
                        editDvdInformation();
                        break;
                    case 4:
                        listDvds();
                        break;
                    case 5:
                        //   viewDvd();
                        searchDvd();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        } catch (DVDLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    //Dependency Injection,
    public DVDLibraryController(DVDLibraryDao dao, DVDLibraryView view, DVDLibraryLambdasAndStreamsDao LSDao) {
        this.dao = dao;
        this.view = view;
        this.LSDao = LSDao;
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void addDvd() throws DVDLibraryDaoException {
        view.displayAddDvdBanner();
        DVD newDvd = view.getNewDvdInfo();
        if (dao.getDvd(newDvd.getTitle()) != null) {
            view.displayDvdExistsError();
        } else {
            dao.addDvd(newDvd.getTitle(), newDvd);
            view.displayAddSuccessBanner();
        }
    }

    private void listDvds() throws DVDLibraryDaoException {
        view.displayDisplayAllBanner();
        List<DVD> dvdList = dao.getAllDVDs();   //loads
        view.displayDvdList(dvdList);
    }

    private void viewDvd() throws DVDLibraryDaoException {
        view.displayDisplayDvdBanner();
        String title = view.getTitleChoice();
        DVD dvd = dao.getDvd(title);        //loads
        view.displayDvd(dvd);
    }

    private void removeDvd() throws DVDLibraryDaoException {
        view.displayRemoveDvdBanner();
        String title = view.getTitleChoice();
        DVD removedDvd = dao.removeDvd(title);   //loads and writes
        view.displayRemoveResult(removedDvd);
    }

    public void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    public void exitMessage() {
        view.displayExitBanner();
    }

    public void editDvdInformation() throws DVDLibraryDaoException {
        view.displayEditDvdBanner();
        String title = view.getTitleChoice();
        DVD prevDvd = dao.getDvd(title);
        if (prevDvd != null) {
            DVD editedDvd = view.editDvdInfo(prevDvd);
            dao.editDvd(prevDvd.getTitle(), editedDvd);
        } else {
            view.displayDvdDoesntExistError();
        }
    }

    public void searchDvd() {
        Boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {

                menuSelection = view.displaySearchMenu();

                switch (menuSelection) {
                    case 1:
                        viewDvd();
                        break;
                    case 2:
                        getDvdsReleasedInYears();
                        break;
                    case 3:
                        getDvdsByMpaaRating();
                        break;
                    case 4:
                        getDvdByDirector();
                        break;
                    case 5:
                        getDvdByStudio();
                        break;
                    case 6:
                        getAverageDvdAge();
                        break;
                    case 7:
                        getNewestDvd();
                        break;
                    case 8:
                        getOldestDvd();
                        break;
                    case 9:
                        getAverageNumNotes();
                        break;
                    case 10:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
        } catch (DVDLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    public void getDvdsReleasedInYears() throws DVDLibraryDaoException {
        int releaseDateMin = view.displaySearchByReleaseDatePrompt();
        List<DVD> dvds = LSDao.searchDvdsReleased(releaseDateMin);
        view.displaySearchResultsBanner();
        view.displayDvdList(dvds);
    }

    public void getDvdsByMpaaRating() throws DVDLibraryDaoException {
        String rating = view.displayMpaaRatingPrompt();
        List<DVD> sortedDvds = LSDao.searchDvdsByMpaaRating(rating);
        view.displaySearchResultsBanner();
        view.displayDvdList(sortedDvds);
    }

    public void getDvdByDirector() throws DVDLibraryDaoException {
        String director = view.displayDirectorPrompt();
        view.displaySearchResultsBanner();
        Map<String, List<DVD>> map = LSDao.searchDvdsByDirector(director);
        map.values().forEach(sortedDvds -> {
            view.displayDvdList(sortedDvds);
        });
    }

    public void getDvdByStudio() throws DVDLibraryDaoException {
        String studio = view.displayStudioPrompt();
        List<DVD> sortedDvds = LSDao.searchDvdsByStudio(studio);
        view.displaySearchResultsBanner();
        view.displayDvdList(sortedDvds);
    }

    public void getAverageDvdAge() throws DVDLibraryDaoException {
        view.displayAverageAgeBanner();
        double averageAge = LSDao.getAverageAge();
        view.displayAverageAgeMessage(averageAge);
    }

    public void getNewestDvd() throws DVDLibraryDaoException {
        view.displayNewestDvdBanner();
        List<DVD> sortedDvds = LSDao.getNewestDvd();
        view.displayDvdList(sortedDvds);
    }

    public void getOldestDvd() throws DVDLibraryDaoException {
        view.displayNewestDvdBanner();
        List<DVD> sortedDvds = LSDao.getOldestDvd();
        view.displayDvdList(sortedDvds);

    }

    public void getAverageNumNotes() throws DVDLibraryDaoException {
        view.displayAverageNumOfNotesBanner();
        double averageNotes = LSDao.getAverageWithNotes();
        view.displayAverageNotesMessage(averageNotes);
    }

}
