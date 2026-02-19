package ui;
import javax.swing.*;

import game.SnakeGame;

import java.awt.*;

public class GamePanel extends JPanel {
    private SnakeGame game;

    public GamePanel(SnakeGame game) {
        this.game = game;
        setPreferredSize(new Dimension(game.getArena().getArenaSize(), game.getArena().getArenaSize()));
        setBackground(Color.black);

        setFocusable(true);
    }

    
    /** 
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (game != null && !game.getGameOver()) {
            game.draw(g);
        }
    }
}
