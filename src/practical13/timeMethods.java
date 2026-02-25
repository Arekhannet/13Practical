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
 * @a AREKHANNE TSHILAMULELE
 * @v25 February 2026
 */

public class timeMethods {
    public static int N = 32654;

    // Main method
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
        System.out.println("Student: AREKHANNE TSHILAMULELE");
        System.out.println("Date: 25 February 2026");
        System.out.println("==================================================");
        System.out.println("Repetitions: " + repetitions);
        System.out.println();
        
        // Read data from file
        Node[] records = readDataFromFile("ulysses.numbered");
        
        if (records == null || records.length == 0) {
            System.out.println("ERROR: Could not read data file!");
            System.out.println("Make sure 'ulysses.numbered' is in the project root folder");
            return;
        }
        
        System.out.println("Successfully loaded " + records.length + " records");
        System.out.println();
        
        // Create sorted copy for binary search
        Node[] sortedRecords = records.clone();
        Arrays.sort(sortedRecords, (a, b) -> Integer.compare(a.key, b.key));
        
        // Generate 30 random keys
        int[] searchKeys = generateRandomKeys(30, 1, 32654);
        
        System.out.println("\nReady to start timing...");
        System.out.println("Will search for " + searchKeys.length + " keys in each run");
        
        // ========== TIME LINEAR SEARCH ==========
        System.out.println("\n--- Timing Linear Search ---");
        runTimeLinear = 0;
        runTime2Linear = 0;
        
        for(int repetition = 0; repetition < repetitions; repetition++) {
            start = System.currentTimeMillis();
            
            // Search for all 30 keys using linear search
            for (int key : searchKeys) {
                linearsearch(records, key);
            }
            
            finish = System.currentTimeMillis();
            time = (double)(finish - start);
            runTimeLinear += time;
            runTime2Linear += (time * time);
            
            // Progress indicator every 5 runs
            if ((repetition + 1) % 5 == 0) {
                System.out.println("  Completed " + (repetition + 1) + "/" + repetitions + " linear search runs");
            }
        }
        
        // ========== TIME BINARY SEARCH ==========
        System.out.println("\n--- Timing Binary Search ---");
        runTimeBinary = 0;
        runTime2Binary = 0;
        
        for(int repetition = 0; repetition < repetitions; repetition++) {
            start = System.currentTimeMillis();
            
            // Search for all 30 keys using binary search
            for (int key : searchKeys) {
                binarysearch(sortedRecords, key);
            }
            
            finish = System.currentTimeMillis();
            time = (double)(finish - start);
            runTimeBinary += time;
            runTime2Binary += (time * time);
            
            // Progress indicator every 5 runs
            if ((repetition + 1) % 5 == 0) {
                System.out.println("  Completed " + (repetition + 1) + "/" + repetitions + " binary search runs");
            }
        }
        
        // ========== CALCULATE STATISTICS ==========
        double aveRuntimeLinear = runTimeLinear / repetitions;
        double stdDeviationLinear = calculateStdDev(runTime2Linear, aveRuntimeLinear, repetitions);
        
        double aveRuntimeBinary = runTimeBinary / repetitions;
        double stdDeviationBinary = calculateStdDev(runTime2Binary, aveRuntimeBinary, repetitions);
        
        // ========== OUTPUT RESULTS ==========
        System.out.println("\n\n" + "=".repeat(60));
        System.out.println("FINAL RESULTS - THE FOUR REQUIRED NUMBERS");
        System.out.println("=".repeat(60));
        System.out.println("\nLINEAR SEARCH:");
        System.out.println("  Average time:       " + fiveD.format(aveRuntimeLinear) + " ms");
        System.out.println("  Standard Deviation: " + fourD.format(stdDeviationLinear) + " ms");
        System.out.println();
        System.out.println("BINARY SEARCH:");
        System.out.println("  Average time:       " + fiveD.format(aveRuntimeBinary) + " ms");
        System.out.println("  Standard Deviation: " + fourD.format(stdDeviationBinary) + " ms");
        System.out.println("\n" + "=".repeat(60));
        System.out.println("\nCOPY THESE FOUR NUMBERS FOR SUBMISSION:");
        System.out.println("==================================================");
        System.out.println(aveRuntimeLinear + " (Linear Avg)");
        System.out.println(stdDeviationLinear + " (Linear StdDev)");
        System.out.println(aveRuntimeBinary + " (Binary Avg)");
        System.out.println(stdDeviationBinary + " (Binary StdDev)");
        System.out.println("==================================================");
        
        // Optional: Show the difference
        double speedup = aveRuntimeLinear / aveRuntimeBinary;
        System.out.println("\nBinary search is approximately " + twoD.format(speedup) + "x faster than linear search");
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
            int lineCount = 0;
            
            System.out.println("Reading file: " + filename);
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("\\s+", 2);
                if (parts.length >= 2) {
                    try {
                        int key = Integer.parseInt(parts[0]);
                        String data = parts[1];
                        nodeList.add(new Node(key, data));
                        lineCount++;
                    } catch (NumberFormatException e) {
                        System.out.println("Warning: Skipping line - invalid key format: " + parts[0]);
                    }
                }
            }
            reader.close();
            
            System.out.println("Loaded " + lineCount + " valid records from file");
            return nodeList.toArray(new Node[0]);
            
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: File '" + filename + "' not found!");
            System.err.println("Current directory: " + System.getProperty("user.dir"));
            return null;
        } catch (IOException e) {
            System.err.println("ERROR reading file: " + e.getMessage());
            return null;
        }
    }
    
    // Method to generate random keys
    static int[] generateRandomKeys(int count, int min, int max) {
        Random rand = new Random();
        int[] keys = new int[count];
        
        System.out.println("\nGenerating " + count + " random keys in range " + min + "-" + max + "...");
        
        for (int i = 0; i < count; i++) {
            keys[i] = rand.nextInt(max - min + 1) + min;
        }
        
        // Display all keys
        System.out.println("Search keys generated:");
        for (int i = 0; i < count; i++) {
            System.out.printf("%05d ", keys[i]);
            if ((i + 1) % 10 == 0) System.out.println();
        }
        System.out.println();
        
        return keys;
    }
    
    // Linear search method
    static int linearsearch(Node[] arr, int targetKey) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].key == targetKey) {
                return i; // Found at index i
            }
        }
        return -1; // Not found
    }
    
    // Binary search method (requires sorted array)
    static int binarysearch(Node[] arr, int targetKey) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid].key == targetKey) {
                return mid; // Found at index mid
            }
            
            if (arr[mid].key < targetKey) {
                left = mid + 1; // Search right half
            } else {
                right = mid - 1; // Search left half
            }
        }
        return -1; // Not found
    }
    
    // Method to calculate standard deviation
    static double calculateStdDev(double sumSquares, double mean, int n) {
        if (n <= 1) return 0;
        // Using formula: sqrt((sum(x²) - n*mean²)/(n-1))
        double variance = (sumSquares - n * mean * mean) / (n - 1);
        return Math.sqrt(Math.max(0, variance)); // Ensure no negative due to rounding
    }
}
