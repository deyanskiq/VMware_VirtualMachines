package javacore.virtual.machines;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import javacore.virtual.machines.commands.AddDeviceCommand;
import javacore.virtual.machines.commands.Command;
import javacore.virtual.machines.commands.CreateVMCommand;
import javacore.virtual.machines.commands.DeleteDeviceCommand;
import javacore.virtual.machines.commands.DeleteVMCommand;
import javacore.virtual.machines.commands.EditVMCommand;
import javacore.virtual.machines.commands.HelpCommand;
import javacore.virtual.machines.commands.PrintVMCommand;
import javacore.virtual.machines.commands.ReadVMCommand;
import javacore.virtual.machines.commands.SaveVMCommand;
import javacore.virtual.machines.commands.StopCommand;
import javacore.virtual.machines.interfaces.IESX;

/***
 * 
 * @author Deyan Denchev . ESX is a system that can manage Virtual Machines.
 *         Manage means that our ESX system should be able to Create, Delete,
 *         Edit and List (display) Virtual Machines. A Virtual Machine or VM is
 *         an entity similar to physical computer. For the purpose of this task
 *         we will use simplified VM, which has only unique ID, name, memory and
 *         a set of devices.
 * 
 *         The ESX system sequentially reads operations (commands) from the
 *         command line and executes them. Operations are one of the following:
 *         � create-vm <id> '<name>' <memory> � delete-vm <id> - �edit-vm <id>'
 *         <new_name>' <new_memory> � add-dev<vm_id> <dev_type> <device_spec> -
 *         � delete-dev <vm_id> <dev_id> - �print-vms �save-vm <id> - �read-vm
 *         <id>
 */
public class ESX implements IESX {
	/***
	 * stores virtual machines in hash table where key is String with the unique
	 * id and the value is the corresponding properties
	 */

	public static final String VMs = "virtual_machines";

	private static Map<String, VirtualMachine> virtualMachines = new Hashtable<String, VirtualMachine>();

	// list of collection with all of the commands
	private static final Collection<Command> COMMANDS = Arrays.asList(new Command[] { new AddDeviceCommand(),
			new CreateVMCommand(), new DeleteDeviceCommand(), new DeleteVMCommand(), new PrintVMCommand(),
			new EditVMCommand(), new HelpCommand(), new SaveVMCommand(), new ReadVMCommand(), new StopCommand() });

	// Map with the commands where the key is String with the command name and
	// the value is the method for the corresponding command

	private static final Map<String, Command> COMMAND_MAP = new HashMap<String, Command>() {

		private static final long serialVersionUID = -1435792139192823872L;

		{
			for (Command command : COMMANDS) {
				put(command.getName(), command);
			}
		}
	};

	public static Collection<Command> getCommands() {
		return COMMANDS;
	}

	public static Map<String, Command> getCommandMap() {
		return COMMAND_MAP;
	}

	/***
	 * loads already saved Virtual machines
	 */
	public void loadFiles() {
		Path path = Paths.get(VMs);
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {

			for (Path fileOrSubDir : stream) {
				if (fileOrSubDir.getFileName().toString().contains("vm_")) {
					String[] id = new String[1];
					id[0] = fileOrSubDir.getFileName().toString().substring(3);
					Command comm = COMMAND_MAP.get("read-vm");

					comm.execute(id);

				}
			}
		} catch (IOException | DirectoryIteratorException x) {
			System.out.println(x.getMessage());
		}

	}

	public void readLines(StringTokenizer st, String command) {

		if (!COMMAND_MAP.containsKey(command)) {

			System.out.println("Invalid command");

		} else {

			Command comm = COMMAND_MAP.get(command);

			String[] args;

			if (st.countTokens() > 0) {
				args = new String[st.countTokens()];
			} else {
				args = new String[1];
			}

			int i = 0;
			while (st.hasMoreTokens()) {
				args[i] = st.nextToken();
				i++;
			}
			try {
				comm.execute(args);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}

		}
	}

	/***
	 * reading commands from console where each command is on a new line and
	 * execute them one by one if command is equal to "exit" the program stops
	 * running
	 */
	@Override
	public void readFromConsole() throws IOException {
		loadFiles();
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			StringTokenizer st = new StringTokenizer(bf.readLine());
			String command = st.nextToken();

			readLines(st, command);
		}

	}

	/***
	 * reading commands from file where each command is on a new line
	 * 
	 */
	@Override
	public void readFromFiles(String... path) throws IOException {
		loadFiles();
		ReadingThread[] threads = new ReadingThread[path.length];
		for (int i = 0; i < path.length; i++) {
			threads[i] = new ReadingThread(path[i]);
			Thread thread = new Thread(threads[i]);
			thread.start();
		}
	}

	public static Map<String, VirtualMachine> getVirtualMachines() {
		return virtualMachines;
	}

}
