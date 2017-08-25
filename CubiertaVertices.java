/**
 *
 *@author David Felipe Hernandez Chiapa
 */

import java.util.Random;
import java.util.ArrayList;

public class CubiertaVertices{

    private Random rand;
    private int numVertices;
    private int[][] matriz;
    private int k;

    public CubiertaVertices(){
        rand =  new Random();
        numVertices = rand.nextInt(10);
        if(numVertices < 5){//asegurando que la grafica tenga al menos 5 vertices.
            numVertices = 5;
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
                    }else{
                        matriz[i][j] = 0;
                    }
                }
        for(int i = 0; i < numVertices; i++)
            for(int j = 0; j < numVertices; j++)
                matriz[j][i] = matriz[i][j];
    }


    private ArrayList<Integer> adivinador(){
        ArrayList<Integer> solucion = new ArrayList<>();
        int numVerticesSol = rand.nextInt(numVertices);//un numero aleatorio de vertices para ser posible solucion.
        if(numVerticesSol == 0){
            numVerticesSol = 1;
        }
        for(int i = 0; i < numVerticesSol; i++){
            int sol = rand.nextInt(numVertices);
            if(!solucion.contains(sol))//se busca que no se agregue mas de una vez el mismo vertice.
                solucion.add(sol);
            else
                i -= 1;
        }
        return solucion;
    }

    private boolean verificador(ArrayList<Integer> solucion){
        if(solucion.size() > k){
            System.out.println("\nEl conjunto solucion es mayor a " + k);
            return false;
        }

        int[][] matrizCopia = new int[numVertices][numVertices];
        for(int i = 0; i < numVertices; i++)
            for(int j = i; j < numVertices; j++)
                matrizCopia[i][j] = matriz[i][j];    //se realiza una copia de la matriz.
        for(int i : solucion){
            for(int j = 0; j < numVertices;j++){ // se ponen 0's en las columnas y filas correspondientes a los vertices de la propuesta de solucion.
                matrizCopia[i][j] = 0;
                matrizCopia[j][i] = 0;
            }
        }

        for(int i = 0; i < numVertices; i++)
            for(int j = i; j < numVertices; j++)
            if(matrizCopia[i][j] == 1)//si hay un 1 en la matrizCopia significa que no se cubrireron todas las aristas con el conjunto de vertices elejido.
                return false;

        return true;
    }


    public static void main(String[] args) {
        CubiertaVertices cv = new CubiertaVertices();
        for(int[] i1 : cv.matriz){
            System.out.println("");
            for(int i : i1){
                System.out.print(i);
            }
        }
        ArrayList<Integer> solucion = cv.adivinador();
        boolean esSol = cv.verificador(solucion);
        System.out.println("\nEl entero k es " + cv.k);
        System.out.print("El conjunto solucion es: ");
        for(int i : solucion)
            System.out.print(i + ", ");
        System.out.println("");
        System.out.println("Es solucion, " + esSol);
    }

}
