package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.dto.Round;
import java.util.List;

/**
 *
 * @author dr304
 */
public interface RoundDao {

    Round addRound(Round round);

    List<Round> getAllRounds();

    public Round getRoundById(int roundId);

    boolean updateRound(Round round);

    boolean deleteRoundById(int id);

}
