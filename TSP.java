/**
 *
 *@author David Felipe Hernandez Chiapa
 */

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.*;


import org.graphstream.graph.*;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.implementations.*;

public class TSP{

    private Random rand;
    private int numVertices;
    private double[][] matriz;
    private int k;
    private Graph g;

    public TSP(){
        g = new SingleGraph("Cuvierta de vertices.");
        rand =  new Random();
        numVertices = rand.nextInt(10);
        if(numVertices < 5){//asegurando que la grafica tenga al menos 5 vertices.
            numVertices = 5;
        }
        for (int i = 0; i < numVertices; i++) {
            g.addNode(Integer.toString(i));
        }
        k = rand.nextInt(numVertices);
        if(k == 0)
            k = 1;
        matriz = new double[numVertices][numVertices];


        for(int i = 0; i < numVertices; i++)
            for(int j = i; j < numVertices; j++)
                if(i == j){
                    matriz[i][j] = 0.0;
                }else{
                        matriz[i][j] = 10.0*rand.nextDouble();
                        g.addEdge(i+"-"+j,i,j);
                }
        for(int i = 0; i < numVertices; i++)
            for(int j = 0; j < numVertices; j++)
                matriz[j][i] = matriz[i][j];

        for(Edge e:g.getEachEdge()) {
            e.setAttribute("weight",matriz[Integer.parseInt(e.getNode0().getId())][Integer.parseInt(e.getNode1().getId())]);
            e.addAttribute("ui.label",Double.toString(Math.round(e.getNumber("weight") * 100d)/100d));
        }
    }




    public class NodeAux {
        Node n;
        boolean visited;
        int visits;

        public NodeAux(Node n) {
            this.n = n;
            visited = false;
            visits = 0;
        }
    }


    public ArrayList<NodeAux> tray(ArrayList<NodeAux> cosos){
        Stack<NodeAux> s = new Stack<>();
        ArrayList<NodeAux> l = new ArrayList<>();


        s.push(cosos.get(0));
        cosos.get(0).visits++;
        String nt = "notintree";
        while (!s.isEmpty()) {
            NodeAux n = s.pop();
            l.add(n);
            Collection<Edge> leaving = n.n.getEdgeSet();
            for (Edge e : leaving) {
                if (nt.equals(e.getAttribute("ui.class"))) {
                    continue;
                }
                Node op = e.getOpposite(n.n);
                NodeAux na = new NodeAux(this.g.getNode(0));
                for(NodeAux na1 : cosos) {
                    if(na1.n.equals(op)){
                        na = na1;
                        break;
                    }
                }
                if(na.visits < 2){
                    s.push(na);
                    na.visits++;
                }
            }
        }

        return l;
    }

    private ArrayList<NodeAux> cosos(){
        ArrayList<NodeAux> cosos = new ArrayList<>();
        for (Node n :this.g) {
            NodeAux na = new NodeAux(n);
            cosos.add(na);
        }
        return cosos;
    }

    private void limpia(){
        for (Edge e : g.getEachEdge()) {
            e.setAttribute("ui.class","notintree");
        }
    }


    public static void main(String[] args) {
        TSP tsp = new TSP();
        String css = "edge .notintree {size:1px;fill-color:gray;} " + "edge .intree {size:3px;fill-color:black;}";
        tsp.g.addAttribute("ui.stylesheet", css);
        Kruskal kruskal = new Kruskal("ui.class", "intree", "notintree");
        kruskal.init(tsp.g);
	 	kruskal.compute();



        for (Edge e : tsp.g.getEachEdge()){
            String value = e.getAttribute("ui.class");
            System.out.println(value);
        }

        ArrayList<NodeAux> cosos = tsp.cosos();

        Node fin = null;
        ArrayList<NodeAux> l = tsp.tray(cosos);
        Node ini = l.get(0).n;
        tsp.limpia();
        for (int i = 0; i < l.size(); i++) {
            NodeAux n1 = l.get(i);
            n1.visited = true;
            fin = n1.n;
            for(int j = i; j < l.size(); j++) {
                NodeAux n2 = l.get(j);
                if(n2.visited) {
                    continue;
                } else {
                    i = j;
                    Edge e = n1.n.getEdgeBetween(n2.n);
                    e.setAttribute("ui.class","intree");
                    break;
                }
            }
        }






        /*ArrayList<Edge> solucion = cv.arbolGenPesoMin();
        for (int sol :solucion) {
            cv.g.getNode(Integer.toString(sol)).addAttribute("ui.style", "fill-color: rgb(0,100,255);");
        }*/
        tsp.g.display();
    }

}
