import java.util.Random;
import java.util.ArrayList;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class CubiertaVertices{

    private Random rand;
    private int numVertices;
    private int[][] matriz;
    private int k;
    private Graph g;

    public CubiertaVertices(){
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
        matriz = new int[numVertices][numVertices];


        for(int i = 0; i < numVertices; i++)
            for(int j = i; j < numVertices; j++)
                if(i == j){
                    matriz[i][j] = 0;
                }else{
                    if(rand.nextGaussian() < 0.6){
                        matriz[i][j] = 1;
                        g.addEdge(i+"-"+j,i,j);
                    }else{
                        matriz[i][j] = 0;
                    }
                }
        for(int i = 0; i < numVertices; i++)
            for(int j = 0; j < numVertices; j++)
                matriz[j][i] = matriz[i][j];
    }

    private boolean hayAristas(){
        for (int[] i: this.matriz) {
            for (int j: i) {
                if (j == 1)
                    return true;
            }
        }
        return false;
    }

    private void vuelveCeros(int i, int j){
        for(int k = 0; k < numVertices; k++) {
            this.matriz[i][k] = 0;
            this.matriz[k][j] = 0;
        }
    }


    private ArrayList<Integer> creaCubierta(){
        ArrayList<Integer> solucion = new ArrayList<>();
        ArrayList<Integer> aux = new ArrayList<>();
        for (int i = 0; i < this.numVertices; i++)
            aux.add(i);
        while (this.hayAristas()) {
            int r1 = rand.nextInt(aux.size());
            int aux1 = aux.get(r1);
            aux.remove(r1);
            solucion.add(aux1);

            int r2 = rand.nextInt(aux.size());
            int aux2 = aux.get(r2);
            solucion.add(aux2);
            aux.remove(r2);
            solucion.add(aux2);

            this.vuelveCeros(aux1,aux2);
            this.vuelveCeros(aux2,aux1);
        }

        return solucion;
    }



    public static void main(String[] args) {
        CubiertaVertices cv = new CubiertaVertices();

        ArrayList<Integer> solucion = cv.creaCubierta();
        for (int sol :solucion) {
            cv.g.getNode(Integer.toString(sol)).addAttribute("ui.style", "fill-color: rgb(0,100,255);");;
        }
        cv.g.display();
    }

}
