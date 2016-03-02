package javacore.virtual.machines.commands;

import java.util.Map.Entry;

import javacore.virtual.machines.ESX;
import javacore.virtual.machines.VirtualMachine;
import javacore.virtual.machines.printing_utils.TableBuilder;

/***
 * 
 * @author Deyan Denchev print-vms - prints a human-friendly summary of all the
 *         current VMs on the standard output for the program
 */
public class PrintVMCommand extends Command {

	public static final String COMMAND_NAME = "print-vms";
	public static final String DESCRIPTION = "Prints a human-friendly summary of all the current VMs on the standard output for the program";

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
	 * @return all of stored virtual machine with their id , name and memory
	 */
	@Override
	public boolean execute(String[] args) {
		TableBuilder tb = new TableBuilder();
		tb.addRow("Id", "Name", "Memory");
		for (Entry<String, VirtualMachine> entry : ESX.getVirtualMachines().entrySet()) {
			tb.addRow(entry.getValue().getId(), entry.getValue().getName(),
					String.valueOf(entry.getValue().getMemory()));
		}
		System.out.println(tb.toString());

		return true;
	}

}
