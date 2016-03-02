package javacore.virtual.machines.devices;

import javacore.virtual.machines.exceptions.VideoCardException;

/***
 * 
 * @author Deyan Denchev class VideoCard inherits abstract class Device
 */
public class VideoCard extends Device {
	private int ram;
	private static final int videoCardMaxSize = 4000000;
	private byte numberOfDisplays;

	/***
	 * 
	 * @param id
	 *            a character string that is unique through all devices for the
	 *            VM. May only contain alphanumeric characters.
	 * @param ram
	 *            (video) measured in KB and its maximum value is 4 GB
	 * @param numberOfDisplays
	 *            - Number
	 * 
	 *            constructor which initialized the fields id,memory and number
	 *            of displays
	 */

	public VideoCard(String... args) {
		super(args[0]);
		try {
			numberOfDisplays = Byte.parseByte(args[2]);
			ram = isVideoCardValid(Integer.parseInt(args[1]));
		} catch (VideoCardException e) {
			System.out.println(e.getMessage());
		}

	}

	private int isVideoCardValid(int videoCard) throws VideoCardException {

		if (videoCard > videoCardMaxSize || videoCard <= 0) {
			throw new VideoCardException("Invalid video card size.");
		} else {
			return videoCard;
		}
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getNumberOfDisplays() {
		return numberOfDisplays;
	}

	public void setNumberOfDisplays(byte numberOfDisplays) {
		this.numberOfDisplays = numberOfDisplays;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("VIDEO_CARD ").append(id).append(" ").append(ram).append(" ").append(numberOfDisplays)
				.append(System.getProperty("line.separator"));
		return sb.toString();
	}
}
