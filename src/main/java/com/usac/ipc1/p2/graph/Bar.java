package com.usac.ipc1.p2.graph;

import java.math.BigDecimal;

/**
 *
 * @author erick
 */
public class Bar {
    /**
     * Nombre de la barra
     */
    public String name;
    /**
     * Valor en y de la barra
     */
    public Number value;

    /**
     * Constructor para Bar
     * @param name nombre de la barra
     * @param value valor de la barra en y
     */
    public Bar(String name, Number value) {
        this.name = name;
        this.value = value;
    }
    
    /**
     * 
     * @param ref Barra que contiene la información con la cual se comparará
     * @return -1 menor que, 0 igual, 1 mayor que
     */
    public int compare(Bar ref){
        return new BigDecimal(value.toString()).compareTo(new BigDecimal(ref.value.toString()));
    }
}
