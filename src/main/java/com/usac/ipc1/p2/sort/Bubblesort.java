package com.usac.ipc1.p2.sort;

import com.usac.ipc1.p2.PlotterFrame;
import com.usac.ipc1.p2.Rep;
import com.usac.ipc1.p2.graph.BarGraph;
import java.io.IOException;
import java.util.Date;
import javax.swing.ImageIcon;

/**
 *
 * @author erick
 */
public class Bubblesort extends Thread implements Runnable {

    /**
     * Sentido de ordenamiento. {@code true} para ascendente, {@code false} para
     * descendente
     */
    private final boolean sortSense;
    private BarGraph b;
    private int pasos, velocidad;
    private String strVelocidad;
    private long mStart, mActual;

    /**
     *
     * @param sortSense sentido de ordenamiento. {@code true} para ascendente,
     * {@code false} para descendente
     * @param barGraph gráfica a ordenar
     * @param velocidad indica el espacio de tiempo entre pasos de ordenación
     */
    public Bubblesort(boolean sortSense, BarGraph barGraph, int velocidad) {
        this.sortSense = sortSense;
        this.b = barGraph;
        this.velocidad = velocidad;
        this.strVelocidad = strVelocidad = velocidad == 200 ? "Rápida" : velocidad == 400 ? "Media" : "Lenta";
    }

    /**
     * Ordena los valores de la gráfica
     */
    @Override
    public void run() {
        // Recupera el tiempo actual
        mStart = new Date().getTime();
        // Utiliza tiempos locales
        long lRef0 = mStart;
        // Procede a ordenar el arreglo
        for (int i = 0; i < b.actualSize() - 1; i++) {
            for (int j = 0; j < b.actualSize() - i - 1; j++) {
                // Cuenta los pasos
                pasos++;
                // Según el sentido de ordenamiento, ordena
                if (sortSense && b.dat[j].value > b.dat[j + 1].value) {
                    // Ascendentemente
                    // Intercambia los datos
                    Sort.swap(b.dat, j, j + 1);
                    // Actualiza la gráfica
                    updateGraph();
                } else if (!sortSense && b.dat[j].value < b.dat[j + 1].value) {
                    // Descendentemente
                    // Intercambia los datos
                    Sort.swap(b.dat, j, j + 1);
                    // Actualiza la gráfica
                    updateGraph();
                }
                // Actualiza el tiempo
                // Cuando la diferencia sea mayor a la velocidad
                // será tiempo de otra iteración, mientras tanto
                // seguirá ordenando
                while ((new Date().getTime() - lRef0) < velocidad) {
                    // Actualiza el tiempo
                    PlotterFrame.updateTime(new Date().getTime()- mStart);
                }
                // Actualiza la referencia
                lRef0 = new Date().getTime();
            }
        }
        // Al finalizar genera el reporte correspondiente
        new Rep("Bubblesort", strVelocidad, sortSense? "Ascendente": "Descendente").genRep();
    }

    /**
     * Detiene la ejecución del hilo momentáneamente
     */
    private void updateGraph() {
        try {
            PlotterFrame.renderGraph(pasos);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
