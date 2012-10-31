// small class to constantly calculate time between frames
// TODO: implement as singleton since multiple instance could run asynchronous
// TODO: how to make this update itself?
public class FrameTimer
{
	// times in millisecons
	private long lastFrameTimeMillis;
	private long thisFrameTimeMillis;

	public FrameTimer()
	{
		lastFrameTimeMillis = System.currentTimeMillis();
	}
	
	public void update()
	{
		lastFrameTimeMillis = thisFrameTimeMillis;
		thisFrameTimeMillis = System.currentTimeMillis();
	}
	
	public long deltaTimeMillis()
	{
		return thisFrameTimeMillis - lastFrameTimeMillis;
	}
	
	public double deltaTime()
	{
		return (thisFrameTimeMillis - lastFrameTimeMillis) / 1000d;
	}
	
	public long currentTimeMillis()
	{
		return thisFrameTimeMillis;
	}
	
	public double currentTime()
	{
		return thisFrameTimeMillis / 1000d;
	}
	           
}
