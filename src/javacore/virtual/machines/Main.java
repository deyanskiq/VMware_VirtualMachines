package javacore.virtual.machines;

import java.io.IOException;

// when deleting HardDiskController to delete all HardDisks attached to it
// more  JUnit tests
//add REST service(available from web site; divide into different modules(loading from console module, loading from file module, loading from web site module))

public class Main {

	public static final String ROOT = "./commands/";

	public static void main(String[] args) throws IOException {
		ESX module = new ESX();

		module.readFromConsole();

		// module.readFromFiles(ROOT + "c1.txt");
		// module.readFromFiles(ROOT + "c1.txt", ROOT + "c2.txt");

	}
}
