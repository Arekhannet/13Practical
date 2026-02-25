package practical13;

import java.io.*;
import java.text.*;
import java.util.*;

public class timeMethods {
    public static int N = 32654;
    
               public static void main(String[] args) {
        System.out.println("CSC211 Practical 13: Search Algorithm Comparison");
        System.out.println("==================================================");
        
        Node[] records = readDataFromFile("ulysses.numbered");
        
        if (records != null) {
            System.out.println("Loaded " + records.length + " records");
            
            // Test key generation
            int[] testKeys = generateRandomKeys(10, 1, 32654);
        }
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
    // Method to generate random keys
    static int[] generateRandomKeys(int count, int min, int max) {
        Random rand = new Random();
        int[] keys = new int[count];
        
        System.out.println("\nGenerating " + count + " random keys...");
        
        for (int i = 0; i < count; i++) {
            keys[i] = rand.nextInt(max - min + 1) + min;
        }
        
        System.out.print("Sample keys: ");
        for (int i = 0; i < Math.min(5, count); i++) {
            System.out.printf("%05d ", keys[i]);
        }
        System.out.println();
        
        return keys;
    }
    // Linear search method
    static int linearsearch(Node[] arr, int targetKey) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].key == targetKey) {
                return i;
            }
        }
        return -1;
    }
    // Binary search method
    static int binarysearch(Node[] arr, int targetKey) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid].key == targetKey) {
                return mid;
            }
            
            if (arr[mid].key < targetKey) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    // Method to calculate standard deviation
    static double calculateStdDev(double sumSquares, double mean, int n) {
        if (n <= 1) return 0;
        double variance = (sumSquares - n * mean * mean) / (n - 1);
        return Math.sqrt(Math.max(0, variance));
    }
