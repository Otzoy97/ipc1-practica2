package com.usac.ipc1.p2.sort;

/**
 *
 * @author otzoy
 */
public class Sort {
    /**
     * Intercambia posiciones en el arreglo
     * @param ref arreglo
     * @param a posición a intercambiar con b
     * @param b posición a intercambiar con a
     */
    public static void swap(Object[] ref, int a, int b){
        var temp = ref[a];
        ref[a] = ref[b];
        ref[b] = temp;
    }
}
