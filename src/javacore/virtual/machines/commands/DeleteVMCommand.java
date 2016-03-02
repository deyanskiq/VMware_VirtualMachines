package javacore.virtual.machines.commands;

import java.nio.file.Path;
import java.nio.file.Paths;

import javacore.virtual.machines.ESX;

/***
 * 
 * @author Deyan Denchev � delete-vm <id> - deletes the virtual machine with the
 *         given id.
 */
public class DeleteVMCommand extends Command {

	public static final String COMMAND_NAME = "delete-vm";
	public static final String DESCRIPTION = " Deletes the virtual machine with the given id.";

	public DeleteVMCommand() {

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
		Path path = Paths.get(ESX.VMs + "/vm_" + vmId);
		if (ESX.getVirtualMachines().containsKey(vmId)) {
			ESX.getVirtualMachines().remove(vmId);
			path.toFile().delete();

			System.out.println("Virtual Machine with \"id: " + vmId + "\" has been deleted");
			return true;

		} else {
			System.out.println("Virtual Machine with \"id: " + vmId + "\" does not exist");
			return false;
		}
	}

}
