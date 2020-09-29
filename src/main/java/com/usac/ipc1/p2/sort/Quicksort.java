/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usac.ipc1.p2.sort;

import com.usac.ipc1.p2.graph.BarGraph;

/**
 *
 * @author erick
 */
public class Quicksort {

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
    public Quicksort(boolean sortSense) {
        this.sortSense = sortSense;
    }

    /**
     * Ordena utilizando el algorito quicksort
     * @param b Grafica de barras
     * @param x indice menor
     * @param y indice mayor
     * @return
     */
    public BarGraph sort(BarGraph b, int x, int y) {
        if (x < y) {
            int pIdx = this.sortSense ? partitionAsc(b, x, y) : partitionDesc(b, x, y);
            sort(b, x, pIdx - 1);
            sort(b, pIdx + 1, y);
        }
        return b;
    }

    /**
     * Para ordenar ascendentemente
     * @param b Gráfica de barras
     * @param x indice menor
     * @param y indice mayor
     * @return
     */
    private int partitionAsc(BarGraph b, int x, int y) {
        // Se elije como pivote el valor medio
        int pv = b.dat[(y + x) / 2].value;
        // Toma una referencia del valor más hacia la izquierda
        int i = x - 1;
        // Toma una referencia del valor más hacia la derecha
        int j = y + 1;
        while (true) {
            // Avanza hacia la derecha
            do {
                i++;
            } while (b.dat[i].value < pv);
            // Avanza hacia la izquierda
            do {
                j--;
            } while (b.dat[i].value > pv);
            // Detiene el ciclo
            if (i >= j) {
                return j;
            }
            com.usac.ipc1.p2.sort.Sort.swap(b.dat, i, j);
        }
    }

    /**
     * Para ordenar descendentemente
     * @param b Gráfica de barras
     * @param x indice menor
     * @param y indice mayor
     * @return
     */
    private int partitionDesc(BarGraph b, int x, int y) {
        // Se elije como pivote el valor medio
        int pv = b.dat[(y + x) / 2].value;

        int i = x - 1;
        int j = y + 1;
        while (true) {
            // Avanza hacia la derecha
            do {
                i++;
            } while (b.dat[i].value > pv);
            // Avanza hacia la izquierda
            do {
                j--;
            } while (b.dat[i].value < pv);
            // Detiene el ciclo
            if (i >= j) {
                return j;
            }
            com.usac.ipc1.p2.sort.Sort.swap(b.dat, i, j);
        }
    }
}
