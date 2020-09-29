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
public class Bubblesort {
    /**
     * Sentido de ordenamiento. {@code true} para ascendente, {@code false} para descendente
     */
    private final boolean sortSense;
    
    /**
     * 
     * @param sortSense sentido de ordenamiento. {@code true} para ascendente, {@code false} para descendente
     */
    public Bubblesort(boolean sortSense){
        this.sortSense = sortSense;
    }
    
    /**
     * Ordena los valores de la gráfica {@code b}
     * @param b Gráfica de barras
     * @return 
     */
    public BarGraph sort(BarGraph b){
        for(int i = 0 ; i < b.actualSize() - 1; i++){
            for(int j=0; j < b.actualSize() - i - 1; j++){
                // Según el sentido de ordenamiento, ordena
                if (sortSense && b.dat[j].value > b.dat[j+1].value){
                    // Ascendentemente
                    com.usac.ipc1.p2.sort.Sort.swap(b.dat, j, j+1);
                } else if (!sortSense && b.dat[j].value < b.dat[j+1].value){
                    // Descendentemente
                    com.usac.ipc1.p2.sort.Sort.swap(b.dat, j, j+1);
                }
            }
        }
        return b;
    }
}
