
/* MoveWrapper.java */

package player;


public class MoveWrapper {

    private Move move;
    private double score;
    
    /**
     * The is a moveWrapper constructor with 0 args. It sets move = null and the score = 0; 
     * 
     * 
     **/
    protected MoveWrapper() {
        this.move = null;
        this.score = 0;
    }

    /**
     * This is the 1 args movewrapper constructor. It inputs the score and sets the move = null. 
     * 
     * @param score
     */
    protected MoveWrapper(double score) {
        this.move = null;
        this.score = score;
    }

    /**
     * THis is the two arg MoveWrapper constructor. It sets the move and the score. 
     * 
     * @param move
     * @param score
     */
    protected MoveWrapper(Move move, double score) {
        this.move = move;
        this.score = score;
    }
    
    /**
     * Setter for the score field. 
     * 
     * @param score
     */
    protected void setScore(double score) {
        this.score = score;
    }

    /**
     * setter for the move field. 
     * 
     * @param move
     */
    protected void setMove(Move move) {
        this.move = move;
    }
    
    /**
     * getter for the score field. 
     * 
     * @return
     */
    protected double getScore() {
        return this.score;
    }
    
    /**
     * getter for the move field. 
     * 
     * @return
     */
    protected Move getMove() {
        return this.move;
    }
}


