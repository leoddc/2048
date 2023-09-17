import java.util.Random;

public class Game {
    int size;
    int[] gameBoard;
    int baseSize;
    Random rand = new Random();
    private final int CHANCE_OF_FOUR = 35; //in %
    private final int NUMBER_OF_SPAWN_TILES = 2;
    Game(int gameSize) {
        baseSize = gameSize;
        size = baseSize * baseSize;
        gameBoard = new int[size];
    }

    public void printBoard() {
        for (int i = 0; size > i; i += baseSize) {
            StringBuilder forPrint = new StringBuilder();
            for (int j = 0; baseSize > j; j++) {
                forPrint.append(gameBoard[j + i]);
                forPrint.append("--");
            }
            System.out.println(forPrint);
        }
    }

    private int generateRandomPosition() {
        boolean found = false;
        int position = 0;

        while (!found) {
            position = rand.nextInt(size);
            if (!(gameBoard[position] > 0)) {
                found = true;
            }
        }
        return position;
    }
    public void generateTile(int value) {
        int spot = generateRandomPosition();
        if (value == 1) {
            int v = 0;
            int r = rand.nextInt(100);
            if (r > (100 - CHANCE_OF_FOUR)) {
                v = 4;
            } else {
                v = 2;
            }
            gameBoard[spot] = v;
        }
        else {
            gameBoard[spot] = value;
        }
    }

    void flipBoard(int flipCount) {
        int i = 0;
        while (flipCount > i) {
            int[] boardBuffer = new int[gameBoard.length];
            for (int j = 0; gameBoard.length > j; j++) {
                int newPosition = (baseSize - 1) + (baseSize * j);
                if (newPosition > size) {
                    int newNewPosition = (newPosition % size) - (j / baseSize);
                    boardBuffer[newNewPosition] = gameBoard[j];
                } else {
                    boardBuffer[newPosition] = gameBoard[j];
                }
            }
            gameBoard = boardBuffer;
            i++;
        }
    }

    public void move() {
        for (int i = 0; gameBoard.length > i; i++) {
            for (int j = 1; size > (i + (j * baseSize)); j++) {
                int aboveIndex = i + baseSize * j;
                if (gameBoard[i] == 0) {
                    // if tile is 0 it is empty
                    break;
                }
                else if (gameBoard[i] == gameBoard[aboveIndex]) {
                    // if a tile above the current tile is of the same value, double
                    gameBoard[i] *= 2;
                    gameBoard[aboveIndex] = 0;
                    break;
                } else if (gameBoard[aboveIndex] == 0) {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    public void sweep() {
        for (int i = 0; gameBoard.length > i; i++) {
            if (gameBoard[i] == 0) {
                for (int j = 1; size > (i + (j * baseSize)); j++) {
                    int aboveIndex = i + baseSize * j;
                    if (gameBoard[aboveIndex] > 0) {
                        gameBoard[i] = gameBoard[aboveIndex];
                        gameBoard[aboveIndex] = 0;
                        break;
                    }
                }
            }
        }
    }

    public int evaluate() {
        int gameStatus = 1;
        for (int i = 0; gameBoard.length > i; i++) {
            if (gameBoard[i] == 2048) {
                return 0;
            }
            else if (gameBoard[i] == 0) {
                gameStatus = 2;
            }
        }
        return gameStatus;
    }

    private void turn() {
        move();
        sweep();
        generateTile(1);
    }

    public void moveUp() {
        turn();
    }

    public void moveDown() {
        flipBoard(2);
        turn();
        flipBoard(2);
    }

    public void moveLeft() {
        flipBoard(1);
        turn();
        flipBoard(3);
    }

    public void moveRight() {
        flipBoard(3);
        turn();
        flipBoard(1);
    }

    public void startGame(int[] test) {
        if (test.length > 0) {
            gameBoard = test;
        } else {
            for (int i = 0; NUMBER_OF_SPAWN_TILES > i; i++) {
                generateTile(1);
            }
        }
    }
}
