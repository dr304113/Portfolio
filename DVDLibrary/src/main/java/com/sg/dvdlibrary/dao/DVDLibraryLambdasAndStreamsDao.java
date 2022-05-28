/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/02/21
 * purpose: M3 Assignment "DVD Library-Adding Lambdas and Streams"
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.util.List;
import java.util.Map;

public interface DVDLibraryLambdasAndStreamsDao extends DVDLibraryDao {

    public List<DVD> searchDvdsReleased(int n) throws DVDLibraryDaoException;   //Serach DVDs released in the last n years

    public List<DVD> searchDvdsByMpaaRating(String mpaaRating) throws DVDLibraryDaoException;   //Search DVDs by MPAA Rating

    public Map<String, List<DVD>> searchDvdsByDirector(String director) throws DVDLibraryDaoException;  //Search DVDs by director. Should be sorted into separate structures by MPAA Rating

    public List<DVD> searchDvdsByStudio(String studio) throws DVDLibraryDaoException;  //Search DVDs by studio

    public double getAverageAge() throws DVDLibraryDaoException;  //Find average age of all movies

    public List<DVD> getOldestDvd() throws DVDLibraryDaoException;  //Find oldest DVD

    public List<DVD> getNewestDvd() throws DVDLibraryDaoException;  //Find newest DVD

    public double getAverageWithNotes() throws DVDLibraryDaoException;  //Find average number of DVDs with notes

}
