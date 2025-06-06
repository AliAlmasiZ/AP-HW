package common.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


public class FileUtils {

	public static Map<String, String> listFilesInFolder(String folderPath) {
		Map<String, String> filesMap = new HashMap<>();
		Path dir = Paths.get(folderPath);

		if (!Files.isDirectory(dir)) {
			System.err.println("Not a directory: " + dir);
			return Collections.emptyMap();
		}

		try	(Stream<Path> stream = Files.list(dir)) {
			List<Path> files = stream.toList();
			for (Path file : files) {
				filesMap.put(file.getFileName().toString(), MD5Hash.HashFile(file.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filesMap;
	}

	public static String getSortedFileList(Map<String, String> files) {
		if(files.isEmpty())
			return "";
		List<String> names =  files.keySet().stream().sorted().toList();
		StringBuilder sb = new StringBuilder();
		for (String name : names) {
			sb.append(name).append(" ").append(files.get(name)).append("\n");
		}
		return sb.toString();
	}
}
