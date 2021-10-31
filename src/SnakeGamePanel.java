import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

@SuppressWarnings("serial")
public class SnakeGamePanel extends JPanel implements ActionListener
{

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
	static final int DELAY = 70;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int objectivesEaten;
	int objectiveX;
	int objectiveY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	SnakeGamePanel(){
		
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.lightGray);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame() {
		
		newObjective();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
		if(running) {
			
			g.setColor(Color.red);
			g.fillOval(objectiveX, objectiveY, UNIT_SIZE, UNIT_SIZE);
		
			for(int i = 0; i< bodyParts;i++) {
				if(i == 0) {
					g.setColor(Color.white);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(Color.black);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}			
			}
			g.setColor(Color.black);
			g.setFont( new Font("Arial",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+objectivesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + objectivesEaten)) / 2, g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
		
	}
	public void newObjective(){
		
		objectiveX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		objectiveY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}
	public void move(){
		
		for(int i = bodyParts; i > 0 ; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) 
		{
		case 'U': // UP
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D': // DOWN 
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L': // LEFT
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R': // RIGHT
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		
	}
	public void checkObjective() {
		
		if((x[0] == objectiveX) && (y[0] == objectiveY)) {
			bodyParts++;
			objectivesEaten++;
			newObjective();
		}
	}
	public void checkCollisions() {
		//Checks if head collides with body
		for(int i = bodyParts; i > 0 ; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		//Check if head touches left border
		if(x[0] < 0) {
			running = false;
		}
		//Check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		//Check if head touches top border
		if(y[0] < 0) {
			running = false;
		}
		//Check if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		
		//Score
		g.setColor(Color.black);
		g.setFont( new Font("Arial",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + objectivesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+objectivesEaten)) / 2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.black);
		g.setFont( new Font("Arial",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkObjective();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}