// color mapping class for graphs
// TODO: extend functionality for ranged mappings etc.
public class ColorMapper 
{
	// value that is considered optimal
	private double optimalValue;
	// maximum distance to optimal color
	private double rangeScale;
	
	private GraphColor goodValueColor;
	private GraphColor badValueColor;
	
	private Interpolator interpolator;
	
	public ColorMapper(double optimalValue, double minValue, double maxValue, Interpolator interpolator, GraphColor goodValueColor, GraphColor badValueColor)
	{
		this.optimalValue = optimalValue;
		this.rangeScale = Math.max(maxValue - optimalValue, optimalValue - minValue);
		
		this.goodValueColor = goodValueColor;
		this.badValueColor = badValueColor;
		this.interpolator = interpolator;
	}
	
	public void updateRange(double minValue, double maxValue)
	{
		this.rangeScale = Math.max(maxValue - optimalValue, optimalValue - minValue);
	}
	
	public GraphColor getOptimalColor()
	{
		return goodValueColor;
	}
	
	public GraphColor getWorstColor()
	{
		return badValueColor;
	}
	
	public ColorMapper(double optimalValue, double minValue, double maxValue, Interpolator interpolator)
	{
		this(optimalValue, minValue, maxValue, interpolator, GraphColor.GREEN, GraphColor.RED);
	}
	
	public GraphColor getInterpolatedColor(double value)
	{
		float t = 1.0f - (float) (Math.abs(value - optimalValue) / rangeScale);
		// interpolate between good and bad color
		return badValueColor.interpolate(goodValueColor, interpolator, t);
	}
}
