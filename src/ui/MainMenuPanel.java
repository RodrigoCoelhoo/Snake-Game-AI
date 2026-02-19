package ui;
import javax.swing.*;

import game.SnakeGame;
import strategy.AStar;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuPanel extends JPanel {
    private String playerName = "";
    private boolean manualEnabled = false;
    private boolean automaticEnabled = false;

    public MainMenuPanel(SnakeGame game, GameFrame gameFrame) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                
                if (manualEnabled && isInsideButton(x, y, (getWidth() / 2) - (getWidth()/2)/2, getHeight()/2, (getWidth() / 2), 40)) {
                    game.setPlayerName(playerName);
					game.setStrategy(null);
                    gameFrame.showGame();
                }
                
                if (automaticEnabled && isInsideButton(x, y, (getWidth() / 2) - (getWidth()/2)/2, getHeight()/2 + 60, (getWidth() / 2), 40)) {
                    game.setPlayerName(playerName);
					game.setStrategy(new AStar());
					game.setStarted(true);
					setFocusable(false);
                    gameFrame.showGame();
                }
            }
        });
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isLetterOrDigit(c) || Character.isWhitespace(c)) {
                    playerName += c;
                } else if (c == '\b' && playerName.length() > 0) {
                    playerName = playerName.substring(0, playerName.length() - 1);
                }
                manualEnabled = !playerName.isEmpty();
                automaticEnabled = !playerName.isEmpty();
                repaint();
            }
        });
        
        requestFocus();
    }
	
	/** 
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		// Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	
		// Title
		g.setColor(Color.GREEN);
		g.setFont(new Font("Impact", Font.PLAIN, 60));
		String title = "Snake Game";
		int xTitle = (getWidth() - g.getFontMetrics().stringWidth(title)) / 2;
		int yTitle = getHeight() / 2 - (getHeight()/2)/2;
		g.drawString(title, xTitle, yTitle);
	
		int onefourthwidth = (getWidth()/2)/2;

		// Name Input (as text)
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.setColor(Color.WHITE);
		String name = "Player Name";
		int xNameInput = (getWidth() / 2) - onefourthwidth;
		int yNameInput = yTitle + 80;
		g.drawRect(xNameInput, yNameInput - 20, (getWidth() / 2), 40);
		if(!manualEnabled || !automaticEnabled)
			g.drawString(name, (getWidth() - g.getFontMetrics().stringWidth(name)) / 2, yNameInput + 10);
		g.drawString(playerName, xNameInput + 10, yNameInput + 10);
	
		// Manual Button
		g.setColor(manualEnabled ? Color.GREEN : Color.GRAY);
		String manualButton = "Manual";
		int manualButtonWidth = g.getFontMetrics().stringWidth(manualButton);
		int xManualButton = (getWidth() / 2) - onefourthwidth;
		int yManualButton = getHeight()/2;
		g.drawRect(xManualButton, yManualButton, (getWidth() / 2), 40);
		g.drawString(manualButton, (getWidth() - manualButtonWidth) / 2, yManualButton + 30);
	
		// Automatic Button
		g.setColor(automaticEnabled ? Color.GREEN : Color.GRAY);
		String automaticButton = "Automatic";
		int automaticButtonWidth = g.getFontMetrics().stringWidth(automaticButton);
		int xAutomaticButton = (getWidth() / 2) - onefourthwidth;
		int yAutomaticButton = yManualButton + 60;
		g.drawRect(xAutomaticButton, yAutomaticButton, (getWidth() / 2), 40);
		g.drawString(automaticButton, (getWidth() - automaticButtonWidth) / 2, yAutomaticButton + 30);
	}
	
    private boolean isInsideButton(int mouseX, int mouseY, int buttonX, int buttonY, int buttonWidth, int buttonHeight) {
        return mouseX >= buttonX && mouseX <= buttonX + buttonWidth && mouseY >= buttonY && mouseY <= buttonY + buttonHeight;
    }
    
    public void requestFocus() {
		setFocusable(true);
        requestFocusInWindow();
    }
}
