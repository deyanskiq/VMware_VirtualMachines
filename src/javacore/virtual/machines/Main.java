package javacore.virtual.machines;

import java.io.IOException;

public class Main {

	public static final String ROOT = "./commands/";

	public static void main(String[] args) throws IOException {
		ESX module = new ESX();

		// Reading from console:
		module.readFromConsole();

		// Reading from files:
		// module.readFromFiles(ROOT + "c1.txt");
		// module.readFromFiles(ROOT + "c1.txt", ROOT + "c2.txt");

	}
}
