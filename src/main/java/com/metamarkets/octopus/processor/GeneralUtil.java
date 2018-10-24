package com.metamarkets.octopus.processor;

import java.net.URL;


public class GeneralUtil {
	
	
	
    /* Returns true if url is valid */
    public static boolean isValidURL(String url)
    {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }
         
        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }

}
