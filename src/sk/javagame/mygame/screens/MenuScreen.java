package sk.javagame.mygame.screens;

import sk.javagame.mygame.FileWriteRead;
import sk.javagame.mygame.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;


public class MenuScreen {
    private BufferedImage gameName;
    private BufferedImage textPlay;


    public MenuScreen() throws FileNotFoundException {
        gameName = ImageLoader.loadImage("images/nameGame.png");
        textPlay = ImageLoader.loadImage("images/textPlay.png");
    }

    public void paintMenu(Graphics2D g){
        g.drawImage(gameName,190,120,400,50,null);
        g.drawImage(textPlay,240,200,300,20,null);

        g.setPaint(Color.darkGray);
        g.setFont(new Font("Serif",Font.BOLD,30));
        g.drawString("Best score",320,260);
        g.drawString(FileWriteRead.read("score.txt"),380,300);
    }

}
