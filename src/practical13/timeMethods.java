package practical13;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * CSC211 Practical 13
 * Comparing Linear Search vs Binary Search Performance
 * 
 * This program times both search algorithms using data from ulysses.numbered
 * Runs 30 repetitions with 30 random keys each time
 * Outputs average time and standard deviation for both algorithms
 * 
 * @AREKHANNE TSHILAMULELE
 * @25 February 2026
 */

public class timeMethods {
    public static int N = 32654;

    // Main method Methodo MUHULWANE
        public static void main(String[] args) {
        DecimalFormat fourD = new DecimalFormat("0.0000");
        DecimalFormat fiveD = new DecimalFormat("0.00000");
        
        long start, finish;
        double runTimeLinear = 0, runTime2Linear = 0;
        double runTimeBinary = 0, runTime2Binary = 0;
        double time;
        int repetitions = 30;
        
        System.out.println("CSC211 Practical 13: Search Algorithm Comparison");
        System.out.println("==================================================");
        System.out.println("Repetitions: " + repetitions);
        System.out.println();
        
        // Read data
        Node[] records = readDataFromFile("ulysses.numbered");
        
        if (records == null || records.length == 0) {
            System.out.println("ERROR: Could not read data file!");
            return;
        }
        
        // Create sorted copy
        Node[] sortedRecords = records.clone();
        Arrays.sort(sortedRecords, (a, b) -> Integer.compare(a.key, b.key));
        
        // Generate 30 random keys
        int[] searchKeys = generateRandomKeys(30, 1, 32654);
        
        System.out.println("\nReady to start timing...");
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
}

        // Time Linear Search
        System.out.println("\n--- Timing Linear Search ---");
        runTimeLinear = 0;
        runTime2Linear = 0;
        
        for(int repetition = 0; repetition < repetitions; repetition++) {
            start = System.currentTimeMillis();
            
            for (int key : searchKeys) {
                linearsearch(records, key);
            }
            
            finish = System.currentTimeMillis();
            time = (double)(finish - start);
            runTimeLinear += time;
            runTime2Linear += (time * time);
            
            if ((repetition + 1) % 5 == 0) {
                System.out.println("  Completed " + (repetition + 1) + "/" + repetitions + " runs");
            }
        }
        // Calculate statistics
        double aveRuntimeLinear = runTimeLinear / repetitions;
        double stdDeviationLinear = calculateStdDev(runTime2Linear, aveRuntimeLinear, repetitions);
        
        double aveRuntimeBinary = runTimeBinary / repetitions;
        double stdDeviationBinary = calculateStdDev(runTime2Binary, aveRuntimeBinary, repetitions);
        
        // Output results
        System.out.println("\n\n" + "=".repeat(50));
        System.out.println("FINAL RESULTS - THE FOUR REQUIRED NUMBERS");
        System.out.println("=".repeat(50));
        System.out.println("\nLINEAR SEARCH:");
        System.out.println("  Average time: " + fiveD.format(aveRuntimeLinear) + " ms");
        System.out.println("  Std Deviation: " + fourD.format(stdDeviationLinear) + " ms");
        System.out.println();
        System.out.println("BINARY SEARCH:");
        System.out.println("  Average time: " + fiveD.format(aveRuntimeBinary) + " ms");
        System.out.println("  Std Deviation: " + fourD.format(stdDeviationBinary) + " ms");
        System.out.println("\n" + "=".repeat(50));
        System.out.println("\nCOPY THESE FOUR NUMBERS:");
        System.out.println(aveRuntimeLinear + " " + stdDeviationLinear + 
                           " " + aveRuntimeBinary + " " + stdDeviationBinary);
