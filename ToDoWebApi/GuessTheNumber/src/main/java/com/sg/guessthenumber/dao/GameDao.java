package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.dto.Game;
import java.util.List;

/**
 *
 * @author dr304
 */
public interface GameDao {

    Game addGame(Game game);

    List<Game> getAllGames();

    Game getGameById(int id);

    boolean updateGame(Game game);

    boolean deleteGameById(int id);
}
