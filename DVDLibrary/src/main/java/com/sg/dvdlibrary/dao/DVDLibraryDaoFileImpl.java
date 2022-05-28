/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/02/21
 * purpose: M3 Assignment "DVD Library-Adding Lambdas and Streams"
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DVDLibraryDaoFileImpl implements DVDLibraryDao, DVDLibraryLambdasAndStreamsDao {

    private final String LIBRARY_FILE;
    public static final String DELIMITER = "::";

    private Map<String, DVD> dvds = new HashMap<>();

    public DVDLibraryDaoFileImpl() {
        LIBRARY_FILE = "library.txt";
    }

    public DVDLibraryDaoFileImpl(String libraryTextFile) {
        LIBRARY_FILE = libraryTextFile;
    }

    //Loading library from txt file, putting new DVD into HashMap, and then writing new object to txt file
    @Override
    public DVD addDvd(String title, DVD dvd) throws DVDLibraryDaoException {
        loadLibrary();
        DVD newDvd = dvds.put(title, dvd);
        writeLibrary();
        return dvd;
    }

    //Loading library from txt file, removing DVD from Hashmap,writing updated library to txt file
    @Override
    public DVD removeDvd(String title) throws DVDLibraryDaoException {
        loadLibrary();
        DVD removedDvd = dvds.remove(title);
        writeLibrary();
        return removedDvd;
    }

    @Override //Putting new dvd into HashMap, removing old dvd from HashMap
    public DVD editDvd(String oldTitle, DVD dvd) throws DVDLibraryDaoException {
        loadLibrary();
        dvds.remove(oldTitle);
        dvds.put(dvd.getTitle(), dvd);
        writeLibrary();
        return dvd;
    }

    //Lists all DVDs from library
    //Loading library from txt file, returning all DVDs currently stored in txt file
    @Override
    public List<DVD> getAllDVDs() throws DVDLibraryDaoException {
        loadLibrary();
        return new ArrayList(dvds.values());
    }

    //Loading library from txt file, getting DVD info with HashMap key
    @Override
    public DVD getDvd(String title) throws DVDLibraryDaoException {
        loadLibrary();
        return dvds.get(title);
    }

    //Creating new DVD object from txt file, return DVD
    private DVD unmarshallDvd(String dvdAsText) {
        String[] dvdTokens = dvdAsText.split(DELIMITER);
        String title = dvdTokens[0];
        DVD dvdFromFile = new DVD(title);
        LocalDate ld = LocalDate.parse(dvdTokens[1]);
        dvdFromFile.setReleaseDate(ld);
        dvdFromFile.setMpaaRating(dvdTokens[2]);
        dvdFromFile.setDirectorName(dvdTokens[3]);
        dvdFromFile.setStudio(dvdTokens[4]);
        dvdFromFile.setUserRating(dvdTokens[5]);
        return dvdFromFile;
    }

    //Reading txt file and loading into HashMap line by line
    //Splitting lines into chunks(properties) at the delimiter
    private void loadLibrary() throws DVDLibraryDaoException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(LIBRARY_FILE)));
        } catch (FileNotFoundException e) {
            throw new DVDLibraryDaoException("Could not load library data into memory", e);
        }
        String currentLine;
        DVD currentDvd;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentDvd = unmarshallDvd(currentLine);
            dvds.put(currentDvd.getTitle(), currentDvd);
        }
        scanner.close();
    }

    //Getting DVD object from HashMap, formatting to readable txt
    private String marshallDvd(DVD aDvd) {
        String dvdAsText = aDvd.getTitle() + DELIMITER;
        dvdAsText += aDvd.getReleaseDate().toString().formatted("MM/dd/yyyy") + DELIMITER;
        dvdAsText += aDvd.getMpaaRating() + DELIMITER;
        dvdAsText += aDvd.getDirectorName() + DELIMITER;
        dvdAsText += aDvd.getStudio() + DELIMITER;
        dvdAsText += aDvd.getUserRating();
        return dvdAsText;
    }

    //Pulling DVDs from List and writing to txt file
    private void writeLibrary() throws DVDLibraryDaoException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        } catch (IOException e) {
            throw new DVDLibraryDaoException("Could not save dvd data.", e);
        }
        String dvdAsText;
        List<DVD> dvdList = this.getAllDVDs();
        for (DVD currentDvd : dvdList) {
            dvdAsText = marshallDvd(currentDvd);
            out.println(dvdAsText);
            out.flush();
        }
        out.close();
    }

    @Override
    public List<DVD> searchDvdsReleased(int n) throws DVDLibraryDaoException {
        loadLibrary();
        LocalDate ld = LocalDate.now();
        int yearPast = ld.minusYears(n).getYear();

        List<DVD> allDvds = new ArrayList(dvds.values());
        List<DVD> dvdsInRange = allDvds.stream()
                .filter((d) -> d.getReleaseDate().getYear() >= yearPast)
                .collect(Collectors.toList());
        return dvdsInRange;

    }

    @Override
    public List<DVD> searchDvdsByMpaaRating(String mpaaRating) throws DVDLibraryDaoException {
        loadLibrary();
        List<DVD> allDvds = new ArrayList(dvds.values());
        List<DVD> sortedDvds = allDvds.stream()
                .filter((d) -> d.getMpaaRating().equalsIgnoreCase(mpaaRating))
                .collect(Collectors.toList());
        return sortedDvds;
    }

    @Override
    public Map<String, List<DVD>> searchDvdsByDirector(String director) throws DVDLibraryDaoException {
        loadLibrary();
        Map<String, List<DVD>> map = dvds.values().stream()
                .filter((d) -> d.getDirectorName().equalsIgnoreCase(director))
                .collect(Collectors.groupingBy(DVD::getMpaaRating));
        return map;
    }

    @Override
    public List<DVD> searchDvdsByStudio(String studio) throws DVDLibraryDaoException {
        loadLibrary();
        List<DVD> allDvds = new ArrayList(dvds.values());
        List<DVD> sortedDvds = allDvds.stream()
                .filter((d) -> d.getStudio().equalsIgnoreCase(studio))
                .collect(Collectors.toList());
        return sortedDvds;
    }

    @Override
    public double getAverageAge() throws DVDLibraryDaoException {
        loadLibrary();
        double averageAge = dvds.values().stream()
                .mapToDouble(DVD::getDvdAgeYears)
                .average()
                .getAsDouble();
        return averageAge;
    }

    @Override
    public List<DVD> getOldestDvd() throws DVDLibraryDaoException {
        loadLibrary();
        List<DVD> allDvds = new ArrayList(dvds.values());
        List<DVD> sortedDvds = allDvds.stream()
                .sorted(Comparator.comparingDouble(DVD::getDvdAgeDays).reversed())
                .collect(Collectors.toList());
        return sortedDvds;
    }

    @Override
    public List<DVD> getNewestDvd() throws DVDLibraryDaoException {
        loadLibrary();
        List<DVD> allDvds = new ArrayList(dvds.values());
        List<DVD> sortedDvds = allDvds.stream()
                .sorted(Comparator.comparingDouble(DVD::getDvdAgeDays))
                .collect(Collectors.toList());
        return sortedDvds;
    }

    @Override
    public double getAverageWithNotes() throws DVDLibraryDaoException {
        loadLibrary();
        double noRatingDvds = 0;
        double totalNumDvds = dvds.size();
        noRatingDvds = dvds.values()
                .stream()
                .filter(dvd -> (dvd.getUserRating().isEmpty()))
                .map(_item -> 1.0)
                .reduce(noRatingDvds, (accumulator, _item) -> accumulator + 1);
        return noRatingDvds / totalNumDvds;
    }

}
