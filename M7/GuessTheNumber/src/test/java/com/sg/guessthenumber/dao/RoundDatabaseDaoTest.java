package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.TestApplicationConfiguration;
import com.sg.guessthenumber.dto.Game;
import com.sg.guessthenumber.dto.Round;
import java.time.LocalDateTime;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;

/**
 *
 * @author dr304
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundDatabaseDaoTest {

    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;

    public RoundDatabaseDaoTest() {
    }

    @Before
    public void setUp() throws Exception {
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            gameDao.deleteGameById(game.getGameId());
        }
    }

    @Test
    public void testAddGetRound() throws Exception {
        Game game = new Game();
        game.setAnswer("1234");
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGameId(game.getGameId());
        round.setGuess("1235");
        round.setExactMatches(3);
        round.setPartialMatches(0);
        round.setGuessTime(LocalDateTime.of(2022, 07, 01, 23, 10, 03));
        round = roundDao.addRound(round);

        Round fromDao = roundDao.getRoundById(round.getRoundId());

        assertEquals(round, fromDao);
    }

    @Test
    public void testGetAllRounds() throws Exception {
        Game game = new Game();
        game.setAnswer("1234");
        gameDao.addGame(game);

        Round round1 = new Round();
        round1.setGameId(game.getGameId());
        round1.setGuess("1200");
        round1.setExactMatches(2);
        round1.setPartialMatches(0);
        round1.setGuessTime(LocalDateTime.of(2022, 07, 01, 23, 10, 03));
        roundDao.addRound(round1);

        Round round2 = new Round();
        round2.setGameId(game.getGameId());
        round2.setGuess("1236");
        round2.setExactMatches(3);
        round2.setPartialMatches(0);
        round2.setGuessTime(LocalDateTime.of(2022, 07, 01, 23, 10, 03));
        roundDao.addRound(round2);

        List<Round> rounds = roundDao.getAllRounds();

        assertEquals(2, rounds.size());
        assertTrue(rounds.contains(round1));
        assertTrue(rounds.contains(round2));
    }

    @Test
    public void testUpdateRound() throws Exception {
        Game game = new Game();
        game.setAnswer("1234");
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGameId(game.getGameId());
        round.setGuess("1230");
        round.setExactMatches(3);
        round.setPartialMatches(0);
        round.setGuessTime(LocalDateTime.of(2022, 07, 01, 23, 10, 03));
        roundDao.addRound(round);

        Round fromDao = roundDao.getRoundById(round.getRoundId());

        assertEquals(round, fromDao);

        round.setGuess("1231");
        round.setPartialMatches(1);

        roundDao.updateRound(round);

        assertNotEquals(round, fromDao);

        fromDao = roundDao.getRoundById(round.getRoundId());

        assertEquals(round, fromDao);
    }

    @Test
    public void testDeleteRoundById() throws Exception {
        Game game = new Game();
        game.setAnswer("1234");
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("1230");
        round.setPartialMatches(0);
        round.setGuessTime(LocalDateTime.of(2022, 07, 01, 23, 10, 03));
        round.setExactMatches(3);
        round.setGameId(game.getGameId());
        round = roundDao.addRound(round);

        roundDao.deleteRoundById(round.getRoundId());

        Game fromGameDao = gameDao.getGameById(game.getGameId());
        Round fromRoundDao = roundDao.getRoundById(round.getRoundId());

        assertNull(fromRoundDao);
        assertNotNull(fromGameDao);
        assertEquals(fromGameDao, game);

    }

}
