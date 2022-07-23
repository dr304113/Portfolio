package com.sg.guessthenumber.service;

import com.sg.guessthenumber.dto.Game;
import com.sg.guessthenumber.dto.Round;
import java.util.List;

/**
 *
 * @author dr304
 */
public interface GuessTheNumberService {

    public Game startAndGenerateAnswer();

    public List<Game> displayGames() throws EmptyDataException;

    public List<Round> displayRoundsByGame(int gameId) throws EmptyDataException;

    public Round guessProcess(int gameId, String guess) throws DataValidationException;

    public Game displayGameById(int gameId) throws EmptyDataException;

    public Boolean deleteGameById(int gameId) throws EmptyDataException;

}
