document.addEventListener('DOMContentLoaded', () => {
    const GRID_SIZE = 6;
    const API_BASE_URL = 'http://localhost:8080/api/game'; // Update with your API URL
    let gameId = null;
    let score = 0;
    
    const gridElement = document.getElementById('grid');
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
            renderBoard(data.board);
            score = data.score;
            updateScore();
        } catch (error) {
            console.error('Error initializing game:', error);
            alert('Failed to initialize game. Please try again.');
        }
    }
    
    async function makeMove(direction) {
        try {
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
                renderBoard(data.board);
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
            renderBoard(data.board);
            score = data.score;
            updateScore();
            hideGameMessage();
        } catch (error) {
            console.error('Error restarting game:', error);
            alert('Failed to restart game. Please try again.');
        }
    }
    
    function renderBoard(board) {
        // Clear the grid
        gridElement.innerHTML = '';
        
        // Create rows and cells
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
                }
                
                rowElement.appendChild(cellElement);
            }
            
            gridElement.appendChild(rowElement);
        }
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
                return; // Ignore other keys
        }
        
        makeMove(direction);
    }
    
    // Touch support for mobile devices
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
        
        // Determine the direction based on the greatest change
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
});