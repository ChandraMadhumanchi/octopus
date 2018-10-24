package com.metamarkets.octopus;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.metamarkets.octopus.processor.Processor;

public class Client {

	private static final String FILE_WITH_URLS = "./src/main/resources/url.txt";
	
	private static final int NO_OF_THREADS = 3;

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

		if ( args.length < 0) {
			System.out.println("Enter valid inputs!!");
			return;
		} 
		
		System.out.println("Process started !!");
		
		//java -jar octopus.jar -n 8 -l 1024k -o breakfast_folder -i ingredients.txt
		
		String breakfast_folder = args[5];
		
		String FILE_WITH_URLS1 = args[7]; 
		
		createDirectoryIfNotExists(breakfast_folder);
		
		ExecutorService executor = Executors.newFixedThreadPool(NO_OF_THREADS);

		final List<String> urlFromList = Processor.getURLList(FILE_WITH_URLS1);

		List<Future<Integer>> futureList = new ArrayList<>();

		for (final String id : urlFromList) {

			Callable<Integer> task = () -> {
				
				Processor.downloadFile(id,breakfast_folder);
				return 0;
				
			};

			Future<Integer> future = executor.submit(task);
			
			futureList.add(future);

		}

		for (Future<Integer> future : futureList) {
			try {
				
				future.get();
				
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		executor.shutdown();

	}
	
	public static void createDirectoryIfNotExists(String folder) {

		File theDir = new File(folder);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			//System.out.println("creating directory: " + theDir.getName());
			boolean result = false;
			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				throw se;
			}
			if (result) {
				System.out.println("DIR created");
			}
		}
	}
	
}