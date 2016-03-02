package javacore.virtual.machines.devices;

/***
 * 
 * @author Deyan Denchev abstract class for the device component with its fields
 */
public abstract class Device {

	protected String id;

	public Device(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
