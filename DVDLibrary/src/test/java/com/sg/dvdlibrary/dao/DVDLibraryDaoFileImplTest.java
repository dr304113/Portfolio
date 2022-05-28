/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author dr304
 */
public class DVDLibraryDaoFileImplTest {

    DVDLibraryDao testDao;

    public DVDLibraryDaoFileImplTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao
                = ctx.getBean("dvdLibraryDao", DVDLibraryDaoFileImpl.class);
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testLibrary.txt";
        //FileWriter blanks file
        new FileWriter(testFile);
        testDao = new DVDLibraryDaoFileImpl(testFile);
    }

    @Test
    public void testAddGetDVD() throws Exception {
        //test method inputs
        String title = "Shooter";
        DVD dvd = new DVD(title);
        LocalDate date = LocalDate.parse("03/23/2007", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        dvd.setReleaseDate(date);
        dvd.setMpaaRating("R");
        dvd.setDirectorName("Antoine Fuqua");
        dvd.setStudio("Paramount");
        dvd.setUserRating("7/10");

        //Adding dvd to DAO
        testDao.addDvd(title, dvd);
        //Getting dvd from DAO
        DVD retrievedDvd = testDao.getDvd(title);

        //Checking if data is equal
        assertEquals(dvd.getTitle(), retrievedDvd.getTitle(), "Checking dvd title.");
        assertEquals(dvd.getReleaseDate(), retrievedDvd.getReleaseDate(), "Checking release data.");
        assertEquals(dvd.getMpaaRating(), retrievedDvd.getMpaaRating(), "Checking MPAA Rating.");
        assertEquals(dvd.getDirectorName(), retrievedDvd.getDirectorName(), "Checking director.");
        assertEquals(dvd.getStudio(), retrievedDvd.getStudio(), "Checking studio.");
        assertEquals(dvd.getUserRating(), retrievedDvd.getUserRating(), "Checking user rating.");
    }

    @Test
    public void testAddGetAllDvds() throws Exception {
        //Creating first dvd
        DVD firstDvd = new DVD("Shooter");
        LocalDate date = LocalDate.parse("03/23/2007", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        firstDvd.setReleaseDate(date);
        firstDvd.setMpaaRating("R");
        firstDvd.setDirectorName("Antoine Fuque");
        firstDvd.setStudio("Paramount");
        firstDvd.setUserRating("7/10");

        //Creating second DVD
        DVD secondDvd = new DVD("Olympus Has Fallen");
        LocalDate date2 = LocalDate.parse("03/18/2013", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        secondDvd.setReleaseDate(date2);
        secondDvd.setMpaaRating("R");
        secondDvd.setDirectorName("Antoine Fuque");
        secondDvd.setStudio("Film District");
        secondDvd.setUserRating("6.5/10");

        //Adding both dvds to DAO
        testDao.addDvd(firstDvd.getTitle(), firstDvd);
        testDao.addDvd(secondDvd.getTitle(), secondDvd);

        //Retrieving list of dvds from DAO
        List<DVD> allDvds = testDao.getAllDVDs();

        //Check general contents
        assertNotNull(allDvds, "List of dvds must not be null.");
        assertEquals(2, allDvds.size(), "List of dvds should have 2 dvds.");

        //Then the specifics
        assertTrue(testDao.getAllDVDs().contains(firstDvd), "The list of dvds should include Shooter.");
        assertTrue(testDao.getAllDVDs().contains(secondDvd), "The list of dvds should include Olympus Has Fallen.");
    }

    @Test
    public void testRemoveDvd() throws Exception {
        //Creating two new dvda
        DVD firstDvd = new DVD("Shooter");
        LocalDate date = LocalDate.parse("03/23/2007", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        firstDvd.setReleaseDate(date);
        firstDvd.setMpaaRating("R");
        firstDvd.setDirectorName("Antoine Fuque");
        firstDvd.setStudio("Paramount");
        firstDvd.setUserRating("7/10");

        DVD secondDvd = new DVD("Olympus Has Fallen");
        LocalDate date2 = LocalDate.parse("03/18/2013", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        secondDvd.setReleaseDate(date2);
        secondDvd.setMpaaRating("R");
        secondDvd.setDirectorName("Antoine Fuque");
        secondDvd.setStudio("Film District");
        secondDvd.setUserRating("6.5/10");

        //Adding both to DAO
        testDao.addDvd(firstDvd.getTitle(), firstDvd);
        testDao.addDvd(secondDvd.getTitle(), secondDvd);

        //Removing the first dvd - Shooter
        DVD removedDvd = testDao.removeDvd(firstDvd.getTitle());

        //Checking that the correct dvd was removed
        assertEquals(removedDvd, firstDvd, "The removed dvd should be Shooter.");

        //Getting all dvds
        List<DVD> allDvds = testDao.getAllDVDs();

        //Checking general contents
        assertNotNull(allDvds, "All dvds list should not be null.");
        assertEquals(1, allDvds.size(), "All dvds should only have 1 dvd.");

        //Then specifics
        assertFalse(allDvds.contains(firstDvd), "All dvds should NOT include Shooter.");
        assertTrue(allDvds.contains(secondDvd), "All dvds should NOT include Olympus Has Fallen.");

        //Removing second dvd
        removedDvd = testDao.removeDvd(secondDvd.getTitle());

        //Checking correct dvd was removed
        assertEquals(removedDvd, secondDvd, "The removed dvd should be Olympus Has Fallen.");

        //Retrieving dvds again
        allDvds = testDao.getAllDVDs();

        //Checking contents of list - should be empty now
        assertTrue(allDvds.isEmpty(), "The retrieved list of dvds should be empty.");

        //Trying to get old dvds by title - they should be null
        DVD retrievedDvd = testDao.getDvd(firstDvd.getTitle());
        assertNull(retrievedDvd, "Shooter was removed, should be null.");
        retrievedDvd = testDao.getDvd(secondDvd.getTitle());
        assertNull(retrievedDvd, "Olympus Has Fallen was removed, should be null.");
    }

    @Test
    public void testEditDvd() throws Exception {

        //Testing title edit
        //Creating test input dvd
        DVD originalDvd = new DVD("Shooter");
        LocalDate date = LocalDate.parse("03/23/2007", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        originalDvd.setReleaseDate(date);
        originalDvd.setMpaaRating("R");
        originalDvd.setDirectorName("Antoine Fuque");
        originalDvd.setStudio("Paramount");
        originalDvd.setUserRating("7/10");

        //Creating edited Dvd with new title
        DVD editedTitleDvd = new DVD("New Shooter");
        LocalDate date2 = LocalDate.parse("03/23/2007", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        editedTitleDvd.setReleaseDate(date2);
        editedTitleDvd.setMpaaRating("R");
        editedTitleDvd.setDirectorName("Antoine Fuque");
        editedTitleDvd.setStudio("Paramount");
        editedTitleDvd.setUserRating("7/10");

        //Adding originalDvd to DAO
        testDao.addDvd(originalDvd.getTitle(), originalDvd);

        //Editing dvd
        DVD editedDvd = testDao.editDvd(originalDvd.getTitle(), editedTitleDvd);

        //Getting dvd from DAO
        DVD retrievedDvd = testDao.getDvd(editedDvd.getTitle());

        //Getting list
        List<DVD> allDvds = testDao.getAllDVDs();

        //Checking if original title is still in list
        assertFalse(allDvds.contains(originalDvd), "All dvds should not include Shooter");
        assertTrue(allDvds.contains(retrievedDvd), "All dvds should include New Shooter");

    }
}
