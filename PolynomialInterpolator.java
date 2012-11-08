// interpolates t -> pow(t, a) for a <- R
public class PolynomialInterpolator extends Interpolator
{
	protected PolynomialInterpolator(double grade)
	{
		this.grade = grade;
	}
	
	private double grade;
	@Override
	public double map(double in) 
	{
		return Math.pow(in, grade);
	}
}