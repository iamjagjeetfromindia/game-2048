package game.two048.controller;

import game.two048.model.Board;
import game.two048.model.Cell;
import game.two048.manager.GameManager;
import game.two048.controller.dto.GameResponse;
import game.two048.controller.dto.MoveRequest;
import game.two048.controller.dto.RestartRequest;
import game.two048.controller.dto.StartGameRequest;
import game.two048.model.GameState;
import game.two048.model.MoveDirection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static game.two048.util.Constants.DEFAULT_GAME_SIZE;

//Controller added to test the game functionality via UI
//Not part of code review but added for completeness
@RestController
@RequestMapping("/api/game")
public class GameController {
    private static final String GAME_NOT_FOUND = "Game not found";
    private static final String INVALID_MOVE_DIRECTION = "Invalid Move Direction";
    private final Map<String, GameManager> activeGames = new ConcurrentHashMap<>();

    @PostMapping("/start")
    public ResponseEntity<GameResponse> startGame(@RequestBody final StartGameRequest request) {
        int size = Objects.requireNonNullElse(request.getSize(), DEFAULT_GAME_SIZE);
        String gameId = UUID.randomUUID().toString();
        GameManager gameManager = new GameManager(size, size);
        activeGames.put(gameId, gameManager);
        Board board = gameManager.getBoard();
        return ResponseEntity.ok(mapToResponse(gameId, board, GameState.IN_PROGRESS.toString()));
    }

    @PostMapping("/move")
    public ResponseEntity<GameResponse> makeMove(@RequestBody final MoveRequest request) {
        GameManager gameManager = activeGames.get(request.getGameId());
        if (gameManager == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new GameResponse(null, null, 0, "NOT_FOUND", GAME_NOT_FOUND));
        }
        try {
            MoveDirection direction = MoveDirection.valueOf(request.getDirection().toUpperCase());
            gameManager.move(direction);
            Board board = gameManager.getBoard();
            String gameState = gameManager.getGameState().toString();
            return ResponseEntity.ok(mapToResponse(request.getGameId(), board, gameState));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new GameResponse(null, null, 0, GameState.IN_PROGRESS.toString(), INVALID_MOVE_DIRECTION));
        }
    }

    @PostMapping("/restart")
    public ResponseEntity<GameResponse> restartGame(@RequestBody final RestartRequest request) {
        GameManager gameManager = activeGames.get(request.getGameId());
        if (gameManager == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new GameResponse(null, null, 0, "NOT_FOUND", GAME_NOT_FOUND));
        }
        int size = gameManager.getBoard().getRows();
        GameManager newGameManager = new GameManager(size, size);
        activeGames.put(request.getGameId(), newGameManager);
        Board board = newGameManager.getBoard();
        return ResponseEntity.ok(mapToResponse(request.getGameId(), board, GameState.IN_PROGRESS.toString()));
    }

    private GameResponse mapToResponse(final String gameId, final Board board, final String gameState) {
        Integer[][] boardArray = new Integer[board.getRows()][board.getCols()];
        for (int i = 0; i < board.getRows(); i++) {
            Cell[] row = board.getRow(i);
            for (int j = 0; j < row.length; j++) {
                boardArray[i][j] = row[j].getValue();
            }
        }
        return new GameResponse(gameId, boardArray, 0, gameState, null);
    }
}