package uk.co.marshmallow_zombies.tmxloader;

public interface IPropertiesHolder {

	public String getPropertyString(String name);

	public int getPropertyInteger(String name);

	public float getPropertyFloat(String name);

	public boolean getPropertyBool(String name);

};
