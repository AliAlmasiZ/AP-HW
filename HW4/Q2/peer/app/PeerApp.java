package peer.app;

import common.models.Message;
import common.utils.FileUtils;
import common.utils.JSONUtils;
import common.utils.MD5Hash;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

public class PeerApp {
	public static final int TIMEOUT_MILLIS = 500;
	private static String ip;
	private static int port;

	//files
	private static String sharedFolderPath;
	private static Map<String, List<String> > sentFiles = new HashMap<>();
	private static Map<String, List<String>> receivedFiles = new HashMap<>();

	//threads
	private static P2TConnectionThread p2TConnectionThread;
	private static P2PListenerThread listenerThread;
	private static List<TorrentP2PThread> torrentP2PThreads;

	//  static fields for peer's ip, port, shared folder path, sent files, received files, tracker connection thread, p2p listener thread, torrent p2p threads

	private static boolean exitFlag = false;

	public static boolean isEnded() {
		return exitFlag;
	}

	public static void initFromArgs(String[] args) throws Exception {
		String[] selfAddress = args[0].split(":");
		ip = selfAddress[0];
		port = Integer.parseInt(selfAddress[1]);
		String[] trackerAddress = args[1].split(":");

		sharedFolderPath = args[2];
		p2TConnectionThread = new P2TConnectionThread(new Socket(trackerAddress[0], Integer.parseInt(trackerAddress[1])));
		listenerThread = new P2PListenerThread(port);
		//  Initialize peer with command line arguments
		// 1. Parse self address (ip:port)
		// 2. Parse tracker address (ip:port)
		// 3. Set shared folder path
		// 4. Create tracker connection thread
		// 5. Create peer listener thread
	}

	public static void endAll() {
		exitFlag = true;
		p2TConnectionThread.end();
		for (TorrentP2PThread thread : torrentP2PThreads) {
			thread.end();
		}
		torrentP2PThreads.clear();
		sentFiles.clear();
		receivedFiles.clear();

		//  Implement cleanup
		// 1. End tracker connection
		// 2. End all torrent threads
		// 3. Clear file lists
	}

	public static void connectTracker() {
		if(p2TConnectionThread != null && !p2TConnectionThread.isAlive())
			p2TConnectionThread.start();
		//  Start tracker connection thread
		// Check if thread exists and not running, then Start thread
	}

	public static void startListening() {
		if(listenerThread != null && !listenerThread.isAlive())
			listenerThread.start();
		//  Start peer listener thread
		// Check if thread exists and not running, then Start thread
	}

	public static void removeTorrentP2PThread(TorrentP2PThread torrentP2PThread) {
		if(torrentP2PThread != null) {
			torrentP2PThreads.remove(torrentP2PThread);
			torrentP2PThread.end();
		}
		//  Remove and cleanup torrent thread
	}

	public static void addTorrentP2PThread(TorrentP2PThread torrentP2PThread) {
		if(torrentP2PThread != null && !torrentP2PThreads.contains(torrentP2PThread))
			torrentP2PThreads.add(torrentP2PThread);
		//  Add new torrent thread
		// 1. Check if thread is valid
		// 2. Check if already exists
		// 3. Add to list
	}

	public static String getSharedFolderPath() {
		return sharedFolderPath;
		//  Get shared folder path
	}

	public static void addSentFile(String receiver, String fileNameAndHash) {
		sentFiles.getOrDefault(receiver, new ArrayList<>()).add(fileNameAndHash);
		//  Add file to sent files list
	}

	public static void addReceivedFile(String sender, String fileNameAndHash) {
		receivedFiles.getOrDefault(sender, new ArrayList<>()).add(fileNameAndHash);
		//  Add file to received files list
	}

	public static String getPeerIP() {
		return ip;
		//  Get peer IP address
	}

	public static int getPeerPort() {
		return port;
		//  Get peer port
	}

	public static Map<String, List<String>> getSentFiles() {
		return Map.copyOf(sentFiles);
		//  Get copy of sent files map
	}

	public static Map<String, List<String>> getReceivedFiles() {
		return Map.copyOf(receivedFiles);
		//  Get copy of received files map
	}

	public static P2TConnectionThread getP2TConnection() {
		return p2TConnectionThread;
		//  Get tracker connection thread
	}

	public static void requestDownload(String ip, int port, String filename, String md5) throws Exception {
		File file = new File(getSharedFolderPath(), filename);
		if(file.exists()) {
//			System.out.println("You already have the file!");
			throw new FileAlreadyExistsException(filename);
		}

		Message downloadRequest = new Message(Map.of(
			"name", filename,
				"md5", md5,
				"receiver_ip", PeerApp.ip,
				"receiver_port", PeerApp.port
		), Message.Type.download_request);
		try {
			Socket socket = new Socket(ip, port);
			socket.setSoTimeout(TIMEOUT_MILLIS);
			DataOutputStream dos =  new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());

			dos.writeUTF(JSONUtils.toJson(downloadRequest));
			dos.flush();


			try (FileOutputStream fos = new FileOutputStream(file)) {
				byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				while((bytesRead = dis.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesRead);
				}
				fos.flush();
				String newMD5 = MD5Hash.HashFile(file.getAbsolutePath());
				if(newMD5 == null || !newMD5.equals(md5)) {
					file.delete();
					throw new RuntimeException("The file has been downloaded from peer but is corrupted!");
//					throw new RuntimeException("The file has been downloaded from peer but is corrupted!");
				}
				String sender = ip + ":" + port;
				String fileNameAndHash = filename + " " + md5;
				addReceivedFile(sender, fileNameAndHash);
				socket.setSoTimeout(0);
//				throw new RuntimeException("File downloaded successfully: " + filename);
			}
		} catch (SocketTimeoutException e) {
			System.err.println("Request Timed out.");
		}




		// Implement file download from peer
		// 1. Check if file already exists
		// 2. Create download request message
		// 3. Connect to peer
		// 4. Send request
		// 5. Receive file data
		// 6. Save file
		// 7. Verify file integrity
		// 8. Update received files list
	}
}
