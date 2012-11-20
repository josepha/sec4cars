import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;


public class ParameterQuad extends ParameterVisualization
{
	// minimum value displayed
	private double minValue;
	// maximum value displayed
	private double maxValue;
	// center of optimal range
	private double optimalValue;
	// visible value range
	private double valueRange;
	private double currentValue;
	private double valueScale;
	// utility to calculate time between frames
	// TODO: move this somewhere it should rather be
	private FrameTimer timer;

	private GL2 gl;
	private GraphColor bgColor;
	// current display color
	private GraphColor currentColor;
	// color mapper
	private ColorMapper mapper;
	
	public ParameterQuad(double minValue, double maxValue, double optimalValue, GraphColor bgColor, GraphColor goodValueColor, GraphColor badValueColor)
	{
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.valueRange = maxValue - minValue;
		this.optimalValue = optimalValue;
		this.valueScale = Math.max(maxValue - optimalValue, optimalValue - minValue);
		
		// TODO: adjust interpolator
		this.mapper = new ColorMapper(optimalValue, minValue, maxValue, Interpolator.POLYNOMIAL(0.6));
		// start color: optimal
		this.currentColor = mapper.getInterpolatedColor(optimalValue);
		
		this.bgColor = bgColor;
		this.currentValue = minValue;
		timer = new FrameTimer();
	}
	
	public ParameterQuad(double minValue, double maxValue, double optimalValue)
	{
		this(minValue, maxValue, optimalValue, GraphColor.BLACK, GraphColor.GREEN, GraphColor.RED);
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

	@Override
	public void display(GLAutoDrawable drawable) 
	{
		// update has to be called repeatedly
		timer.update();
		this.update(Math.sin(timer.currentTime()), timer.deltaTime());
		
		// set interpolated display color
		currentColor.setDrawColor(gl);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glBegin(GL.GL_TRIANGLE_FAN);
			gl.glVertex2d(-0.1, 0);
			gl.glVertex2d( 0.1, 0);
			gl.glVertex2d( 0.1, 2*(currentValue-minValue)/valueRange - 1);
			gl.glVertex2d(-0.1, 2*(currentValue-minValue)/valueRange - 1);
		gl.glEnd();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
	{
		// TODO: implement
	}

	@Override
	public void update(double newValue, double deltaTime) 
	{
		currentValue = newValue;
		currentColor = mapper.getInterpolatedColor(newValue);
	}

	@Override
	public void setMinValue(double minValue) 
	{
		this.minValue = minValue;
		updateInternals();
	}

	@Override
	public void setMaxValue(double maxValue) 
	{
		this.maxValue = maxValue;
		updateInternals();
	}

	@Override
	public void setValueRange(double minValue, double maxValue) 
	{
		this.minValue = minValue;
		this.maxValue = maxValue;
		updateInternals();
	}
	
	private void updateInternals()
	{
		this.valueRange = maxValue - minValue;
		mapper.updateRange(minValue, maxValue);
		this.valueScale = Math.max(maxValue - optimalValue, optimalValue - minValue);
	}

	@Override
	public double getMinValue() 
	{
		return minValue;
	}

	@Override
	public double getMaxValue() 
	{
		// TODO Auto-generated method stub
		return maxValue;
	}

	@Override
	public GraphColor getGraphColor() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGraphColor(GraphColor color) 
	{	
		// TODO Auto-generated method stub
	}

	@Override
	public double getTimeFrame() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTimeFrame(double range) 
	{
		// TODO Auto-generated method stub
	}

}
