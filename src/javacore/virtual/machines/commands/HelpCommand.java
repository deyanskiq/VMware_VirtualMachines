package javacore.virtual.machines.commands;

import javacore.virtual.machines.ESX;
import javacore.virtual.machines.printing_utils.TableBuilder;

public class HelpCommand extends Command {

	public static final String COMMAND_NAME = "help";
	public static final String DESCRIPTION = "Lists all of the commands with their description";

	@Override
	public String getName() {
		return COMMAND_NAME;
	}

	@Override
	public boolean execute(String[] args) {
		TableBuilder tb = new TableBuilder();
		tb.addRow("Name", "Description");
		for (Command comm : ESX.getCommands()) {
			tb.addRow(comm.getName(), comm.getDescription());
		}
		System.out.println(tb.toString());

		return true;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

}
