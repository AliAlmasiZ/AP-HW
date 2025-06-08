package tracker.controllers;

import common.models.CLICommands;
import common.models.Message;
import common.utils.FileUtils;
import tracker.app.PeerConnectionThread;
import tracker.app.TrackerApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TrackerCLIController {
	public static String processCommand(String command) {
		if(TrackerCommands.END.matches(command)) {
			return endProgram();
		} else if (TrackerCommands.REFRESH_FILES.matches(command)) {
			return refreshFiles();
		} else if (TrackerCommands.RESET_CONNECTIONS.matches(command)) {
			return resetConnections();
		} else if (TrackerCommands.LIST_PEERS.matches(command)) {
			return listPeers();
		} else if (TrackerCommands.LIST_FILES.matches(command)) {
			return listFiles(command);
		} else if (TrackerCommands.GET_RECEIVES.matches(command)) {
			return getReceives(command);
		} else if (TrackerCommands.GET_SENDS.matches(command)) {
			return getSends(command);
		}
		return CLICommands.invalidCommand;
		// Process tracker CLI commands
		// 1. Check command type (END_PROGRAM, REFRESH_FILES, RESET_CONNECTIONS, LIST_PEERS, LIST_FILES, GET_RECEIVES, GET_SENDS)
		// 2. Call appropriate handler
		// 3. Return result or error message
	}

	private static String getReceives(String command) {
		String ip = TrackerCommands.GET_RECEIVES.getGroup(command, "ip");
		int port = Integer.parseInt(TrackerCommands.GET_RECEIVES.getGroup(command, "port"));

		PeerConnectionThread connection = TrackerApp.getConnectionByIpPort(ip, port);

		if(connection == null)
			return "Peer not found.";

		Map<String, List<String>> receivedFilesMap = TrackerConnectionController.getReceives(connection);


		if (receivedFilesMap.isEmpty()) {
			return "No files received by " + ip + ":" + port;
		}

		List<String> outputLines = new ArrayList<>();

		for (Map.Entry<String, List<String>> entry : receivedFilesMap.entrySet()) {
			String receiver = entry.getKey();
			for (String fileAndHash : entry.getValue()) {
				// fileAndHash is "<filename> <hash>"
				outputLines.add(fileAndHash + " - " + receiver);
			}
		}

		Collections.sort(outputLines);

		return String.join("\n", outputLines);
		// Get list of files received by a peer
	}

	private static String getSends(String command) {
		String ip = TrackerCommands.GET_SENDS.getGroup(command, "ip");
		int port = Integer.parseInt(TrackerCommands.GET_SENDS.getGroup(command, "port"));

		PeerConnectionThread connection = TrackerApp.getConnectionByIpPort(ip, port);

		if(connection == null)
			return "Peer not found.";

		Map<String, List<String>> sentFilesMap = TrackerConnectionController.getSends(connection);


		if (sentFilesMap.isEmpty()) {
			return "No files sent by " + ip + ":" + port;
		}

		List<String> outputLines = new ArrayList<>();

		for (Map.Entry<String, List<String>> entry : sentFilesMap.entrySet()) {
			String receiver = entry.getKey();
			for (String fileAndHash : entry.getValue()) {
				// fileAndHash is "<filename> <hash>"
				outputLines.add(fileAndHash + " - " + receiver);
			}
		}

		Collections.sort(outputLines);

		return String.join("\n", outputLines);


		// Get list of files sent by a peer
	}

	private static String listFiles(String command) {
		String ip = TrackerCommands.LIST_FILES.getGroup(command, "ip");
		int port = Integer.parseInt(TrackerCommands.LIST_FILES.getGroup(command, "port"));

		PeerConnectionThread connection = TrackerApp.getConnectionByIpPort(ip, port);
		if(connection == null)
			return "Peer not found.";
		if (connection.getFileAndHashes() == null || connection.getFileAndHashes().isEmpty())
			return "Repository is empty.";
		return FileUtils.getSortedFileList(connection.getFileAndHashes());
		// List files of a peer (do not send command, use the cached list)
	}

	private static String listPeers() {
		if(TrackerApp.getConnections() == null || TrackerApp.getConnections().isEmpty()) {
			return "No peers connected.";
		}

		List<String> peerInfoList = new ArrayList<>();

		for (PeerConnectionThread connection : TrackerApp.getConnections()) {
			if(connection.getOtherSideIP() != null && connection.getOtherSidePort() != 0) {
				peerInfoList.add(connection.getOtherSideIP() + ":" + connection.getOtherSidePort());
			}
		}

		Collections.sort(peerInfoList);

		return String.join("\n", peerInfoList);
		// List all connected peers
	}

	private static String resetConnections() {
		for (PeerConnectionThread connection : TrackerApp.getConnections()) {
			connection.refreshStatus();
		}
		return refreshFiles();
		// Reset all peer connections
		// Refresh status and file list for each peer
	}

	private static String refreshFiles() {
		for (PeerConnectionThread connection : TrackerApp.getConnections()) {
			connection.refreshFileList();
		}
		return "";
		// Refresh file lists for all peers
	}

	private static String endProgram() {
		TrackerApp.endAll();
		return "";
	}
}
