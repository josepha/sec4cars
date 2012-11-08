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
	private Color bgColor;
	// current display color
	private Color currentColor;
	// color mapper
	private ColorMapper mapper;
	
	public ParameterQuad(double minValue, double maxValue, double optimalValue, Color bgColor, Color goodValueColor, Color badValueColor)
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
		this(minValue, maxValue, optimalValue, Color.BLACK, Color.GREEN, Color.RED);
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
			gl.glVertex2d( 0.1, 2*(currentValue-minValue) / valueRange-1);
			gl.glVertex2d(-0.1, 2*(currentValue-minValue) / valueRange-1);
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

}
