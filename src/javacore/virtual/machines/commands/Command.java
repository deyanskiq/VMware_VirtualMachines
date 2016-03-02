package javacore.virtual.machines.commands;

/***
 * 
 * @author Deyan Denchev abstract class Command with method for getting the name
 *         of the command
 */
public abstract class Command {
	public static String COMMAND_NAME;
	public static String DESCRIPTION;

	public abstract String getName();

	public abstract String getDescription();

	public abstract boolean execute(String[] args);
}
