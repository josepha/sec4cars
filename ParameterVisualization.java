import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public abstract class ParameterVisualization implements GLEventListener
{
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

	@Override
	public abstract void display(GLAutoDrawable drawable);

	@Override
	public abstract void reshape(GLAutoDrawable drawable, int x, int y, int width, int height);
	
	// update method that is called every time a new value is added
	public abstract void update(double newValue, double deltaTime);
}
