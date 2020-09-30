package com.usac.ipc1.p2.sort;

import com.usac.ipc1.p2.graph.BarGraph;

/**
 *
 * @author otzoy
 */
public class Shellsort {

    /**
     * Sentido de ordenamiento. {@code true} para ascendente, {@code false} para
     * descendente
     */
    private final boolean sortSense;

    /**
     *
     * @param sortSense sentido de ordenamiento. {@code true} para ascendente,
     * {@code false} para descendente
     */
    public Shellsort(boolean sortSense) {
        this.sortSense = sortSense;
    }

    /**
     * Ordena utilizando el algoritmo shellsort
     *
     * @param b
     * @return
     */
    public BarGraph sort(BarGraph b) {
        // Comienza con un intervalo grande, hasta llegar a 1
        for (int gap = b.actualSize() / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < b.actualSize(); i++) {
                var temp = b.dat[i];
                int j;
                for (j = i; j >= gap && b.dat[j - gap].value > temp.value; j -= gap) {
                    b.dat[j] = b.dat[j - gap];
                }
                b.dat[j] = temp;
            }
        }
        return b;
    }
}
