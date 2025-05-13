import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SudokuCell extends JPanel implements ActionListener{

    int i, j;  // the place on the grid
    int value; // the value, if known
    String valuesText = new String();
    public SudokuModel theModel;

    private static final int SIZE = 50;
    protected static final String NEWLINE = System.getProperty("line.separator");
    
    public float DELTA = 0.01f;
    public ArrayList<Timer> fadeOutTimers = new ArrayList<Timer>();
    public ArrayList<Timer> fadeInTimers = new ArrayList<Timer>();
    private float alpha = 1f;
    private float fadeOutMinimum = 0f;
    
    public JLabel valuesLabel = new JLabel();
    private boolean noBoldBorder = false;
    public boolean notInTheGrid = false;
    public Color fontColor = Color.BLACK;

    public int fontSize = 20;
    public int fontSizeLarge = 20;
    public int fontSizeSmall = 14;
    public int paddingTop = 3;
    public int paddingLeft = 4;
    public Font theFont = new Font("Serif", Font.PLAIN, fontSize);
        
    public SudokuCell(int i, int j){
	super();

        this.setOpaque(false);

	valuesLabel.setFont(theFont);
	valuesLabel.setForeground(fontColor);
	valuesLabel.setSize(new Dimension(SIZE, SIZE));
	
	setLayout(new GridBagLayout());
	setPreferredSize(new Dimension(50, 50));

	this.i = i;
	this.j = j;

	String OS = System.getProperty("os.name");
	
	if(OS.startsWith("Windows")){
	    //Windows
	    fontSizeLarge = 20;
	    fontSizeSmall = 11;
	    //paddingTop = 0;
	    //paddingLeft = 4;
	} else if(OS.startsWith("Mac")){
	    //Mac
	    fontSizeLarge = 20;
	    fontSizeSmall = 14;
	    //paddingTop = 3;
	    //paddingLeft = 4;
	} else {
	    //Linux?
	    fontSizeLarge = 20;
	    fontSizeSmall = 12;
	}
	
    }

    public void setValuesLabel(String valuesText){
		    valuesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		    valuesLabel.setVerticalAlignment(SwingConstants.CENTER);
		    
		    if(valuesText.length() <= 1){
			// Single digit

			if(valuesText.compareTo("0") != 0){
			    valuesLabel.setText(valuesText);
			} else {
			    valuesLabel.setText(" ");
			}

			fontSize = fontSizeLarge;
			valuesLabel.setFont(new Font("Serif", Font.PLAIN, fontSize));
		    } else {
			// More choices available
			valuesLabel.setText(valuesText);
			
			fontSize = fontSizeSmall;
			valuesLabel.setFont(new Font("Serif", Font.PLAIN, fontSize));
		    }
    }

    public String formatPossibleValues(){
	// first refresh the values
	ArrayList<Integer> possibleValues = theModel.getPossibleValues(i, j);
	
	if(possibleValues.size() == 1){
	    valuesText = Integer.toString(possibleValues.get(0));
	    repaint();
	    //return;
	    return Integer.toString(possibleValues.get(0));
	}

	StringBuilder sb = new StringBuilder();
	for(int i=1; i<=9; i++){
	    if(possibleValues.contains(i)){
		if(i != 3 && i != 6 && i !=9){
		    sb.append(i + " ");
		} else {
		    sb.append(i);
		}
	    } else {
		sb.append(" ");
		sb.append(" ");
	    }
	    
	    if(i%3 == 0){
		sb.append(NEWLINE);
	    }
	}

	valuesText = sb.toString();
	repaint();
	
	return sb.toString();
    }

    private void drawString(Graphics g, String text, int x, int y) {
	int whitespaces = 0;

	if(text.matches("\\d")){
	    y += g.getFontMetrics().getHeight();
	    g.drawString(text, x, y);
	    return;
	}
	
	for (String line : text.split(NEWLINE)){
	    y += g.getFontMetrics().getHeight();
	    
	    for(String digit: line.split("\\s+")){
		if(!digit.matches("^-?\\d+$")){
		    continue;
		}
		
		if(Integer.parseInt(digit) % 3 == 1){
		    //1, 4, 7
		    whitespaces = 0;
		} else if(Integer.parseInt(digit) % 3 == 2){
		    //2, 5, 8
		    whitespaces = 3;
		    } else if(Integer.parseInt(digit) % 3 == 0){
		    //3, 6, 9
		    whitespaces = 6;
		}

		int x_offset = g.getFontMetrics().stringWidth(" ") * whitespaces;
		// we are printing individual digits
		// as drawString doesn't handle two or more consecutive
		// whitespaces
		g.drawString(digit, x + x_offset, y);
	    }
	}
    }
    
    @Override
    protected void paintComponent(Graphics grphcs) {
	super.paintComponent(grphcs);
	
	Graphics2D g2d = (Graphics2D) grphcs;
	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	
	Color prevColor = new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), (int)(alpha*255));
	
	g2d.setColor(prevColor);

	int top = 2;
	int left = 2;
	int bottom = 2;
	int right = 2;

	if(noBoldBorder == false){
	    // sub-grids border
	    if(this.i % 3 == 0){top = 4;}
	    if(this.j % 3 == 0){left = 4;}
	    if(this.i % 3 == 2){bottom = 4;}
	    if(this.j % 3 == 2){right = 4;}

	    // outside border
	    if(this.i % 9 == 0){top = 8;}
	    if(this.j % 9 == 0){left = 8;}
	    if(this.i % 9 == 8){bottom = 8;}
	    if(this.j % 9 == 8){right = 8;}
	}	 

	int x_offset = 0;
	int y_offset = 0;
	
	String OS = System.getProperty("os.name");

	if(OS.startsWith("Windows")){
	    g2d.setStroke(new BasicStroke(top));
	    g2d.drawLine(0, 0, 49, 0);
	    g2d.setStroke(new BasicStroke(left));
	    g2d.drawLine(0, 0, 0, 49);
	    g2d.setStroke(new BasicStroke(bottom));
	    g2d.drawLine(0, 49, 49, 49);
	    g2d.setStroke(new BasicStroke(right));
	    g2d.drawLine(50, 0, 50, 50);
	    
	    x_offset = 5;
	    y_offset = 15;
	} else if(OS.startsWith("Mac")){
	    //Mac
	    g2d.setStroke(new BasicStroke(top));
	    g2d.drawLine(0, 0, 50, 0);
	    g2d.setStroke(new BasicStroke(left));
	    g2d.drawLine(0, 0, 0, 50);
	    g2d.setStroke(new BasicStroke(bottom));
	    g2d.drawLine(0, 50, 50, 50);
	    g2d.setStroke(new BasicStroke(right));
	    g2d.drawLine(50, 0, 50, 50);
	} else {
	    //Linux?
	    g2d.setStroke(new BasicStroke(top));
	    g2d.drawLine(0, 0, 50, 0);
	    g2d.setStroke(new BasicStroke(left));
	    g2d.drawLine(0, 0, 0, 50);
	    g2d.setStroke(new BasicStroke(bottom));
	    g2d.drawLine(0, 50, 50, 50);
	    g2d.setStroke(new BasicStroke(right));
	    g2d.drawLine(50, 0, 50, 50);

	    x_offset = 0;
	    y_offset = 5;
	}

	int fontSize = fontSizeSmall;
        int stringWidth = g2d.getFontMetrics().stringWidth("1 2 ") - x_offset;
	int stringHeight = g2d.getFontMetrics().getHeight() * valuesText.split(NEWLINE).length - 2;
	int stringHeightNoBorder = 4;
	
	if(valuesText.length() <= 1){
	    fontSize = fontSizeLarge;
	    stringWidth = g2d.getFontMetrics().stringWidth("9")/2;
	    stringHeight += 8 + y_offset;
	    stringHeightNoBorder = 6;
	}
	
	Font f = new Font("Serif", Font.PLAIN, fontSize);
	g2d.setFont(f);
	g2d.setColor(fontColor);

        int text_xpos = (50 - right - stringWidth)/2;
	int text_ypos = (50 - bottom - stringHeight)/2;

	if(noBoldBorder == true){
	    text_xpos = (50 - stringWidth - 6)/2;
	    text_ypos = (50 - stringHeight - stringHeightNoBorder)/2; 
	}

	drawString(g2d, valuesText, text_xpos, text_ypos);
    }

    @Override
    public Dimension getPreferredSize() {
	return new Dimension(50, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(theModel.isAnimationPaused){
	    return;
	}
		
	Timer thisTimer = ((Timer)e.getSource());
	
	theModel.stopAllTimersOnDiffLevelComparedTo(thisTimer);

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
	fadeOutMinimum = 0f;
    }

    public void fadeOutALittle(){
	fadeOutMinimum = 0.30f;
    }
    
    public void fadeIn(){
    }

    public void setModel(SudokuModel model){
	this.theModel = model;
    }

    public void setNoBoldBorder(){
	noBoldBorder = true;
    }

    public void setNotInTheGrid(){
	// this means this cell is in the right of the screen
	// use this in order to assign new click actions
	// in sudoku cell listener
	notInTheGrid = true;
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

    public void setFontColor(Color color){
	this.fontColor = color;
	repaint();
    }
}
