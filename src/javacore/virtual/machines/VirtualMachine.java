package javacore.virtual.machines;

import java.util.Hashtable;
import java.util.Map;

import javacore.virtual.machines.devices.Device;
import javacore.virtual.machines.exceptions.NotUniqueDeviceException;

/***
 * 
 * @author Deyan Denchev This class represent virtual machine with all of its
 *         fields.
 */
public class VirtualMachine {

	private String name;

	private String id;

	private int memory;

	private int numberOfCPUs;
	private Map<String, Device> devices;

	/***
	 * constructor which initialized the fields name,id,memory and devices
	 * 
	 * @param name
	 *            -There may be more than one VM with a given name. Name is a
	 *            string, which contains alphanumeric characters and space
	 * @param numberOfCPUs
	 *            -number of CPUs, which should be between 1 and 8
	 * @param id
	 *            - unique number for each of the virtual machines
	 * @param memory
	 *            - the size of the RAM of the VM. This is measured in bytes and
	 *            it is stored in bytes.
	 *
	 */
	public VirtualMachine(String name, String id, int memory, int numberOfCPUs) {
		this.name = name;
		this.id = id;
		this.memory = memory;
		this.setNumberOfCPUs(numberOfCPUs);
		this.devices = new Hashtable<String, Device>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getNumberOfCPUs() {
		return numberOfCPUs;
	}

	public void setNumberOfCPUs(int numberOfCPUs) {
		this.numberOfCPUs = numberOfCPUs;
	}

	/**
	 * 
	 * @return field 'devices' which stores key as unique String id of the
	 *         device and value as value to the corresponding device
	 */
	public Map<String, Device> getDevices() {
		return devices;
	}

	public void setDevices(Hashtable<String, Device> devices) {
		this.devices = devices;
	}

	/***
	 * 
	 * @param device
	 * @throws NotUniqueDeviceException
	 *             if the id isn't unique
	 */
	public void addDevice(Device device) throws NotUniqueDeviceException {
		if (this.devices.containsKey(device.getId())) {
			throw new NotUniqueDeviceException("Device with the same ID already exists");
		} else {
			this.devices.put(device.getId(), device);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(id).append(" ").append(name).append(" ").append(memory).append(System.getProperty("line.separator"));

		return sb.toString();
	}

}
