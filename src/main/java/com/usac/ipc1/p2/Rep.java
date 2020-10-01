/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usac.ipc1.p2;

import com.usac.ipc1.p2.graph.BarGraph;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;

/**
 *
 * @author erick
 */
public class Rep {

    private final String algoritmo, velocidad, orden;

    /**
     * Constructor para el reporte html
     *
     * @param algoritmo
     * @param velocidad
     * @param orden
     */
    public Rep(String algoritmo, String velocidad, String orden) {
        this.algoritmo = algoritmo;
        this.velocidad = velocidad;
        this.orden = orden;
    }

    /**
     * Recupera el texto html para el reporte de la gráfica
     *
     * @return
     */
    public void genRep() {
        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html>\n");
        sb.append("<html lang=\"en\">\n");
        sb.append("<head>\n");
        sb.append("    <meta charset=\"UTF-8\">\n");
        sb.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        sb.append("    <title>[IPC1]- Práctica 2</title>\n");
        sb.append("<style type=\"text/css\">\n");
        sb.append(".tg  {border-collapse:collapse;border-spacing:0;margin:0px auto;width:100%;}\n");
        sb.append(".tga  {border-collapse:collapse;border-spacing:0;margin:0px auto;width:100%;}\n");
        sb.append(".tg td{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:15px 20px;word-break:normal;}\n");
        sb.append(".tga td{border-bottom-width:1px;border-color:black;border-style:solid;border-top-width:1px;border-width:0px;font-family:Arial, sans-serif;font-size:14px;overflow:hidden;padding:10px 5px;word-break:normal;}\n");
        sb.append(".tg .tg-m32z{background-color:#34cdf9;border-color:#dae8fc;color:#000000;font-family:Arial, Helvetica, sans-serif !important;;font-size:14px;font-weight:bold;text-align:center;vertical-align:middle}\n");
        sb.append(".tg .tg-iy0o{border-color:#dae8fc;font-family:Arial, Helvetica, sans-serif !important;;font-size:14px;text-align:center;vertical-align:middle}\n");
        sb.append(".tga .tg-7d50{font-family:Arial, Helvetica, sans-serif !important;;font-size:15px;font-weight:bold;text-align:left;vertical-align:middle}\n");
        sb.append(".tga .tg-b1yu{font-family:Arial, Helvetica, sans-serif !important;;font-size:15px;text-align:right;vertical-align:middle}\n");
        sb.append(".tga .tg-7d51{font-family:Arial, Helvetica, sans-serif !important;;font-size:22px;font-weight:bold;text-align:left;vertical-align:middle}\n");

        sb.append("</style>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("<h1 style=\"text-align:center\">").append("Erick Jerónimo").append("</h1>\n");
        sb.append("<h3 style=\"text-align:center\">").append("20000000").append("</h3>\n");
        sb.append("<hr>\n");
        sb.append("<table class=\"tga\">\n");
        sb.append("<tbody>\n");
        sb.append("  <tr>\n");
        sb.append("    <td class=\"tg-7d51\" colspan=\"4\">").append(algoritmo).append("</td>\n");
        sb.append("  </tr>\n");
        sb.append("  <tr>\n");
        sb.append("    <td class=\"tg-7d50\">Tiempo</td>\n");
        sb.append("    <td class=\"tg-b1yu\">").append(PlotterFrame.lblTiempo.getText()).append("</td>\n");
        sb.append("    <td class=\"tg-7d50\">Velocidad</td>\n");
        sb.append("    <td class=\"tg-b1yu\">").append(velocidad).append("</td>\n");
        sb.append("  </tr>\n");
        sb.append("  <tr>\n");
        sb.append("    <td class=\"tg-7d50\">Pasos</td>\n");
        sb.append("    <td class=\"tg-b1yu\">").append(PlotterFrame.lblPasos.getText()).append("</td>\n");
        sb.append("    <td class=\"tg-7d50\">Orden</td>\n");
        sb.append("    <td class=\"tg-b1yu\">").append(orden).append("</td>\n");
        sb.append("  </tr>\n");
        sb.append("</tbody>\n");
        sb.append("</table>\n");
        sb.append("<hr>\n");
        sb.append("<div style=\"position:relative;\">\n");
        sb.append("<table class=\"tg\" style=\"float:left;width:50%;\"\n");
        sb.append("<tbody>\n");
        sb.append("  <tr>\n");
        sb.append("    <td class=\"tg-m32z\" colspan=\"2\">Dato mínimo</td>\n");
        sb.append("  </tr>\n");
        sb.append("  <tr>\n");
        if (orden.equals("Ascendente")) {
            sb.append("    <td class=\"tg-iy0o\">").append(PlotterFrame.sortedGraph.dat[0].name).append("</td>\n");
            sb.append("    <td class=\"tg-iy0o\">").append(PlotterFrame.sortedGraph.dat[0].value).append("</td>\n");
        } else {
            sb.append("    <td class=\"tg-iy0o\">").append(PlotterFrame.sortedGraph.dat[PlotterFrame.sortedGraph.actualSize() - 1].name)
                    .append("</td>\n");
            sb.append("    <td class=\"tg-iy0o\">").append(PlotterFrame.sortedGraph.dat[PlotterFrame.sortedGraph.actualSize() - 1].value)
                    .append("</td>\n");
        }

        sb.append("  </tr>\n");
        sb.append("</tbody>\n");
        sb.append("</table>\n");
        sb.append("<table class=\"tg\" style=\"float:right;width:50%;\">\n");
        sb.append("<tbody>\n");
        sb.append("  <tr>\n");
        sb.append("    <td class=\"tg-m32z\" colspan=\"2\">Dato máximo</td>\n");
        sb.append("  </tr>\n");
        sb.append("  <tr>\n");
        if (orden.equals("Ascendente")) {
            sb.append("    <td class=\"tg-iy0o\">").append(PlotterFrame.sortedGraph.dat[0].name).append("</td>\n");
            sb.append("    <td class=\"tg-iy0o\">").append(PlotterFrame.sortedGraph.dat[0].value).append("</td>\n");
        } else {
            sb.append("    <td class=\"tg-iy0o\">").append(PlotterFrame.sortedGraph.dat[PlotterFrame.sortedGraph.actualSize() - 1].name)
                    .append("</td>\n");
            sb.append("    <td class=\"tg-iy0o\">").append(PlotterFrame.sortedGraph.dat[PlotterFrame.sortedGraph.actualSize() - 1].value)
                    .append("</td>\n");
        }
        sb.append("  </tr>\n");
        sb.append("</tbody>\n");
        sb.append("</table>\n");
        sb.append("</div>\n");
        sb.append("<br><br><br><br><br><hr>\n");
        sb.append("<h1 style=\"text-align:center\">").append("Datos desordenados").append("</h1>\n");
        this.append(sb, PlotterFrame.unsortedGraph);
        sb.append("<br><hr>\n");
        sb.append("<h1 style=\"text-align:center\">").append("Datos ordenados").append("</h1>\n");
        this.append(sb, PlotterFrame.sortedGraph);
        sb.append("<br></body>\n");
        sb.append("</html>\n");
        
        try (FileWriter f = new FileWriter("rep-"+PlotterFrame.sortedGraph.getTituloGraph()+".html")){
            f.write(sb.toString());
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Inserta los datos de una gráfica
     *
     * @param sb referencia al {@code StringBuilder} original
     * @param bGraph gráfica a insertar
     */
    private void append(StringBuilder sb, BarGraph bGraph) {
        sb.append("<table class=\"tg\">\n");
        sb.append("<tbody>\n");
        sb.append("  <tr>\n");
        sb.append("    <td class=\"tg-m32z\">").append(bGraph.getTituloX()).append("</td>\n");
        for (var dat : bGraph.dat) {
            if (dat == null) {
                break;
            }
            sb.append("    <td class=\"tg-iy0o\">").append(dat.name).append("</td>\n");
        }
        sb.append("  </tr>\n");
        sb.append("  <tr>\n");
        sb.append("    <td class=\"tg-m32z\">").append(bGraph.getTituloY()).append("</td>\n");
        for (var dat : bGraph.dat) {
            if (dat == null) {
                break;
            }
            sb.append("    <td class=\"tg-iy0o\">").append(dat.value).append("</td>\n");
        }
        sb.append("  </tr>\n");
        sb.append("</tbody>\n");
        sb.append("</table>\n<br>\n");
        try {
            sb.append("<img style=\"display:block;margin-left:auto;margin-right:auto;width:100%\" src=\"data:image/png;base64, ");
            // Convierte la imagen a base64
            ByteArrayOutputStream ox = new ByteArrayOutputStream();
            RenderedImage rendImg = bGraph.render();
            ImageIO.write(rendImg, "png", ox);
            sb.append(Base64.getEncoder().encodeToString(ox.toByteArray()));
            sb.append("\" alt=\"Graph\"/>\n");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
