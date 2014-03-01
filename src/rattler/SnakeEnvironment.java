/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rattler;

import audio.AudioPlayer;
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
import java.util.ArrayList;

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
    private int moveCounter = getSpeed();
    private ArrayList<Point> brokenhearts;
    private ArrayList<Point> hearts;
    private Image heart;
    private Image brokenheart;
    private GameState gameState = GameState.RUNNING;
    private Image teardrop;
    private ArrayList<Point> teardrops;

    public SnakeEnvironment() {
//        loadImage();
    }

    @Override
    public void initializeEnvironment() {

        this.setBackground(ResourceTools.loadImageFromResource("resources/forrest.png"));
        this.heart = ResourceTools.loadImageFromResource("resources/redheart.png");
        this.brokenheart = ResourceTools.loadImageFromResource("resources/brokenheart.png");
        this.teardrop = ResourceTools.loadImageFromResource("resources/teardrop.png");





        this.grid = new Grid();
//        this.grid.setColor(new Color(34, 139, 34));
        this.grid.setColor(new Color(192, 192, 192));
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
        this.hearts.add(new Point(38, 18));

        this.poisonBottles = new ArrayList<Point>();
        this.poisonBottles.add(new Point(7, 14));
        this.poisonBottles.add(new Point(20, 20));
        this.poisonBottles.add(new Point(30, 3));

        this.brokenhearts = new ArrayList<Point>();
        this.brokenhearts.add(new Point(9, 20));
        this.brokenhearts.add(new Point(1, 14));
        this.brokenhearts.add(new Point(22, 20));
        this.brokenhearts.add(new Point(30, 8));
        this.brokenhearts.add(new Point(15, 20));
        this.brokenhearts.add(new Point(35, 14));
        this.brokenhearts.add(new Point(40, 5));
        this.brokenhearts.add(new Point(2, 8));
        this.brokenhearts.add(new Point(12, 8));
        this.brokenhearts.add(new Point(16, 4));
        this.brokenhearts.add(new Point(23, 5));
        this.brokenhearts.add(new Point(10, 9));
        this.brokenhearts.add(new Point(3, 6));
        this.brokenhearts.add(new Point(4, 18));
        this.brokenhearts.add(new Point(13, 5));
        this.brokenhearts.add(new Point(20, 8));
        this.brokenhearts.add(new Point(17, 10));
        this.brokenhearts.add(new Point(25, 4));
        this.brokenhearts.add(new Point(27, 9));
        this.brokenhearts.add(new Point(28, 6));
        this.brokenhearts.add(new Point(29, 18));
        this.brokenhearts.add(new Point(31, 14));
        this.brokenhearts.add(new Point(33, 4));

        this.teardrops = new ArrayList<Point>();
        this.teardrops.add(new Point(20, 4));
        this.teardrops.add(new Point(5, 14));
        this.teardrops.add(new Point(10, 20));
        this.teardrops.add(new Point(36, 18));
        this.teardrops.add(new Point(30, 12));
        this.teardrops.add(new Point(28, 14));
        
        this.snake = new Snake();
        this.snake.setBodyColor(new Color(220, 20, 60));
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 4));
        this.snake.getBody().add(new Point(5, 3));
        this.snake.getBody().add(new Point(4, 3));
        this.snake.getBody().add(new Point(4, 2));
        this.snake.getBody().add(new Point(4, 1));

//        AudioPlayer.play("resources/Amazed.mp3");
        AudioPlayer.play("/resources/heartbreak_warfare.wav");



    }

    private Point getRandomGridLocation() {
        return new Point((int) (Math.random() * this.grid.getColumns()), (int) (Math.random() * this.grid.getRows()));

    }

    @Override
    public void timerTaskHandler() {
        //System.out.println("Timer");
        if (this.gameState == GameState.RUNNING) {
            if (snake != null) {
                if (moveCounter <= 0) {
                    snake.move();
                    moveCounter = getSpeed();
//                    speed = 2;
                    checkSnakeIntersection();
                    if (snake.selfHitTest()) {
                        gameState = GameState.ENDED;
                    }
                } else {
                    moveCounter--;
                }
            }
            if (snake.getHead().x < 0) {
                snake.getHead().x = grid.getColumns();
            } else if (snake.getHead().x > grid.getColumns()) {
                snake.getHead().x = 0;
            } else if (snake.getHead().y < 0) {
                snake.getHead().y = grid.getRows();
            } else if (snake.getHead().y > grid.getRows()) {
                snake.getHead().y = 0;
            }

        }

    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameState == GameState.RUNNING) {
                gameState = GameState.PAUSED;
            } else if (gameState == GameState.PAUSED) {
                gameState = GameState.RUNNING;
            }
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
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            this.setScore(this.getScore() + 57);
            //AudioPlayer.play("/resources/heartbreak_warfare.wav");
