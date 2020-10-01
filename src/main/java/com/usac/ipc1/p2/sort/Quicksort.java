package com.usac.ipc1.p2.sort;

import com.usac.ipc1.p2.PlotterFrame;
import com.usac.ipc1.p2.Rep;
import com.usac.ipc1.p2.graph.Bar;
import com.usac.ipc1.p2.graph.BarGraph;
import java.util.Date;

/**
 *
 * @author erick
 */
public class Quicksort extends Thread {

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
    public Quicksort(boolean sortSense, BarGraph barGraph, int velocidad) {
        this.sortSense = sortSense;
        this.b = barGraph;
        this.velocidad = velocidad;
        this.strVelocidad = velocidad == 200 ? "Rápida" : velocidad == 400 ? "Media" : "Lenta";
    }

    /**
     *
     */
    @Override
    public void run() {
        // Recupera el tiempo actual
        mStart = new Date().getTime();
        // Se utiliza para marcar la velocidad del ordenamiento
        lRef0 = mStart;
        // Ordena los elementos
        this.sort(b, 0, b.actualSize() - 1);
        // Al finalizar genera el reporte correspondiente
        new Rep("Quicksort", strVelocidad, sortSense ? "Ascendente" : "Descendente").genRep();
        PlotterFrame.btnSortGraph.setEnabled(true);
    }

    /**
     * Ordena utilizando el algorito quicksort
     *
     * @param b Grafica de barras
     * @param x indice menor
     * @param y indice mayor
     */
    public void sort(BarGraph b, int x, int y) {
        if (x < y) {
            int pIdx = this.sortSense ? partitionAsc(b, x, y) : partitionDesc(b, x, y);
            sort(b, x, pIdx - 1);
            sort(b, pIdx + 1, y);
        }
    }

    /**
     * Para ordenar ascendentemente
     *
     * @param b Gráfica de barras
     * @param x indice menor
     * @param y indice mayor
     * @return
     */
    private int partitionAsc(BarGraph b, int x, int y) {
        // Se elije como pivote el valor medio
        Bar pv = b.dat[(y + x) / 2];
        // Toma una referencia del valor más hacia la izquierda
        int i = x - 1;
        // Toma una referencia del valor más hacia la derecha
        int j = y + 1;
        while (true) {
            // Avanza hacia la derecha
            do {
                i++;
            } while (b.dat[i].compare(pv)== -1);
            // Avanza hacia la izquierda
            do {
                j--;
            } while (b.dat[j].compare(pv) == 1);
            // Detiene el ciclo
            if (i >= j) {
                return j;
            }
            // Cuenta los pasos
            pasos++;
            com.usac.ipc1.p2.sort.Sort.swap(b.dat, i, j);
            // Actualiza la gráfica
            PlotterFrame.renderGraph(pasos);
            this.updateT();
        }
    }

    /**
     * Para ordenar descendentemente
     *
     * @param b Gráfica de barras
     * @param x indice menor
     * @param y indice mayor
     * @return
     */
    private int partitionDesc(BarGraph b, int x, int y) {
        // Se elije como pivote el valor medio
        Bar pv = b.dat[(y + x) / 2];

        int i = x - 1;
        int j = y + 1;
        while (true) {
            // Avanza hacia la derecha
            do {
                i++;
            } while (b.dat[i].compare(pv) == 1);
            // Avanza hacia la izquierda
            do {
                j--;
            } while (b.dat[j].compare(pv) == -1);
            // Detiene el ciclo
            if (i >= j) {
                return j;
            }
            // Cuenta los pasos
            pasos++;
            com.usac.ipc1.p2.sort.Sort.swap(b.dat, i, j);
            // Actualiza la gráfica
            PlotterFrame.renderGraph(pasos);
            this.updateT();
        }
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
