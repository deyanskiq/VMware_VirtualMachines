package javacore.virtual.machines.devices;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import javacore.virtual.machines.exceptions.HardDiskException;

public class HardDisk extends Device {
	// 100TB
	private static long maxSize = 100000000000L;
	private String controllerId;
	private static ArrayList<HardDisk> hardDisks = new ArrayList<HardDisk>();
	private static Map<Integer, ArrayList<HardDisk>> hardDiskTable = new Hashtable<Integer, ArrayList<HardDisk>>();
	private long size;
	private static final int maxCountOfHardDiskWithControllerTypeIDE = 3;
	private static final int maxCountOfHardDiskWithControllerTypeSCSI = 15;

	/**
	 * 
	 * @param id
	 *            -a character string that is unique among all devices for the
	 *            VM. May only contain alphanumeric characters
	 * @param size
	 *            - hard disk size in bytes. Maximum size of a hard disk is 100
	 *            TB(100 * 1024 * 1024 * 1024 * 1024 bytes)
	 * @param controller
	 *            - id of the controller that the hard disk is attached to.
	 *            Should be a valid id of a hard disk controller that already
	 *            exists.
	 * 
	 *            <dev_id> <size> <controller_id> Deleting a HardDisk
	 *            Controller, deletes all the hard disks attached to it.
	 * 
	 */

	public HardDisk(String... args) {
		super(args[0]);
		try {
			if (isValidCountOfHardDisks()) {
				this.setSize(isValidSize(Long.parseLong(args[1])));
				controllerId = args[2];

				hardDisks.add(this);
				hardDiskTable.put(Integer.parseInt(controllerId), hardDisks);
			} else {
				throw new HardDiskException("Illegal number of hard disk on current controller is reached ");
			}

		} catch (HardDiskException e) {
			System.out.println(e.getMessage());

		}
	}

	private boolean isValidCountOfHardDisks() {
		for (Map.Entry<Integer, ArrayList<HardDisk>> entry : hardDiskTable.entrySet()) {
			if ((HardDiskController.getHardDiskControllers().get(entry.getKey()).equals("IDE")
					&& entry.getValue().size() > maxCountOfHardDiskWithControllerTypeIDE)
					|| (HardDiskController.getHardDiskControllers().get(entry.getKey()).equals("SCSI")
							&& entry.getValue().size() > maxCountOfHardDiskWithControllerTypeSCSI)) {
				return false;
			}
		}
		return true;
	}

	private long isValidSize(long size) throws HardDiskException {
		if (size < maxSize) {
			return size;
		} else {
			throw new HardDiskException("Invalid hard disk size");
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

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getControllerId() {
		return controllerId;
	}

	public void setControllerId(String id) {
		controllerId = id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("HardDisk ").append(id).append(" ").append(size);

		return sb.toString();
	}

	public static Map<Integer, ArrayList<HardDisk>> getHardDisks() {
		return hardDiskTable;
	}

}
