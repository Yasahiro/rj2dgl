package uk.co.marshmallow_zombies.rj2dgl.framework;

public class Vector2 {

	public float x, y;

	public Vector2() {
		this.x = 0;
		this.y = 0;
	}

	public Vector2(float v) {
		this.x = v;
		this.y = v;
	}

	public Vector2(Vector2 v) {
		this.x = v.x;
		this.y = v.y;
	}

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vector2 v) {
		this.x += v.x;
		this.y += v.y;
	}

	public void subtract(Vector2 v) {
		this.x -= v.x;
		this.y -= v.y;
	}

	public void multiply(Vector2 v) {
		this.x *= v.x;
		this.y *= v.y;
	}

	public void divide(Vector2 v) {
		this.x /= v.x;
		this.y /= v.y;
	}

	@Override
	public String toString() {
		return String.format("{%f, %f}", this.x, this.y);
	}

	public static final Vector2 ONE = new Vector2(1);
	public static final Vector2 UNIT_X = new Vector2(1, 0);
	public static final Vector2 UNIT_Y = new Vector2(0, 1);
	public static final Vector2 ZERO = new Vector2(0);

};
