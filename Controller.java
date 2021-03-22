import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * This class serves as the controller for client.
 * This class controls the game process.
 * 
 * @author coco
 * @version 1.3
 */
public class Controller {
	private View view;
	private ActionListener submitListener;
	
	private Socket socket;
	private Scanner in;
	private BufferedReader input;
	private PrintWriter out;

	public static int turns;
	
	/**
	 * This is the constructor for Controller class.
	 * 
	 * @param view set the view of Controller.
	 */
	public Controller(View view) {
		this.view = view;
	}
	
	/**
	 * This method starts the Controller.
	 * This method makes connection to the server, sends and reads message.
	 */
	public void start() {
		try {
			this.socket = new Socket("127.0.0.1", 12345);
			this.in = new Scanner(socket.getInputStream());
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		submitListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if(view.getTextField().getText()!="") {
					view.setLabel("WELCOME "+view.getTextField().getText());
					view.setFrameTitle("Tic Tac Toe-Player: "+view.getTextField().getText());
					view.getSubmitButton().setEnabled(false);
					view.getTextField().setEnabled(false);
					for(int i=0; i<9; i++) {
						view.getButtons()[i].setEnabled(true);
					}
				}
			}
		};
		view.getSubmitButton().addActionListener(submitListener);
	
		for(int i=0; i<9;i++) {
			final int j = i;
			view.getButtons()[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					out.println("MOVE"+j);
				}
			});
		}
		
		Thread handler = new ClinetHandler(socket);
		handler.start();
	}
	
	
	class ClinetHandler extends Thread {
		private Socket socket;

		public ClinetHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				readFromServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void readFromServer() throws Exception {
			String response;
			String mark="X";
			String omark="O";
			response = input.readLine();
			if (response.startsWith("O")) {
				mark = "O";
				omark="X";
			}
			try {
				
				while (in.hasNextLine()) {
					
					response = in.nextLine();
			        if (response.startsWith("VALID")) {
			        	int loc = Integer.parseInt(response.substring(6));
	                    view.setLabel("Valid move, wait for your opponent");
	                    view.setButtons(loc,mark);
	                } 
			        else if (response.startsWith("OPPONENT")) {
	                    int loc = Integer.parseInt(response.substring(9));
	                    view.setLabel("Your opponent has moved, now is your turn");
	                    view.setButtons(loc,omark);
	                } 
			        else if (response.startsWith("WIN")) {
			        	view.setMessage("Congratulation. You Win.");
	                    break;
	                } 
			        else if (response.startsWith("LOSE")) {
	                    view.setMessage("You lose.");
	                    break;
	                } 
			        else if (response.startsWith("DRAW")) {
	                    view.setMessage("Draw.");
	                    break;
	                }
			        else if (response.startsWith("END")) {
			        	view.setMessage("Game Ends. One of the players left.");
			        	break;
			        }
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				socket.close();
			}
		}
	}
	
}
