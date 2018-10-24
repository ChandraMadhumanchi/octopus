package com.metamarkets.octopus.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FilenameUtils;

public class Processor {

	private static final String FILE_DOWNLOADS = "./fileDownloads";

	public static List<String> getURLList(String filePath) {
		List<String> list = new ArrayList<String>();

		Path path = Paths.get(filePath);
		try (Stream<String> stream = Files.lines(path)) {
			stream.forEach((k) -> {
				if (!k.trim().isEmpty() && GeneralUtil.isValidURL(k)) {
					list.add(k);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	public static void downloadFile(String fileURL,String breakfast_folder) throws Exception {
		
		System.out.println("downloadFile ");
		
		if (null != fileURL) {
			try {

				URL url = new URL(fileURL);

				String fileName = FilenameUtils.getName(url.getPath());
				
				//System.out.println("fileURL :" +  fileURL);
				
				//System.out.println("fileName :"+ fileName);
				
				//System.out.println("FILE_DOWNLOADS :"+ FILE_DOWNLOADS);
				
				FileDownload.downloadWithJavaNIO(fileURL, fileName, breakfast_folder);

				

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		
		
	}
	
	
}
