package sistema;

import java.util.ArrayList;
import java.util.LinkedList;

public class Recorrido {
    private LinkedList<Aeropuerto> camino;
    private double kmTotales;

    Recorrido(){
        camino = new LinkedList<Aeropuerto>();
        kmTotales = 0.0;
    }
    Recorrido(LinkedList<Aeropuerto> camino, double kmTotales){
        this.camino = camino;
        this.kmTotales = kmTotales;
    }

    public double getKmTotales() {
        return kmTotales;
    }

    public ArrayList<Aeropuerto> getCamino() {
        return new ArrayList<Aeropuerto>(camino);
    }

    public void setCamino(LinkedList<Aeropuerto> camino) {
        this.camino = camino;
    }

    public void setKmTotales(double kmTotales) {
        this.kmTotales = kmTotales;
    }

    @Override
    public String toString() {
        return "Recorrido Final= " + camino.toString() + ". Km Totales= " + kmTotales;
    }
}
