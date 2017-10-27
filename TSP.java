/**
 *
 *@author David Felipe Hernandez Chiapa
 */

import java.util.Random;
import java.util.ArrayList;

import org.graphstream.graph.*;
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
                    if(rand.nextGaussian() < 0.6){
                        matriz[i][j] = 10.0*rand.nextDouble();
                        g.addEdge(i+"-"+j,i,j);
                    }else{
                        matriz[i][j] = 0.0;
                    }
                }
        for(int i = 0; i < numVertices; i++)
            for(int j = 0; j < numVertices; j++)
                matriz[j][i] = matriz[i][j];

        for(Edge e:g.getEachEdge()) {
            e.setAttribute("weight",matriz[Integer.parseInt(e.getNode0().getId())][Integer.parseInt(e.getNode1().getId())]);
            e.addAttribute("ui.label",Double.toString(Math.round(e.getNumber("weight") * 100d)/100d));
        }
    }

    private ArrayList<Edge> arbolGenPesoMin(){
        return null;
    }





    public static void main(String[] args) {
        TSP tsp = new TSP();

        /*ArrayList<Edge> solucion = cv.arbolGenPesoMin();
        for (int sol :solucion) {
            cv.g.getNode(Integer.toString(sol)).addAttribute("ui.style", "fill-color: rgb(0,100,255);");
        }*/
        tsp.g.display();
    }

}
