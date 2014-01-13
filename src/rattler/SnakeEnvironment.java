/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rattler;

import environment.Environment;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Owner
 */
class SnakeEnvironment extends Environment {
    private Grid grid;
    private int score = 0;
    private Snake snake;
    
    private int speed = 11;
    private int moveCounter = speed;
    
    

    public SnakeEnvironment() {
      
    }

    @Override
    public void initializeEnvironment() {
        
        this.setBackground(ResourceTools.loadImageFromResource("resources/snake.jpg"));
        
        this.grid = new Grid();
        this.grid.setColor(new Color(34,139,34));
        this.grid.setPosition(new Point(20,100));
        this.grid.setCellHeight(20);
        this.grid.setCellWidth(20);
            this.grid.setColumns(42);
            this.grid.setRows(22);
            
            this.snake = new Snake();
            
            this.snake.getBody().add(new Point(5,5));
            this.snake.getBody().add(new Point(5,4));
            this.snake.getBody().add(new Point(5,3));
            this.snake.getBody().add(new Point(4,3));
           






    }

    @Override
    public void timerTaskHandler() {
        //System.out.println("Timer");
        if (snake != null){
            if(moveCounter <= 0){
                snake.move();
                moveCounter = speed;
            } else { 
                moveCounter--;
            }
        }
    
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            this.score += 57;
          
        } else if (e.getKeyCode() == KeyEvent.VK_M){
            snake.move();
        } else if (e.getKeyCode() == KeyEvent.VK_W){
            snake.setDirection(Direction.UP);
        }else if (e.getKeyCode() == KeyEvent.VK_A){
            snake.setDirection(Direction.LEFT);
        }else if (e.getKeyCode() == KeyEvent.VK_S){
            snake.setDirection(Direction.DOWN);
        }else if (e.getKeyCode() == KeyEvent.VK_D){
            snake.setDirection(Direction.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_G)
            snake.setGrowthCounter(2);
    
    
    
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_SPACE){
//            this.score += 57;
        
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (this.grid != null){
            grid.paintComponent(graphics);
            
            Point cellLocation;
            graphics.setColor(Color.red);
            if (snake != null){
                for (int i = 0; i < snake.getBody().size(); i++) {
                    if (i == 0){
                        graphics.setColor(new Color(210,180,140));
                    } else {graphics.setColor(new Color(205,133,63));}
                    
                    cellLocation= grid.getCellPosition(snake.getBody().get(i));
                    
                    graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(),grid.getCellHeight());
                    
                }
            }
    }
        graphics.setFont(new Font("Calibri", Font.ITALIC,50)); 
        graphics.drawString("Score: "+ this.score, 50, 60);
        
      
    }
    
}
