import javax.swing.*;
import java.awt.*;

public class ViewController extends JLayeredPane {
    public static MutableBoolean DEBUG;
    
    public SudokuModel theModel;
    public SudokuEdges theEdges; // 1st layer with edges between cells and values
    public SudokuGrid theGrid; // 2nd layer with the SudokuCells and Circles

    public static final int SIZE = 9;
    
    public ViewController(SudokuModel theModel){
	super();

	this.theModel = theModel;
	
	setPreferredSize(new Dimension(1200, 550-4));
	setLayout(new BorderLayout());
	
	theEdges = new SudokuEdges(theModel);
	theEdges.setPreferredSize(new Dimension(1200, 550-4));
	
	theGrid = new SudokuGrid(theModel, SIZE, SIZE);

	theEdges.DEBUG = theModel.DEBUG;
	theGrid.DEBUG = theModel.DEBUG;

	theModel.setGrid(theGrid);

	theModel.setViewController(this);
	
	theEdges.setOpaque(false);
	theGrid.setOpaque(false);
	add(theEdges, 0, 0, 0, 1150, 550-4);
	add(theGrid, 0, 0, 1, 1150, 550-4); 
    }

    public void setModel(SudokuModel theModel){
	this.theModel = theModel;
    }

    public void add (JComponent jc, int x, int y, int z, int width, int height){
	jc.setLocation(x, y);
	jc.setBounds(new Rectangle(new Point(x, y), new Dimension(width, height)));
	setLayer(jc, new Integer(z));
	add(jc);
	jc.setVisible(true);
    }

    public void showWelcomeScreen(){
	theEdges.showWelcomeScreen();
    }

    public void hideWelcomeScreen(){
	theEdges.hideWelcomeScreen();
    }
}
