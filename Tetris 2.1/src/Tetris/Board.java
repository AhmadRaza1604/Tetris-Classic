/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 *
 * @author Ahmad R
 */
public class Board extends JPanel implements KeyListener, MouseListener, MouseMotionListener{
    public static int STATE_GAME_PLAY=0;
    public static int STATE_GAME_PAUSE=1;
    public static int STATE_GAME_OVER=2;
    private int state= STATE_GAME_PLAY;
    private static int FPS=60;
    private static int delay=FPS/1000;
    private BufferedImage Logo;
    private Rectangle stopBounds, restartBounds;
    private int mouseX, mouseY;
//   String e="";
    public String diff[]={"Easiest","Easy","Medium","Hard","Hardest"};
//    Menu m=new Menu(e);

    public static final int BOARD_WIDTH=10, BOARD_HEIGHT=20, BLOCK_SIZE=30;
    private Timer looper;
    private Color[][] board =new Color[BOARD_HEIGHT][BOARD_WIDTH];
    private Random random;
    //private int[][] shape1= {{1, 1, 1},{0, 1, 0}};
    
    private Color[] colors={Color.decode("#ed1c24"),Color.decode("#ff7f27"),Color.decode("#fff200"),Color.decode("#22b14c"),Color.decode("#00a2e8"),Color.decode("#a349a4"),Color.decode("#3f48cc")};
    private Shape[] shapes=new Shape[7];
    private Shape currentShape, nextShape;
    public int Score=0;
    int d=Menu.getDifficulty();
    private String Level=diff[d];
    public int highScore;
    public Board() {
        Logo=ImageLoader.loadImage("/Riphah-Logos.jpg");
        random=new Random();
        shapes[0]=new Shape(new int[][]{{1,1,1,1}}, this, colors[0]);// I shape
        shapes[1]=new Shape(new int[][]{{1,1,1},{0,1,0}}, this, colors[1]);// T shape
        shapes[2]=new Shape(new int[][]{{1,1,1},{1,0,0}}, this, colors[2]);// L shape
        shapes[3]=new Shape(new int[][]{{1,1,1},{0,0,1}}, this, colors[3]);// J shape
        shapes[4]=new Shape(new int[][]{{0,1,1},{1,1,0}}, this, colors[4]);// s shape
        shapes[5]=new Shape(new int[][]{{1,1,0},{0,1,1}}, this, colors[5]);// z shape
        shapes[6]=new Shape(new int[][]{{1,1},{1,1}}, this, colors[6]);// o shape
        
        nextShape=shapes[random.nextInt(shapes.length)];
        currentShape=shapes[random.nextInt(shapes.length)];
        
    looper =new Timer(delay, new ActionListener() {
        int n=0;
        @Override   
        public void actionPerformed(ActionEvent e) {
            update();
            repaint();
        }   
    });
    looper.start();                
    }
    
