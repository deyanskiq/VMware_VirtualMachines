package javacore.virtual.machines.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javacore.virtual.machines.ESX;
import javacore.virtual.machines.VirtualMachine;
import javacore.virtual.machines.devices.Device;
import javacore.virtual.machines.devices.DeviceFactory;
import javacore.virtual.machines.devices.DeviceFactory.DevType;
import javacore.virtual.machines.exceptions.NotUniqueDeviceException;

/***
 * 
 * @author Deyan Denchev read-vm <id> - Reads the configuration of the VM with
 *         the specified id from the file system and replaces the current
 *         settings.
 */
public class ReadVMCommand extends Command {
	public static final String COMMAND_NAME = "read-vm";
	public static final String DESCRIPTION = "Reads the configuration of the VM with the specified id from the file system and replaces the current settings. Use 'read-vm' command when you have already used 'edit-vm' command";

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

		try (BufferedReader bf = new BufferedReader(new FileReader(ESX.VMs + "/vm_" + id));) {
			StringBuilder sb = new StringBuilder();

			String line = bf.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
				line = bf.readLine();
			}

			String[] info = sb.toString().split(System.getProperty("line.separator"));
			String[] vm = info[0].split(" ");

			String name = vm[1];
			int memory = Integer.parseInt(vm[2]);
			int numberOfCPUs = Integer.parseInt(vm[3]);
			ESX.getVirtualMachines().put(id, new VirtualMachine(name, id, memory, numberOfCPUs));

			for (int i = 1; i < info.length; i++) {
				String[] dev = info[i].split(" ");
				String[] properties = new String[dev.length - 1];
				for (int j = 1; j < dev.length; j++) {

					properties[j - 1] = dev[j];
				}

				DeviceFactory df = new DeviceFactory();
				DevType type = DevType.getType(dev[0]);

				Device d = df.createDevice(type, properties);

				try {
					ESX.getVirtualMachines().get(id).addDevice(d);
				} catch (NotUniqueDeviceException e) {

				}

			}

		} catch (

		IOException e)

		{
			e.printStackTrace();
		}

		return false;
	}
}