//            AudioPlayer.play("/resources/Heaven.wav");

        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameState = GameState.ENDED;
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
            //grid.paintComponent(graphics);

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
                if (this.teardrops != null) {
                    for (int i = 0; i < this.teardrops.size(); i++) {
                        //this.brokenhearts.get(i);
                        Point cellPosition = this.grid.getCellPosition(this.teardrops.get(i));
                        graphics.drawImage(teardrop, cellPosition.x, cellPosition.y, this.grid.getCellWidth(), this.grid.getCellHeight(), this);

                    }
                }


                if (this.poisonBottles != null) {
                    for (int i = 0; i < this.poisonBottles.size(); i++) {
                        this.poisonBottles.get(i);
                        GraphicsPalette.drawPoisonBottle(graphics, this.grid.getCellPosition(this.poisonBottles.get(i)), this.grid.getCellSize(), Color.yellow);
                    }
                }

                Point cellLocation;
//                graphics.setColor(Color.blue);
                if (snake != null) {
                    for (int i = 0; i < snake.getBody().size(); i++) {
                        if (i == 0) {
                            graphics.setColor(snake.getBodyColor());
                        } else {
                            graphics.setColor(snake.getBodyColor());
                        }
                        cellLocation = grid.getCellPosition(snake.getBody().get(i));
                        graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());
                    }
                }
            }
            //GraphicsPalette.drawApple(graphics, new Point(100, 100), new Point(100, 100));


            graphics.setColor(Color.red);
            graphics.setFont(new Font("Calibri", Font.BOLD, 30));
            graphics.drawString("Score: " + this.getScore(), 550, 90);
            graphics.setFont(new Font("Calibri", Font.BOLD, 25));
            graphics.drawString("   Collect the hearts, avoid the broken hearts,  ", 345, 30);
            //for extra points! Hit too many broken hearts and the game is over.
            graphics.drawString("and die with the love potions or if you hit yourself. ", 345, 60);
            graphics.setColor(Color.white);
            graphics.setFont(new Font("Calibri", Font.BOLD, 40));
            graphics.drawString("Rattle Your Love ", 40, 60);


            if (gameState == GameState.ENDED) {
                graphics.setColor(Color.red);
                graphics.setFont(new Font("Calibri", Font.BOLD, 80));
                graphics.drawString("GAME OVER!", 200, 250);


            }
        }
    }

    private void checkSnakeIntersection() {
        //if the snake location is the same as any apple,
        //then grow the snake and remove the apple 
        //later, move the apple and make a sound and increase the score

        for (int i = 0; i < this.hearts.size(); i++) {
            if (snake.getHead().equals(this.hearts.get(i))) {
                //System.out.println("APPLE Chomp!!!");
                this.setScore(this.getScore() + 10);
                this.speed = 4;
                //move the heart to a new location
                this.hearts.get(i).setLocation(getRandomGridLocation());
                this.snake.setBodyColor(new Color(220, 20, 60));
            }
        }

        for (int i = 0; i < this.brokenhearts.size(); i++) {
            if (snake.getHead().equals(this.brokenhearts.get(i))) {
                //System.out.println("BOOM!!!");
                this.setScore(this.getScore() - 15);
                snake.grow(1);
                this.brokenhearts.get(i).setLocation(getRandomGridLocation());
            }
        }

        for (int i = 0; i < this.poisonBottles.size(); i++) {
            if (snake.getHead().equals(this.poisonBottles.get(i))) {
                this.gameState = GameState.ENDED;
            }
        }
        for (int k = 0; k < this.teardrops.size(); k++) {
            if (snake.getHead().equals(this.teardrops.get(k))) {
                snake.setBodyColor(Color.blue);
                this.score -= 10;
                this.teardrops.get(k).setLocation(getRandomGridLocation());
                this.speed = 1;
            }
        }

    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param newScore the score to set
     */
    public void setScore(int newScore) {
        if ((this.score < 30) && (newScore >= 30)) {
            this.setSpeed(6);
        } else if ((this.score < 60) && (newScore >= 60)) {
            this.setSpeed(4);
        } else if ((this.score < 90) && (newScore >= 90)) {
            this.setSpeed(2);
        } else if ((this.score < 150) && (newScore >= 150)) {
            this.setSpeed(1);
        }

        this.score = newScore;
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
        System.out.println("speed up");
        //sound
        AudioPlayer.play("/resources/Heartbeat.wav");


    }
}
