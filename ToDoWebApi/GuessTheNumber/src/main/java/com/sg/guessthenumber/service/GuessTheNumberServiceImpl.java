package com.sg.guessthenumber.service;

import com.sg.guessthenumber.dao.GameDao;
import com.sg.guessthenumber.dao.RoundDao;
import com.sg.guessthenumber.dto.Game;
import com.sg.guessthenumber.dto.Round;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dr304
 */
@Repository
public class GuessTheNumberServiceImpl implements GuessTheNumberService {

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    @Override
    public Game startAndGenerateAnswer() {
        List<Integer> answerList = new ArrayList<>();

        //Generating four random digits for answer
        while (answerList.size() < 4) {
            int num = (int) (Math.random() * 9 + 1);

            //Not allowing duplicate digits in answer
            if (!answerList.contains(num)) {
                answerList.add(num);
            }
        }
        //Converting List to one String
        String answer = answerList.get(0).toString()
                + answerList.get(1).toString()
                + answerList.get(2).toString()
                + answerList.get(3).toString();

        //Creating new Game with answer
        Game currentGame = new Game();
        currentGame.setAnswer(answer);
        currentGame.setFinished(false);

        return gameDao.addGame(currentGame);
    }

    @Override
    public Round guessProcess(int gameId, String guess) throws DataValidationException {
        Game game = gameDao.getGameById(gameId);
        String answer = game.getAnswer();
        Round round = new Round();
        int eCounter = 0;
        int pCounter = 0;

        //Just some validation here
        if (game == null) {
            throw new DataValidationException("No game found");
        }
        if (game.isFinished()) {
            throw new DataValidationException("A solution has already been found for this game");
        }
        try {
            Integer.parseInt(String.valueOf(guess));
        } catch (NumberFormatException e) {
            throw new DataValidationException("Your guess must be numeric");
        }

        //Convert to CharArray to iterate through answer/guess digits
        if (guess.length() == 4 && !guess.isBlank()) {
            char[] answerChar = Arrays.stream(answer.split("(?!^)"))
                    .collect(Collectors.joining())
                    .toCharArray();
            char[] guessChar = Arrays.stream(guess.split("(?!^)"))
                    .collect(Collectors.joining())
                    .toCharArray();

            //Checking each digit one by one - by index
            //Nested for loops could be refactored to increase performance. Shouldn't be an issue with this small program.
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (guessChar[i] == answerChar[j]) {
                        if (i == j) {
                            eCounter++;
                        } else {
                            pCounter++;
                        }
                    }
                }
            }

            //Creating/Adding round to DB
            round.setGuessTime(LocalDateTime.now());
            round.setGuess(guess);
            round.setExactMatches(eCounter);
            round.setPartialMatches(pCounter);
            round.setGameId(game.getGameId());
            roundDao.addRound(round);

            if (eCounter == 4) {
                game.setFinished(true);
                gameDao.updateGame(game);
            }
        } else {
            throw new DataValidationException("Your guess must include 4 digits");
        }
        return round;
    }

    @Override
    public List<Game> displayGames() throws EmptyDataException {
        List<Game> allGames = gameDao.getAllGames();

        if (allGames.isEmpty()) {
            throw new EmptyDataException("No Games Found");
        }
        //Hiding answer until solution is found
        for (Game game : allGames) {
            if (!game.isFinished()) {
                game.setAnswer("In Progress...(Solution Hidden)");
            }
        }
        return allGames;
    }

    @Override
    public List<Round> displayRoundsByGame(int gameId) throws EmptyDataException {
        List<Round> allRounds = roundDao.getAllRounds();
        List<Round> gameRounds = new ArrayList<>();

        if (allRounds.isEmpty()) {
            throw new EmptyDataException("No Rounds Found");
        }
        //Adding rounds to new List for specific gameId
        for (Round round : allRounds) {
            if (round.getGameId() == gameId) {
                gameRounds.add(round);
            }
        }
        return gameRounds;
    }

    @Override
    public Game displayGameById(int gameId) throws EmptyDataException {
        Game game;
        try {
            game = gameDao.getGameById(gameId);

            //Hiding answer until solution is found
            if (!game.isFinished()) {
                game.setAnswer("In Progress...(Solution Hidden)");
            }
        } catch (NullPointerException e) {
            throw new EmptyDataException("No Game Found");
        }
        return game;
    }

    @Override
    public Boolean deleteGameById(int gameId) {
        return gameDao.deleteGameById(gameId);
    }

}
