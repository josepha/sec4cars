import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

/** 
 * <p>
 * Abstrakte Basisklasse aller Visualisierungen.
 * </p>
 * 
 * <p>
 * Zu jeder View gehört eine Visualisierung
 * stellt 2D-Daten graphisch dar. Sie kann dafür mit der Methode update neue Daten aufnehmen und rendert diese
 * </p>
 * 
 */

public abstract class AbstractVisualization implements GLEventListener
{
	// view holding this visualization
	protected AbstractView containingView = null;
	protected boolean autoAdjust = true;
	
	public abstract void addValue(float f);
	public abstract void addValue(float f, int i);
	
	// enable or disable range autoadjust
	public void setAutoAdjust(boolean adjust)
	{
		this.autoAdjust = adjust;
	}
	
	// set containing view
	public void setContainingView(AbstractView view)
	{
		containingView = view;
	}
	
	// methods inherited by GLEventListener interface
	@Override
	public abstract void init(GLAutoDrawable drawable);
	
	// getters
	public abstract double getMinValue(int id);
	public abstract double getMaxValue(int id);
	public abstract GraphColor getGraphColor(int id);
	public abstract double getTimeFrame();
	
	// setters
	public abstract void setMinValue(double minValue, int id);
	public abstract void setMaxValue(double maxValue, int id);
	public abstract void setValueRange(double minValue, double maxValue, int id);
	public abstract void setGraphColor(GraphColor color, int id);
	public abstract void setTimeFrame(double range);

	@Override
	public abstract void dispose(GLAutoDrawable drawable);

	/**
	 * renders visualisation 
	 * called as often as possible
	 * @param  drawable the drawable onto which is rendered
	 */
	
	@Override
	public abstract void display(GLAutoDrawable drawable);
	
	@Override
	public abstract void reshape(GLAutoDrawable drawable, int x, int y, int width, int height);
	

}
