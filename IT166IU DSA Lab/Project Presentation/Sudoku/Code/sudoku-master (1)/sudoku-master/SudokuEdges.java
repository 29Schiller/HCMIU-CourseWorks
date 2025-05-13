import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.Line2D;

public class SudokuEdges extends JPanel implements ActionListener {
    public static MutableBoolean DEBUG;

    public SudokuModel theModel;

    public float DELTA = 0.01f;
    public ArrayList<Timer> fadeOutTimers = new ArrayList<Timer>();
    public ArrayList<Timer> fadeInTimers = new ArrayList<Timer>();

    public Timer timerSolve;
    
    private float alpha = 1f;
    private float fadeOutMinimum = 0f;

    private boolean fadingOut;
    
    JLabel statusLabel;
    public static JButton printButton;

    private int offset_y;
    private int distanceBetweenCells_y;

    public int SLEEP = 500; // THREAD SLEEP BETWEEN EDGE DRAWING
    public int SLEEP_BETWEEN_STEPS = 3000;
    
    public Color[][] edgeColors;

    public Color prevColor;

    public ArrayList<TripletIIB> moves = new ArrayList<TripletIIB>();

    public SudokuEdges(SudokuModel theModel){
	super();
	
	this.theModel = theModel;

	edgeColors  = new Color[20][20];

	for(int i=0; i<20; i++){
	    for(int j=0; j<20; j++){
		//initiallization
		edgeColors[i][j] = Color.LIGHT_GRAY;
	    }
	}

	for(int i=1; i<10; i++){
	    for(int j=10; j<19; j++){
		//initiallization
		edgeColors[i][j] = Color.WHITE;
	    }
	}
		
	setPreferredSize(new Dimension(1200, 550-4));
	setLayout(null);

	statusLabel = new JLabel();
	statusLabel.setFont(new Font("Serif", Font.PLAIN, 14));
	statusLabel.setText("<html><div align=center>Please select a row, column<br> or block</div></html>");
	statusLabel.setSize(new Dimension(200, 50));
	add(statusLabel, 750, getPreferredSize().height/2 - 25, statusLabel.getWidth(), statusLabel.getHeight());

    }

    public void setModel(SudokuModel theModel){
	this.theModel = theModel;
    }

    public void loadColorsFromModel(){
	for(int k=0; k<9; k++){ // each of the 9 variables
	    if(theModel.theGrid.sudokuCells3Now[k] == null){
		System.out.println("return");
		return;
	    }
	    
	    int shown_x = theModel.theGrid.sudokuCells3Now[k].i;
	    int shown_y = theModel.theGrid.sudokuCells3Now[k].j;

	    ArrayList<Integer> possibleValues = theModel.worldPeek().grid[shown_x][shown_y].getPossibleValues(); 
	    
	    for(int l=1; l<=9; l++){ // each of the 9 possible digits from a sudoku cell in a sudoku row
		int u = k + 1;
		int v = l + 9;
		
		if(possibleValues.contains(l)){
		    // the value exists in the domain
		    edgeColors[u][v] = Color.LIGHT_GRAY;
		} else {
		    // the value does not exist in the domain
		    edgeColors[u][v] = Color.WHITE; // invisible color
		}
	    }
	}
    }
    
    public void add (JComponent jc, int x, int y, int width, int height){
	jc.setLocation(x, y);
	jc.setBounds(new Rectangle(new Point(x, y), new Dimension(width, height)));
	add(jc);
	jc.setVisible(true);
	
	repaint();
	
    }

    public void showWelcomeScreen(){
	statusLabel.setVisible(true);
    }

