/* MachinePlayer.java */

package player;
import list.*;

/**
 *  An implementation of an automatic Network player.
 *  
 */
public class MachinePlayer extends Player {
    
    private int chipsOnTable = 20;
    private int myColor;
    private int searchDepth;
    private Board board;

    public static final int SEARCHDEPTH = 4;
    public static final int BLACK = 0;
    public static final int WHITE = 1;
    public static final int BLOCKED = 3;
    public static final int EMPTY = 2;
    
    /**
     * This is the machinePlayer() constructor. It Sets the color of the player. 
     * It also searches the depth of which minimax will search. 
     * @param color1
     */
    public MachinePlayer(int color1) {
        this.myColor = color1;
        board = new Board();
        this.searchDepth = SEARCHDEPTH;
    }
    
    /**
     * This is a 2 argument constructor for the machine player class. It sets the color equal to color and
     * sets the search Depth. is also creates the gameBoard().  
     * 
     * @param color
     * @param searchDepth
     */
    public MachinePlayer(int color1, int searchDepth) {
        this.myColor = color1;
        this.searchDepth = searchDepth;
        board = new Board();
    }

    /**
     * When the opponent moves, it records the opponent move in the gameBoard so you can counter it. 
     * Allows you to evaluate the board effectively. it returns true if their move is legal. False otherwise. 
     * 
     */
    public boolean opponentMove(Move move) {
        chipsOnTable--;
        int oppColor = oppColor(this.myColor);
        boolean isValid = board.isValidMove(move.x1,move.y1,oppColor);
        if (isValid) {
            board.doMove(oppColor, move);
            return true;
        } else {
            return false;
        }
    }
    


    /**
     * This is the chooseMove() method which selects a move for the machine player. 
     * It utilizes the minimax and the evaluation function to pick the best moves. 
     */
public Move chooseMove() {
        chipsOnTable--;
        MoveWrapper test = new MoveWrapper();
        if (chipsOnTable <= 0) {
            if (board.finishHim(myColor).length() > 0) {
                test.setScore(Integer.MAX_VALUE);
                test.setMove(new Move(((DListNode) board.finishHim(myColor).front()).xCol(), ((DListNode) board.finishHim(myColor).front()).yRow()));
            } else if (board.opponentPosition(myColor) > 0) {
                test.setScore(Integer.MIN_VALUE);
                int xval = 0;
                int location = board.opponentPosition(myColor);
                while (location >= 10) {
                    xval++;
                    location = location - 10;
                }
                int yval = location;
                test.setMove(new Move(xval, yval));
            } else {
                test = miniMaxAlgorithm(myColor, -1000, 10000, 1);
            }
            int location = board.badMoves(myColor);
            int xval = 0;
            while (location >= 10) {
                xval++;
                location = location - 10;
            }
            int yval = location;
            Move m = new Move(test.getMove().x1, test.getMove().y1, xval, yval);
            board.doMove(myColor, m);
            return m;
        }
        MoveWrapper best = new MoveWrapper();
            if (board.finishHim(myColor).length() > 0) {
                best.setScore(Integer.MAX_VALUE);
                best.setMove(new Move(((DListNode) board.finishHim(myColor).front()).xCol(), ((DListNode) board.finishHim(myColor).front()).yRow()));
            } else if (board.opponentPosition(myColor) > 0) {
                test.setScore(Integer.MIN_VALUE);
                int xval = 0;
                int location = board.badMoves(myColor);
                while (location >= 10) {
                    xval++;
                    location = location - 10;
                }
                int yval = location;
                best.setMove(new Move(xval, yval));
            } else {
        best = miniMaxAlgorithm(myColor, -10000, 10000, 1);
            }
        board.doMove(myColor, best.getMove());
        return best.getMove();
        }
    
    
    /**
     * this is forceMove() basically it does the move and returns true 
     * if valid and returns false otherwise given an input Move to be forced. 
     * 
     */
    public boolean forceMove(Move move) {
        chipsOnTable--;
        if (board.isValidMove(move.x1, move.y1, myColor)) {
            if (move.moveKind ==2 && board.findColor(move.x2, move.y2) != myColor){
                return false;
            }
            board.doMove(myColor, move);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is the minimax algorithm as shown by Professor Shewchuk in lecture. 
     * It implements alpha beta pruning to figure out the best move to be chosen
     * by the machine player. 
     * 
     * @param color
     * @param alpha
     * @param beta
     * @param sdepth
     * @return
     */
    private MoveWrapper miniMaxAlgorithm(int color, double alpha, double beta, int sdepth) {
        MoveWrapper best = new MoveWrapper();
        MoveWrapper reply;
        
        if (sdepth == SEARCHDEPTH) {
            MoveWrapper test = new MoveWrapper();
            test.setScore(evaluateBoard(board));
            return test;
        }
        if (color == this.myColor) {
            best.setScore(alpha);
        } else {
            best.setScore(beta);
        }
        
        int[] validmoveslist;
        validmoveslist = new int[101];
        for (int it = 0; it < 101; it++){
            validmoveslist[it]=0;
        }
        int iterator = 0;
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                if (board.isValidMove(x, y, color)){
                    validmoveslist[iterator] = x*10 + y;
                    iterator++;
                }
            }
        }
        int current = 0;
        while (validmoveslist[current] != 0) {
            int location = validmoveslist[current];
            int xval = 0;
            while (location >= 10) {
                xval++;
                location = location - 10;
            }
            int yval = location;
            Move move = new Move(xval, yval);
            board.doMove(color, move);
            reply = miniMaxAlgorithm(oppColor(color), alpha, beta, sdepth + 1);
            board.undoMove(color, move);
            if ((color == this.myColor) && (reply.getScore() > best.getScore())) {
                best.setMove(move);
                best.setScore(reply.getScore());
                alpha = reply.getScore();
            } else if ((color == oppColor(this.myColor)) && (reply.getScore() < best.getScore())) {
                best.setMove(move);
                best.setScore(reply.getScore());
                beta = reply.getScore();
            }
            if (alpha >= beta) {
                return best;
            }
            current++;
        }
        return best;
    }
    
