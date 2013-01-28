// simple class representing 2-dimensional vectors
public class Vec2{

	public double x, y;
	public int id;
	
	public Vec2() 
	{
	}

	public Vec2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vec2(double x, double y, int id)
	{
		this.x=x;
		this.y=y;
		this.id=id;
	}

	public Vec2(Vec2 other) {
		set(other);
	}
	
	public int getID()
	{
		return id;
	}
	
	public Vec2 set(double x, double y)
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
		double len = length();
		if (len != 0) 
		{
			x /= len;
			y /= len;
		}
		return this;
	}

	public double dot(Vec2 v) 
	{
		return x * v.x + y * v.y;
	}

	public Vec2 scale(double factor)
	{
		x *= factor;
		y *= factor;
		return this;
	}
	
	public double length() 
	{
		return (double) Math.sqrt(x*x + y*y);
	}

	public double lengthSquared () 
	{
		return x*x + y*y;
	}
	
	public double distance(Vec2 other)
	{
		double distX = x - other.x;
		double distY = y - other.y;
		return (double) Math.sqrt(distX*distX + distY*distY);
	}
	
	public double distance(double x, double y)
	{
		x -= this.x;
		y -= this.y;
		return (double) Math.sqrt(x*x + y*y);
	}

	public String toString () 
	{
		return "(" + x + "," + y + ")";
	}
}
