package peer.controllers;

import common.models.Message;
import common.utils.FileUtils;
import peer.app.P2TConnectionThread;
import peer.app.PeerApp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static common.models.Message.Type;
import static peer.app.PeerApp.TIMEOUT_MILLIS;

public class P2TConnectionController {
	public static Message handleCommand(Message message) {
		if(message.getType().equals(Message.Type.command)) {
			String command = message.getFromBody("command");
			switch (command) {
				case "status" :
					return status();
				case "get_files_list":
					return getFilesList();
				case "get_sends":
					return getSends();
				case "get_receives":
					return getReceives();
			}
		}
		return null;
		// Handle incoming tracker-to-peer commands
		// 1. Parse command from message
		// 2. Call appropriate handler (status, get_files_list, get_sends, get_receives)
		// 3. Return response message
	}

	private static Message getReceives() {
		Map<String, Object> body = new HashMap<>();
		body.put("command", "get_receives");
		body.put("response", "ok");
		body.put("received_files", PeerApp.getReceivedFiles());
		return new Message(body, Type.response);

		// Return information about received files

	}

	private static Message getSends() {
		Map<String, Object> body = new HashMap<>();
		body.put("command", "get_sends");
		body.put("response", "ok");
		body.put("sent_files", PeerApp.getSentFiles());
		return new Message(body, Type.response);
		// Return information about sent files
	}

	public static Message getFilesList() {
		Map<String, Object> body = new HashMap<>();
		body.put("command", "get_files_list");
		body.put("response", "ok");
		body.put("files", FileUtils.listFilesInFolder(PeerApp.getSharedFolderPath()));
		return new Message(body, Type.response);
		// Return list of files in shared folder
	}

	public static Message status() {
		Map<String, Object> body = new HashMap<>();
		body.put("command", "status");
		body.put("response", "ok");
		body.put("peer", PeerApp.getPeerIP());
		body.put("listen_port", PeerApp.getPeerPort());
		return new  Message(body, Type.response);
		// Return peer status information
	}

	public static Message sendFileRequest(P2TConnectionThread tracker, String fileName) throws Exception {
		Message request = new Message(Map.of("name", fileName), Type.file_request);
		Message response = tracker.sendAndWaitForResponse(request, TIMEOUT_MILLIS);

		if(response == null) {
			throw new RuntimeException();
		}

		return response;
//		if(response != null) {
//			if ("error".equals(response.getFromBody("response"))) {
//				if(response.getFromBody("error").equals("not_found")) {
//					throw new RuntimeException("No peer has the file!");
//				}
//				if(request.getFromBody("error").equals("multiple_hash")) {
//					throw new RuntimeException("Multiple hashes found!");
//				}
//			}
//			if("peer_found".equals(request.getFromBody("response"))) {
//				return response;
//			}
//		}

		// Send file request to tracker and handle response
		// 1. Build request message
		// 2. Send message and wait for response
		// 3. raise exception if error or return response
	}
}
