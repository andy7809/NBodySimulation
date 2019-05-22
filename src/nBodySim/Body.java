package nBodySim;

import lib.Vector2D;

/**
 * A body in the n-body simulation
 * @author andre
 *
 */
public class Body {
	/**
	 * The name of this body, for identification purposes
	 */
	private String name;
	/**
	 * The position of this object on the map
	 */
	private Vector2D position;
	/**
	 * The velocity of this object
	 */
	private Vector2D velocity;
	/**
	 * The acceleration of this body
	 */
	private Vector2D acceleration;
	/**
	 * The mass of this body
	 */
	private double mass;
	/**
	 * The radius of this body, used in calculating collisions
	 */
	private double radius;
	/**
	 * The path to the picture that this picture uses, relative to src\data
	 */
	private String picturePath;
	
	/**
	 * Create a new body from a name
	 * @param name
	 */
	public Body(String name) {
		this.name = name;
	}
	
	/**
	 * Create a new body from a name and position
	 * @param name
	 * @param position
	 */
	public Body(String name, Vector2D position) {
		this.name = name;
		this.position = position;
	}
	
	/**
	 * Create a new body
	 * @param name
	 * @param position
	 * @param velocity
	 * @param mass
	 */
	public Body(String name, Vector2D position, Vector2D velocity, double mass) {
		this.name = name;
		this.position = position;
		this.velocity = velocity;
	}
	
	/**
	 * Create a new body, filling all fields
	 * @param name
	 * @param position
	 * @param velocity
	 * @param acceleration
	 * @param mass
	 * @param radius
	 * @param picturePath
	 */
	public Body(String name, Vector2D position, Vector2D velocity, Vector2D acceleration, double mass, double radius, String picturePath) {
		this.name = name;
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.mass = mass;
		this.radius = radius;
		this.picturePath = "src\\data\\" + picturePath;
	}
	
	/**
	 * Updat this object with a net force and time change
	 * @param netForce
	 * @param deltaTime
	 */
	public void update(Vector2D netForce, double deltaTime) {
		acceleration = Vector2D.scale(netForce.unitVector(), netForce.magnitude() / mass);
		velocity.add(Vector2D.scale(acceleration.unitVector(), acceleration.magnitude() * deltaTime));
		position.add(Vector2D.scale(velocity.unitVector(), velocity.magnitude() * deltaTime));
	}
	
	/**
	 * Get a body from one line in the files that are distributed initially
	 * @param str
	 * @return the body represented by this line
	 */
	public static Body getBodyFromString(String str) {
		str = str.trim();
		String[] arr = str.split("\\s+");
		Vector2D position = new Vector2D(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
		Vector2D velocity = new Vector2D(Double.parseDouble(arr[2]), Double.parseDouble(arr[3]));
		Vector2D acceleration = new Vector2D();
		String picPath = arr[5];
		String name = picPath.split("\\.")[0];
		double mass = Double.parseDouble(arr[4]);
		return new Body(name, position, velocity, acceleration, mass, 1, picPath);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * @return the velocity
	 */
	public Vector2D getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the acceleration
	 */
	public Vector2D getAcceleration() {
		return acceleration;
	}

	/**
	 * @param acceleration the acceleration to set
	 */
	public void setAcceleration(Vector2D acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * @return the mass
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * @param mass the mass to set
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * @return the picturePath
	 */
	public String getPicturePath() {
		return picturePath;
	}

	/**
	 * @param picturePath the picturePath to set
	 */
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	@Override
	public String toString() {
		return position.getX() + " " + position.getY() + " " + velocity.getX() + " " + velocity.getY() +
				" " + mass + " " + picturePath.split("\\\\")[2];
	}
}
