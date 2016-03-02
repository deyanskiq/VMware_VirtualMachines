package javacore.virtual.machines.commands;

import javacore.virtual.machines.ESX;
import javacore.virtual.machines.devices.Device;
import javacore.virtual.machines.devices.DeviceFactory;
import javacore.virtual.machines.devices.DeviceFactory.DevType;
import javacore.virtual.machines.exceptions.NotUniqueDeviceException;

/***
 * 
 * @author Deyan Denchev add-dev <vm_id> <dev_type> <device_spec> - adds a new
 *         device to the virtual machine with id - vm_id. Each device has a
 *         unique id among all devices in this VM. Device id is specified in
 *         device_spec. dev_type will be VIDEO_CARD or NETWORK_CARD for video
 *         card or network card respectively. device_spec will be a device
 *         specification for a device of the type specified (see below the
 *         device specification for video card and network card).
 */
public class AddDeviceCommand extends Command {

	public static final String COMMAND_NAME = "add-dev";
	public static final String DESCRIPTION = "Adds a new device to the virtual machine with id - vm_id. Each device has a unique id among all devices in this VM. ";

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public String getName() {
		return COMMAND_NAME;
	}

	/***
	 * 
	 * @param args
	 *            - get vmid and devType from the arguments
	 * @return true if device is successfully added to the virtual machine
	 *         and @return false if the virtual machine doesn't exist
	 */

	@Override
	public boolean execute(String[] args) {
		String vmId = args[0];
		String devType = args[1];
		DeviceFactory df = new DeviceFactory();
		DeviceFactory.DevType type = DevType.getType(devType);
		String[] devProperties = new String[args.length - 2];
		for (int i = 2; i < args.length; i++) {
			devProperties[i - 2] = args[i];
		}

		Device device = df.createDevice(type, devProperties);
		if (ESX.getVirtualMachines().containsKey(vmId)) {
			try {

				ESX.getVirtualMachines().get(vmId).addDevice(device);
				System.out.println("Device added to " + vmId);
				return true;
			} catch (NotUniqueDeviceException nce) {
				System.out.println("Device with the same id already exists");
				return false;

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				return false;
			}

		} else {
			System.out.println("Virtual machine with \"id: " + vmId + "\" does not exist");
			return false;
		}
	}
}
