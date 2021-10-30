/**
 * This class to the generate all Hamiltonian Cycles given an undirected or directed graph. 
 * The Hamiltonian path is defined as a path traversing all vertexes exactly once.
 * The Hamiltonian cycle is the hamiltonian path where there is an arc from the end of the path to the beginning of the path.
 * Finding a hamiltonian path, cycle, or all cycles is a NP-complete problem.
 * Backtracking approach is taken to solve the problem in this class. 
 * Given a graph (V, E), The time complexity is O(V!) and the space complexity is O(n)
 * 
 * @Author: Fei Xie
 * @Email: xiefei0117@gmail.com
 */

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

class HamiltonianCycle {
    private HamiltonianCycle() {}
    private static Map<String, List<String>> graph;
    private static Map<String, Boolean> visited;
    private static int vertexCount;
    

    public static void generateGraph(String filepath, boolean isDirected) {
        
        try {
            Scanner input = new Scanner(new File(filepath));
            List<List<String>> data = new ArrayList<>();
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] items = line.split("\\s+|,|-|;");
                String orig = items[0];
                String dest = items[1];
                List<String> pair = new ArrayList<>();
                pair.add(orig);
                pair.add(dest);
                data.add(pair);
                if (!isDirected) {
                    pair = new ArrayList<>();
                    pair.add(dest);
                    pair.add(orig);
                    data.add(pair);
                }

            }
            input.close();
            generateGraph(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static void generateGraph(List<List<String>> data) {
        assert data.size() > 0 && data.get(0).size() == 2;
        graph = new HashMap<>();
        visited = new HashMap<>();

        for (List<String> pair : data) {
            String origin = pair.get(0);
            String destination = pair.get(1);
            graph.computeIfAbsent(origin, x -> new ArrayList<>()).add(destination);
            visited.put(origin, false);
        }
        vertexCount = graph.size();
        System.out.println(graph);
    }

    public static List<List<String>> getAllHamiltonianCycles() {
        List<List<String>> ans = new ArrayList<>();

        List<String> candidate = new ArrayList<>();
        
        String start = null;
        for (String key : graph.keySet()) {
            start = key;
            break;
        }
        candidate.add(start);
        visited.put(start, true);
        backtracking(ans, candidate);
        return ans;
    }

    private static boolean isArcFromV1ToV2 (String v1, String v2) {
        List<String> adjs = graph.get(v1);

        for (String v : adjs) {
            if (v2.equals(v))
                return true;
        }
        return false;
    }

    private static void backtracking(List<List<String>> results, List<String> path) {
        if (path.size() == vertexCount && isArcFromV1ToV2(path.get(path.size() - 1), path.get(0))) {
            List<String> pathToAdd = new ArrayList<>(path);
            pathToAdd.add(path.get(0));
            results.add(pathToAdd);
            System.out.println("Number of finded pathes: " + results.size());
            return;
        }

        String curr = path.get(path.size() - 1);
        List<String> adjs = graph.get(curr);

        for (String next : adjs) {
            if (!visited.get(next)) {
                path.add(next);
                visited.put(next, true);
                backtracking(results, path);
                path.remove(path.size() - 1);
                visited.put(next, false);
            }
        }
    }

    public static void main(String[] args) {
        assert args.length == 2 : "It needs two arguments: the file containing the arcs; a boolean variable if the arcs are directed or not";
        boolean isDirected = (args[1] == "true") ? true : false;

        HamiltonianCycle.generateGraph(args[0], isDirected);
        List<List<String>> ans = HamiltonianCycle.getAllHamiltonianCycles();
        if (ans.size() == 0)
            System.out.println("There is no Hamiltonian Cycle.");
        for (List<String> path : ans) {
            System.out.println(path);
        }
    }
}