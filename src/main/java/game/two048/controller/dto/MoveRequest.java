package game.two048.controller.dto;

import lombok.Data;

@Data
public class MoveRequest {

    private String gameId;


    private String direction; // "LEFT", "RIGHT", "UP", "DOWN"
}