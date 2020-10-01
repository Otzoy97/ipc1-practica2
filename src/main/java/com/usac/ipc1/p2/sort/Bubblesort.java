package com.usac.ipc1.p2.sort;

import com.usac.ipc1.p2.PlotterFrame;
import com.usac.ipc1.p2.Rep;
import com.usac.ipc1.p2.graph.BarGraph;
import java.util.Date;

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
    private final BarGraph b;
    private int pasos;
    private final int velocidad;
    private String strVelocidad;
    private long mStart;

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
        // Se utiliza para marcar la velocidad del ordenamiento
        long lRef0 = mStart;
        // Procede a ordenar el arreglo
        for (int i = 0; i < b.actualSize() - 1; i++) {
            for (int j = 0; j < b.actualSize() - i - 1; j++) {
                // Cuenta los pasos
                pasos++;
                // Según el sentido de ordenamiento, ordena
                if (sortSense && b.dat[j].compare(b.dat[j + 1]) == 1) {
                    // Ascendentemente
                    // Intercambia los datos
                    Sort.swap(b.dat, j, j + 1);
                    // Actualiza la gráfica
                    PlotterFrame.renderGraph(pasos);
                } else if (!sortSense && b.dat[j].compare(b.dat[j + 1]) == -1) {
                    // Descendentemente
                    // Intercambia los datos
                    Sort.swap(b.dat, j, j + 1);
                    // Actualiza la gráfica
                    PlotterFrame.renderGraph(pasos);
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
        PlotterFrame.btnSortGraph.setEnabled(true);
    }
}
