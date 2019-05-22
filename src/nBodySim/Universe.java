package nBodySim;

import java.util.ArrayList;

import lib.StdDraw;
import lib.Tuple;
import lib.Vector2D;

/**
 * A universe of n-bodies, allows for collision of objects and gravitational forces
 * between the bodies
 * @author andre
 *
 */
public class Universe {
	/**
	 * The constant G
	 */
	private final double gravitationalConstant = 6.67e-11; //N*m^2*kg^2
	/**
	 * The simulaiton time
	 */
	private double time;
	/**
	 * The radius of the sim
	 */
	private double radius;
	/**
	 * all bodies in this sim
	 */
	private ArrayList<Body> allBodies;
	/**
	 * Bodies that have collided this tick
	 */
	private ArrayList<Tuple<Body, Body>> collisionBodies;
	/**
	 * The max time that this simulation should run to
	 */
	private double maxTime;
	
	/**
	 * The given star background for the window
	 */
	private final static String starBackground = "src\\data\\starfield.jpg";
	
	/**
	 * Initialize an empty universe of a given radius
	 * @param radius
	 */
	public Universe(double radius) {
		time = 0;
		this.radius = radius;
		allBodies = new ArrayList<Body>();
		collisionBodies = new ArrayList<Tuple<Body, Body>>();
	}
	
	/**
	 * Add a body to this simulation
	 * @param toAdd
	 */
	public void addBody(Body toAdd) {
		allBodies.add(toAdd);
	}
	
	/**
	 * Get a body by its name
	 * @param name
	 * @return the body with the corresponding name
	 */
	public Body getByName(String name) {
		for(Body x : allBodies) {
			if(x.getName().equals(name)) {
				return x;
			}
		}
		return null;
	}
	
	/**
	 * Update this simulation by an amount of time
	 * @param deltaTime
	 * @return Whether a collision occurred this tick
	 */
	public boolean update(double deltaTime) {
		for(Body x : allBodies) {
			Vector2D netForce = getNetForce(x);
			x.update(netForce, deltaTime);
			if(collision()) {
				return false;
			}
		}
		redraw();
		if(!collisionBodies.isEmpty()) {
			removeCollisionBodies();
		}
		time += deltaTime;
		return true;
	}
	
	/**
	 * Redraw the StdDraw window, with the original bakcground and new positions of bodies
	 */
	private void redraw() {
		StdDraw.clear();
		StdDraw.picture(0.5, 0.5, starBackground);
		for(Body x : allBodies) {
			StdDraw.picture(getPosition(x.getPosition().getX()), getPosition(x.getPosition().getY()), x.getPicturePath());
		}
		StdDraw.show();
	}
	
	/**
	 * Handle all collsions. Colliding bodies combine, and their new momentums are calculated
	 */
	private void removeCollisionBodies() {
		if(!collisionBodies.isEmpty()) {
			for(Tuple<Body, Body> pair : collisionBodies) {
				if(allBodies.contains(pair.x) && allBodies.contains(pair.y)) {
					double newMass = pair.x.getMass() + pair.y.getMass();
					if(pair.x.getMass() > pair.y.getMass()) {
						pair.x.setMass(newMass);
						remove(pair.y);
					}
					else {
						pair.y.setMass(newMass);
						remove(pair.x);
					}
				}
			}
		}
	}
	
	/**
	 * Remove a body
	 * @param toRemove
	 * @return whether the body was found and removed
	 */
	public boolean remove(Body toRemove) {
		boolean contained = allBodies.remove(toRemove);
		return contained;
	}
	
	/**
	 * Get the net force acting on this body
	 * @param b
	 * @return the vector, representing the net force on this body
	 */
	private Vector2D getNetForce(Body b) {
		Vector2D netForce = new Vector2D();
		for(Body x : allBodies) {
			if(!x.getPosition().equals(b.getPosition())) {
				netForce.add(getGravitationForce(b, x));
				netForce.add(getCollisionForces(b, x));
			}
		}
		return netForce;
	}
	
	/**
	 * Get the forces from a collision, force is the change in momentum in physics
	 * @param b1
	 * @param b2
	 * @return the collision force, or change in momentum, as a vct
	 */
	private Vector2D getCollisionForces(Body b1, Body b2) {
		double distance = Vector2D.distance(b1.getPosition(), b2.getPosition());
		if(distance < b1.getRadius() || distance < b2.getRadius()) {
			collisionBodies.add(new Tuple<Body, Body>(b1, b2));
			return Vector2D.scale(b2.getVelocity(), b2.getMass());			
		}
		return new Vector2D();
	}
	
	/**
	 * Get the gravitation forces acting on free, from source
	 * @param free
	 * @param source
	 * @return the gravitational forces acting on free, from the source body, as a vct
	 */
	private Vector2D getGravitationForce(Body free, Body source) {
		double r = free.getPosition().distance(source.getPosition());
		double forceMagnitude = (gravitationalConstant * free.getMass() * source.getMass()) / (r * r);
		Vector2D differenceVector = Vector2D.subtract(free.getPosition(), source.getPosition());
		double sinTheta = differenceVector.getY() / r;
		double cosTheta = differenceVector.getX() / r;
		Vector2D retVct = new Vector2D(forceMagnitude * cosTheta, forceMagnitude * sinTheta);
		retVct.negate();
		return retVct;
	}
	
	/**
	 * Check for all collisions
	 * @return whether a collision was found
	 */
	private boolean collision() {
		for(Body x : allBodies) {
			for(Body y : allBodies) {
				double distance = Vector2D.distance(x.getPosition(), y.getPosition());
				if(distance < x.getRadius() && x.getPosition() != y.getPosition()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Get the current adjusted position of a value
	 * @param val
	 * @return the adjusted value, based on the radius of the universe
	 */
	public double getPosition(double val) {
		return ((val + radius) / (2 * radius));
	}
	
	public boolean continueSimulation() {
		return time < maxTime;
	}
	
	public double getTime() {
		return time;
	}
	public void setWorldTime(double time) {
		this.time = time;
	}
	public ArrayList<Body> getAllBodies() {
		return allBodies;
	}
	public void setAllBodies(ArrayList<Body> allBodies) {
		this.allBodies = allBodies;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public double getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(double maxTime) {
		this.maxTime = maxTime;
	}

	@Override
	public String toString() {
		String retString = "";
		retString += allBodies.size() + "\n";
		retString += radius;
		for(Body x : allBodies){
			retString +=  "\n" + x;
		}
		return retString;
	}
}
