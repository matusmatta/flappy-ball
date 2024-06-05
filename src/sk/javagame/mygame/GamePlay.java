package sk.javagame.mygame;

import sk.javagame.mygame.objects.Ball;
import sk.javagame.mygame.objects.Column;
import sk.javagame.mygame.objects.Score;
import sk.javagame.mygame.screens.GameOverScreen;
import sk.javagame.mygame.screens.MenuScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private final Timer timer;
    private int bgX = 0;
    private int speedMoveXBackground = 0;

    private final BufferedImage bg;
    private final BufferedImage ball;
    private final BufferedImage textTap;
    private final BufferedImage grass;


    private boolean spacePressed = false;
    private boolean instructionScreen = false;
    private boolean playGameScreenActive = false;
    private boolean menuScreenActive = true;
    private boolean gameOverScreenActive = false;

    private Column column;
    private final MenuScreen menuScreen;
    private Score score;
    private final GameOverScreen gameOverScreen;


    public GamePlay() throws IOException {
        int delay = 8;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        column = new Column();
        menuScreen = new MenuScreen();
        score = new Score();
        gameOverScreen = new GameOverScreen();
        bg = ImageLoader.loadImage("images/bg.jpg");
        ball = ImageLoader.loadImage("images/ball.png");
        textTap = ImageLoader.loadImage("images/textTap.png");
        grass = ImageLoader.loadImage("images/grass.png");


        timer = new Timer(delay,this);
        timer.start();

    }


    private void moveBg(){
        bgX -= speedMoveXBackground;
        if(bgX < -800){
            bgX = 0;
        }
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        //background
        g.drawImage(bg,bgX,0,800,600,null);
        g.drawImage(bg,bgX+800,0,800,600,null);

        //menu screen
        if(menuScreenActive){
            menuScreen.paintMenu(g2d);
        }

        //play game screen instruction to play
        if(instructionScreen && !playGameScreenActive){
            g.drawImage(ball, Ball.getBallPosX(), Ball.getBallPosY(),30,30,null);
            column.paintColumn(g);
            g.drawImage(textTap,70,120,250,20,null);
        }

        //play game screen
        if(playGameScreenActive){
            instructionScreen = false;
            menuScreenActive = false;
            g.drawImage(ball, Ball.getBallPosX(), Ball.getBallPosY(),30,30,null);
            column.paintColumn(g);
            score.paintScorePlayGame(g2d);
        }

        if(gameOverScreenActive){
            gameOverScreen.paintGameOverScreen(g2d, score.getScore());
        }
        //grass
        g.drawImage(grass,bgX,490,800,100,null);
        g.drawImage(grass,bgX+800,490,800,100,null);

        g.dispose();
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        requestFocus();
        timer.start();

        Ball.ballGravity(playGameScreenActive);
        Ball.ballFly(spacePressed);
        if(playGameScreenActive) {
            moveBg();
            column.columnGeneratorPosition();
            column.ballHit();
            score.scoreCount(column.getColumn());
        }
        if(column.isGameOver()){
            if(Integer.valueOf(FileWriteRead.read("score.txt")) < score.getScore()){
                FileWriteRead.write(String.valueOf(score.getScore()), "score.txt");
            }
            playGameScreenActive = false;
            gameOverScreenActive = true;
        }
        repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_P && !gameOverScreenActive){
            menuScreenActive = false;
            instructionScreen = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !playGameScreenActive && !menuScreenActive && !gameOverScreenActive){
            playGameScreenActive = true;
            speedMoveXBackground = 2;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE && playGameScreenActive && !spacePressed){
            spacePressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_R && gameOverScreenActive){
            column = new Column();
            score = new Score();
            Ball.setBallPosY(200);
            gameOverScreenActive = false;
            instructionScreen = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE && playGameScreenActive && spacePressed){
            spacePressed = false;
        }
    }

}
