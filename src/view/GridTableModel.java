package view;

import javax.swing.table.AbstractTableModel;
import javax.swing.ImageIcon;
import battle.Square;

public class GridTableModel extends AbstractTableModel {

  private int noOfRows, noOfCols;
  private Square[][] grid;
  private static final String PATH = "./data/"; 
  private String imageFree= "free.png";
  private String imageHitAndFree= "freeHit.png";
  private String imageBusyAndHit= "busyHit.png";
  private String imageBusy= "busy.png";
    
  
 /*
  * Constructor
  * @param grid : the table to display
  */
  public GridTableModel(Square[][] grid) {
    this.grid = grid;
    noOfRows = this.grid.length;
    noOfCols = this.grid[0].length;
  }

// Implementing the tree abstract methods:
  
   public int getRowCount() {
    return(noOfRows);
  }
  public int getColumnCount() {
    return(noOfCols);
  }

  public Object getValueAt(int r,int c) {
    Object result = new Object();
    Square sq = grid[c][r];
    if ((sq.isFree())&&(!sq.isHit())) result= new ImageIcon(PATH + imageFree);
    else if (sq.isFree() && (sq.isHit())) result= new ImageIcon(PATH + imageHitAndFree);
    else if (!sq.isFree() && (!sq.isHit())) result= new ImageIcon(PATH + imageBusy);
    else result= new ImageIcon(PATH + imageBusyAndHit);
    return result;
  }
 
 
  /**
   * get the name of the column
   * @param c : the number of the column
   * @return a String for the name of the column
   */
  public String getColumnName(int c){
    return (new Integer(c).toString());
  }
  
   /**
   * get the class of the object at column c
   * @param c : the number of the column
   * @return the class of the object at column c
   */
   public Class getColumnClass(int c) {
      return this.getValueAt(0, c).getClass();
   }
}