    /**
     * This is the evaluation function of a input Board. The way it works is the following: 
     * 
     * It checks to see if there are pieces at the goal lines. It assigns a point for being at each respective goal-line. 
     * It subtracts points if your opponent has pieces at their goal line. 
     * 
     * Then based on the difference between your connections and your opponents connections it adds to BoardScore. So 
     * if your opponent is winning the score is negative and if your opponent is loosing then the boardScore is
     * positive. 
     * 
     * It returns the boardScore. 
     * 
     * 
     * @param b
     * @return
     */
    private float evaluateBoard(Board b) {
        float score = 0;
        int[] goal = {0,0,0,0};
        float goalScore = .05f;
        double mainConnections = 0;
        double otherConnections = 0;
        
        for (int a = 1; a < 7; a++){
            if (b.findColor(a, 0) == Board.BLACK) {
                goal[0]++;
            }
            if (b.findColor(a, 7) == Board.BLACK) {
                goal[1]++;
            }
            if (b.findColor(0, a) == Board.WHITE) {
                goal[2]++;
            }
            if (b.findColor(7, a) == Board.WHITE) {
                goal[3]++;
            }
        }
        
        for (int g = 0; g < 2; g++){
            if (goal[g] == 1){
                if (myColor == Board.BLACK){
                    score += goalScore;
                }
                else {
                    score -= goalScore;
                }
            }
            if (goal[g + 2] == 1) {
                if (myColor == Board.WHITE){
                    score += goalScore;
                }
                else {
                    score -= goalScore;
                }
            }
        }
        
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                if (b.findColor(x, y) == myColor){
                    mainConnections += (b.checkDirection(x, y)).length();
                }
                else if (b.findColor(x, y) == oppColor(myColor)){
                    otherConnections += (b.checkDirection(x, y)).length();
                 }
            }
        }
        
        mainConnections = otherConnections / 2;
        otherConnections = otherConnections / 2;
        score += (mainConnections - otherConnections) / 20;
        
        if (b.finishHim(myColor).length() > 0) {
            score = score * 10 + 100 * b.finishHim(myColor).length();
        }
        if (b.opponentPosition(myColor) > 0) {
            score = score / 10 + 100;
        }
        return score;
    }
 
    /**
     * Determines what your opponent's color is. It inputs an int color and outputs your opponents color. 
     * 
     * @param color
     * @return
     */
    private static int oppColor(int color) {
        if (color == Board.BLACK) {
            return Board.WHITE;
        } else {
            return Board.BLACK;
        }
    }

}        
