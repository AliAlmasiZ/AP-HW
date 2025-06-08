package peer.controllers;

import common.models.CLICommands;
import common.models.Message;
import common.utils.FileUtils;
import peer.app.P2TConnectionThread;
import peer.app.PeerApp;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Map;

public class PeerCLIController {

	public static String processCommand(String command) {
		if(PeerCommands.DOWNLOAD.matches(command)) {
			return handleDownload(command);
		} else if (PeerCommands.LIST.matches(command)) {
			return handleListFiles();
		} else if (PeerCommands.END.matches(command)) {
			return endProgram();
		}
		return CLICommands.invalidCommand;
		// Process Peer CLI commands
		// 1. Check command type (END_PROGRAM, DOWNLOAD, LIST)
		// 2. Call appropriate handler
		// 3. Return result or error message
	}

	private static String handleListFiles() {
		Map<String, String> files = FileUtils.listFilesInFolder(PeerApp.getSharedFolderPath());
		if(files == null || files.isEmpty())
			return "Repository is empty.";
		return FileUtils.getSortedFileList(files);
		// Handle list files command
	}

	private static String handleDownload(String command) {
		try {
			String filename =  PeerCommands.DOWNLOAD.getGroup(command, "fileName");
			P2TConnectionThread tracker = PeerApp.getP2TConnection();
			Message response = P2TConnectionController.sendFileRequest(tracker, filename);

			if ("error".equals(response.getFromBody("response"))) {
				if(response.getFromBody("error").equals("not_found")) {
					return "No peer has the file!";
				}
				if(response.getFromBody("error").equals("multiple_hash")) {
					return "Multiple hashes found!";
				}
			}

			String md5 = response.getFromBody("md5");
			String ip = response.getFromBody("peer_have");
			int port = response.getIntFromBody("peer_port");

			PeerApp.requestDownload(ip, port, filename, md5);
			return "File downloaded successfully: " + filename;
		} catch (FileAlreadyExistsException alreadyExistsException) {
			return "You already have the file!";
		}
		catch (Exception e) {
            return e.getMessage();
        }
        // Handle download command
		// Send file request to tracker
		// Get peer info and file hash
		// Request file from peer
		// Return success or error message
	}

	public static String endProgram() {
		PeerApp.endAll();
		return "";
	}
}
