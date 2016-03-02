package javacore.virtual.machines.interfaces;

import java.io.IOException;

/***
 * 
 * @author Deyan Denchev This interface contains methods for reading from the
 *         console and method for reading from file
 */
public interface IESX {

	void readFromConsole() throws IOException;

	void readFromFiles(String... path) throws IOException;

}
