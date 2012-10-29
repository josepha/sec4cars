import java.util.Iterator;
import java.util.LinkedList;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class ParameterGraph extends ParameterVisualization 
{
	private float totalTime;
	// minimum value displayed
	private float minValue;
	// maximum value displayed
	private float maxValue;
	// visible value range
	private float valueRange;
	// total time frame displayed by graph in seconds
	private float timeFrame;
	
	private LinkedList<Vec2> points;

	public ParameterGraph(String name, float minValue, float maxValue, float timeFrame)
	{
		// maxValue has to be larger than minValue
		assert(maxValue >= minValue);
		
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.valueRange = maxValue - minValue;
		this.timeFrame = timeFrame;
		
		totalTime = 0;
		points = new LinkedList<Vec2>();
	}

	@Override
	public void init(GLAutoDrawable drawable) 
	{
	}

	@Override
	public void dispose(GLAutoDrawable drawable) 
	{
	}

	@Override
	public void display(GLAutoDrawable drawable) 
	{
		GL2 gl = drawable.getGL().getGL2();
		float totalX = 0;
		Iterator<Vec2> iter = points.iterator();
		
		Vec2 point;
		gl.glBegin(GL.GL_LINE_STRIP);
		while(iter.hasNext())
		{
			point = iter.next();
			gl.glVertex2f(totalX, point.y);
			totalX += point.x;
		}
		gl.glEnd();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
	{
	}

	@Override
	public void update(float newValue, float deltaTime) {
		
		float mappedValue = (newValue - minValue) / valueRange;
		Vec2 newPoint = new Vec2(deltaTime, mappedValue);
		points.add(newPoint);
		// update total time
		totalTime += deltaTime;
		// remove oldest points when total time is reached
		while(totalTime > timeFrame)
		{
			Vec2 head = points.poll();
			totalTime -= head.x;
		}
	}

}
