/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/02/21
 * purpose: M3 Assignment "DVD Library-Adding Lambdas and Streams"
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.util.List;

public interface DVDLibraryDao {

    DVD addDvd(String title, DVD dvd) throws DVDLibraryDaoException;  //Adds DVD to library, passing title in as parameter

    DVD removeDvd(String title) throws DVDLibraryDaoException;   //Removes DVD from library, again passing title in as parameter

    List<DVD> getAllDVDs() throws DVDLibraryDaoException;   //Lists all DVDs from library

    DVD getDvd(String title) throws DVDLibraryDaoException;  //Searches DVD by title and displays info
    
    DVD editDvd(String oldTitle, DVD dvd) throws DVDLibraryDaoException; //Edits DVD
}
