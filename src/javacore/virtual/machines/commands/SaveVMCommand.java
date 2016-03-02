package javacore.virtual.machines.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map.Entry;

import javacore.virtual.machines.ESX;
import javacore.virtual.machines.devices.Device;

/***
 * 
 * @author Deyan Denchev save-vm <id> - Saves the VM with the specified id on
 *         the file system.
 */
public class SaveVMCommand extends Command {

	public static final String COMMAND_NAME = "save-vm";
	public static final String DESCRIPTION = "Saves the VM with the specified id on the file system.";

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
		String id = args[0];
		Path path = Paths.get(ESX.VMs + "/vm_" + id);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile()));) {

			path.toFile().createNewFile();
			StringBuilder sb = new StringBuilder();
			sb.append(ESX.getVirtualMachines().get(id).toString());

			for (Entry<String, Device> e : ESX.getVirtualMachines().get(id).getDevices().entrySet()) {
				sb.append(e.getValue().toString());
			}
			bw.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}
