/* Board.java */

package player; 
import list.*;

public class Board {

    /**
     * These variables ensures which spots of the board are open and which aren't. It also establishes an iterator and 
     * a list of items that have been visited during search. 
     **/
    private static boolean isTLOpen = true;
    private static boolean isLOpen = true;
    private static boolean isTOpen = true;
    private static boolean isTROpen = true;
    private static boolean isROpen = true;
    private static boolean isBROpen = true;
    private boolean iterator = true;
    private static boolean isBOpen = true;
    private static boolean isBLOpen = true;
    private  int[] hasVisited = new int[6];

    /**
     * These are general game variables. Not to be construed with the others. 
     */
    private static final int EMPTY = -1;
    protected static final int WHITE = 1;
    protected static final int BLACK = 0;
    private static final int NORMAL = 2;
    

    /**
     *  These are static variables used for determining which direction the linkage occurred during the linking process. 
     **/
    private static final int TOP = 1;
    private static final int TL = 0;
    private static final int RIGHT = 3;
    private static final int TR = 2;
    private static final int LEFT = 7;
    private static final int BR = 4;
    private static final int BL = 6;
    private static final int BOTTOM = 5;
    
    /**
     * This is a general Board() constructor which calls upon the the BoardWrapper. 
     * The board wrapper function makes sure that an proper board is created for the game. 
     */
    private BoardWrapper[][] board = new BoardWrapper[8][8];
    
    protected Board(){
        for (int x = 0; x < 8; x++ ) {
            for (int y = 0; y < 8; y++) {
                board[x][y] = new BoardWrapper(x,y);
                board[x][y].changeColor(-1);
            }
        }
    }

    private static BoardWrapper[][] newBoard(){
        Board toReturn = new Board();
        return toReturn.board;
    }
    
        
    /**
     * Undo Moves does literally what it says. It checks to see if there is a different color at 
     * that space you move into and move from. It then undo's the move and replaces the color. 
     * 
     * @param color
     * @param move
     */
     protected void undoMove(int color, Move move) {
        if (move.moveKind == Move.STEP) {
             board[move.x1][move.y1].changeColor(EMPTY);
             board[move.x2][move.y2].changeColor(color);
         }else if (move.moveKind == Move.ADD) {
             board[move.x1][move.y1].changeColor(EMPTY);
         }
     }
    
     /**
      * This function performs the given move for you. If the move is a Step move then it steps from one position into another. 
      * If the move is a add move then it just adds. Finally, if its quit then it does nothing. 
      * 
      * @param color
      * @param move
      */
    protected void doMove(int color, Move move) {
        if (move.STEP == move.moveKind){
            board[move.x2][move.y2].changeColor(EMPTY);
            board[move.x1][move.y1].changeColor(color);
        }else if (move.ADD == move.moveKind){
            board[move.x1][move.y1].changeColor(color);
        }else{
        }
    }
    
    
    /**
     * graph Traverse traverses the board and checks to see if you have pieces that surround position x and y with the same color. 
     * This is used to check for clustering and enforce it. It returns the number of chips around a cell. 
     * 
     * @param x
     * @param y
     * @param c
     * @return
     */
    private int graphTraverse(int x, int y, int c){
        int toRet=0;
        if (x != 0){if (c == board[x - 1][y].color() ){toRet++;}}
        if (x != 7){if (c == board[x + 1][y].color() ){toRet++;}}
        
        if (y != 0){if (c == board[x][y - 1].color() ){toRet++;}}
        if (y != 7){if (c == board[x][y + 1].color() ){toRet++;}}
        
        if (x != 0 && y != 0){ if (c == board[x - 1][y - 1].color() ){toRet++;}}
        if (x != 7 && y != 0){ if (c == board[x + 1][y - 1].color() ){toRet++;}}
        if (x != 0 && y != 7){ if (c == board[x - 1][y + 1].color() ){toRet++;}}
        if (x != 7 && y != 7){ if (c == board[x + 1][y + 1].color() ){toRet++;}}
        return toRet;
    }

