package uk.co.marshmallow_zombies.rj2dgl.physics;

import java.awt.Rectangle;

import uk.co.marshmallow_zombies.rj2dgl.framework.Vector2;

/**
 * A rigidbody object.
 * 
 * @author Oliver Davenport
 */
public class Rigidbody {

	private World world; // Parent world
	private boolean hasGravity = true; // Whether or not to respond to gravity
	private float mass = 1f; // Rigidbody mass
	private Vector2 position = Vector2.ZERO; // Position
	private Vector2 acceleration = Vector2.ZERO; // Acceleration
	private Vector2 size = Vector2.ZERO; // Size

	/**
	 * Sets the parent world.
	 * 
	 * @param v
	 *            The {@code World}.
	 */
	void setWorld(World v) {
		this.world = v;
	}

	/**
	 * Gets the parent world.
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Gets whether the rigidbody should respond to gravity.
	 */
	public boolean hasGravity() {
		return hasGravity;
	}

	/**
	 * Toggles whether the rigidbody should respond to gravity.
	 */
	public void toggleGravity() {
		hasGravity = !hasGravity;
	}

	/**
	 * Gets the mass of the rigidbody.
	 */
	public float getMass() {
		return mass;
	}

	/**
	 * Sets the mass of the rigidbody.
	 * 
	 * @param v
	 *            The mass.
	 */
	public void setMass(float v) {
		mass = v;
	}

	/**
	 * Gets the position.
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 * 
	 * @param v
	 *            The {@code Vector2} position.
	 */
	public void setPosition(Vector2 v) {
		this.position = v;
	}

	/**
	 * Gets the size of the rigidbody.
	 */
	public Vector2 getSize() {
		return size;
	}

	/**
	 * Sets the size of the rigidbody.
	 * 
	 * @param size
	 *            The {@code Vector2} size.
	 */
	public void setSize(Vector2 v) {
		this.size = v;
	}

	/**
	 * Kills the rigidbody's acceleration
	 */
	public void killAcceleration() {
		this.acceleration = Vector2.ZERO;
	}

	/**
	 * Do gravity updates.
	 */
	public void doGravity() {
		if (!hasGravity)
			return;

		// Update position
		acceleration.y += 0.01;
		position.y += (mass * world.getGravity() + (acceleration.y)) / 10;
	}

	public boolean collidesWith(Rigidbody rigidbody) {
		return getRectangle().intersects(rigidbody.getRectangle());
	}

	Rectangle getRectangle() {
		// Get rigidbody properties
		Vector2 position = getPosition();
		Vector2 size = getSize();

		// Get properties
		int x = (int) position.x;
		int y = (int) position.y;
		int w = (int) size.x;
		int h = (int) size.y;

		// Create a rectangle
		Rectangle rectangle = new Rectangle(x, y, w, h);

		return rectangle;
	}
};
