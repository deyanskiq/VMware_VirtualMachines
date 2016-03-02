package javacore.virtual.machines;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javacore.virtual.machines.commands.Command;

public class ReadingThread implements Runnable {

	private String path;

	public ReadingThread(String path) {
		this.setPath(path);
	}

	public String extractText() {
		StringBuilder sb = new StringBuilder();

		try (BufferedReader bf = new BufferedReader(new FileReader(path));) {

			String line = bf.readLine();

			while (line != null) {
				sb.append(line).append(System.getProperty("line.separator"));
				line = bf.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public synchronized void executeLines(StringTokenizer st, String command) {
		if (!ESX.getCommandMap().containsKey(command)) {
			System.out.println("Invalid command");
		} else {
			Command comm = ESX.getCommandMap().get(command);
			String[] args = new String[st.countTokens()];
			int k = 0;
			while (st.hasMoreTokens()) {
				args[k] = st.nextToken();
				k++;
			}

			try {
				comm.execute(args);

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void run() {
		String[] lines = extractText().split(System.getProperty("line.separator"));
		for (String line : lines) {
			StringTokenizer st = new StringTokenizer(line);

			String command = st.nextToken();

			executeLines(st, command);

		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
