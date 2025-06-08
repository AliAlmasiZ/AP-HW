package tracker.app;

import common.models.ConnectionThread;
import common.models.Message;
import tracker.controllers.TrackerConnectionController;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static common.models.Message.Type;
import static tracker.app.TrackerApp.TIMEOUT_MILLIS;

public class PeerConnectionThread extends ConnectionThread {
	private HashMap<String, String> fileAndHashes;

	public PeerConnectionThread(Socket socket) throws IOException {
		super(socket);
	}

	@Override
	public boolean initialHandshake() {
		try {
			refreshStatus();
			refreshFileList();
			TrackerApp.addPeerConnection(this);
			return true;
			// Implement initial handshake
			// Refresh peer status (IP and port), Get peer's file list, Add connection to tracker's connection list
		} catch (Exception e) {
			return false;
		}
	}

	public void refreshStatus() {


        Message command = new Message(Map.of("command", "status"), Type.command);
        Message response = sendAndWaitForResponse(command, TIMEOUT_MILLIS);
        if (response != null) {
            String ip = response.getFromBody("");
            int port = response.getIntFromBody("");

            this.setOtherSideIP(ip);
            this.setOtherSidePort(port);
        }
		TrackerApp.removePeerConnection(this);
		this.end();

        // Implement status refresh
		// Send status command and update peer's IP and port and wait for response
		// then update peer's IP and port
	}

	public void refreshFileList() {
		Map<String, Object> body = new HashMap<>();
		body.put("command", "get_files_list");
		Message command = new Message(body, Type.command);
		Message response = sendAndWaitForResponse(command, TIMEOUT_MILLIS);
		if(response != null) {
			Map<String, String> files = response.getFromBody("files");
			fileAndHashes = new HashMap<>(files);
		}
		// Implement file list refresh
		// Request and update peer's file list
	}

	@Override
	protected boolean handleMessage(Message message) {
		if (message.getType() == Message.Type.file_request) {
			sendMessage(TrackerConnectionController.handleCommand(message));
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		super.run();
		TrackerApp.removePeerConnection(this);
	}

	public Map<String, String> getFileAndHashes() {
		return Map.copyOf(fileAndHashes);
	}
}
