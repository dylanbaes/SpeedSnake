import javax.swing.JFrame;
import java.io.Serial;


public class GameFrame extends JFrame {
	
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	GameFrame() {
		this.add(new GamePanel());
		this.setTitle("Speed Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
}
