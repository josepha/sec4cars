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
	public abstract double getMinValue();
	public abstract double getMaxValue();
	public abstract GraphColor getGraphColor();
	public abstract double getTimeFrame();
	
	// setters
	public abstract void setMinValue(double minValue);
	public abstract void setMaxValue(double maxValue);
	public abstract void setValueRange(double minValue, double maxValue);
	public abstract void setGraphColor(GraphColor color);
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
	
	// update method that is called every time a new value is added
	public abstract void update(double newValue, double deltaTime);
}
