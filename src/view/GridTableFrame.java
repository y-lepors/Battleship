package view;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import battle.Square;
/**
 * GridTableFrame : frame for GridTable
 */
public class GridTableFrame extends SimpleFrame {
  private final int rowHeight = 40;  //en pixel

  /**
   * Constructor
   * It creates the GridTableModel
   * @param grid : the data table to display
   */
  public GridTableFrame(Square[][] grid) {
    if (grid != null) {
      // set the grid size
      //repaire les x vers le bas , les Y vers la droite
       this.setSize(rowHeight*grid[0].length, rowHeight*grid.length);
      // create the model
      GridTableModel otmodel = new GridTableModel(grid);
      JTable tab = new JTable(otmodel);
      // to adjust some parameters
      tab.setShowGrid(true);
      // color for the grid lines
      tab.setGridColor(Color.BLUE);
      tab.setBackground(Color.LIGHT_GRAY);
      tab.setRowHeight(rowHeight);
      
      JScrollPane SP = new JScrollPane(tab);
      this.getContentPane().add(SP);
    }
     else System.out.println("GridTbleFrame: grid cannot bu null");
      
    }

 
}