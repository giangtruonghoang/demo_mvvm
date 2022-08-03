package net.dirox.c4p.common.extension;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ConvertBetweenURLAndURI {
     
    public static void main(String[] args) {
         
        URI uri = null;
        URL url = null;
 
        // Create a URI
        try {
            uri = new URI("http://www.javacodegeeks.com/");
            System.out.println("URI created: " + uri);
        }
        catch (URISyntaxException e) {
            System.out.println("URI Syntax Error: " + e.getMessage());
        }
 
        // Convert URI to URL
        try {
            url = uri.toURL();
            System.out.println("URL from URI: " + url);
        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
         
        // Create a URL
        try {
            url = new URL("http://examples.javacodegeeks.com/");
            System.out.println("URL created: " + url);
        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
 
        // Convert a URL to a URI
        try {
            uri = url.toURI();
            System.out.println("URI from URL: " + uri);
        }
        catch (URISyntaxException e) {
            System.out.println("URI Syntax Error: " + e.getMessage());
        }
         
    }
 
}