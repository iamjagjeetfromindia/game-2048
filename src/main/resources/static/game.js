const GRID_SIZE = 4;
const API_BASE_URL = 'http://localhost:8080/api/game';
const CHANGE_COLORS = {
    NEW: '#ffecb3',       // Light yellow for new tiles
    MERGED: '#81c784',    // Light green for merged tiles
    MOVED: '#64b5f6',     // Light blue for moved tiles
    RESET_DELAY: 500      // Highlight duration in ms
};

let gameId = null;
let score = 0;
let previousBoard = null;

// DOM elements
const previousGridElement = document.getElementById('previous-grid');
const currentGridElement = document.getElementById('current-grid');
const scoreElement = document.getElementById('score');
const gameMessageElement = document.getElementById('game-message');
const messageTextElement = document.getElementById('message-text');
const restartButton = document.getElementById('restart-button');

// Initialize game
initGame();

// Event listeners
document.addEventListener('keydown', handleKeyPress);
restartButton.addEventListener('click', restartGame);

async function initGame() {
    try {
        const response = await fetch(`${API_BASE_URL}/start`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ size: GRID_SIZE })
        });

        const data = await response.json();
        gameId = data.gameId;
        previousBoard = data.board;
        renderBoard(previousGridElement, previousBoard);
        renderBoard(currentGridElement, data.board);
        score = data.score;
        updateScore();
    } catch (error) {
        console.error('Error initializing game:', error);
        alert('Failed to initialize game. Please try again.');
    }
}

async function makeMove(direction) {
    try {
        // Save current board as previous board
        previousBoard = JSON.parse(JSON.stringify(getCurrentBoard()));
        renderBoard(previousGridElement, previousBoard);

        const response = await fetch(`${API_BASE_URL}/move`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                gameId,
                direction
            })
        });

        const data = await response.json();

        if (!data.message) {
            // Pass previousBoard to highlight changes
            renderBoard(currentGridElement, data.board, previousBoard);
            score = data.score;
            updateScore();

            if (data.gameState === 'WIN') {
                showGameMessage('You Win!');
            } else if (data.gameState === 'GAME_OVER') {
                showGameMessage('Game Over!');
            }
        }
    } catch (error) {
        console.error('Error making move:', error);
    }
}

function getCurrentBoard() {
    const rows = currentGridElement.querySelectorAll('.grid-row');
    const board = [];

    rows.forEach(row => {
        const cells = row.querySelectorAll('.grid-cell');
        const rowValues = [];

        cells.forEach(cell => {
            const value = cell.textContent || 0;
            rowValues.push(value === '0' ? null : parseInt(value));
        });

        board.push(rowValues);
    });

    return board;
}

async function restartGame() {
    try {
        const response = await fetch(`${API_BASE_URL}/restart`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ gameId })
        });

        const data = await response.json();
        gameId = data.gameId;
        previousBoard = data.board;
        renderBoard(previousGridElement, previousBoard);
        renderBoard(currentGridElement, data.board);
        score = data.score;
        updateScore();
        hideGameMessage();
    } catch (error) {
        console.error('Error restarting game:', error);
        alert('Failed to restart game. Please try again.');
    }
}

function renderBoard(gridElement, board, prevBoard = null) {
    gridElement.innerHTML = '';

    for (let row = 0; row < GRID_SIZE; row++) {
        const rowElement = document.createElement('div');
        rowElement.className = 'grid-row';

        for (let col = 0; col < GRID_SIZE; col++) {
            const cellElement = document.createElement('div');
            cellElement.className = 'grid-cell';

            const value = board[row][col];
            if (value !== null && value !== 0) {
                cellElement.textContent = value;
                cellElement.classList.add(`tile-${value}`);

                // Highlight changes if previous board is provided
                if (prevBoard) {
                    highlightChanges(cellElement, row, col, value, prevBoard);
                }
            }

            rowElement.appendChild(cellElement);
        }

        gridElement.appendChild(rowElement);
    }
}

function highlightChanges(cell, row, col, value, prevBoard) {
    const prevValue = prevBoard[row][col];

    // New tile (was empty before)
    if (prevValue === null || prevValue === 0) {
        cell.style.backgroundColor = CHANGE_COLORS.NEW;
        cell.classList.add('tile-highlight-new');
    }
    // Merged tile (value doubled from previous)
    else if (value === prevValue * 2) {
        cell.style.backgroundColor = CHANGE_COLORS.MERGED;
        cell.classList.add('tile-highlight-merged');
    }
    // Moved tile (value existed elsewhere before)
    else if (value === prevValue) {
        // Check if this tile was empty before
        let existedElsewhere = false;
        for (let r = 0; r < GRID_SIZE; r++) {
            for (let c = 0; c < GRID_SIZE; c++) {
                if (prevBoard[r][c] === value && (r !== row || c !== col)) {
                    existedElsewhere = true;
                    break;
                }
            }
            if (existedElsewhere) break;
        }

        if (existedElsewhere) {
            cell.style.backgroundColor = CHANGE_COLORS.MOVED;
            cell.classList.add('tile-highlight-moved');
        }
    }

    // Remove highlight after delay
    setTimeout(() => {
        cell.style.backgroundColor = '';
        cell.classList.remove(
            'tile-highlight-new',
            'tile-highlight-merged',
            'tile-highlight-moved'
        );
    }, CHANGE_COLORS.RESET_DELAY);
}

function updateScore() {
    scoreElement.textContent = score;
}

function showGameMessage(message) {
    messageTextElement.textContent = message;
    gameMessageElement.classList.add('game-over');
}

function hideGameMessage() {
    gameMessageElement.classList.remove('game-over');
}

function handleKeyPress(event) {
    if (gameMessageElement.classList.contains('game-over')) {
        return;
    }

    let direction;
    switch (event.key) {
        case 'ArrowLeft':
            direction = 'LEFT';
            break;
        case 'ArrowRight':
            direction = 'RIGHT';
            break;
        case 'ArrowUp':
            direction = 'UP';
            break;
        case 'ArrowDown':
            direction = 'DOWN';
            break;
        default:
            return;
    }

    makeMove(direction);
}

// Touch support
let touchStartX = 0;
let touchStartY = 0;

document.addEventListener('touchstart', (e) => {
    touchStartX = e.touches[0].clientX;
    touchStartY = e.touches[0].clientY;
}, false);

document.addEventListener('touchend', (e) => {
    if (gameMessageElement.classList.contains('game-over')) {
        return;
    }

    const touchEndX = e.changedTouches[0].clientX;
    const touchEndY = e.changedTouches[0].clientY;

    const dx = touchEndX - touchStartX;
    const dy = touchEndY - touchStartY;

    if (Math.abs(dx) > Math.abs(dy)) {
        if (dx > 0) {
            makeMove('RIGHT');
        } else {
            makeMove('LEFT');
        }
    } else {
        if (dy > 0) {
            makeMove('DOWN');
        } else {
            makeMove('UP');
        }
    }
}, false);