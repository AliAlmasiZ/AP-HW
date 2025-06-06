package peer.app;

import common.utils.MD5Hash;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class TorrentP2PThread extends Thread {
	private final Socket socket;
	private final File file;
	private final String receiver;
	private final BufferedOutputStream dataOutputStream;

	public TorrentP2PThread(Socket socket, File file, String receiver) throws IOException {
		this.socket = socket;
		this.file = file;
		this.receiver = receiver;
		this.dataOutputStream = new BufferedOutputStream(socket.getOutputStream());
		PeerApp.addTorrentP2PThread(this);
	}

	@Override
	public void run() {
		try(FileInputStream fis= new FileInputStream(file);) {
			byte[] buffer = new byte[4 * 1024];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				dataOutputStream.write(buffer, 0, bytesRead);
			}

			dataOutputStream.flush();

			String fileHash = MD5Hash.HashFile(this.file.getAbsolutePath());
			String fileNameAndHash = this.file.getName() + " " + fileHash;
			PeerApp.addSentFile(receiver, fileNameAndHash);

		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
		// Implement file transfer
		// 1. Open file input stream
		// 2. Read file in chunks and send to peer
		// 3. Flush and close output stream
		// 4. Update sent files list with file name and MD5 hash

		try {
			socket.close();
		} catch (Exception ignored) {}

		PeerApp.removeTorrentP2PThread(this);
	}

	public void end() {
		try {
			dataOutputStream.close();
			socket.close();
		} catch (Exception ignored) {}
	}
}
