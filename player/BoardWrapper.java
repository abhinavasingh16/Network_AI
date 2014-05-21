package player;

public class BoardWrapper {
    
    private int myColor;
    private int x;
    private int y;
    private int atGoal;
    
    /**
    *   This is the one argument BoardWrapper constructor. 
    */
    protected BoardWrapper(int part)
    {
        clearCell(getX(part), getY(part));
    }

    /**
    *   This is the 2 argument BoardWrapper Constructor. It ensures that the xVal and yVal are constructed. 
    **/
    
    protected BoardWrapper(int xVal, int yVal){
        clearCell(xVal, yVal);
    }
    
    /**
    *  getter for the goal line field. 
    **/
    protected int touchDown() {
        return atGoal;
    }
    
    /**
    * Clears entire Tables for clearing. 
    **/
    protected void clearCell(int xVal, int yVal) {
        myColor = -1;
        x = xVal;
        y = yVal;
        changeGoalValue();
    }
    
    
    /**
     * Changes the color of the specific checker space, its just a setter. 
     * 
     * @param c
     */
    protected void changeColor(int c) {
        myColor = c;
    }
    
    /**
     * Gets the color field. 
     * 
     * @return
     */
    protected int color() {
        return myColor;
    }
    
    
    /**
     * Gets the YValue of the number that the checker board represents. 
     * 
     * @param number - the checker board number. 
     * @return
     */
    protected int getY(int number) {
        int returnValue = number % 10;
        return returnValue;
    }

    /**
     * Same as above but for the X value. 
     * 
     * @param loci
     * @return
     */
    protected int getX(int loci) {
        int returnValue = 0;
        while (loci >= 10)
            {
                returnValue++;
                loci = loci - 10;
            }
        return returnValue;
    }
    
  /**
   *  This internally determines how elements of the board are represented. 
   *  If its a corner and blocked off = 3
   *  If piece occupies it = 0 or 1 depending on color. 
   *  Free = 2. 
   * 
   **/
  protected void changeGoalValue(){
    if ((x == 0) && (y==0) || (x == 0) && (y == 7) || (x == 7) && (y == 0) || (x == 7) && (y == 7)){
      atGoal = 3;
    }else if (x == 0 || x == 7){
      atGoal = 1;
    }else if (x == 0 || y == 7){
      atGoal = 0; 
    }else{
      atGoal = 2;
    }
  }
}

