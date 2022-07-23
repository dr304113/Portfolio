package com.sg.guessthenumber.Controller;

import com.sg.guessthenumber.dto.Game;
import com.sg.guessthenumber.dto.Round;
import com.sg.guessthenumber.service.DataValidationException;
import com.sg.guessthenumber.service.EmptyDataException;
import com.sg.guessthenumber.service.GuessTheNumberService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dr304
 */
@Component
@RestController
@RequestMapping("/api/guessthenumber")
public class GuessTheNumberController {

    @Autowired
    public GuessTheNumberService service;

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public String startGame() {
        Game game = service.startAndGenerateAnswer();
        return "GAME ID: " + game.getGameId() + " STARTED";
    }

    @PostMapping
    @RequestMapping("/guess")
    public Round guess(@RequestBody Round round) throws DataValidationException {
        return service.guessProcess(round.getGameId(), round.getGuess());
    }

    @GetMapping("/game")
    public List<Game> showAllGames() throws EmptyDataException {
        return service.displayGames();
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> findGameById(@PathVariable int gameId) throws EmptyDataException {
        Game game = service.displayGameById(gameId);
        if (game == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game);
    }

    @GetMapping("/round/{gameId}")
    public ResponseEntity<List<Round>> showAllRounds(@PathVariable int gameId) throws EmptyDataException {
        List<Round> rounds = service.displayRoundsByGame(gameId);
        if (rounds.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(rounds);
    }

    @DeleteMapping("/delete/{gameId}")
    public ResponseEntity deleteGame(@PathVariable int gameId) throws EmptyDataException {
        if (service.deleteGameById(gameId)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
