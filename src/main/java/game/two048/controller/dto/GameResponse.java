package game.two048.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameResponse {
    private String gameId;
    private Integer[][] board;
    private int score;
    private String gameState;
    private String message; // For error messages or other information
}