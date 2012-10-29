
public class Vec2{

	public float x, y;
	
	public Vec2() 
	{
	}

	public Vec2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Vec2(Vec2 other) {
		set(other);
	}
	
	public Vec2 set(float x, float y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	public Vec2 set(Vec2 other)
	{
		x = other.x;
		y = other.y;
		return this;
	}

	public Vec2 substract(Vec2 other)
	{
		x -= other.x;
		y -= other.y;
		return this;
	}
	
	public Vec2 add(Vec2 other)
	{
		x += other.x;
		y += other.y;
		return this;
	}

	public Vec2 normalize()
	{
		float len = length();
		if (len != 0) 
		{
			x /= len;
			y /= len;
		}
		return this;
	}

	public float dot(Vec2 v) 
	{
		return x * v.x + y * v.y;
	}

	public Vec2 scale(float factor)
	{
		x *= factor;
		y *= factor;
		return this;
	}
	
	public float length() 
	{
		return (float) Math.sqrt(x*x + y*y);
	}

	public float lengthSquared () 
	{
		return x*x + y*y;
	}
	
	public float distance(Vec2 other)
	{
		float distX = x - other.x;
		float distY = y - other.y;
		return (float) Math.sqrt(distX*distX + distY*distY);
	}
	
	public float distance(float x, float y)
	{
		x -= this.x;
		y -= this.y;
		return (float) Math.sqrt(x*x + y*y);
	}

	public String toString () 
	{
		return "(" + x + "," + y + ")";
	}
}
