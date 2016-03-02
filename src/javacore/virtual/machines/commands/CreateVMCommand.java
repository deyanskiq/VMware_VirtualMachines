package javacore.virtual.machines.commands;

import javacore.virtual.machines.ESX;
import javacore.virtual.machines.VirtualMachine;
import javacore.virtual.machines.exceptions.CreateVirtualMachineException;

/***
 * 
 * @author Deyan Denchev create-vm <id> '<name>' <memory> - creates a new
 *         virtual machine with the specified id, name and memory and with no
 *         devices. Memory parameter is in bytes.
 */
public class CreateVMCommand extends Command {

	public static final String COMMAND_NAME = "create-vm";
	public static final String DESCRIPTION = " Creates a new virtual machine with the specified id, name and memory and with no devices. Memory parameter is in bytes.";

	public CreateVMCommand() {

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
		String name = args[1];
		int memory = Integer.parseInt(args[2]);
		int numberOfCPUs = Integer.parseInt(args[3]);
		if (numberOfCPUs < 1 || numberOfCPUs > 8) {
			try {
				throw new CreateVirtualMachineException("Number of CPUs should be between 1 and 8");
			} catch (CreateVirtualMachineException e) {
				System.out.println(e.getMessage());
				return false;
			}
		}

		if (!ESX.getVirtualMachines().containsKey(vmId)) {
			ESX.getVirtualMachines().put(vmId, new VirtualMachine(name, vmId, memory, numberOfCPUs));
			System.out.println("Virtual Machine created");
			return true;
		} else {
			System.out.println("Virtual Machine with \"id: " + vmId + "\" already exists");
			return false;
		}
	}

}
