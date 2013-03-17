package uk.co.marshmallow_zombies.rj2dgl.physics;

import java.util.ArrayList;
import java.util.List;

public class World {

	private float gravity = 1f; // World gravity
	private List<Rigidbody> rigidbodies = new ArrayList<Rigidbody>(); // Rigidbodies

	/**
	 * Gets all rigidbodies in the world.
	 */
	public Rigidbody[] getRigidbodies() {
		return rigidbodies.toArray(new Rigidbody[rigidbodies.size()]);
	}

	/**
	 * Adds a rigidbody to the world.
	 * 
	 * @param rigidbody
	 *            The {@code Rigidbody} to add.
	 */
	public void addRigidbody(Rigidbody rigidbody) {
		rigidbody.setWorld(this);
		rigidbodies.add(rigidbody);
	}

	/**
	 * Removes a rigidbody from the world.
	 * 
	 * @param rigidbody
	 *            The {@code Rigidbody} to remove.
	 */
	public void removeRigidbody(Rigidbody rigidbody) {
		rigidbodies.remove(rigidbody);
	}

	/**
	 * Removes all rigidbodies from the world.
	 */
	public void clearRigidbodies() {
		rigidbodies.clear();
	}

	/**
	 * Gets the gravity of the rigidbody.
	 */
	public float getGravity() {
		return gravity;
	}

	/**
	 * Sets the gravity of the rigidbody.
	 * 
	 * @param v
	 *            The gravity.
	 */
	public void setGravity(float v) {
		gravity = v;
	}

};