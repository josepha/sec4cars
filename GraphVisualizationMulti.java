import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;


// plot of 2D point data
// color mapping currently disabled
// TODO: unse?
public class GraphVisualizationMulti extends AbstractVisualization 
{
	private int anzahl; //number of graphs
	private double totalTime;
	// minimum value displayed
	private double[] minValue;
	// maximum value displayed
	private double[] maxValue;
	// visible value range
	private double[] valueRange;
	// maximum time visible at once in seconds
	private double timeFrame;
	// small constant offset that is added to time frame to close the gap
	// between left frame border and beginning of the graph
	// TODO: adjust value
	private final double borderOffset = 0.1;
	// utility to calculate time between frames
	// TODO: remove later on
	private FrameTimer[] timer;
	// points of the graph mapped to fit in value range
	private LinkedList<Vec2> points;
	private LinkedList<Vec2> buffer1;
	private LinkedList<Vec2> buffer2;
	private boolean buffer1active;
	// mapped colors for points
	//private LinkedList<GraphColor> colors;
	private double scaleOffset = 0.02;

	private GL2 gl;
	
	//private ColorMapper mapper;
	
	private GraphColor[] graphColor;
	private GraphColor bgColor;
	private GraphColor axisColor;
	
	private GLUT glut;
	
	private Iterator<Vec2> iterPoints;
	private int bufferCount;

	// constructors
	//public ParameterGraph(double minValue, double maxValue, double optimalValue, double timeFrame, GraphColor bgColor, GraphColor axisColor, GraphColor goodColor, GraphColor badColor)
	public GraphVisualizationMulti(double minValue, double maxValue, double optimalValue, double timeFrame, GraphColor bgColor, GraphColor axisColor, GraphColor graphColor, int anzahl)
	{
		// maxValue has to be larger than minValue
		assert(maxValue >= minValue && optimalValue >= minValue && optimalValue <= maxValue);
		
		this.anzahl=anzahl;
		
		this.minValue = new double[anzahl];
		this.maxValue = new double[anzahl];
		this.graphColor = new GraphColor[anzahl];
		this.valueRange = new double[anzahl];
		this.timer = new FrameTimer[anzahl];
		
		//this.minValue = minValue;
		//this.maxValue = maxValue;
		//this.valueRange = maxValue - minValue;
		this.timeFrame = timeFrame;
		
		this.bgColor = bgColor;
		this.axisColor = axisColor;
		//this.graphColor = graphColor;
		
		//this.mapper = new ColorMapper(optimalValue, minValue, maxValue, Interpolator.CUBIC_FIT, goodColor, badColor);
		
		totalTime = 0;
		buffer1active = true;
		bufferCount=0;
		
		points = new LinkedList<Vec2>();
		buffer1 = new LinkedList<Vec2>();
		buffer2 = new LinkedList<Vec2>();
		//colors = new LinkedList<GraphColor>();
		
		//System.out.println(anzahl+"");
		
		for(int i=0;i<anzahl;i++)
		{
			this.minValue[i] = minValue;
			this.maxValue[i] = maxValue;
			this.graphColor[i] = GraphColor.random();
			//System.out.println(i+" "+graphColor.toString());
			this.valueRange[i] = maxValue - minValue;
			this.timer[i] = new FrameTimer();
		}
	
	}
	
	// default colors
	public GraphVisualizationMulti(double minValue, double maxValue, double optimalValue, double timeFrame, int anzahl)
	{
		this(minValue, maxValue, optimalValue, timeFrame, GraphColor.BLACK, GraphColor.DARK_GRAY, GraphColor.random(), anzahl);
	}
	
	// centered around y-axis
	public GraphVisualizationMulti(int anzahl, double range, double optimalValue, double timeFrame, GLUT glut_)
	{		
		this(-range/2, range/2, optimalValue, timeFrame, anzahl);
		this.glut=glut_;				
	}

	
	// setters
	public void setMinValue(double minValue, int id)
	{
		this.minValue[id] = minValue * (1+scaleOffset);
		updateInternals(id);
	}
	
	public void setMaxValue(double maxValue, int id)
	{
		this.maxValue[id] = maxValue;
		updateInternals(id);
	}

	// set graph color
	public void setAxisColor(GraphColor color)
	{
		this.axisColor = color;
	}
	
