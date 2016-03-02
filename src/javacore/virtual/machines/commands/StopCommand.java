package javacore.virtual.machines.commands;

import java.util.Map.Entry;

import javacore.virtual.machines.ESX;
import javacore.virtual.machines.VirtualMachine;

public class StopCommand extends Command {

	private static final String COMMAND_NAME = "stop";
	private static final String DESCRIPTION = "Stops the program";

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
		Command command = ESX.getCommandMap().get("save-vm");

		for (Entry<String, VirtualMachine> entry : ESX.getVirtualMachines().entrySet()) {
			String[] params = new String[1];
			params[0] = entry.getKey();

			command.execute(params);
		}

		System.exit(0);
		return true;
	}

}
