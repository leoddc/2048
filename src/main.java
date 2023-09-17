import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Game game = new Game(4);
        int[] startingBoard = {};
        game.startGame(startingBoard);

        Scanner s = new Scanner(System.in);

        game.printBoard();

        boolean gameLoop = true;

        while (gameLoop) {
            System.out.println("Move with 'up', 'down', 'left', 'right'");
            String input = s.next();
            switch (input) {
                case "up" -> game.moveUp();
                case "down" -> game.moveDown();
                case "left" -> game.moveLeft();
                case "right" -> game.moveRight();
            }
            switch (game.evaluate()) {
                case 0:
                    gameLoop = false;
                    System.out.println("You win!");
                case 1:
                    gameLoop = false;
                    System.out.println("You loose. :(");
            }
            game.printBoard();
        }
    }
}
