package lib;


/**
 * A scientific vector implementation with accompanying mathematical methods
 * @author andre
 *
 */

public class Vector2D {
	private double x;
	private double y;
	
	/**
	 * Empty Constructor
	 */
	public Vector2D() {
		x = 0;
		y = 0;
	}
	
	/**
	 * Create a vct with two of the same value
	 * @param value the x and y of this vector
	 */
	public Vector2D(double value) {
		x = value;
		y = value;
	}
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Add a vector to this one
	 * @param vct
	 */
	public void add(Vector2D vct) {
		x += vct.getX();
		y += vct.getY();
	}
	
	/**
	 * Subtract a vector from this one
	 * @param vct
	 */
	public void subtract(Vector2D vct) {
		x -= vct.getX();
		y -= vct.getY();
	}
	
	public static Vector2D subtract(Vector2D vct1, Vector2D vct2) {
		return new Vector2D(vct1.getX() - vct2.getX(), vct1.getY() - vct2.getY());
	}
	
	/**
	 * Scale a vector by a scalar value
	 * @param scale
	 */
	public void scale(double scale) {
		x *= scale;
		y *= scale;
	}
	
	/**
	 * A static scale function
	 * @param vct vector to scale
	 * @param scale scalar to multiply by
	 * @return the scaled vector
	 */
	public static Vector2D scale(Vector2D vct, double scale) {
		return new Vector2D(vct.getX() * scale, vct.getY() * scale);
	}
	
	
	/**
	 * The negative of this vector
	 */
	public void negate() {
		x *= -1;
		y *= -1;
	}
	
	/**
	 * Divide by a scalar value, throw an illegal arg exception if passed zero
	 * @param divisor
	 */
	public void divide(double divisor) {
		if(divisor == 0) {
			throw new IllegalArgumentException("Divisor is equal to 0");
		}
		else {
			x /= divisor;
			y /= divisor;
		}
	}
	
	/**
	 * Get the magnitude of this vector
	 * @return the magnitude of this vector, also known as its length
	 */
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}
	
	/**
	 * Get the phi of this 2d vector, or its polar angle
	 * @return the polar angle of this vct
	 */
	public double phi() {
		double angle = Math.atan2(y, x);
		if (angle < 0) 
			angle += 2 * Math.PI;
		return angle;
	}
	
	
	/**
	 * This vct's unit vector, that is a vector in the same direction with length 1
	 * @return this vector's unit vector
	 */
	public Vector2D unitVector() {
		double magnitude = magnitude();
		if(magnitude == 0) {
			return new Vector2D();
		}
		else {
			return new Vector2D(x/magnitude, y/magnitude);
		}
	}
	
	/**
	 * The dot product of this vector and another
	 * @param vct
	 * @return resulting scalar value from dot product
	 */
	public double dotProduct(Vector2D vct) {
		return x * vct.getX() + y * vct.getY();
	}
	
	/**
	 * The distance between this vector and another
	 * @param vct
	 * @return the distance between this and the passed vector
	 */
	public double distance(Vector2D vct) {
		Vector2D displacement = new Vector2D(x - vct.getX(), y - vct.getY());
		return displacement.magnitude();
	}
	
	/**
	 * A static distance method
	 * @param vct1
	 * @param vct2
	 * @return The distance between the two vectors
	 **/
	public static double distance(Vector2D vct1, Vector2D vct2) {
		Vector2D displacement = new Vector2D(vct1.getX() - vct2.getX(), vct1.getY() - vct2.getY());
		return displacement.magnitude();
	}
	
	/**
	 * The angle between this vector and another
	 * @param vct
	 * @return The angle between this vector and another
	 */
	public double angleBetween(Vector2D vct) {
		return Math.acos(dotProduct(vct) / (magnitude() * vct.magnitude()));
	}
	
	/**
	 * Convert a polar vector into a cartesian vector
	 * @param r
	 * @param phi
	 * @return the resulting cartesian vector
	 */
	public static Vector2D polarVector(double r, double phi) {
		double x = r * Math.cos(phi);
		double y = r * Math.sin(phi);
		
		return new Vector2D(x, y);
	}
	
	@Override
	public String toString() {
		return "Vector2D [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
}
