/* DListNode.java */

package list;

/**
 *  A DListNode is a mutable node in a DList (doubly-linked list).
 **/

public class DListNode extends ListNode {

  /**
   *  (inherited)  item references the item stored in the current node.
   *  (inherited)  myList references the List that contains this node.
   *  prev references the previous node in the DList.
   *  next references the next node in the DList.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   **/

    protected DListNode prev;
    protected DListNode next;
    protected int xCol;
    protected int yRow;
    protected int direction;
    
  /**
   *  DListNode() constructor.
   *  @param i the item to store in the node.
   *  @param l the list this node is in.
   *  @param p the node previous to this node.
   *  @param n the node following this node.
   */
    DListNode(Object i, DList l, DListNode p, DListNode n) {
        prev = p;
        yRow = -1;
        next = n;
        xCol = -1;
      item = i;
      myList = l;
      direction = -1;
  }
    
  /**
  * getter for yRow(). 
  **/
   public int yRow() {
       return yRow;
   }
    
   /**
   *  getter for xCol(). 
   **/
    public int xCol() {
        return xCol;
    }
    
    /**
    *   YRow sets the row for y. 
    **/
    public void yRow(int y) {
        yRow = y;
    }
    
    /**
    * setter for direc
    **/
    public void direc(int d) {
        direction = d;
    }

    /**
    * setter for xCol() which inputs x. 
    **/
    public void xCol(int x) {
        xCol = x;
    }
    
    /**
    * getter for the direc field. 
    **/
    public int direc() {
        return direction;
    }
    
  

  /**
   *  isValidNode returns true if this node is valid; false otherwise.
   *  An invalid node is represented by a `myList' field with the value null.
   *  Sentinel nodes are invalid, and nodes that don't belong to a list are
   *  also invalid.
   *
   *  @return true if this node is valid; false otherwise.
   *
   *  Performance:  runs in O(1) time.
   */
  public boolean isValidNode() {
      return myList != null;
  }

  /**
   *  next() returns the node following this node.  If this node is invalid,
   *  throws an exception.
   *
   *  @return the node following this node.
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public ListNode next() throws InvalidNodeException {
      if (!isValidNode()) { 
          throw new InvalidNodeException("WRONG NODE");
    }
      return next; 
  }

  /**
   *  prev() returns the node preceding this node.  If this node is invalid,
   *  throws an exception.
   *
   *  @param node the node whose predecessor is sought.
   *  @return the node preceding this node.
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public ListNode prev() throws InvalidNodeException {
      if (!isValidNode()) { 
          throw new InvalidNodeException("WRONG NODE");
    }
      return prev; 
  }

  /**
   *  insertAfter() inserts an item immediately following this node.  If this
   *  node is invalid, throws an exception.
   *
   *  @param item the item to be inserted.
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public void insertAfter(Object item) throws InvalidNodeException {
      if (!isValidNode()) { 
          throw new InvalidNodeException("WRONG NODE"); 
    }
      DListNode node;
      node = ((DList) myList).newNode(item, (DList) myList, this, this.next);
      next.prev = node;
      next = node;
      myList.size++;
  }

  /**
   *  insertBefore() inserts an item immediately preceding this node.  If this
   *  node is invalid, throws an exception.
   *
   *  @param item the item to be inserted.
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public void insertBefore(Object item) throws InvalidNodeException {
      if (!isValidNode()) {
      throw new InvalidNodeException("WRONG NODE");
    }
      DListNode node; 
      node = ((DList) myList).newNode(item, (DList) myList, this.prev, this); 
    prev.next = node;
    prev = node;
    myList.size++;
  }

  /**
   *  remove() removes this node from its DList.  If this node is invalid,
   *  throws an exception.
   *
   *  @exception InvalidNodeException if this node is not valid.
   *
   *  Performance:  runs in O(1) time.
   */
  public void remove() throws InvalidNodeException {
    if (!isValidNode()) {
      throw new InvalidNodeException("WRONG NODE");
    }
    prev.next = next;
    next.prev = prev;
    myList.size--; 

    myList = null;
    next = null;
    prev = null;
  }

}