    public void hideWelcomeScreen(){
	setAlphaZero();
	statusLabel.setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
	super.paintComponent(grphcs);
	
	Graphics2D g2d = (Graphics2D) grphcs;
	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
	offset_y = (getPreferredSize().height - 9 * 50)/2;
	distanceBetweenCells_y = (getPreferredSize().height - 9 * 50)/(9-1);

	if(statusLabel.isVisible()){
	    return;
	}

	if(theModel.theGrid.sudokuCells3Now[0] == null){
	    // do not show any lines as we do not have any
	    // row column or block selected
	    return;
	}

	/////// add the lines
	int x1 = 0;
	int y1 = 0;
	int x2 = 0;
	int y2 = 0;

	int u;
	int v;
	
	g2d.setStroke(new BasicStroke(1));

	for(int i =0; i<9; i++){
	    // from S to vars
	    x1 = 500;
	    y1 = (5-1) * 50 + (5-1)*distanceBetweenCells_y;
	    
	    x2 = 600;
	    y2 = (i) * 50 + (i)*distanceBetweenCells_y;

	    x1 += 50; 
	    x2 += 0;
	    y1 += 25;
	    y2 += 25;

	    u = 0;
	    v = i + 1;

	    g2d.setColor(drawColor(u, v));
	    g2d.draw(new Line2D.Float(x1, y1, x2, y2));
	}

	for(int i =0; i<9; i++){
	    // from values to T
	    x1 = 1000;
	    y1 = (i) * 50 + (i)*distanceBetweenCells_y;

	    x2 = 1100;
	    y2 = (5-1) * 50 + (5-1)*distanceBetweenCells_y;

	    // was 25 all four
	    x1 += 50;
	    x2 += 0;
	    y1 += 25;
	    y2 += 25;

	    u = i + 9 + 1;
	    v = 19;
		    
	    g2d.setColor(drawColor(u, v));
	    g2d.draw(new Line2D.Float(x1, y1, x2, y2));
	}
	
	for(int i=0; i<9; i++){
	    for(int j=0; j<9; j++){
		if(theModel.theGrid.sudokuCells3Now[i] == null){
		    return;
		}
		
		// from VAR i to VALUE j
	        int shown_x = theModel.theGrid.sudokuCells3Now[i].i;
		int shown_y = theModel.theGrid.sudokuCells3Now[i].j;

		x1 = 600;
		y1 = (i) * 50 + (i)*distanceBetweenCells_y;
		
		x2 = 1000; // was 800
		y2 = (j) * 50 + (j)*distanceBetweenCells_y;

		// was 25 all four
		x1 += 50;
		x2 += 0;
		y1 += 25;
		y2 += 25;

		u = i + 1;
		v = j + 9 + 1;
			    
		g2d.setColor(drawColor(u, v));
		g2d.draw(new Line2D.Float(x1, y1, x2, y2));
	    }
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(theModel.isAnimationPaused){
	    return;
	}
		
	Timer thisTimer = ((Timer)e.getSource());
	
	theModel.stopAllTimersOnDiffLevelComparedTo(thisTimer);

	if(thisTimer == timerSolve){
	    SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>(){
		    protected Boolean doInBackground() throws Exception{
			try{
			    SwingUtilities.invokeAndWait(new Runnable() {
				    public void run() {
					theModel.allDifferent(); 
				    }
				});
			} catch (Exception e2){
			    e2.printStackTrace();
			}
		    
			return true;
		    }
		};
	    
	    worker.execute();
	    
	    thisTimer.stop();
	    theModel.removeTimer(thisTimer);
	    theModel.startNextTimers();
	}
	
	if(fadeOutTimers.contains(thisTimer)){
	    alpha -= DELTA;
	    
	    if (alpha <= fadeOutMinimum) {
		alpha = fadeOutMinimum;

		fadeOutTimers.remove(thisTimer);
		
		thisTimer.stop();
		theModel.removeTimer(thisTimer);
		theModel.startNextTimers();
	    }
	} else if(fadeInTimers.contains(thisTimer)){
	    alpha += DELTA;
	    
	    if (alpha >= 1) {
		alpha = 1;

		fadeInTimers.remove(thisTimer);
		
		thisTimer.stop();
		theModel.removeTimer(thisTimer);
		theModel.startNextTimers();
	    }
	}

	theModel.lastRunningTimer = thisTimer;
	
	repaint();
    }


    public void setAlphaZero(){
	alpha = 0;

        repaint();
    }

    public void setAlphaOne(){
	alpha = 1;

        repaint();
    }
    
    public void setOpaqueOn(){
	this.setOpaque(true);
    }

    public void setOpaqueOff(){
	this.setOpaque(false);
    }
    
    public void fadeOut(){
	fadingOut = true;
	fadeOutMinimum = 0f;
    }

    public void fadeOutALittle(){
	fadeOutMinimum = 0.30f;
    }
    
    public void fadeIn(){
	fadingOut = false;
    }

    public Timer getTimerFadeOut(){
	Timer timer1 = new Timer(10, null);
	timer1.setInitialDelay(100);
        timer1.addActionListener(this);
	timer1.setCoalesce(false);

	fadeOutTimers.add(timer1);
	
	return timer1;
    }

    public Timer getTimerFadeIn(){
	Timer timer2 = new Timer(10, null);
	timer2.setInitialDelay(100);
        timer2.addActionListener(this);
	timer2.setCoalesce(false);

	fadeInTimers.add(timer2);
	
	return timer2;	
    }

    public void drawPath(ArrayList<Integer> path){
	for(int i=0; i<path.size()-1; i++){
	    if(path.get(i) < path.get(i+1)){
		moves.add(new TripletIIB(path.get(i), path.get(i+1), Color.BLUE));
	    } else {
		moves.add(new TripletIIB(path.get(i+1), path.get(i), Color.BLUE));
	    }
	}
    }

    public void drawPathNow(ArrayList<Integer> path){

	for(int i=0; i<path.size()-1; i++){
	    if(path.get(i) < path.get(i+1)){
		moves.add(new TripletIIB(path.get(i), path.get(i+1), Color.BLACK));
	    } else {
		moves.add(new TripletIIB(path.get(i+1), path.get(i), Color.BLACK));
	    }
	}
    }

    public void drawBlack(int u, int v){
	// special as it doesn't repaint/sleep
	edgeColors[u][v] = Color.BLACK;
    }
    
    public void drawRed(int u, int v){
	edgeColors[u][v] = Color.RED;

	try{
	    repaint();
		
	    Thread.sleep(SLEEP);
	} catch (Exception e){
	    e.printStackTrace();
	}
    }

    public void drawGreen(int u, int v){
	edgeColors[u][v] = Color.GREEN;

	try{
	    repaint();
	    
	    Thread.sleep(SLEEP);
	} catch (Exception e){
	    e.printStackTrace();
	}
    }

    public void drawBlue(int u, int v){
	edgeColors[u][v] = Color.BLUE;

	try{
	    repaint();
		
	    Thread.sleep(SLEEP);
	} catch (Exception e){
	    e.printStackTrace();
	}
    }


    public void storeMoves(ArrayList<TripletIIB> triplets){
	for(TripletIIB t:triplets){
	    this.moves.add(t);
	}
    }

    public void drawMoves(){
	for(TripletIIB t:this.moves){
	    theModel.ifNotAnimatingThenWait();

	    if(t.getC() == Color.GREEN){
		drawGreen(t.getA(), t.getB());
	    } else if(t.getC() == Color.RED){
		drawRed(t.getA(), t.getB());
	    } else if(t.getC() == Color.BLUE){
		if(prevColor != Color.BLUE){
		    applyDrawing();
		}
		
		drawBlue(t.getA(), t.getB());		
	    }
	    
	    prevColor = t.getC();

	    theModel.ifNotAnimatingThenWait();
	}
		    
	applyDrawing();
    }

    public void drawMovesNow(){
	for(TripletIIB t:this.moves){
	    if(t.getC() == Color.BLACK){
		drawBlack(t.getA(), t.getB());
	    }
	}
		    
	moves = new ArrayList<TripletIIB>();

	try{
	    repaint();
		
	    Thread.sleep(SLEEP_BETWEEN_STEPS);
	} catch (Exception e){
	    e.printStackTrace();
	}
    }

    public Color drawColor(int u, int v){
	Color drawColor = null;

	Color invisibleColor = new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), (int)(0*255));
		