    /**
     * isValidMove is basically the referee. It ensures that specific moves are allowed. Enforces clustering. 
     * Checks to see if you have pieces in closed objects. It also checks to see if you are stepping correctly.
     * It returns true f it is indeed legal and false otherwise.  
     * 
     * @param x
     * @param y
     * @param color
     * @return
     */
    protected boolean isValidMove(int x, int y, int color){
        if ( x > 7 || x < 0 || y > 7 || y < 0){
            return false;}

        if (board[x][y].color() != EMPTY){
            return false;}
        
        if (graphTraverse(x,y,color) > 1){
            return false;}

        if (x != 0 && color == board[x - 1][y].color() && graphTraverse(x - 1,y,color) > 0) {
        return false;
        }
        if (x != 7 && color == board[x + 1][y].color() && graphTraverse(x + 1,y,color) > 0) { 
        return false;
        }
        if (y != 0 && color == board[x][y - 1].color() && graphTraverse(x,y - 1,color) > 0) { 
        return false;
        }
        if (y != 7 && color == board[x][y + 1].color() && graphTraverse(x,y + 1,color) > 0) { 
        return false;
        }
        if (x != 0 && y != 0 && color == board[x - 1][y - 1].color() && graphTraverse(x - 1,y - 1,color) > 0) {
        return false;
        }
        if (x != 7 && y != 0 && color == board[x + 1][y - 1].color() && graphTraverse(x + 1,y - 1,color) > 0) { 
        return false;
        }
        if (x != 0 && y != 7 && color == board[x - 1][y + 1].color() && graphTraverse(x - 1,y + 1,color) > 0) { 
        return false;
        }
        if (x != 7 && y != 7 && color == board[x + 1][y + 1].color() && graphTraverse(x + 1,y + 1,color) > 0) { 
        return false;
        }
        
        if (board[x][y].touchDown() != color && board[x][y].touchDown() != NORMAL){
                    return false;}

        return true;
    }
    
   

    /**
     * This checks to see if a x and y value are in a 
     * 2D array called storage. This ensres that you arent calling
     * mantainNetworks twice. 
     * 
     * @param x
     * @param y
     * @param storage
     * @return
     */
    private static boolean inStorage(int x, int y, int[][] storage){
        for (int i = 0; i < storage.length; i++ ){
            if (storage[i][0] == x && storage[i][1] == y){
                return true;
            }else if(storage[i][0] == y && storage[i][1] == x){
                return true;
            }
        }
        return false;
    }


    

    /**
     * Helper method that makes sure to check if x1,y1 and x2,y2 
     * are diagonal to each other. Returns true if they are and 
     * false if they aren't. 
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private static boolean isOrthogonal(int x1, int y1, int x2, int y2){
        if ((x1+y1)==(x2+y2) || (x1-y1)==(x2-y2)){
            return true;
        } else{
            return false;
        }
    }
    
    /**
     * Private method made for debugging. Please disreagaurd. 
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private static boolean isAligned(int x1, int y1, int x2, int y2){
        if (isOrthogonal(x1,y1,x2,y2)){
            return true;
        } else if ((x1 == x2) || (y1 == y2)) { //checks horizontally and vertically
            return true;
        }else{
            return false;
        }
    }


    /**
     * Checks to see if you have a network. Returns true if you do and false otherwise. 
     * 
     * @return
     */
    protected boolean isNetwork() {
        return (colorNetwork(BLACK) || colorNetwork(WHITE));
    }
    
