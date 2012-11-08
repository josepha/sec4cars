// container class for the visualizations
// will contain UI components to control output behavior 
// TODO: extend
import java.awt.BorderLayout;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JPanel;

import com.jogamp.opengl.util.FPSAnimator;

// TODO implement stuff belonging to the view
public class ParameterView extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private GLCanvas glCanvas;
	
	public void setVisualization(ParameterVisualization visualization)
	{
		glCanvas.addGLEventListener(visualization);
	}

	public ParameterView(String name) 
	{
		super();
		GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        glCanvas = new GLCanvas( glcapabilities );
        
        // TODO: remove
        FPSAnimator animator = new FPSAnimator(glCanvas, 60);
        animator.start();
        add(glCanvas, BorderLayout.CENTER);
	}	
}
