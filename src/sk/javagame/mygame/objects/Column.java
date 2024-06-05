package sk.javagame.mygame.objects;

import sk.javagame.mygame.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Column {
    private final int columnWidth = 30;
    private final int columnHeight = 300;
    private final int spaceBetweenColumn = 430;
    private final int[][] column = new int[4][2];

    private boolean gameOver = false;

    private final BufferedImage columnImage;

    private final Random random;

    public Column() {
        random = new Random();
        columnImage = ImageLoader.loadImage("images/column.png");

        //column1 X and Y position
        column[0][0] = 375;
        column[0][1] = -100;

        //column2 X and Y position
        column[1][0] = 600;
        column[1][1] = -150;

        //column3 X and Y position
        column[2][0] = 825;
        column[2][1] = -50;

        //column 4 X and Y position
        column[3][0] = 1050;
        column[3][1] = -200;
    }

    //generate new position column and move X position column
    public void columnGeneratorPosition(){
        int randomRange = 200;
        for(int i = 0; i < column.length; i++){
            column[i][0] = column[i][0] - 2;

            if(column[i][0] < -100){
                column[i][0] = column[i][0] + 900;
                column[i][1] = (random.nextInt(randomRange) * (-1));
            }

        }
    }

    public void paintColumn(Graphics g){
        for(int i = 0; i < column.length; i++){
            g.drawImage(columnImage,column[i][0],column[i][1],columnWidth,columnHeight,null);
            g.drawImage(columnImage,column[i][0],column[i][1]+spaceBetweenColumn,columnWidth,columnHeight,null);
        }
    }

    //if ball hit column or up or down border than gameOver set true
    public void ballHit(){
        for(int i = 0; i < column.length; i++) {
            if (new Rectangle(Ball.getBallPosX(), Ball.getBallPosY(), 20, 20).intersects(column[i][0], column[i][1], columnWidth, columnHeight)){
                gameOver = true;
            }
            if(new Rectangle(Ball.getBallPosX(), Ball.getBallPosY(), 20, 20).intersects(column[i][0], column[i][1]+spaceBetweenColumn, columnWidth, columnHeight)){
                gameOver = true;
            }
            if(Ball.getBallPosY() < -30 || Ball.getBallPosY() > 500){
                gameOver = true;
            }
        }
    }


    public int[][] getColumn(){
        return column;
    }

    public boolean isGameOver(){
        return gameOver;
    }

}
