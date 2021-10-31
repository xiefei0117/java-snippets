/**
 * This class contains the Kosaraju's algorithm to detect the strongly connected components (SCCs) with in a directed grah
 * @Author: Fei Xie
 * @Email: xiefei0117@gmail.com
 */

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

public class Kosaraju {

    private Kosaraju() {}

    private static Map<String, List<String>> graph;
    private static Map<String, List<String>> graphT; //transposed graph
    private static ArrayDeque<String> stack;

    private static List<List<String>> SCC;

    private static void generateGraph(String filepath) {
        graph = new HashMap<>();
        graphT = new HashMap<>();
        
        try {
            Scanner input = new Scanner(new File(filepath));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] items = line.split("\\s+|,|-|;", 2);
                String orig = items[0];
                String dest = items[1];

                //add each line's info into the graph
                graph.computeIfAbsent(orig, x -> new ArrayList<>()).add(dest);
                graphT.computeIfAbsent(dest, x-> new ArrayList<>()).add(orig);
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static List<List<String>> findSCC(String filename) {
        generateGraph(filename);
        stack = new ArrayDeque<>();
        SCC = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String vertex : graph.keySet()) {
            firstDFSPass(visited, vertex);
        }
        visited = new HashSet<>();
        while (!stack.isEmpty()) {
            String v = stack.pop();
            if (visited.contains(v))
                continue;
            SCC.add(new ArrayList<>()); //new list of components in the same SCC
            List<String> currList = SCC.get(SCC.size() - 1);
            secondDFSPass(visited, v, currList);
        }
        return SCC;
    }

    private static void firstDFSPass(Set<String> visited, String vertex) {   
        
        if (visited.contains(vertex))
            return;
        visited.add(vertex);
        List<String> adjs = graph.get(vertex);
        if (adjs != null) {
            for (String adj : adjs) {
                System.out.println("first path " + adj);
                firstDFSPass(visited, adj);
        }
        }
        stack.push(vertex);
    }

    private static void secondDFSPass(Set<String> visited, String vertex, List<String> listSameSCC) {
        if (visited.contains(vertex))
            return;
        visited.add(vertex);
        listSameSCC.add(vertex);
        List<String> adjs = graphT.get(vertex);
        if (adjs != null) {
            for (String adj : adjs) {
                secondDFSPass(visited, adj, listSameSCC);
            }
        }
    }

    public static void main(String[] args) {
        assert args.length == 1 : "It needs one argument: the file containing the arcs";
        List<List<String>> sccs = Kosaraju.findSCC(args[0]);
        System.out.println(sccs);
        for (List<String> scc : sccs) {
            System.out.println(scc);
        }
    }

 }