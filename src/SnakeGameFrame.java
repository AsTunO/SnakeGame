import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SnakeGameFrame extends JFrame 
{
	SnakeGameFrame() {
		
		this.add(new SnakeGamePanel());
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null); 
	}
}