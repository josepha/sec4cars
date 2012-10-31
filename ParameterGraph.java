import java.util.Iterator;
import java.util.LinkedList;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class ParameterGraph extends ParameterVisualization 
{
	private double totalTime;
	// minimum value displayed
	private double minValue;
	// maximum value displayed
	private double maxValue;
	// visible value range
	private double valueRange;
	// maximum time visible at once in seconds
	private double timeFrame;
	// small constant offset that is added to time frame to close the gap
	// between left frame border and beginning of the graph
	// TODO: adjust value
	private final double offset = 0.1;
	// utility to calculate time between frames
	// TODO: move this somewhere it should rather be
	private FrameTimer timer;
	// points of the graph mapped to fit in value range
	private LinkedList<Vec2> points;

	private GL2 gl;
	
	private Color bgColor;
	private Color graphColor;
	private Color axisColor;
	
	// constructors
	public ParameterGraph(double minValue, double maxValue, double timeFrame, Color bgColor, Color graphColor, Color axisColor)
	{
		// maxValue has to be larger than minValue
		assert(maxValue >= minValue);
		
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.valueRange = maxValue - minValue;
		this.timeFrame = timeFrame;
		
		this.bgColor = bgColor;
		this.graphColor = graphColor;
		this.axisColor = axisColor;
		
		totalTime = 0;
		timer = new FrameTimer();
		points = new LinkedList<Vec2>();
	}
	
	// default colors
	public ParameterGraph(double minValue, double maxValue, double timeFrame)
	{
		this(minValue, maxValue, timeFrame, Color.DARKGRAY, Color.WHITE, Color.GREEN);
	}
	
	// centered around origin
	public ParameterGraph(double range, double timeFrame)
	{
		this(-range/2, range/2, timeFrame);
	}
	
	// default timeFrame
	public ParameterGraph(double range)
	{
		this(range, 15);
	}
	
	// set graph color
	public void setGraphColor(Color color)
	{
		this.graphColor = color;
	}

	// set graph color
	public void setAxisColor(Color color)
	{
		this.axisColor = color;
	}
	
	// set bg color
	public void setBGColor(Color color)
	{
		this.bgColor = color;
		bgColor.setClearColor(gl);
	}
	
	@Override
	public void init(GLAutoDrawable drawable) 
	{
		gl = drawable.getGL().getGL2();
		bgColor.setClearColor(gl);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) 
	{
	}

	// render method
	@Override
	public void display(GLAutoDrawable drawable) 
	{
		// update has to be called repeatedly
		timer.update();
		this.update(Math.sin(timer.currentTime()), timer.deltaTime());
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		
		drawGraph(gl);
		drawAxis();	
	}
	
	// draw graph as line strip
	private void drawGraph(GL2 gl)
	{
		// set color
		graphColor.setDrawColor(gl);
		double totalX = 1.0;
		Iterator<Vec2> iter = points.iterator();
		
		Vec2 point;
		gl.glBegin(GL.GL_LINE_STRIP);
		while(iter.hasNext())
		{
			point = iter.next();
			// viewport is defined from -1 to 1 in both x and y direction so values need to be transformed again
			gl.glVertex2d(2*totalX-1, 2*point.y-1);
			totalX -= point.x / timeFrame;
		}
		gl.glEnd();
	}
	
	// draw axis and value range
	private void drawAxis()
	{
		// set color
		axisColor.setDrawColor(gl);
		double zeroY = -2*minValue/valueRange - 1;
		gl.glBegin(GL.GL_LINES);
			gl.glVertex2d(-1, zeroY);
			gl.glVertex2d( 1, zeroY);
		gl.glEnd();
	}

	//TODO: implement
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
	{
	}

	@Override
	public void update(double newValue, double deltaTime) {
		
		double mappedValue = (newValue - minValue) / valueRange;
		Vec2 newPoint = new Vec2(deltaTime, mappedValue);
		points.addFirst(newPoint);
		// update total time
		totalTime += deltaTime;
		// remove oldest points when total time is reached
		while(totalTime > timeFrame + offset)
		{
			Vec2 tail = points.removeLast();
			totalTime -= tail.x;
		}
	}

}