	// set bg color
	public void setBGColor(GraphColor color)
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
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		
		//drawAxis();	
		drawGraph(gl);
		drawText();
	}
	
	private void drawText()
	{		
		
//		gl.glRasterPos2f(0.75f, 0.92f);
//		graphColor.setDrawColor(gl);		
//		if(!points.isEmpty())
//		{
//			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, points.getFirst().y+"");
//		}		
//		gl.glRasterPos2f(-0.98f, 0.92f);
//		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, maxValue+"");
//		gl.glRasterPos2f(-0.98f, -0.97f);
//		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, minValue+"");
	}
	
	// draw graph as line strip
	private void drawGraph(GL2 gl)
	{
		if(buffer1active)
		{
			while(!buffer2.isEmpty())
			{
				Vec2 point = buffer2.getLast();
				points.addFirst(point);
				buffer2.removeLast();				
			}
			//buffer1active=false;
		}
		else //buffer2 aktiv
		{
			while(!buffer1.isEmpty())
			{
				Vec2 point = buffer1.getLast();
				points.addFirst(point);
				buffer1.removeLast();				
			}
			//buffer1active=true;
		}
		
		for(int i=0;i<anzahl;i++)
		{
			double totalX = 1.0;
			iterPoints = points.iterator();		
			graphColor[i].setDrawColor(gl);	

			Vec2 point;
			
			gl.glLineWidth(1.0f);
			gl.glBegin(GL.GL_LINE_STRIP);
			while(iterPoints.hasNext())
			{
				//try
				//{
					point = iterPoints.next();
					if(point.getID()==i)
					{
						//iterColors.next().setDrawColor(gl);
						// values are transformed according to value range
						// viewport is defined from -1 to 1 in both x and y direction so values need to be transformed again
						double mappedValue = (point.y - minValue[i]) / valueRange[i];
						// introduce an offset to avoid graph touching veiwport borders
						mappedValue = mappedValue * (1 - 2*scaleOffset) + scaleOffset;
						gl.glVertex2d(2*totalX-1, 2*mappedValue-1);
						totalX -= point.x / timeFrame;
					}
				//}
				//catch(ConcurrentModificationException e2) {}
			}
			gl.glEnd();
			
			totalX = 1.0;
			gl.glPointSize(3.0f);
			iterPoints = points.iterator();
			gl.glBegin(GL.GL_POINTS);
			while(iterPoints.hasNext())
			{
				//try
				//{ 
					point = iterPoints.next();
				
					if(point.getID()==i)
					{
						//iterColors.next().setDrawColor(gl);
						// values are transformed according to value range
						// viewport is defined from -1 to 1 in both x and y direction so values need to be transformed again
						double mappedValue = (point.y - minValue[i]) / valueRange[i];
						// introduce an offset to avoid graph touching veiwport borders
						mappedValue = mappedValue * (1 - 2*scaleOffset) + scaleOffset;
						gl.glVertex2d(2*totalX-1, 2*mappedValue-1);
						totalX -= point.x / timeFrame;
					}
				//}				
				//catch(ConcurrentModificationException e) { }
			}
			gl.glEnd();
		}		
		
		// remove oldest points when total time is reached
		while(totalTime > timeFrame + borderOffset + timeFrame/10.0)				//TODO
		{
			Vec2 tail = points.removeLast();			
			if(tail.id==0 && tail.x<10)totalTime -= tail.x;
		}
	}
	
	// draw axis and value range
	private void drawAxis()
	{
		// set color
//		axisColor.setDrawColor(gl);
//		gl.glLineWidth(1.0f);
//		double zeroY = -2*minValue/valueRange - 1;
//		// TODO: improve speed
//		gl.glBegin(GL.GL_LINES);
//			gl.glVertex2d(-1, zeroY);
//			gl.glVertex2d( 1, zeroY);
//		gl.glEnd();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
	{
		//TODO: implement
	}


	
	public void addValue(float newValue, int id)
	{
		timer[id].update();

		double delta = timer[id].deltaTime();
		if(delta>10)delta=0.5;
		Vec2 newPoint = new Vec2(delta, newValue,id);
		
		
		//points.addFirst(newPoint);
		
		if(buffer1active)
		{			
			buffer1.addFirst(newPoint);
		}
		else
		{			
			buffer2.addFirst(newPoint);
		}
		
		bufferCount++;
		if(bufferCount>0) //switch buffer nach x werten
		{
			bufferCount=0;
			buffer1active=!buffer1active;
		}
		
		//System.out.println(bufferCount+" "+buffer1active);
		
		// update range to contain new value 
		if(autoAdjust)
		{
			if(newValue < minValue[id]) setMinValue(newValue,id);
			if(newValue > maxValue[id]) setMaxValue(newValue,id);
		}
		
		// update total time 
		//totalTime von nur einem wert nehmen, da graphen parallel laufen		
		
		if(id==0 && delta<10)totalTime += delta;
		
		//System.out.println(totalTime+" "+timeFrame);
		
		
	}

	@Override
	public void setValueRange(double minValue, double maxValue, int id) 
	{
		this.minValue[id] = minValue;
		this.maxValue[id] = maxValue;
		updateInternals(id);
	}
	
	// helper function that updates valueRange
	// TODO: future members that need to be updated go here
	private void updateInternals(int id)
	{
		this.valueRange[id] = maxValue[id] - minValue[id];
		//mapper.updateRange(minValue, maxValue);
		// update fields of containing view GUI
		if(containingView != null) containingView.updateRangeFields();
	}

	@Override
	public double getMinValue(int id) 
	{
		return minValue[id];
	}

	@Override
	public double getMaxValue(int id) 
	{
		return maxValue[id];
	}

	@Override
	public GraphColor getGraphColor(int id)
	{
		return graphColor[id];
	}

	@Override
	public void setGraphColor(GraphColor color, int id) 
	{
		this.graphColor[id] = color;
	}

	@Override
	public double getTimeFrame() 
	{
		return timeFrame;
	}

	@Override
	public void setTimeFrame(double range) 
	{
		this.timeFrame = range;
	}

	@Override
	public void addValue(float f) {
		// TODO Auto-generated method stub
		
	}

}
