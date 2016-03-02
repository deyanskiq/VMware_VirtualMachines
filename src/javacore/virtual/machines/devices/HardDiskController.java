package javacore.virtual.machines.devices;

import java.util.Hashtable;
import java.util.Map;

import javacore.virtual.machines.exceptions.HardDiskControllerException;

/***
 * 
 * @author Deyan Denchev IDE controller supports maximum 4 disks while SCSI
 *         controller supports 16 Hard Disks. A VM can have maximum 1 IDE
 *         controller and maximum 4 SCSI controllers.
 * 
 *         <dev_id> <controller_type> - Controller_type is one of the two
 *         Strings IDE or SCSI.
 */
public class HardDiskController extends Device {

	private String type;
	private static Map<Integer, String> hardDiskControllerTable = new Hashtable<Integer, String>();
	private static int maxCountOfIDEControllers = 1;
	private static int maxCountOfSCSIControllers = 4;

	/***
	 * 
	 * @param id
	 *            - a character string that is unique among all devices for the
	 *            VM. May only contain alphanumeric characters.
	 * @param type
	 *            - one of the two values: IDE or SCSI
	 */

	public HardDiskController(String... args) {
		super(args[0]);
		try {
			if (isCountofControllersValid()) {
				setType(isValidType(args[1]));
				hardDiskControllerTable.put(Integer.parseInt(id), this.getType());
			} else {
				throw new HardDiskControllerException("Maximum number of controllers from this type have reached");
			}
		} catch (HardDiskControllerException e) {
			System.out.println(e.getMessage());
		}
	}

	private boolean isCountofControllersValid() {
		int ideCounter = 0;
		int scsiCounter = 0;

		for (Map.Entry<Integer, String> entry : hardDiskControllerTable.entrySet()) {
			if (entry.getValue().equals("IDE")) {
				ideCounter++;
			} else if (entry.getValue().equals("SCSI")) {
				scsiCounter++;
			}

		}
		if (ideCounter > maxCountOfIDEControllers || scsiCounter > maxCountOfSCSIControllers) {
			return false;
		} else {
			return true;
		}
	}

	private String isValidType(String controllerType) throws HardDiskControllerException {
		if (controllerType.equals("IDE") || controllerType.equals("SCSI")) {
			return controllerType;
		} else {
			throw new HardDiskControllerException("Unknown hard disk controller type.");
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static Map<Integer, String> getHardDiskControllers() {
		return hardDiskControllerTable;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("HardDiskController ").append(id).append(" ").append(type)
				.append(System.getProperty("line.separator"));

		return sb.toString();
	}

}