	Color grayColor = new Color(Color.LIGHT_GRAY.getRed(), Color.LIGHT_GRAY.getGreen(), Color.LIGHT_GRAY.getBlue(), (int)(alpha*255));
		
	//black
	Color prevColor = new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), (int)(alpha*255));

	Color blueColor = new Color(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue(), (int)(alpha*255));
	Color redColor = new Color(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue(), (int)(alpha*255));
	Color greenColor = new Color(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue(), (int)(alpha*255));

	greenColor = greenColor.darker();
	
	if(edgeColors[u][v] == Color.LIGHT_GRAY){
	    return grayColor;
	}

	if(edgeColors[u][v] == Color.BLACK){
	    return prevColor;
	}

	if(edgeColors[u][v] == Color.BLUE){
	    return blueColor;
	}

	if(edgeColors[u][v] == Color.RED){
	    return redColor;
	}

	if(edgeColors[u][v] == Color.GREEN){
	    return greenColor;
	}

	if(edgeColors[u][v] == Color.WHITE){
	    return invisibleColor;
	}
	
	return drawColor;
    }

    public void applyDrawing(){
	for(int i=0; i<20; i++){
	    for(int j=0; j<20; j++){
		if(edgeColors[i][j] == Color.GREEN){
		    edgeColors[i][j] = Color.BLACK;
		}

		if(edgeColors[i][j] == Color.RED){
		    edgeColors[i][j] = Color.LIGHT_GRAY;
		}
	    }
	}

	try{
	    repaint();
		
	    Thread.sleep(SLEEP);
	} catch (Exception e){
	    e.printStackTrace();
	}
    }

    public void finishDrawing(boolean alsoDeleteUnmatched){
	if(alsoDeleteUnmatched){
	    for(int i=0; i<20; i++){
		for(int j=0; j<20; j++){
		    if(j<10 || i>9 || edgeColors[i][j] == Color.LIGHT_GRAY){
			edgeColors[i][j] = Color.WHITE;
		    }
		}
	    }
	}
	int u = 0;
	int v = 0;
	
	for(int i =0; i<9; i++){
	    // from S to vars
	    u = 0;
	    v = i + 1;
	    edgeColors[u][v] = Color.WHITE;
	}

	for(int i =0; i<9; i++){
	    // from values to T
	    u = i + 9 + 1;
	    v = 19;
	    edgeColors[u][v] = Color.WHITE;
	}
	
	try{
	    repaint();
		
	    Thread.sleep(SLEEP);
	} catch (Exception e){
	    e.printStackTrace();
	}
    }

    public void makeAllEdgesGray(){
	// make all edges gray
	// i.e. remove black edges representing maximum matching
	// so we can continue with tarjan
	
	for(int i=0; i<20; i++){
	    for(int j=0; j<20; j++){
		if(edgeColors[i][j] == Color.BLACK){
		    edgeColors[i][j] = Color.LIGHT_GRAY;
		}
	    }
	}

	repaint();
    }

    public Timer getTimerSolve(){
	Timer timer1 = new Timer(10, null);
	timer1.setInitialDelay(100);
        timer1.addActionListener(this);
	timer1.setCoalesce(false);

	timerSolve = timer1;
	
	return timerSolve;
    }
}
