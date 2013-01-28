import java.util.Iterator;
import java.util.LinkedList;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

// plot of 2D point data
// color mapping currently disabled
// TODO: unse?
public class GraphVisualization extends AbstractVisualization {
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
	private final double borderOffset = 0.1;
	// utility to calculate time between frames
	// TODO: remove later on
	private FrameTimer timer;
	// points of the graph mapped to fit in value range
	private LinkedList<Vec2> points;
	// mapped colors for points
	// private LinkedList<GraphColor> colors;
	private double scaleOffset = 0.02;

	private GL2 gl;

	// private ColorMapper mapper;

	private GraphColor graphColor;
	private GraphColor bgColor;
	private GraphColor axisColor;

	private GLUT glut;

	private Iterator<Vec2> iterPoints;

	// constructors
	// public ParameterGraph(double minValue, double maxValue, double
	// optimalValue, double timeFrame, GraphColor bgColor, GraphColor axisColor,
	// GraphColor goodColor, GraphColor badColor)
	public GraphVisualization(double minValue, double maxValue,
			double optimalValue, double timeFrame, GraphColor bgColor,
			GraphColor axisColor, GraphColor graphColor) {
		// maxValue has to be larger than minValue
		assert (maxValue >= minValue && optimalValue >= minValue && optimalValue <= maxValue);

		this.minValue = minValue;
		this.maxValue = maxValue;
		this.valueRange = maxValue - minValue;
		this.timeFrame = timeFrame;

		this.bgColor = bgColor;
		this.axisColor = axisColor;
		this.graphColor = graphColor;

		// this.mapper = new ColorMapper(optimalValue, minValue, maxValue,
		// Interpolator.CUBIC_FIT, goodColor, badColor);

		totalTime = 0;
		timer = new FrameTimer();
		points = new LinkedList<Vec2>();
		// colors = new LinkedList<GraphColor>();

	}

	// default colors
	public GraphVisualization(double minValue, double maxValue,
			double optimalValue, double timeFrame) {
		this(minValue, maxValue, optimalValue, timeFrame, GraphColor.BLACK,
				GraphColor.DARK_GRAY, GraphColor.random());
	}

	// centered around y-axis
	public GraphVisualization(double range, double optimalValue,
			double timeFrame, GLUT glut_) {
		this(-range / 2, range / 2, optimalValue, timeFrame);
		this.glut = glut_;

	}

	// setters
	public void setMinValue(double minValue, int i) {
		this.minValue = minValue * (1 + scaleOffset);
		updateInternals();
	}

	public void setMaxValue(double maxValue, int i) {
		this.maxValue = maxValue;
		updateInternals();
	}

	// set graph color
	public void setAxisColor(GraphColor color) {
		this.axisColor = color;
	}

	// set bg color
	public void setBGColor(GraphColor color) {
		this.bgColor = color;
		bgColor.setClearColor(gl);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();
		bgColor.setClearColor(gl);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	// render method
	@Override
	public void display(GLAutoDrawable drawable) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		drawAxis();
		drawGraph(gl);
		drawText();
	}

	private void drawText() {

		gl.glRasterPos2f(0.75f, 0.92f);
		graphColor.setDrawColor(gl);
		if (!points.isEmpty()) {
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, points.getFirst().y
					+ "");
		}
		gl.glRasterPos2f(-0.98f, 0.92f);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, maxValue + "");
		gl.glRasterPos2f(-0.98f, -0.97f);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, minValue + "");
	}

	// draw graph as line strip
	private void drawGraph(GL2 gl) {
		double totalX = 1.0;
		iterPoints = points.iterator();
		// Iterator<GraphColor> iterColors = colors.iterator();

		graphColor.setDrawColor(gl);
		Vec2 point;

		gl.glLineWidth(1.0f);
		gl.glBegin(GL.GL_LINE_STRIP);
		while (iterPoints.hasNext()) {
			point = iterPoints.next();
			// iterColors.next().setDrawColor(gl);
			// values are transformed according to value range
			// viewport is defined from -1 to 1 in both x and y direction so
			// values need to be transformed again
			double mappedValue = (point.y - minValue) / valueRange;
			// introduce an offset to avoid graph touching veiwport borders
			mappedValue = mappedValue * (1 - 2 * scaleOffset) + scaleOffset;
			gl.glVertex2d(2 * totalX - 1, 2 * mappedValue - 1);
			totalX -= point.x / timeFrame;
		}
		gl.glEnd();

		totalX = 1.0;
		gl.glPointSize(3.0f);
		iterPoints = points.iterator();
		gl.glBegin(GL.GL_POINTS);
		while (iterPoints.hasNext()) {
			point = iterPoints.next();
			// iterColors.next().setDrawColor(gl);
			// values are transformed according to value range
			// viewport is defined from -1 to 1 in both x and y direction so
			// values need to be transformed again
			double mappedValue = (point.y - minValue) / valueRange;
			// introduce an offset to avoid graph touching veiwport borders
			mappedValue = mappedValue * (1 - 2 * scaleOffset) + scaleOffset;
			gl.glVertex2d(2 * totalX - 1, 2 * mappedValue - 1);
			totalX -= point.x / timeFrame;
		}
		gl.glEnd();

	}

	// draw axis and value range
	private void drawAxis() {
		// set color
		axisColor.setDrawColor(gl);
		gl.glLineWidth(1.0f);
		double zeroY = -2 * minValue / valueRange - 1;
		// TODO: improve speed
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2d(-1, zeroY);
		gl.glVertex2d(1, zeroY);
		gl.glEnd();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		// TODO: implement
	}

	public void addValue(float newValue) {
		timer.update();

		double delta = timer.deltaTime();
		Vec2 newPoint = new Vec2(delta, newValue);
		points.addFirst(newPoint);

		// update range to contain new value
		if (autoAdjust) {
			if (newValue < minValue)
				setMinValue(newValue, 0);
			if (newValue > maxValue)
				setMaxValue(newValue, 0);
		}

		// update total time
		totalTime += delta;
		// remove oldest points when total time is reached
		while (totalTime > timeFrame + borderOffset) {
			Vec2 tail = points.removeLast();
			totalTime -= tail.x;
			// colors.removeLast();
		}
	}

	@Override
	public void setValueRange(double minValue, double maxValue, int i) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		updateInternals();
	}

	// helper function that updates valueRange
	// TODO: future members that need to be updated go here
	private void updateInternals() {
		this.valueRange = maxValue - minValue;
		// mapper.updateRange(minValue, maxValue);
		// update fields of containing view GUI
		if (containingView != null)
			containingView.updateRangeFields();
	}

	@Override
	public double getMinValue(int id) {
		return minValue;
	}

	@Override
	public double getMaxValue(int id) {
		return maxValue;
	}

	@Override
	public GraphColor getGraphColor(int id) {
		return graphColor;
	}

	@Override
	public void setGraphColor(GraphColor color, int id) {
		this.graphColor = color;
	}

	@Override
	public double getTimeFrame() {
		return timeFrame;
	}

	@Override
	public void setTimeFrame(double range) {
		this.timeFrame = range;
	}

}
