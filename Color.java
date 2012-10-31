import javax.media.opengl.GL;
import javax.media.opengl.GL2;

// simple class representing colors
public class Color 
{
	public static final Color WHITE = new Color(1, 1, 1);
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color RED = new Color(1, 0, 0);
	public static final Color GREEN = new Color(0, 1, 0);
	public static final Color BLUE = new Color(0, 0, 1);
	public static final Color DARKGRAY = new Color(0.25f, 0.25f, 0.25f);
	public static final Color LIGHTGRAY = new Color(0.75f, 0.75f, 0.75f);
	
	public float red;
	public float green;
	public float blue;
	public float alpha;
	
	public Color(float red, float green, float blue, float alpha)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public Color(float red, float green, float blue)
	{
		this(red, green, blue, 1.0f);
	}
	
	public Color(float gray)
	{
		this(gray, gray, gray, 1.0f);
	}
	
	public Color(Color other)
	{
		this.red = other.red;
		this.green = other.green;
		this.blue = other.blue;
		this.alpha = other.alpha;
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
