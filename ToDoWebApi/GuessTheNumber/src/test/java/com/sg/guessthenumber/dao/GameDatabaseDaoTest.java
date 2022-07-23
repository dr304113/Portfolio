package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.TestApplicationConfiguration;
import com.sg.guessthenumber.dao.GameDao;
import com.sg.guessthenumber.dao.RoundDao;
import com.sg.guessthenumber.dto.Game;
import com.sg.guessthenumber.dto.Round;
import java.time.LocalDateTime;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author dr304
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDatabaseDaoTest {

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    public GameDatabaseDaoTest() {
    }

    @Before
    public void setUp() throws Exception {
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            gameDao.deleteGameById(game.getGameId());
        }
        List<Round> rounds = roundDao.getAllRounds();
        for (Round round : rounds) {
            roundDao.deleteRoundById(round.getRoundId());
        }
    }

    @Test
    public void testAddGetGame() throws Exception {
        Game game = new Game();
        game.setAnswer("1234");
        game = gameDao.addGame(game);

        Game fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);
    }

    @Test
    public void testGetAllGames() throws Exception {
        Game game = new Game();
        game.setAnswer("0001");
        gameDao.addGame(game);

        Game game2 = new Game();
        game2.setAnswer("0002");
        gameDao.addGame(game2);

        List<Game> games = gameDao.getAllGames();

        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
    }

    @Test
    public void testUpdateGame() throws Exception {
        Game game = new Game();
        game.setAnswer("0001");
        game = gameDao.addGame(game);

        Game fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);

        game.setAnswer("0002");
        game.setFinished(true);

        gameDao.updateGame(game);

        assertNotEquals(game, fromDao);
        fromDao = gameDao.getGameById(game.getGameId());
        assertEquals(game, fromDao);
    }

    @Test
    public void testDeleteGameById() throws Exception {
        Game game = new Game();
        game.setAnswer("0001");
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("0000");
        round.setPartialMatches(1);
        round.setGuessTime(LocalDateTime.now());
        round.setExactMatches(3);
        round.setGameId(game.getGameId());
        round = roundDao.addRound(round);

        gameDao.deleteGameById(game.getGameId());

        Game fromGameDao = gameDao.getGameById(game.getGameId());
        Round fromRoundDao = roundDao.getRoundById(round.getRoundId());

        assertNull(fromGameDao);
        assertNull(fromRoundDao);

    }
}
