package javacore.virtual.machines.devices;

import java.util.regex.Pattern;

import javacore.virtual.machines.exceptions.NetworkCardException;

/***
 * 
 * @author Deyan Denchev
 */
public class NetworkCard extends Device {

	private static Pattern IP_PATTERN = Pattern
			.compile("^(([01]?[0-9]?[0-9]|2([0-4][0-9]|5[0-5]))\\.){3}([01]?[0-9]?[0-9]|2([0-4][0-9]|5[0-5]))$");
	private static Pattern MAC_PATTERN = Pattern.compile("^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$");

	private String mac;
	private String ip;

	/***
	 * 
	 * @param id
	 *            - a character string that is unique through all devices for
	 *            the VM. May only contain alphanumeric characters.
	 * @param mac
	 *            -MAC address this constructor initialized id and mac fields
	 * @throws NetworkCardException
	 */

	public NetworkCard(String... args) {
		super(args[0]);
		try {
			setMac(isValidMac(args[1]));

			if (args.length > 2) {
				setIp(isValidIp(args[2]));
			}
		} catch (NetworkCardException e) {
			System.out.println(e.getMessage());
		}
	}

	/***
	 * 
	 * @param id
	 *            - a character string that is unique through all devices for
	 *            the VM. May only contain alphanumeric characters.
	 * @param mac
	 *            - -MAC address
	 * @param ip
	 *            -it's optional parameter (Because of that is this additional
	 *            constructor)
	 * @throws NetworkCardException
	 *
	 * 
	 */

	// validates the MAC address(allows separating MAC address with ":" (Linux)
	// , "-" (Windows), "." (others))
	private String isValidMac(String mac) throws NetworkCardException {
		if (MAC_PATTERN.matcher(mac).matches()) {
			return mac;
		} else {
			throw new NetworkCardException("Mac address not valid");

		}
	}

	// validates the IP address
	private String isValidIp(String ip) throws NetworkCardException {
		if (IP_PATTERN.matcher(ip).matches()) {
			return ip;
		} else {
			throw new NetworkCardException("IP adrress not valid");
		}
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (ip != null) {
			sb.append("NETWORK_CARD ").append(id).append(" ").append(mac).append(" ").append(ip)
					.append(System.getProperty("line.separator"));
		} else {
			sb.append("NETWORK_CARD ").append(id).append(" ").append(mac).append(" ")
					.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
}
