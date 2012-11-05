import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public abstract class ParameterVisualization implements GLEventListener
{
	// methods inherited by GLEventListener interface
	@Override
	public abstract void init(GLAutoDrawable drawable);

	@Override
	public abstract void dispose(GLAutoDrawable drawable);

	@Override
	public abstract void display(GLAutoDrawable drawable);

	@Override
	public abstract void reshape(GLAutoDrawable drawable, int x, int y, int width, int height);
	
	// update method that is called every time a new value is added
	public abstract void update(float newValue, float deltaTime);
}
