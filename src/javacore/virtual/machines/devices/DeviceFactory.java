package javacore.virtual.machines.devices;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class DeviceFactory {
	/***
	 * 
	 * @author Deyan Denchev DevType enum structure with all of the current
	 *         device types and there String name
	 */
	public static enum DevType {
		VIDEO_CARD("VIDEO_CARD"), NETWORK_CARD("NETWORK_CARD"), HardDiskController("HardDiskController"), HardDisk(
				"HardDisk");

		private final String value;

		private static final Map<String, DevType> lookup = new HashMap<String, DevType>();

		private static final Map<String, Constructor<?>> ctorMap = new HashMap<String, Constructor<?>>();

		static {
			for (DevType type : DevType.values()) {
				lookup.put(type.getValue(), type);
			}
			try {
				ctorMap.put("VIDEO_CARD", VideoCard.class.getConstructor(String[].class));
				ctorMap.put("NETWORK_CARD", NetworkCard.class.getConstructor(String[].class));
				ctorMap.put("HardDiskController", HardDiskController.class.getConstructor(String[].class));
				ctorMap.put("HardDisk", HardDisk.class.getConstructor(String[].class));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private DevType(String type) {
			this.value = type;
		}

		public String getValue() {
			return value;
		}

		/***
		 * 
		 * @param value
		 *            is the name of the device
		 * @return type of the device
		 */
		public static DevType getType(String value) {
			return lookup.get(value);
		}
	}

	/***
	 * 
	 * @param type
	 *            -type of the Device
	 * @param args
	 *            - depending on how much arguments there are , different
	 *            devices will be created
	 * 
	 */
	public Device createDevice(DevType type, String... args) {
		Object d = new Object();
		try {
			Object[] pass = { args };
			d = DevType.ctorMap.get(type.getValue()).newInstance(pass);

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new IllegalArgumentException();
		}
		return (Device) d;

	}

}
