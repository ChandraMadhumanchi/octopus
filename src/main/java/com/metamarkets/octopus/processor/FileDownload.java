package com.metamarkets.octopus.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class FileDownload {

	public static void downloadWithJavaNIO(String fileURL, String localFilename, String folderPath) throws IOException {

		long startTime = System.currentTimeMillis();

		URLConnection connection = new URL(fileURL).openConnection();
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		connection.connect();

		InputStream reader = connection.getInputStream();
		int bytesRead = 0;
		try (ReadableByteChannel readableByteChannel = Channels.newChannel(connection.getInputStream());
				FileOutputStream fileOutputStream = new FileOutputStream(folderPath + "/" + localFilename);
				FileChannel fileChannel = fileOutputStream.getChannel()) {

			fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

			final ByteBuffer buffer = ByteBuffer.allocateDirect(2 * 1024);

			bytesRead = readableByteChannel.read(buffer);
		}

		long endTime = System.currentTimeMillis();

		System.out.println("Running time, seconds: " + new Long(endTime - startTime).toString() + " millseconds).\n");

		System.out.println("Bytes downloaded: " + new Long(endTime - startTime).toString() + " millseconds).\n");

		System.out.println("\n");

	}

}
