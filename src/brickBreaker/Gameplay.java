package brickBreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer time;
	private int delay = 8;
	
	private int playerX = 300;
	
	private int ballX = 100;
	private int ballY = 300;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator brickMap;
	
	public Gameplay(){
		brickMap = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		time = new Timer(delay, this);
		time.start();
	}
	
	public void paint(Graphics g){
		//background
		g.setColor(Color.black);
		g.fillRect(0, 0, 700, 600);
		
		//draw map
		brickMap.draw(g);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 597);
		g.fillRect(0, 0, 697, 3);
		g.fillRect(691, 0, 3, 597);
		
		//the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);

		//the ball
		g.setColor(Color.yellow);
		g.fillOval(ballX, ballY, 20, 20);
		
		//score
		g.setColor(Color.white);
		g.drawString("Score: " + score, 620, 50);
		
		//game over
		if(ballY > 580) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.drawString("GAME OVER", 320, 280);
			g.drawString("Press Enter to Restart", 290, 300);
		}
		
		//win
		if (totalBricks == 0){
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.drawString("YOU WIN", 325, 280);
			g.drawString("Press Enter to Restart", 290, 300);
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent key) {
		time.start();
		
		if(play){
			ballX += ballXdir;
			ballY += ballYdir;
			if(ballX < 0){
				ballXdir = -ballXdir;
			}
			if(ballX > 671){
				ballXdir = -ballXdir;
			}
			if(ballY < 3){
				ballYdir = -ballYdir;
			}
			Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);
			Rectangle paddleRect = new Rectangle(playerX, 550, 100, 8);
			if(ballRect.intersects(paddleRect)){
				ballYdir = -ballYdir;
			}
			A: for (int i = 0; i < brickMap.map.length; i++){
				for (int j = 0; j < brickMap.map[0].length; j++){
					if (brickMap.map[i][j] > 0){
						Rectangle brickRect = new Rectangle(brickMap.brickX + j*brickMap.xWidth, brickMap.brickY + i*brickMap.yWidth, brickMap.xWidth, brickMap.yWidth);
						if(ballRect.intersects(brickRect)){
							brickMap.setBrickValue(0, i, j);
							totalBricks--;
							score+=5;
							if ((ballX + 19 <= brickRect.x) || (ballX + 1 >= brickRect.x + brickRect.width)) {
								ballXdir = -ballXdir;
							} else {
							ballYdir = -ballYdir;
							}
							break A;
						}
					}
				}
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_RIGHT){
			if(playerX > 591){
				playerX = 591;
			} else {
				moveRight();
			}
		}
		if(key.getKeyCode() == KeyEvent.VK_LEFT){
			if(playerX < 3){
				playerX = 3;
			} else {
				moveLeft();
			}
		}
		if(key.getKeyCode() == KeyEvent.VK_ENTER){
			if (!play) {
				play = true;
				playerX = 300;
				ballX = 100;
				ballY = 300;
				ballXdir = -1;
				ballYdir = -2;
				totalBricks = 21;
				score = 0;
				brickMap = new MapGenerator(3, 7);
				repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {}

	@Override
	public void keyTyped(KeyEvent key) {}

	public void moveRight(){
		play = true;
		playerX+=20;
	}
	
	public void moveLeft(){
		play = true;
		playerX-=20;
	}
}
