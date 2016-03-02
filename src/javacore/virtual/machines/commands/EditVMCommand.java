package javacore.virtual.machines.commands;

import javacore.virtual.machines.ESX;

/***
 * 
 * @author Deyan Denchev edit-vm <id> '<new_name>'
 *         <new_memory> <number_of_CPUs> - edit the VM with the given id. Even
 *         when the name, the memory or the number of CPUs of the VM remains
 *         unchanged, they are provided.
 */
public class EditVMCommand extends Command {

	public static final String COMMAND_NAME = "edit-vm";
	public static final String DESCPRTION = "Edit the VM with the given id. Even when the name, the memory or the number of CPUs of the VM remains unchanged, they are provided.";

	@Override
	public String getName() {
		return COMMAND_NAME;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public boolean execute(String[] args) {
		String vmId = args[0];
		String name = args[1];
		int memory = Integer.parseInt(args[2]);
		int numberOfCPUs = Integer.parseInt(args[3]);

		if (ESX.getVirtualMachines().containsKey(vmId)) {
			ESX.getVirtualMachines().get(vmId).setName(name);
			ESX.getVirtualMachines().get(vmId).setMemory(memory);
			ESX.getVirtualMachines().get(vmId).setNumberOfCPUs(numberOfCPUs);
			System.out.println("Virtual Machine successfully edited");
			return true;
		} else {
			System.out.println("Virtual machine with \"id: " + vmId + "\" does not exist");
			return false;
		}
	}

}
