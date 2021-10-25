import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.lang.Double;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;



class Dijsktra {

    private Dijsktra() {}

    static private Map<String, List<SimpleEntry<String, Double>>> graph;
    static private Map<String, Double> distMap;
    static private PriorityQueue<SimpleEntry<String, SimpleEntry<String, Double>>> heap;
    static private Map<String, String> parentMap;
    static private String origin;
    static private String dummy = "dummy";

    private static void generateGraph(String filepath) {
        graph = new HashMap<>();
        
        try {
            Scanner input = new Scanner(new File(filepath));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] items = line.split("\\s+|,|-|;", 3);
                String orig = items[0];
                String dest = items[1];
                Double dist = Double.valueOf(items[2]);

                //add each line's info into the graph
                graph.computeIfAbsent(orig, x -> new ArrayList<>()).add(new SimpleEntry(dest, dist));
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static void assertOnResultsAvailable(String o, String d) {
        assert o.equals(origin) : "Mismatch in origin between " + origin + " and " + o;

        assert distMap.containsKey(d) : "Paths have not yet been calculated between " + o + " and " + d + "; or no path exists.";
    }

    public static double getDistance(String o, String d) {
        assertOnResultsAvailable(o, d);
        return distMap.get(d);
    }

    public static List<String> getPath(String o, String d) {
        assertOnResultsAvailable(o, d);
        List<String> list = new ArrayList<>();
        getPathHelper(d, list);
        return list;
    }

    private static void getPathHelper(String curr, List<String> list) {
        if (!parentMap.get(curr).equals(dummy)) {
            getPathHelper(parentMap.get(curr), list);
        }
        list.add(curr);
    }

    public static SimpleEntry<Double, List<String>> calculatePath(String o, String d) {
        heap = new PriorityQueue<>((x1, x2) -> x1.getValue().getValue().compareTo(x2.getValue().getValue()));
        parentMap = new HashMap<>();
        distMap = new HashMap<>();
        origin = o;

        heap.offer(new SimpleEntry(origin, new SimpleEntry(dummy, 0.0)));

        while (!heap.isEmpty() && !distMap.containsKey(d)) {
            SimpleEntry<String, SimpleEntry<String, Double>> entry = heap.poll();
            String curr = entry.getKey();
            String parent = entry.getValue().getKey();
            Double cummDist = entry.getValue().getValue();

            if (!distMap.containsKey(curr)) {
                distMap.put(curr, cummDist);
                parentMap.put(curr, parent);
                List<SimpleEntry<String, Double>> adjs = graph.get(curr);
                for (SimpleEntry<String, Double> adj : adjs) {
                    heap.offer(new SimpleEntry(adj.getKey(), new SimpleEntry(curr, adj.getValue() + cummDist)));
                }
            }
        }
        double distance = getDistance(o, d);
        List<String> path = getPath(o, d);
        return new SimpleEntry(distance, path);
    }
    
    public static void main(String[] args) {
        assert args.length == 3 : "It needs three arguments: distance file, origin, and destination.";
        String filename = args[0];
        String o = args[1];
        String d = args[2];
        System.out.println("input: " + filename + " " + o + " " + d);
        Dijsktra.generateGraph(filename);
        SimpleEntry<Double, List<String>> ans = Dijsktra.calculatePath(o, d);
        System.out.println("Distance : " + ans.getKey());
        System.out.println("Path : " + ans.getValue());
    }
}