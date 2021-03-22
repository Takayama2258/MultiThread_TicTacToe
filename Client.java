import javax.swing.SwingUtilities;

/**
 * This class serves as the client of Tic-Tac-Toe game.
 * 
 * @author coco
 * @version 1.0
 */
public class Client {
	
	/**
	 * This method is the main method of Client.
	 * This method runs the client.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				View view = new View();
				Controller controller = new Controller(view);
				controller.start();
			}
		});
	}
}
