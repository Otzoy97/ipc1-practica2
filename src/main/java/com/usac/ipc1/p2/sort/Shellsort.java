/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
     * @param b 
     * @return
     */
    public BarGraph sort(BarGraph b){
        return b;
    }
}
