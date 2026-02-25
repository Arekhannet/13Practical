package practical13;

import java.io.*;
import java.text.*;
import java.util.*;

public class timeMethods {
    public static int N = 32654;
    
    public static void main(String[] args) {
        System.out.println("CSC211 Practical 13: Search Algorithm Comparison");
        System.out.println("==================================================");
        System.out.println("Step 1: Basic structure complete");
    }
    
    // Node class for storing key-value pairs
    static class Node {
        int key;
        String data;
        
        Node(int key, String data) {
            this.key = key;
            this.data = data;
        }
    }
        // Method to read data from file
    static Node[] readDataFromFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            ArrayList<Node> nodeList = new ArrayList<>();
            String line;
            
            System.out.println("Reading file: " + filename);
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("\\s+", 2);
                if (parts.length >= 2) {
                    int key = Integer.parseInt(parts[0]);
                    String data = parts[1];
                    nodeList.add(new Node(key, data));
                }
            }
            reader.close();
            
            System.out.println("Loaded " + nodeList.size() + " records");
            return nodeList.toArray(new Node[0]);
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found! Place ulysses.numbered in project root.");
            return null;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
}
