package tracker.controllers;

import common.models.Message;
import tracker.app.PeerConnectionThread;
import tracker.app.TrackerApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tracker.app.TrackerApp.TIMEOUT_MILLIS;

public class TrackerConnectionController {
	public static Message handleCommand(Message message) {
		if(message.getType().equals(Message.Type.file_request)) {
			String fileName = message.getFromBody("name");
			String hash = null;
			PeerConnectionThread hasThat = null;
			for (PeerConnectionThread connection : TrackerApp.getConnections()) {
				 String newHash =  connection.getFileAndHashes().get(fileName);
				 if(hash != null && newHash != null) {
					 if(!hash.equals(newHash))  {
						 Map<String, Object> body = new HashMap<>();
						 body.put("response", "error");
						 body.put("error", "multiple_hash");
						 return new Message(body, Message.Type.response);
					 }
				 }
				 if(newHash != null) {
					 hasThat = connection;
					 hash = newHash;
				 }
			}
			if(hash == null) {
				Map<String, Object> body = new HashMap<>();
				body.put("response", "error");
				body.put("error", "not_found");
				return new Message(body, Message.Type.response);
			}
			Map<String, Object> body = new HashMap<>();
			body.put("response", "peer_found");
			body.put("md5", hash);
			body.put("peer_have", hasThat.getOtherSideIP());
			body.put("peer_port", hasThat.getOtherSidePort());
			return new Message(body, Message.Type.response);

		}
		return null;
		// Handle incoming peer-to-tracker commands
		// 1. Validate message type and content
		// 2. Find peers having the requested file
		// 3. Check for hash consistency
		// 4. Return peer information or error
	}

	public static Map<String, List<String>> getSends(PeerConnectionThread connection) {
		Message command = new Message(Map.of("command", "get_sends"), Message.Type.command);
		Message response = connection.sendAndWaitForResponse(command, TIMEOUT_MILLIS);

		return response.getFromBody("sent_files");
		// Get list of files sent by a peer
		// 1. Build command message
		// 2. Send message and wait for response
		// 3. Parse and return sent files map
	}

	public static Map<String, List<String>> getReceives(PeerConnectionThread connection) {
		Message command = new Message(Map.of("command", "get_receives"), Message.Type.command);
		Message response = connection.sendAndWaitForResponse(command, TIMEOUT_MILLIS);

		return response.getFromBody("received_files");
		// Get list of files received by a peer
		// 1. Build command message
		// 2. Send message and wait for response
		// 3. Parse and return received files map
	}
}
