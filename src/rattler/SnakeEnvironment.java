/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rattler;

import environment.Environment;
import environment.GraphicsPalette;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Owner
 */
class SnakeEnvironment extends Environment {

    private Grid grid;
    private int score = 0;
    private Snake snake;
    // private ArrayList<Point> myImage;
    private ArrayList<Point> poisonBottles;
    private int speed = 11;
    private int moveCounter = speed;
    private ArrayList<Point> brokenhearts;
    private ArrayList<Point> hearts;
    private Image heart;
    private Image brokenheart;

    public SnakeEnvironment() {
//        loadImage();
    }

    @Override
    public void initializeEnvironment() {

        this.setBackground(ResourceTools.loadImageFromResource("resources/silver.png"));
        this.heart = ResourceTools.loadImageFromResource("resources/redheart.png");
        this.brokenheart = ResourceTools.loadImageFromResource("resources/brokenheart.png");


        this.grid = new Grid();
//        this.grid.setColor(new Color(34, 139, 34));
        this.grid.setColor(Color.PINK);
        this.grid.setPosition(new Point(20, 100));
        this.grid.setCellHeight(20);
        this.grid.setCellWidth(20);
        this.grid.setColumns(42);
        this.grid.setRows(22);

        this.hearts = new ArrayList<Point>();
        this.hearts.add(new Point(20, 12));
        this.hearts.add(new Point(10, 14));
        this.hearts.add(new Point(40, 14));
        this.hearts.add(new Point(30, 14));
        this.hearts.add(new Point(35, 5));
        this.hearts.add(new Point(10, 2));

        this.poisonBottles = new ArrayList<Point>();
        this.poisonBottles.add(new Point(7, 14));
        this.poisonBottles.add(new Point(20, 20));
        this.poisonBottles.add(new Point(30, 3));

        this.brokenhearts = new ArrayList<Point>();
        this.brokenhearts.add(new Point(9, 20));
        this.brokenhearts.add(new Point(1, 14));
        this.brokenhearts.add(new Point(22, 20));
        this.brokenhearts.add(new Point(30, 8));

        this.snake = new Snake();

        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 4));
        this.snake.getBody().add(new Point(5, 3));
        this.snake.getBody().add(new Point(4, 3));

    }

    private Point getRandomGridLocation() {
        return new Point((int) (Math.random() * this.grid.getColumns()), (int) (Math.random() * this.grid.getRows()));

    }

    @Override
    public void timerTaskHandler() {
        //System.out.println("Timer");
        if (snake != null) {
            if (moveCounter <= 0) {
                snake.move();
                moveCounter = speed;
                checkSnakeIntersection();
            } else {
                moveCounter--;
            }
        }

    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.score += 57;

        } else if (e.getKeyCode() == KeyEvent.VK_M) {
            snake.move();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            snake.setDirection(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            snake.setDirection(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            snake.setDirection(Direction.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_G) {
            snake.setGrowthCounter(2);
        }



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
        if (this.grid != null) {
            graphics.drawImage(null, WIDTH, WIDTH, WIDTH, WIDTH, this);
        }

        if (this.grid != null) {
            grid.paintComponent(graphics);

            if (this.hearts != null) {
                for (int i = 0; i < this.hearts.size(); i++) {
                    Point cellPosition = this.grid.getCellPosition(this.hearts.get(i));

                    graphics.drawImage(heart, cellPosition.x, cellPosition.y, this.grid.getCellWidth(), this.grid.getCellHeight(), this);

                }
                if (this.brokenhearts != null) {
                    for (int i = 0; i < this.brokenhearts.size(); i++) {
                        //this.brokenhearts.get(i);
                        Point cellPosition = this.grid.getCellPosition(this.brokenhearts.get(i));
                        graphics.drawImage(brokenheart, cellPosition.x, cellPosition.y, this.grid.getCellWidth(), this.grid.getCellHeight(), this);

                    }

                }
                if (this.poisonBottles != null) {
                    for (int i = 0; i < this.poisonBottles.size(); i++) {
                        this.poisonBottles.get(i);
                        GraphicsPalette.drawPoisonBottle(graphics, this.grid.getCellPosition(this.poisonBottles.get(i)), this.grid.getCellSize(), Color.yellow);
                    }
                }



                Point cellLocation;
                graphics.setColor(Color.blue);

                if (snake != null) {
                    for (int i = 0; i < snake.getBody().size(); i++) {
                        if (i == 0) {
                            graphics.setColor(new Color(255, 20, 147));
                        } else {
                            graphics.setColor(new Color(199, 21, 133));
                        }

                        cellLocation = grid.getCellPosition(snake.getBody().get(i));

                        graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());

                    }
                }
            }
            //GraphicsPalette.drawApple(graphics, new Point(100, 100), new Point(100, 100));


            graphics.setColor(new Color(255, 20, 147));
            graphics.setFont(new Font("Calibri", Font.BOLD, 20));
            graphics.drawString("Score: " + this.score, 50, 90);
            graphics.drawString("Collect the hearts, avoid the broken, and collect the love potions ", 50, 30);
            //for extra points! Hit too many broken hearts and the game is over.
            graphics.drawString("for extra points! Hit too many broken hearts and the game is over. ", 50, 60);


        }
    }

    private void checkSnakeIntersection() {
        //if the snake location is the same as any apple,
        //then grow the snake and remove the apple 
        //later, move the apple and make a sound and increase the score

        for (int i = 0; i < this.hearts.size(); i++) {

            if (snake.getHead().equals(this.hearts.get(i))) {
                System.out.println("APPLE Chomp!!!");
            }

        }
        for (int i = 0; i < this.brokenhearts.size(); i++) {

            if (snake.getHead().equals(this.brokenhearts.get(i))) {
                System.out.println("BOOM!!!");

            }
        }
        for (int i = 0; i < this.poisonBottles.size(); i++) {

            if (snake.getHead().equals(this.poisonBottles.get(i))) {
                System.out.println("Gulppppp..");
            }
        }

    }
}
