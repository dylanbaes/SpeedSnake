import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.Serial;
import java.util.Random;


import javax.swing.JPanel;

//Speed Snake: the snake gets faster after you eat a set number of worms and the worms will also start to move via AI after you have gotten to a specific length

public class GamePanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;
	static final int SCREEN_WIDTH = 800;
	static final int SCREEN_HEIGHT = 800;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	final int[] x = new int[GAME_UNITS];
	final int[] y = new int[GAME_UNITS];
	int delay = 100;
	int bodyParts = 5;
	int wormsEaten = 0;
	int wormX;
	int wormY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new myKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newWorm();
		running = true;
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		//drawing the worm
		g.setColor(Color.orange);
		g.fillOval(wormX, wormY, UNIT_SIZE, UNIT_SIZE);
		
		//drawing the snake
		for (int i = 0; i < bodyParts; i++) {
			if (i == 0) {
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			else {
				g.setColor(new Color(45, 180, 0));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}

		}
	}
	
	public void newWorm() { //randomly spawn a new worm within the window size
		wormX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
		wormY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
	}
	
	public void move() {
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}

		switch (direction) {
			case 'U' -> y[0] = y[0] - UNIT_SIZE;
			case 'D' -> y[0] = y[0] + UNIT_SIZE;
			case 'L' -> x[0] = x[0] - UNIT_SIZE;
			case 'R' -> x[0] = x[0] + UNIT_SIZE;
		}
	}
	
	public void checkWorms() {
		if((x[0] == wormX) && (y[0] == wormY)) {
			bodyParts++;
			wormsEaten++;
			newWorm();
			if (wormsEaten >= 5) {
				if (delay >= 10){
					delay -= 10; // After 5 worms eaten, the snake will get faster and faster until the delay limit is reach which is a delay of 0
					timer.setDelay(delay);
				}
			}
		}
	}
	
	public void checkCollisions() {
		//does head collide with body
		for (int i = bodyParts; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
				break;
			}
		}
		//does head collide with left border
		if(x[0] < 0) {
			running = false;
		}
		//does head collide with right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		//does head collide with top border
		if(y[0] < 0) {
			running = false;
		}
		//does head collide with bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		if(!running) {
			timer.stop();
		}
		
	}
	
	public void gameOver(Graphics g) {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkWorms();
			checkCollisions();
		}
		repaint();
		
	}
	
	public class myKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}

}
