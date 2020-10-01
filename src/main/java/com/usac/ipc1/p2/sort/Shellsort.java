package com.usac.ipc1.p2.sort;

import com.usac.ipc1.p2.PlotterFrame;
import com.usac.ipc1.p2.Rep;
import com.usac.ipc1.p2.graph.BarGraph;
import java.util.Date;

/**
 *
 * @author erick
 */
public class Shellsort extends Thread {

    /**
     * Sentido de ordenamiento. {@code true} para ascendente, {@code false} para
     * descendente
     */
    private final boolean sortSense;
    private final BarGraph b;
    private int pasos;
    private final int velocidad;
    private String strVelocidad;
    private long mStart, lRef0;

    /**
     *
     * @param sortSense sentido de ordenamiento. {@code true} para ascendente,
     * {@code false} para descendente
     * @param barGraph
     * @param velocidad
     */
    public Shellsort(boolean sortSense, BarGraph barGraph, int velocidad) {
        this.sortSense = sortSense;
        this.b = barGraph;
        this.velocidad = velocidad;
        this.strVelocidad = velocidad == 200 ? "Rápida" : velocidad == 400 ? "Media" : "Lenta";
    }

    /**
     * Ordena utilizando el algoritmo shellsort
     */
    @Override
    public void run() {
        // Recupera el tiempo actual
        mStart = new Date().getTime();
        // Se utiliza para marcar la velocidad del ordenamiento
        lRef0 = mStart;
        // Comienza con un intervalo grande, hasta llegar a 1
        for (int gap = b.actualSize() / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < b.actualSize(); i++) {
                var temp = b.dat[i];
                int j;
                if (sortSense) {
                    for (j = i; j >= gap && b.dat[j - gap].compare(temp) == 1; j -= gap) {
                        // Cuenta los pasos
                        pasos++;
                        b.dat[j] = b.dat[j - gap];
                        // Actualiza la gráfica
                        PlotterFrame.renderGraph(pasos);
                        // Espera para la siguiente iteración
                        this.updateT();
                    }
                } else {
                    for (j = i; j >= gap && b.dat[j - gap].compare(temp) == -1; j -= gap) {
                        // Cuenta los pasos
                        pasos++;
                        b.dat[j] = b.dat[j - gap];
                        // Actualiza la gráfica
                        PlotterFrame.renderGraph(pasos);
                        // Espera para la siguiente iteración
                        this.updateT();
                    }
                }
                // Cuenta los pasos
                pasos++;
                b.dat[j] = temp;
                // Actualiza la gráfica
                PlotterFrame.renderGraph(pasos);
                // Espera para la siguiente iteración
                this.updateT();
            }
        }
        // Al finalizar genera el reporte correspondiente
        new Rep("Shellsort", strVelocidad, sortSense ? "Ascendente" : "Descendente").genRep();
        PlotterFrame.btnSortGraph.setEnabled(true);
    }

    /**
     *
     */
    private void updateT() {
        // Actualiza el tiempo
        // Cuando la diferencia sea mayor a la velocidad
        // será tiempo de otra iteración, mientras tanto
        // seguirá ordenando
        while ((new Date().getTime() - lRef0) < velocidad) {
            // Actualiza el tiempo
            PlotterFrame.updateTime(new Date().getTime() - mStart);
        }
        // Actualiza la referencia
        lRef0 = new Date().getTime();
    }
}
