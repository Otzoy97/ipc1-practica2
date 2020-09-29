/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usac.ipc1.p2.graph;

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
    public int value;

    /**
     * Constructor para Bar
     * @param name nombre de la barra
     * @param value valor de la barra en y
     */
    public Bar(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
