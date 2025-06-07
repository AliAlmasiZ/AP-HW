package peer.app;

import common.models.Message;
import common.utils.JSONUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static peer.app.PeerApp.TIMEOUT_MILLIS;

public class P2PListenerThread extends Thread {
	private final ServerSocket serverSocket;

	public P2PListenerThread(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
	}

	private void handleConnection(Socket socket) throws Exception {
		if(socket == null) return;
		socket.setSoTimeout(TIMEOUT_MILLIS);
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		DataInputStream dis = new DataInputStream(socket.getInputStream());

		Message response = JSONUtils.fromJson(dis.readUTF());
		if (response.getType().equals(Message.Type.download_request)) {
			TorrentP2PThread torrentP2PThread = new TorrentP2PThread(
					socket,
					new File(PeerApp.getSharedFolderPath() + "/" + response.getFromBody("name")),
					response.getFromBody("receiver_ip") + ":" + response.getFromBody("receiver_port")
			);
			PeerApp.addTorrentP2PThread(torrentP2PThread);
			torrentP2PThread.start();
		} else {
			socket.close();
		}


		// Implement peer connection handling
		// 1. Set socket timeout
		// 2. Read incoming message
		// 3. Parse message type
		// 4. Handle download requests by starting a new TorrentP2PThread
		// 5. Close socket for other message types (EOF)
	}

	@Override
	public void run() {
		while (!PeerApp.isEnded()) {
			try {
				Socket socket = serverSocket.accept();
				handleConnection(socket);
			} catch (Exception e) {
				break;
			}
		}

		try {serverSocket.close();} catch (Exception ignored) {}
	}
}
