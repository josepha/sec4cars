import java.awt.BorderLayout;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.test.junit.jogl.demos.es1.OneTriangle;

public class ParameterView extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private GLCanvas glCanvas;
	
	public void setVisualization(ParameterVisualization visualization)
	{
		glCanvas.addGLEventListener(visualization);
	}

	public ParameterView(String name) 
	{
		// call super constructor with parameters
		//super(name, true, true, true, true);
		super();
		GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        glCanvas = new GLCanvas( glcapabilities );
        
        glCanvas.addGLEventListener( new GLEventListener() {
            
            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
                OneTriangle.setup( glautodrawable.getGL().getGL2(), width, height );
            }
            
            @Override
            public void init( GLAutoDrawable glautodrawable ) {
            }
            
            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            }
            
            @Override
            public void display( GLAutoDrawable glautodrawable ) {
                OneTriangle.render( glautodrawable.getGL().getGL2(), glautodrawable.getWidth(), glautodrawable.getHeight() );
            }
        });
        this.getContentPane().add(glCanvas, BorderLayout.CENTER);
	}	
}