    private void update()
    {
        if(state==STATE_GAME_PLAY)
        {
            
        currentShape.update();
        }
    }
    public void setNextShape()
    {
        
        int shape=random.nextInt(shapes.length);
        int color=random.nextInt(colors.length);
        nextShape=new Shape(shapes[shape].getCoords(), this, colors[color]);
    }
    public void setCurrentShape()
    {
              
        currentShape=nextShape;
        setNextShape();
        for (int row = 0; row < currentShape.getCoords().length; row++) {
            for (int col = 0; col < currentShape.getCoords()[0].length; col++) {
                if (currentShape.getCoords()[row][col] != 0) {
                    if (board[currentShape.getY() + row][currentShape.getX() + col] != null) {
                        state=STATE_GAME_OVER;
                    }
                }
            }
        }
    }    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        currentShape.render(g);
        for(int row=0; row<board.length; row++)
        {
            for(int col=0; col<board[row].length; col++)
            {
                if(board[row][col] !=null)
                {
                    g.setColor(board[row][col]);
                    g.fillRect(col*BLOCK_SIZE, row*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
        
        // Drawing Board
        g.setColor(Color.GREEN);
        for(int row=0; row<=BOARD_HEIGHT; row++)
        {
            g.drawLine(0, BLOCK_SIZE*row, BLOCK_SIZE*BOARD_WIDTH, BLOCK_SIZE*row);
        }
        for(int col=0; col<=BOARD_WIDTH; col++)
        {
            g.drawLine(BLOCK_SIZE*col, 0, col*BLOCK_SIZE, BLOCK_SIZE*BOARD_HEIGHT);
        }
        

        
        //showing next shape
        g.setColor(Color.WHITE);
        g.setFont(new Font("Georgia", Font.BOLD, 20));
        g.drawString("Next Shape:", Game.WIDTH-150,40 );
                g.setColor(nextShape.getColor());
        for (int row = 0; row < nextShape.getCoords().length; row++) {
            for (int col = 0; col < nextShape.getCoords()[0].length; col++) {
                if (nextShape.getCoords()[row][col] != 0) {
                  g.fillRect(col * 30 + 320, row * 30 + 50, Board.BLOCK_SIZE, Board.BLOCK_SIZE);
                  
                }
            }
        }
        
        g.setColor(Color.GREEN);
                for (int row = 0; row < nextShape.getCoords().length; row++) {
            for (int col = 0; col < nextShape.getCoords()[0].length; col++) {
                if (nextShape.getCoords()[row][col] != 0) {
                  g.drawRect(col * 30 + 320, row * 30 + 50, Board.BLOCK_SIZE, Board.BLOCK_SIZE);          
                }
            }
        }
        
                String s=Menu.getname();
        if(state==STATE_GAME_OVER)
        {
         int t=RScore();
        
        g.setColor(Color.RED);
        g.setFont(new Font("Georgia", Font.BOLD, 30));
                g.drawRect(25, Game.HEIGHT/2-35, 270, 120);
        g.setColor(Color.WHITE);
        g.fillRect(25, Game.HEIGHT/2-35, 270, 120);
                g.setColor(Color.GREEN);
        g.drawString("GAME OVER!", 55,Game.HEIGHT/2);
        g.setFont(new Font("Georgia", Font.BOLD, 20));
        g.drawString(s+"'s Score : ", 55,Game.HEIGHT/2+30);
        g.drawString(""+t, 150,Game.HEIGHT/2+60);
        }
        if(state==STATE_GAME_PAUSE){
        g.setColor(Color.RED);
        g.setFont(new Font("Georgia", Font.BOLD, 30));
        g.drawRect(25, Game.HEIGHT/2-35, 270, 50);
        g.setColor(Color.WHITE);
        g.fillRect(25, Game.HEIGHT/2-35, 270, 50);
        g.setColor(Color.GREEN);
        g.drawString("GAME PAUSED!", 35,Game.HEIGHT/2);
        }
        if(state==STATE_GAME_PLAY || state==STATE_GAME_PAUSE || state==STATE_GAME_OVER){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Georgia", Font.BOLD, 20));
        g.drawString("Player: ", Game.WIDTH-135,Game.HEIGHT/2-80);
        g.drawString(s, Game.WIDTH-135,Game.HEIGHT/2-40);
        g.drawString("Score: "+Score, Game.WIDTH-135,Game.HEIGHT/2);
        g.drawString("Difficulty: ", Game.WIDTH-135,Game.HEIGHT/2+40);
        g.drawString(Level, Game.WIDTH-135,Game.HEIGHT/2+80);
        g.drawImage(Logo, Game.WIDTH-135, Game.HEIGHT/2+100, this);
        
        }
      
    }
    public Color[][] getBoard()
    {
        return board;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
   }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            currentShape.speedUp();
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            currentShape.moveRight();
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            currentShape.moveLeft();
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            currentShape.rotateShape();
        }
        //New Game
        
        if(state==STATE_GAME_PLAY || state==STATE_GAME_OVER || state==STATE_GAME_PAUSE)
        {
            
         if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            Score=0;
            
         for(int row=0; row<board.length; row++)
        {
            for(int col=0; col<board[row].length; col++)
            {
             board[row][col]=null;
            }
        }
         setCurrentShape();
         state=STATE_GAME_PLAY;
        }
        }
        //Game Pause
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
        if(state==STATE_GAME_PLAY)
        {
         state=STATE_GAME_PAUSE;
        }
        else if(state==STATE_GAME_PAUSE)
        {
         state=STATE_GAME_PLAY;
        }
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
        if(state==STATE_GAME_PLAY || state==STATE_GAME_OVER || state==STATE_GAME_PAUSE)
        {
            
         Game.Destroy();
         new Menu().setVisible(true);
        }
}
    }

    @Override
    public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            currentShape.speedDown();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    public void Score()
    {
        Score=Score+1;
    }
    public int RScore()
    {
        return Score;
    }

}
