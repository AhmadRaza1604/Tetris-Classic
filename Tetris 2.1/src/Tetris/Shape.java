/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tetris;
import static Tetris.Board.BLOCK_SIZE;
import static Tetris.Board.BOARD_HEIGHT;
import java.awt.Color;
import java.awt.Graphics;
/**
 *
 * @author Ahmad R
 */
public class Shape extends Menu{
private int x=4, y=0;


    public int normal=getN();
    public int fast=50;
    public int delayTimeForMovement=normal;
    private long beginTime;
    private int deltaX=0;
    private boolean collision=false;
    private int[][] coords;
    private Board board;
    private Color color;
    int diff=getDiff();
    
    public Shape(int[][] coords, Board board, Color color)
    {
        this.coords=coords;
        this.board=board;
        this.color=color;
    }
        
//    public void Level(int value)
//    {
//        normal =normal-value;
//    }
    public void setX(int x)
    {
        this.x=x;
    }
    public void setY(int y)
    {
        this.y=y;
    }
    public void Reset()
    {
        this.x=4;
        this.y=0;
        collision=false;
    }
    public void update()
    {
        
        if(collision)
        {   //fill board color
           for(int row=0; row<coords.length; row++)
           {
              for(int col=0; col<coords[0].length; col++)
              {
                  if(coords[row][col]!=0)
                  {
                      board.getBoard()[y+row][x+col]=color;
                  }
              }
           }
           checkLine();
//           board.Score();
           //set current shape
           board.setCurrentShape();
           
                return;
            }
        boolean moveX=true;
            //Check Horizontal Boundary
            if(!(x+deltaX+coords[0].length>10) && !(x+deltaX<0))
            {
                for(int row=0; row<coords.length; row++)
                {
                 for(int col=0; col<coords[row].length; col++)
                {
                    if(coords[row][col]!=0){
                    if(board.getBoard()[y+row][x+deltaX+col]!=null)
                    {
                       moveX=false; 
                    }}
                }   
                }
                if(moveX){
                x+=deltaX;}
            }
            deltaX=0;
            if(System.currentTimeMillis() - beginTime >  delayTimeForMovement){
                //vertical Movement
                if(!(y+coords.length>=BOARD_HEIGHT))
                {
                    for(int row=0; row<coords.length; row++){
                        for(int col=0; col<coords[row].length; col++)
                        {
                            if(coords[row][col]!=0)
                            {
                                if(board.getBoard()[y+1+row][x+deltaX+col]!=null)
                                {
                                    collision=true;
                                }
                            }
                        }
                    }
                    if(!collision){
                    y++;}
                }
                else{
                collision=true;
                }
            beginTime=System.currentTimeMillis();
            }
    }
    private void checkLine()
    {
        boolean SC=false;
        int bottomLine=board.getBoard().length-1;
        for(int topLine=board.getBoard().length-1; topLine>0; topLine--)
        {
            SC=false;
            int count =0;
            for(int col=0; col<board.getBoard()[0].length; col++)
            {
                if(board.getBoard()[topLine][col] !=null)
                {
                    count++;
                }
                board.getBoard()[bottomLine][col]=board.getBoard()[topLine][col];
                SC=true;
            }
            if(count< board.getBoard()[0].length)
            {
                bottomLine--;
                
            }
        }if(SC==true){board.Score();}
        
    }
    public void rotateShape(){
        int[][] rotatedShape =transposeMatrix(coords);
        reverseRows(rotatedShape);
        //check right and bottom
        if((x+ rotatedShape[0].length>Board.BOARD_WIDTH) || (y+rotatedShape.length>20)){
            return;
        }
        //check for collision with other shapes
        for(int row=0; row< rotatedShape.length; row++)
        {
            for(int col=0; col<rotatedShape[row].length; col++)
            {
                if(rotatedShape[row][col] !=0)
                {
                    if(board.getBoard()[y+row][x+col] !=null)
                    {
                        return;
                    }
                }
            }
        }
        coords=rotatedShape;
    }
    private int[][] transposeMatrix(int[][] matrix){
        int[][] temp=new int[matrix[0].length][matrix.length];
        for(int row=0; row<matrix.length; row++){
        for(int col=0; col<matrix[0].length; col++)
        {
            temp[col][row]=matrix[row][col];
        }
        }
        return temp;
    }
    private void reverseRows(int[][] matrix){
         int middle=matrix.length/2;
         for(int row=0; row<middle; row++)
         {
             int[] temp=matrix[row];
             matrix[row]=matrix[matrix.length-row-1];
             matrix[matrix.length-row-1]=temp;
         }
    }
    
    public void render(Graphics g)
    {
        //Drawing Shape
        for(int row=0; row<coords.length; row++)
        {
            for(int col=0; col<coords[0].length; col++)
            {
                if(coords[row][col] !=0){
                g.setColor(color);
                g.fillRect(col*BLOCK_SIZE+x*BLOCK_SIZE, row*BLOCK_SIZE+y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }}
        }
    }
        public Color getColor() {
        return color;
    }
    public void  speedUp()
    {
        delayTimeForMovement=fast;
    }
    public void speedDown()
    {
        delayTimeForMovement=normal;
    }
    public void moveRight()
    {
     deltaX=1;   
    }
    public void moveLeft()
    {
     deltaX=-1;   
    }
    public int getY(){return y;}
    public int getX(){return x;}
    public int[][] getCoords(){
    return coords;
    }
}