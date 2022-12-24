import javax.swing.*;
import java.awt.event.ActionEvent;

public class SnakeGame extends JFrame {
    private GameBoard board;
    public SnakeGame(){
        board = new GameBoard();
        add(board);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main (String[] args){
        JFrame snakeGame = new SnakeGame();
    }
}
