package com.usac.ipc1.p2.graph;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author erick
 */
public class BarGraph {
    /**
     * Valores a utilizar
     */
    public Bar dat[];
    /**
     * Contador para el número de datos reales que dat almacen
     */
    private int datCount = 0;
    /**
     * Titulo para el eje de las x
     */
    private String tituloX;
    /**
     * Titulo para el eje de las y
     */
    private String tituloY;
    /**
     * Titulo para la gráfica
     */
    private String tituloGraph;

    /**
     * Constructor para la gráfica de barras
     * 
     * @param tituloX       título para el eje x
     * @param tituloY       título para el eje y
     * @param tituloGrafica título para la gráfica
     */
    public BarGraph(String tituloX, String tituloY, String tituloGrafica) {
        this.tituloX = tituloX;
        this.tituloY = tituloY;
        this.tituloGraph = tituloGrafica;
        this.dat = new Bar[10];
    }

    /**
     * Agrega una barra a la gráfica
     * 
     * @param name
     * @param value
     */
    public void add(String name, int value) {
        // Ya no hay espacio
        if (datCount == dat.length) {
            return;
        }
        // Aún hay espacio
        for (int i = 0; i < dat.length; i++) {
            if (dat[i] == null) {
                dat[i] = new Bar(name, value);
                datCount++;
                return;
            }
        }
    }

    /**
     * Genera la gráfica y la devuelve en base64
     * 
     * @return
     * @throws IOException
     */
    public BufferedImage render() throws IOException {
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        for (int i = 0; i < datCount; i++) {
            data.addValue(dat[i].value, dat[i].name, this.tituloX);
        }
        JFreeChart chart = ChartFactory.createBarChart(this.tituloGraph, this.tituloX, this.tituloY, data,
                PlotOrientation.VERTICAL, true, true, false);
        return chart.createBufferedImage(1000, 800);
    }

    /**
     * Recupera la cantidad de elementos almacenados en el arreglo
     * 
     * @return
     */
    public int actualSize() {
        return this.datCount;
    }

    public String getTituloX() {
        return tituloX;
    }

    public String getTituloY() {
        return tituloY;
    }
}
