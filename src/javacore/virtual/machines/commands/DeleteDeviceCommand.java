package javacore.virtual.machines.commands;

import javacore.virtual.machines.ESX;
import javacore.virtual.machines.devices.HardDisk;
import javacore.virtual.machines.devices.HardDiskController;

/***
 * 
 * @author Deyan Denchev delete-dev <vm_id> <dev_id> - deletes the device with
 *         the specified 'dev_id' from the virtual machine with id 'vm_id'.
 */
public class DeleteDeviceCommand extends Command {

	public static final String COMMAND_NAME = "delete-dev";
	public static final String DESCRIPTION = " Deletes the device with the specified 'dev_id' from the virtual machine with id 'vm_id'.";

	public DeleteDeviceCommand() {

	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public String getName() {
		return COMMAND_NAME;
	}

	@Override
	public boolean execute(String[] args) {
		String vmId = args[0];
		String devId = args[1];

		if (ESX.getVirtualMachines().containsKey(vmId)) {
			if (ESX.getVirtualMachines().get(vmId).getDevices().containsKey(devId)) {
				ESX.getVirtualMachines().get(vmId).getDevices().remove(devId);

				// when deleting HardDiskController to delete all HardDisks
				// attached to it
				// int[] ids = null;
				// int i = 0;
				// ArrayList<HardDisk> hardDisksInfo =
				// HardDisk.getHardDisks().get(Integer.parseInt(devId));
				// for (HardDisk hardDisk : hardDisksInfo) {
				// ids[i] = Integer.parseInt(hardDisk.toString().substring(10,
				// 11));
				// }
				// Iterator it =
				// VirtualMachine.this.devices.entrySet().iterator();
				// while (it.hasNext()) {
				// Map.Entry pair = (Map.Entry) it.next();
				// System.out.println(pair.getKey() + " = " + pair.getValue());
				// }

				HardDiskController.getHardDiskControllers().remove(Integer.parseInt(devId));
				HardDisk.getHardDisks().remove(Integer.parseInt(devId));
				return true;
			} else {
				System.out.println("Device with \"id: " + devId + "\" does not exist");
				return false;
			}
		} else {
			System.out.println("Virtual machine with \"id: " + vmId + "\" does not exist");
			System.out.println();
			return false;
		}
	}

}
