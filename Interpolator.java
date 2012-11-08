// all-purpose interpolation class that maps a linear interpolated value t <- [0,1] to a non-linear function
public abstract class Interpolator
{
	static PolynomialInterpolator POLYNOMIAL(double grade)
	{
		return new PolynomialInterpolator(grade);
	}
	
	static Interpolator LINEAR = new Interpolator()
	{
		@Override
		public double map(double in) {
			return in;
		}
	};
	
	static Interpolator QUADRATIC = new Interpolator()
	{
		@Override
		public double map(double in) {
			return in*in;
		}
	};
	
	static Interpolator CUBIC_FIT = new Interpolator()
	{
		@Override
		public double map(double in) {
			return in*in*(-2*in + 3);
		}
	};
	
	public abstract double map(double in);
}
