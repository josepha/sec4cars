import java.awt.Color;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

// simple class representing colors
public class GraphColor 
{
	public static final GraphColor WHITE = new GraphColor(1, 1, 1);
	public static final GraphColor BLACK = new GraphColor(0, 0, 0);
	public static final GraphColor RED = new GraphColor(1, 0, 0);
	public static final GraphColor GREEN = new GraphColor(0, 1, 0);
	public static final GraphColor BLUE = new GraphColor(0, 0, 1);
	public static final GraphColor YELLOW = new GraphColor(1, 1, 0);
	public static final GraphColor ORANGE = new GraphColor(1, 0.5f, 0);
	public static final GraphColor DARK_GRAY = new GraphColor(0.25f, 0.25f, 0.25f);
	public static final GraphColor LIGHT_GRAY = new GraphColor(0.75f, 0.75f, 0.75f);
	
	public float red;
	public float green;
	public float blue;
	public float alpha;
	
	// return random color
	public static GraphColor random()
	{
		return new GraphColor((float) Math.random(), (float) Math.random(), (float) Math.random());
	}
	
	public GraphColor(float red, float green, float blue, float alpha)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public GraphColor(float red, float green, float blue)
	{
		this(red, green, blue, 1.0f);
	}
	
	
	public GraphColor(Color color)
	{
		this(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
	}
	
	public GraphColor(float gray)
	{
		this(gray, gray, gray, 1.0f);
	}
	
	public GraphColor(GraphColor other)
	{
		this.red = other.red;
		this.green = other.green;
		this.blue = other.blue;
		this.alpha = other.alpha;
	}
	
	public Color toJColor()
	{
		return new Color(red, green, blue, alpha);
	}
	
	public GraphColor interpolate(GraphColor other, Interpolator interpol, double t)
	{
		assert(0 <= t && t <= 1);
		float mappedT = (float) interpol.map(t);
		return new GraphColor((1-mappedT) * this.red + mappedT * other.red,
						 (1-mappedT) * this.green + mappedT * other.green,
						 (1-mappedT) * this.blue + mappedT * other.blue,
						 (1-mappedT) * this.alpha + mappedT * other.alpha);
	}
	
	public GraphColor interpolate(GraphColor other, double t)
	{
		return interpolate(other, Interpolator.LINEAR, t);

	}
	
	public void setClearColor(GL gl)
	{
		gl.glClearColor(red, green, blue, alpha);
	}
	
	public void setDrawColor(GL2 gl)
	{
		gl.glColor4f(red, green, blue, alpha);
	}

}
