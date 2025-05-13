import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

public class Circle extends JPanel implements ActionListener{
    public float DELTA = 0.01f;
    public ArrayList<Timer> fadeOutTimers = new ArrayList<Timer>();
    public ArrayList<Timer> fadeInTimers = new ArrayList<Timer>();
    private float alpha = 1f;
    private float fadeOutMinimum = 0f;

    JLabel textLabel;

    public SudokuModel theModel;

    public String value;

    public Color fontColor = Color.WHITE;
    public Color circleColor = Color.BLACK;

    public int paddingTop = 3;
    public int paddingLeft = 2;
    
    public Circle(SudokuModel theModel, String value){
	super();

	setSize(new Dimension(50, 50));
	
	this.theModel = theModel;
	
    this.setOpaque(false);
	
	this.value = value;

	String OS = System.getProperty("os.name");
	
	if(OS.startsWith("Windows")){
	    //Windows
	    paddingTop = 0;
	    paddingLeft = 2;
	} else {
	    //Mac
	    paddingTop = 6;
	    paddingLeft = 2;
	}
	
	textLabel = new JLabel();

	textLabel.setHorizontalAlignment(SwingConstants.CENTER);
	textLabel.setVerticalAlignment(SwingConstants.CENTER);

	textLabel.setSize(new Dimension(getWidth(), getHeight()));

	textLabel.setFont(new Font("Serif", Font.PLAIN, 30));
	textLabel.setForeground(fontColor);
	textLabel.setText("<html><div style=\"text-align: center; padding-top: " + paddingTop + "px; padding-left: " + paddingLeft + "px;\">"+ value +"</div></html>");
	add(textLabel);	
    }
    
    @Override
    protected void paintComponent(Graphics grphcs) {
	super.paintComponent(grphcs);
	
	Graphics2D g2d = (Graphics2D) grphcs;
	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

	if(theModel.theGrid.sudokuCells3Now[0] == null){
	    textLabel.setVisible(false);
	    return;
	} else {
	    textLabel.setVisible(true);
	}

	Color prevColor = new Color(circleColor.getRed(), circleColor.getGreen(), circleColor.getBlue(), (int)(alpha*255));
	
	g2d.setColor(prevColor);
	
	g2d.fillOval(0, 0, getWidth(), getHeight());

	prevColor = new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), (int)(alpha*255));
	g2d.setColor(prevColor);
	g2d.fillOval(0, 0, getWidth(), getHeight());

	prevColor = new Color(circleColor.getRed(), circleColor.getGreen(), circleColor.getBlue(), (int)(alpha*255));
	
	g2d.setColor(prevColor);
	
	g2d.fillOval(1, 1, getWidth()-2, getHeight()-2);
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

    public void setCircleColor(Color theColor){
	this.circleColor = theColor;

	repaint();
    }

    public void setFontColor(Color colorName){
	this.fontColor = colorName;
	textLabel.setForeground(fontColor);
    }
}