    /**
     * This is the first of many helper methods for isNetwork(). colorNetwork checks to see if a specific color has a network. 
     * 
     * @param specColor
     * @return
     */
    protected boolean colorNetwork(int specColor) {
        Boolean isNetwork = false;
        for (int i = 0; i < 8; i++) {
            if ((specColor == WHITE && board[0][i].color() == specColor) || (specColor == BLACK && board[i][0].color() == specColor)) {
                DList firstChipEncountered;
                firstChipEncountered = new DList();
                firstChipEncountered.insertFront(-1);
                DListNode firstPiece = (DListNode) firstChipEncountered.front();
                if (specColor == WHITE) {
                    firstPiece.xCol(0);
                    firstPiece.yRow(i);
                } else if (specColor == BLACK) {
                    firstPiece.xCol(i);
                    firstPiece.yRow(0);
                }
                firstPiece.direc(-1);
                isNetwork = networkSearch(specColor, firstChipEncountered, 1);
                if(isNetwork) {
                    break;
                }
            }
        }
        return isNetwork;
    }
    

    /**
     * networkSearch() does a pseudo DFS to check to see if you have 6 linkages for a color. 
     * It retains a visited array to ensure you aren't entring into a unnecessary cycle. 
     * Probably should have used disjoint sets now that I think about it.  
     * It finds potential candidates and then checks to see if they create a network. 
     * 
     * @param color
     * @param prevPieces
     * @param loop
     * @return
     */
    private boolean networkSearch(int color, DList prevPieces, int loop) {
        if (prevPieces.length() > (int) hasVisited[2]) {
            hasVisited[0] = hasVisited[3];
            hasVisited[1] = hasVisited[4];
            hasVisited[2] = prevPieces.length();
        }
        boolean isNetwork = false;
        DListNode node = (DListNode)prevPieces.back();
        DList linkages = checkDirection(node.yRow(),node.xCol());
        linkages = (DList) linkages;
        DListNode point = (DListNode) linkages.front();
        DListNode secondPoint = point; 
        int linkDirec = point.direc();
        int linkY = point.yRow();
        int linkX = point.xCol();
        try {
            if (linkages.length() > 0) {
                for(int i = -1; i<=linkages.length(); i++) {
                    linkX = point.xCol();
                    linkY = point.yRow();
                    linkDirec = point.direc();
                    if (linkages.length() != 1 && point != linkages.back()) {
                        secondPoint = (DListNode) point.next();
                    }
                    if(!checkAndFilter(prevPieces,linkY,linkX,linkDirec)) {
                        if (point != null && linkages.length() == 1){
                            point.remove();
                            point = null; 
                            break;
                        } else if (point == linkages.back() && point != null) {
                            point = (DListNode)point;
                            secondPoint = (DListNode) secondPoint;
                            point.remove();
                            point = secondPoint;
                            break;
                        } else {
                            point = (DListNode)point;
                            secondPoint = (DListNode)secondPoint;
                            point.remove();
                            point = secondPoint ;
                            continue;
                        }
                    }
                    else if (loop >= 5) {
                        if (color == BLACK && linkY == 7 || color == WHITE && linkX == 7 ) {
                            isNetwork = true;
                            break;
                        }
                    }
                    if (point != null && linkY != 0 && point != linkages.back() && linkX != 0) {
                        point = (DListNode) point.next();
                    }
                }
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }
        if (isNetwork){
            return isNetwork;
        }else{
            return finalChecker(prevPieces, linkages, loop);
        } 
    }

    /**
     * checkAndFilter takes a potential x and y candidate and tries to DFS it to determine if it reaches the end with a 6 linked chain. 
     * It chains together linkages basically. It eturns true if you have the necessary amount of linkages for a network, and false otherwise. 
     * 
     * @param toBeFiltered
     * @param yCandidate
     * @param xCandidate
     * @param fromWhere
     * @return
     */
    private boolean checkAndFilter(DList toBeFiltered, int yCandidate, int xCandidate, int fromWhere) {
        toBeFiltered = (DList) toBeFiltered;
        DListNode pointer = (DListNode) toBeFiltered.front();
        for (int i = 0; i<toBeFiltered.length(); i++) {
            pointer = (DListNode) pointer;
            DListNode filterNode = (DListNode) toBeFiltered.back();
            if (xCandidate == 0 || yCandidate == 0 || (xCandidate == pointer.xCol() && yCandidate == pointer.yRow()) ||fromWhere == filterNode.direc()) {
                return false;
            }
            try {
                DListNode compNode = (DListNode) toBeFiltered.back();
                if (pointer != compNode) {
                    pointer = (DListNode) pointer.next();
                }
            } catch (InvalidNodeException e) {
                System.err.println(e);
            }
        }
        return true;
    }
    
    /**
     * This method call helps check and filter chain together networks and returns true if it makes a network after chaining 
     * the linkages together. It mantains a loop in order to mantain how many searches are done. 
     * 
     * @param prevPieces
     * @param linkages
     * @param loop
     * @return
     */
    private boolean finalChecker(DList prevPieces, DList linkages, int loop) {
        if (prevPieces.length() > (int) hasVisited[2]) {
            hasVisited[1] = hasVisited[4];
            hasVisited[0] = hasVisited[3];
            hasVisited[2] = prevPieces.length();
        }
        boolean search = true;
        DListNode pointer = (DListNode) linkages.front();
        int linkedDirec = pointer.direc(); 
        int xLink = pointer.xCol();
        int yLink = pointer.yRow();
        DListNode node = (DListNode)prevPieces.back();
        int color = board[node.xCol()][node.yRow()].color();
        
        try {
            for (int i = 0; i < linkages.length(); i++) {
                linkedDirec = pointer.direc();
                xLink = pointer.xCol();
                yLink = pointer.yRow();
                DListNode newNode = (DListNode)prevPieces.back();
                search = checkAndFilter(prevPieces, xLink, yLink, linkedDirec);
                if (search && xLink != 7 && yLink != 7 && linkedDirec != newNode.direc()){
                    prevPieces.insertBack(-1);
                    ((DListNode) prevPieces.back()).direc(linkedDirec);
                    ((DListNode) prevPieces.back()).yRow(yLink);
                    ((DListNode) prevPieces.back()).xCol(xLink);
                    if (loop <= 10) {
                        if (networkSearch(color, prevPieces, loop + 1)) {
                            return true;
                        }
                    }
                    prevPieces.back().remove();
                    if (pointer != linkages.back()) {
                        pointer = (DListNode) pointer.next();
                    }
                }
                if (pointer != linkages.back()) {
                    pointer = (DListNode) pointer.next();
                }
            }
        } catch (InvalidNodeException e) {
            System.err.println(e);
        }
        return false;
    }
      
      /**
     * This is a very general searcher method. It starts with a x and y position. 
     * It counts up, left, right, down, and in all other cardinal directions. 
     * The moment it hits a chip of the same color it forms a linkage. This returns a DList of the 
     * linkages. 
     * 
     * @param yValue
     * @param xValue
     * @return
     */
    protected DList checkDirection(int yValue,int xValue) {
        int thePlayer = board[xValue][yValue].color();
        int i = 1;
        DList returnList = new DList();
        iterator = true;
        while (iterator) {
            iterator = false;
            /* While at least one direction is available for traversing, i is increment & the xValue & yValue variables & i are manipulated to check cells in the cardinal and mid-cardinal directions. insertLinkedCells is called as a helper function if & only if the direction is open to being traversed (meaning a chip has not been found in that direction yet), and the cell is a location on the board. */
            if (isTLOpen && xValue - i >= 0 && yValue - i >= 0 &&
                xValue - i <= 7 && yValue - i <= 7) {
                /* Checks northwestern cells */
                returnList = finalizeInsert(xValue - i, yValue - i, thePlayer, returnList, TL);
            }
            if (isTOpen && xValue >= 0 && yValue - i >= 0 &&
                xValue <= 7 && yValue - i <= 7) { 
                /* Checks northern cells */
                returnList = finalizeInsert(xValue, yValue - i, thePlayer, returnList, TOP);
            }
            if (isTROpen && xValue + i >= 0 && yValue - i >= 0 &&
                xValue + i <= 7 && yValue - i <= 7) {
                /* Checks northeastern cells */
                returnList = finalizeInsert(xValue + i, yValue - i, thePlayer, returnList, TR);
            }
            if (isROpen && xValue + i >= 0 && yValue >= 0 &&
                xValue + i <= 7 && yValue <= 7) {
                /* Checks eastern cells */
                returnList = finalizeInsert(xValue + i, yValue, thePlayer, returnList, RIGHT);
            }
            if (isBROpen && xValue + i >= 0 && yValue + i >= 0 &&
                xValue + i <= 7 && yValue + i <= 7) {
                /* Checks southeastern cells */
                returnList = finalizeInsert(xValue + i, yValue + i, thePlayer, returnList, BR);
            } 
            if (isBOpen && xValue >= 0 && yValue + i >= 0 &&
                xValue <= 7 && yValue + i <= 7) {
                /* Checks southern cells */
                returnList = finalizeInsert(xValue, yValue + i, thePlayer, returnList, BOTTOM);
            }
            if (isBLOpen && xValue - i >= 0 && yValue + i >= 0 &&
                xValue - i <= 7 && yValue + i <= 7) {
                /* Checks southwestern cells */
                returnList = finalizeInsert(xValue - i, yValue + i, thePlayer, returnList, BL);
            }
            if (isLOpen && xValue - i >= 0 && yValue >= 0 &&
                xValue - i <= 7 && yValue <= 7) {
                /* Checks western cells */
                returnList = finalizeInsert(xValue - i, yValue, thePlayer, returnList, LEFT);
            }
            i++;
        }
        isTLOpen = true;
        isTOpen = true;
        isTROpen = true;
        isROpen = true;
        isBROpen = true;
        isBOpen = true;
        isBLOpen = true;
        isLOpen = true;
        return returnList;
    }
    
    /**
     *finalizeInsert() just makes the node that represents the linkage. 
     * 
     * @param potX
     * @param potY
     * @param color
     * @param returnList
     * @param dirn
     * @return
     */
    private DList finalizeInsert(int potX, int potY, int color, DList returnList, int dirn) {   
        if(board[potX][potY].color() != EMPTY) {
            switch (dirn) {
                /* Do not allow further traversal of the direction given. If this was not included, we would be in danger of "skipping over" chips. */
            case 0: isTLOpen = false;
                break;
            case 1: isTOpen = false;
                break;
            case 2: isTROpen = false;
                break;
            case 3: isROpen = false;
                break;
            case 4: isBROpen = false;
                break;
            case 5: isBOpen = false;
                break;
            case 6: isBLOpen = false;
                break;
            case 7: isLOpen = false;
                break;
            default: break;
            }
            if(board[potX][potY].color() == color) {
                /* If the chip that is in the given cell belongs to thePlayer, add that chip to the linkedCells list */
                returnList.insertBack(-1);
                DListNode tempReturnList = (DListNode) returnList.back();
                tempReturnList.xCol(potX);
                tempReturnList.yRow(potY);
                tempReturnList.direc(dirn);
            }
        }
        iterator = true;
        return returnList;
    }                  
    
    /**
     * This is what the many years spent playing mortal kombat prepared you for. 
     * This method traverses the board and checks to see if there is a winningBoard amongst the color. 
     * This is to be used in the machine player to determine which move to do. If it sees a move that gives it a win then it 
     * chooses that move. 
     * 
     * @param color
     * @return
     */
    protected DList finishHim(int color) {
        DList winMovesList = new DList();
        winningMoveLoop:
        for (int i = 0; i < 8; i++) {
            /* Traverse x-values */
            for (int j = 0; j<8; j++) {
                /* Traverse y-values */
                if (isValidMove(i, j, color)) {
                    /* If (i, y) is a valid cell per isValidMove for thePlayer, check to see i placing a chip would immediately yield a win. */
                    board[i][j].changeColor(color);
                    if (colorNetwork(color)) {
                        /* If the cell creates a network when thePlayer's chip is placed, stop the iteration & return that cell's X & Y Coordinates in the array */     
                        winMovesList.insertBack(-1);
                        ((DListNode) winMovesList.back()).xCol(i);
                        ((DListNode) winMovesList.back()).yRow(j);
                        board[i][j].changeColor(EMPTY);
                    }
                    board[i][j].changeColor(EMPTY);
                }
            }
        }
        return winMovesList;
    }

    /**
     * badMove controls how you stepmove in the game. If your step move has the potential to go totally badly. 
     * Then it increments a counter as a possible bad move and returns it. 
     * 
     * 
     * @param color
     * @return
     */
    protected int badMoves(int color) {
        Board tempBoard = new Board();
        int value = 0;
        int numOfConnections = 0;
        int numOfBlocks = 0;
        int location = 0;
        int theOpponent = -1;
        int lowest = 100000000;
        if (color == WHITE) {
            theOpponent = BLACK;
        } else {
            theOpponent = WHITE;
        }
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                        if (board[x][y].color() == color && board[x][y].touchDown() == 2) {
                            numOfConnections = checkDirection(x, y).length();
                            tempBoard.board[x][y].changeColor(theOpponent);
                            numOfBlocks = checkDirection(x, y).length();
                            tempBoard.board[x][y].changeColor(color);
                            location = x * 10 + y;
                            value = numOfConnections * 100 - numOfBlocks * 10 + location;
                              if (value < lowest) {
                                lowest = location;
                              }
                        }
            }
        }                
        return lowest;
    }
    
    /**
     * This is the opposite of finishHim. 
     * It lets you know how many moves exist for your opponent to win so you can minimize them during evaluation.  
     * 
     * 
     * @param color
     * @return
     */
    protected int opponentPosition(int color) {
        int theOpponent = -1;
        Board tempBoard = this;
        
        if (color == WHITE) {
            theOpponent = BLACK;
        } else {
            theOpponent = WHITE;
        }
        
        DList listOfWins = finishHim(theOpponent);
        DListNode winsCursor = (DListNode) listOfWins.front();
        for (int i = 0; i < listOfWins.length(); i++) {
            if (isValidMove(winsCursor.xCol(), winsCursor.yRow(), color)) {
                return winsCursor.xCol() * 10 + winsCursor.yRow();
            } else {
                try {
                    winsCursor = (DListNode) winsCursor.next();
                } catch (InvalidNodeException INE) {
                    System.err.println(INE);
                }
            }
        }
        return -1;
    }
    
    /**
     * This is a general toString() method for your board. Use it for debugging. 
     * It returns a string represntation of your gameBoard. 
     */
    public String toString(){
        String fullLine = "-----------------";
        String result = fullLine;
        int b = 0;
        for (short i = 1; i < 16; i++){
            if (i%2 == 0){
                result = result + "\n" + fullLine;
            } else{
                result = result + "\n";
                for (int a = 0; a < 8; a++){
                    result = result + "|" + this.board[b][a];
                }
                result = result + "|";
                b++;
            }
        }
        return result;
    }
    
    public static String gameBoardToString(int[][] board){
        String fullLine = "-----------------";
        String result = fullLine;
        int b = 0;
        for (short i = 1; i < 16; i++){
            if (i%2 == 0){
                result = result + "\n" + fullLine;
            } else{
                result = result + "\n";
                for (int a = 0; a < 8; a++){
                    result = result + "|" + board[b][a];
                }
                result = result + "|";
                b++;
            }
        }
        return result;
    }
    
    
    /**
     * This tells you what color exists at a specific part of the board. 
     * 
     * @param x
     * @param y
     * @return
     **/
    protected int findColor(int x, int y) {
        if (x > 7|| x < 0 || y > 7 || y < 0) {
            return EMPTY;
        } else {
            return board[x][y].color();
        }
    }
    
}
