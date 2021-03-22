
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class serves as the server for Tic-Tac-Toe game.
 * 
 * @author coco
 * @version 1.2
 */
public class TicTacToeServer {

	/**
	 * This method is the main method of the server.
	 * This method will run the server and connected to two players to start the game.
	 */
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(12345);
        System.out.println("Server is Running...");
        try {
            while (true) {
                Game game = new Game();
                Game.Player playerX = game.new Player(listener.accept(), "X");
                Game.Player playerO = game.new Player(listener.accept(), "O");
                playerX.setOpponent(playerO);
                playerO.setOpponent(playerX);
                game.currentPlayer = playerX;
                playerX.start();
                playerO.start();
            }
        } finally {
            listener.close();
        }
    }
}

/**
 * This class acts as the game in process.
 * 
 * @author coco
 *
 */
class Game {

    private Player[] board = {null, null, null, null, null, null, null, null, null};

    Player currentPlayer;

    /**
     * This method check if the game ends with one player winning at the moment.
     * 
     * @return boolean whether game has a winner or not.
     */
    public boolean hasWinner() {
        return
            (board[0] != null && board[0] == board[1] && board[0] == board[2])
          ||(board[3] != null && board[3] == board[4] && board[3] == board[5])
          ||(board[6] != null && board[6] == board[7] && board[6] == board[8])
          ||(board[0] != null && board[0] == board[3] && board[0] == board[6])
          ||(board[1] != null && board[1] == board[4] && board[1] == board[7])
          ||(board[2] != null && board[2] == board[5] && board[2] == board[8])
          ||(board[0] != null && board[0] == board[4] && board[0] == board[8])
          ||(board[2] != null && board[2] == board[4] && board[2] == board[6]);
    }

    /**
     * This method check if the board is filled up and the game ends in draw.
     * 
     * @return boolean whether the game ends in draw or not.
     */
    public boolean boardFilledUp() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method check if the move taken by players is valid.
     * The move is valid only if 
     * - The move is not occupied by any mark.
     * - The move is made in the player’s turn.
     * - The move is made within the 3 x 3 board.
     * 
     * @return boolean whether the move is valid or not.
     */
    public synchronized boolean legalMove(int location, Player player) {
        if (player == currentPlayer && board[location] == null) {
            board[location] = currentPlayer;
            currentPlayer = currentPlayer.opponent;
            currentPlayer.otherPlayerMoved(location);
            return true;
        }
        return false;
    }

    class Player extends Thread {
        String mark;
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;

        public Player(Socket socket, String mark) {
            this.socket = socket;
            this.mark = mark;
            try {
            	input = new Scanner(socket.getInputStream());
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println(mark);
               
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        public void setOpponent(Player opponent) {
            this.opponent = opponent;
        }

        public void otherPlayerMoved(int location) {
            output.println("OPPONENT " + location);
            output.println(hasWinner() ? "LOSE" : boardFilledUp() ? "DRAW" : "");
        }

        public void run() {
        	try {
        		while (input.hasNextLine()) {
                String command = input.nextLine();
                if( command.startsWith("MOVE")){
                int location = Integer.parseInt(command.substring(4));
                if (legalMove(location, this)) {
                	output.println("VALID "+location);
                    output.println(hasWinner() ? "WIN": boardFilledUp() ? "DRAW": "");
                	} 
                } 
            }
            } catch (Exception e) {
				System.out.println(e.getMessage());
            } finally {
                try {socket.close();} catch (IOException e) {}
            }  	
        }
    }
}