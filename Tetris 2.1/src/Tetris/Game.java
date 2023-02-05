package Tetris;

import javax.swing.JFrame;
/**
 *
 * @author Ahmad R
 */
public class Game {
    public static final int WIDTH = 465, HEIGHT = 640;
    
    private Board board;
    private static JFrame frame;
    public Game()
    {
        frame =new JFrame("Tetris Classic");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        board=new Board();
        frame.add(board);
        frame.addKeyListener(board);
        frame.setVisible(true);
    }
    static void Destroy()
    {
        frame.dispose();
    }
    
//    public static void main(String[] args) {
//    new Game();    
//    }

}
