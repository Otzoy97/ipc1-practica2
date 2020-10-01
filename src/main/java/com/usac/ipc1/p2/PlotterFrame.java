package com.usac.ipc1.p2;

import java.awt.GridBagConstraints;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;

import com.usac.ipc1.p2.graph.BarGraph;
import com.usac.ipc1.p2.sort.Bubblesort;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author erick
 */
@SuppressWarnings("serial")
public class PlotterFrame extends JFrame {

    private JLabel lblPath;
    public static JLabel lblImg;
    private JButton btnFindPath, btnGenGraph, btnSortGraph;
    private JTextField txtTitleGraph;
    private ButtonGroup btnGAlgorithm, btnGType;
    private JRadioButton rbtnAsce, rbtnDesc, rbtnBubble, rbtnShell, rbtnQuick;
    private JSlider slidVelocity;

    protected static JLabel lblTiempo, lblPasos;
    private JLabel lblAlgoritmo, lblOrden, lblVelocidad;

    private String CSVFile;

    protected static BarGraph unsortedGraph, sortedGraph;

    /**
     * Constructor
     */
    public PlotterFrame() {
        init();
        CSVFile = null;
        unsortedGraph = null;
        sortedGraph = null;
    }

    /**
     * Muestra un selector de archivo para cargar el archivo .csv
     *
     * @param e
     */
    private void btnFindPathAction(ActionEvent e) {
        // Muestra el dialogo
        JFileChooser fChooser = new JFileChooser();
        fChooser.setMultiSelectionEnabled(false);
        fChooser.setDialogTitle("Abrir...");
        fChooser.setFileFilter(new FileNameExtensionFilter("Archivo separado por comas (*.csv)", "csv"));
        int r = fChooser.showDialog(this, "Abrir");
        if (r == JFileChooser.APPROVE_OPTION) {
            this.lblPath.setText(fChooser.getSelectedFile().getAbsolutePath());
            CSVFile = fChooser.getSelectedFile().getAbsolutePath();
        }
    }

    /**
     * Genera la gŕafica sin ordenar
     *
     * @param e
     */
    private void btnGenGraphAction(ActionEvent e) {
        if (this.CSVFile == null) {
            JOptionPane.showMessageDialog(this, "Cargue un archivo *.csv con el formato correcto", "Generar gráfica",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (this.txtTitleGraph.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Indique el título de la gráfica", "Generar gráfica",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Recupera la información del archivo csv
        try {
            String textFile = Files.readString(Paths.get(CSVFile), StandardCharsets.UTF_8);
            // Separa por lineas
            String[] lines = textFile.split("\n");
            // Recupera los encabezados
            String[] titles = lines[0].split(",");
            // Inicializa la gráfica
            unsortedGraph = new BarGraph(titles[0], titles[1], this.txtTitleGraph.getText());
            sortedGraph = new BarGraph(titles[0], titles[1], this.txtTitleGraph.getText());
            // Inserta los valores
            for (int i = 1; i < lines.length; i++) {
                String[] dat = lines[i].split(",");
                unsortedGraph.add(dat[0], Integer.parseInt(dat[1]));
                sortedGraph.add(dat[0], Integer.parseInt(dat[1]));
            }
            // Genera la gráfica y la inserta en una etiqueta
            PlotterFrame.renderGraph(0);
            // Habilita el botón para ordenar la gráfica
            this.btnSortGraph.setEnabled(true);
        } catch (IOException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *
     * @param e
     */
    private void btnSortGraphAction(ActionEvent e) {
        new Bubblesort(true, sortedGraph, 300).start();
    }

    /**
     * Actualiza la gráfica
     *
     * @param pasos
     * @throws java.io.IOException
     */
    public static void renderGraph(int pasos) throws IOException {
        // Actualiza la gráfica
        var temp = new ImageIcon(sortedGraph.render());
        lblImg.setIcon(temp);
        lblImg.revalidate();
        lblImg.repaint();
        lblImg.update(lblImg.getGraphics());
        // Actualiza el tiempo y los pasos
        lblPasos.setText(pasos + "");
    }
    
    public static void updateTime(long tiempo){
        DateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
        lblTiempo.setText(sdf.format(tiempo));
    }

    /**
     * Inicializa y configura los componentes
     */
    private void init() {
        GridBagConstraints gridBag;

        btnGAlgorithm = new ButtonGroup();
        btnGType = new ButtonGroup();

        setPreferredSize(new Dimension(1024, 576));
        setMinimumSize(new Dimension(800, 450));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("[IPC1] - Práctica 2");

        getContentPane().setLayout(new GridBagLayout());
        // -----------------------------------------------------
        // Panel que contendrá todo lo demás
        // -----------------------------------------------------
        JPanel panel01 = new JPanel(new GridBagLayout());
        // panel01.setBackground(Color.LIGHT_GRAY);
        panel01.setOpaque(true);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 0;
        gridBag.fill = GridBagConstraints.BOTH;
        gridBag.weightx = 1.0;
        gridBag.weighty = 1.0;
        getContentPane().add(panel01, gridBag);
        // -----------------------------------------------------
        // Panel superior
        // -----------------------------------------------------
        JPanel upperPanel = new JPanel(new GridBagLayout());
        upperPanel.setBorder(BorderFactory.createEtchedBorder());
        // upperPanel.setBackground(Color.WHITE);
        upperPanel.setOpaque(true);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 0;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.weightx = 1.0;
        gridBag.insets = new Insets(5, 5, 5, 5);
        panel01.add(upperPanel, gridBag);
        // -----------------------------------------------------
        // Panel inferior
        // -----------------------------------------------------
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        // bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setOpaque(true);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 1;
        gridBag.fill = GridBagConstraints.BOTH;
        gridBag.weightx = 1.0;
        gridBag.weighty = 1.0;
        gridBag.insets = new Insets(5, 5, 5, 5);
        panel01.add(bottomPanel, gridBag);
        // -----------------------------------------------------
        // Diseño panel superior
        // -----------------------------------------------------
        // ETIQUETA PATH
        lblPath = new JLabel(" ");
        lblPath.setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 12));

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 0;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.weightx = 1.0;
        gridBag.insets = new Insets(5, 5, 10, 0);
        upperPanel.add(lblPath, gridBag);
        // BOTON PATH
        btnFindPath = new JButton("Examinar...");
        btnFindPath.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
        btnFindPath.setHorizontalAlignment(SwingConstants.CENTER);
        btnFindPath.addActionListener((ActionEvent e) -> {
            btnFindPathAction(e);
        });

        gridBag = new GridBagConstraints();
        gridBag.gridx = 1;
        gridBag.gridy = 0;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(5, 0, 10, 5);
        upperPanel.add(btnFindPath, gridBag);
        // CAMPO DE TEXTO, TITULO
        txtTitleGraph = new JTextField();
        txtTitleGraph.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 1;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 0, 0);
        upperPanel.add(txtTitleGraph, gridBag);
        // ETIQUETA TITULO
        JLabel lblTitle = new JLabel("Título de la gráfica");
        lblTitle.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 10));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBackground(Color.LIGHT_GRAY);
        lblTitle.setOpaque(true);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 2;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 5, 0);
        upperPanel.add(lblTitle, gridBag);
        // BOTON GENERAR GRAFICA
        btnGenGraph = new JButton("Generar gráfica");
        btnGenGraph.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
        btnGenGraph.setHorizontalAlignment(SwingConstants.CENTER);
        btnGenGraph.addActionListener((ActionEvent e) -> {
            btnGenGraphAction(e);
        });

        gridBag = new GridBagConstraints();
        gridBag.gridx = 1;
        gridBag.gridy = 1;
        gridBag.gridheight = 2;
        gridBag.fill = GridBagConstraints.BOTH;
        gridBag.insets = new Insets(0, 0, 5, 5);
        upperPanel.add(btnGenGraph, gridBag);
        // -----------------------------------------------------
        // Diseño panel inferior
        // -----------------------------------------------------
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createEtchedBorder());

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 0;
        gridBag.gridheight = 2;
        gridBag.fill = GridBagConstraints.BOTH;
        gridBag.weightx = 1.0;
        gridBag.weighty = 1.0;
        gridBag.insets = new Insets(0, 0, 0, 5);
        bottomPanel.add(leftPanel, gridBag);

        lblImg = new JLabel(" ");
        lblImg.setHorizontalAlignment(SwingConstants.CENTER);
        lblImg.setBackground(Color.WHITE);
        lblImg.setOpaque(true);

        JScrollPane jscPane = new JScrollPane(lblImg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 0;
        gridBag.fill = GridBagConstraints.BOTH;
        gridBag.weightx = 1.0;
        gridBag.weighty = 1.0;
        leftPanel.add(jscPane, gridBag);
        // -----------------------------------------------------
        JPanel rightUpPanel = new JPanel(new GridBagLayout());
        rightUpPanel.setBorder(BorderFactory.createEtchedBorder());
        rightUpPanel.setOpaque(true);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 1;
        gridBag.gridy = 0;
        gridBag.fill = GridBagConstraints.BOTH;
        gridBag.insets = new Insets(0, 5, 0, 0);
        gridBag.weighty = 0.25;
        bottomPanel.add(rightUpPanel, gridBag);
        // ETIQUETA ALGORITMO
        lblAlgoritmo = new JLabel(" ");
        lblAlgoritmo.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 16));
        lblAlgoritmo.setHorizontalAlignment(SwingConstants.CENTER);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 0;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(5, 5, 0, 5);
        gridBag.weightx = 1.0;
        gridBag.weighty = 1.0;
        rightUpPanel.add(lblAlgoritmo, gridBag);

        JLabel etiq0 = new JLabel("Algoritmo");
        etiq0.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 10));
        etiq0.setHorizontalAlignment(SwingConstants.CENTER);
        etiq0.setBackground(Color.LIGHT_GRAY);
        etiq0.setOpaque(true);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 1;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 0, 5);
        gridBag.weightx = 1.0;
        rightUpPanel.add(etiq0, gridBag);
        // ETIQUETA VELOCIDAD
        lblVelocidad = new JLabel(" ");
        lblVelocidad.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 16));
        lblVelocidad.setHorizontalAlignment(SwingConstants.CENTER);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 2;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 0, 5);
        gridBag.weightx = 1.0;
        gridBag.weighty = 1.0;
        rightUpPanel.add(lblVelocidad, gridBag);

        etiq0 = new JLabel("Velocidad");
        etiq0.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 10));
        etiq0.setHorizontalAlignment(SwingConstants.CENTER);
        etiq0.setBackground(Color.LIGHT_GRAY);
        etiq0.setOpaque(true);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 3;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 0, 5);
        gridBag.weightx = 1.0;
        rightUpPanel.add(etiq0, gridBag);
        // ETIQUETA ORDEN
        lblOrden = new JLabel(" ");
        lblOrden.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 16));
        lblOrden.setHorizontalAlignment(SwingConstants.CENTER);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 4;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 0, 5);
        gridBag.weightx = 1.0;
        gridBag.weighty = 1.0;
        rightUpPanel.add(lblOrden, gridBag);

        etiq0 = new JLabel("Orden");
        etiq0.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 10));
        etiq0.setHorizontalAlignment(SwingConstants.CENTER);
        etiq0.setBackground(Color.LIGHT_GRAY);
        etiq0.setOpaque(true);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 5;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 0, 5);
        gridBag.weightx = 1.0;
        rightUpPanel.add(etiq0, gridBag);
        // ETIQUETA TIEMPO
        lblTiempo = new JLabel(" ");
        lblTiempo.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 16));
        lblTiempo.setHorizontalAlignment(SwingConstants.CENTER);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 6;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 0, 5);
        gridBag.weightx = 1.0;
        gridBag.weighty = 1.0;
        rightUpPanel.add(lblTiempo, gridBag);

        etiq0 = new JLabel("Tiempo");
        etiq0.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 10));
        etiq0.setHorizontalAlignment(SwingConstants.CENTER);
        etiq0.setBackground(Color.LIGHT_GRAY);
        etiq0.setOpaque(true);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 7;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 0, 5);
        gridBag.weightx = 1.0;
        rightUpPanel.add(etiq0, gridBag);
        // ETIQUETA PASOS
        lblPasos = new JLabel(" ");
        lblPasos.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 16));
        lblPasos.setHorizontalAlignment(SwingConstants.CENTER);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 8;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 0, 5);
        gridBag.weightx = 1.0;
        gridBag.weighty = 1.0;
        rightUpPanel.add(lblPasos, gridBag);

        etiq0 = new JLabel("Pasos");
        etiq0.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 10));
        etiq0.setHorizontalAlignment(SwingConstants.CENTER);
        etiq0.setBackground(Color.LIGHT_GRAY);
        etiq0.setOpaque(true);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 9;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 5, 5);
        gridBag.weightx = 1.0;
        rightUpPanel.add(etiq0, gridBag);
        // -----------------------------------------------------
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBorder(BorderFactory.createEtchedBorder());
        // rightPanel.setBackground(Color.WHITE);
        rightPanel.setOpaque(true);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 1;
        gridBag.gridy = 1;
        gridBag.fill = GridBagConstraints.BOTH;
        gridBag.insets = new Insets(5, 5, 0, 0);
        gridBag.weighty = 0.75;
        bottomPanel.add(rightPanel, gridBag);
        // ETIQUETA ALGORITMO
        JLabel lblAlgorithm = new JLabel("Algoritmo");
        lblAlgorithm.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 0;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(5, 5, 0, 0);
        rightPanel.add(lblAlgorithm, gridBag);
        // OPCION BUBBLE
        rbtnBubble = new JRadioButton("Bubblesort");
        rbtnBubble.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
        btnGAlgorithm.add(rbtnBubble);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 1;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 0, 0);
        rightPanel.add(rbtnBubble, gridBag);
        // OPCION QUICK
        rbtnQuick = new JRadioButton("Quicksort");
        rbtnQuick.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
        btnGAlgorithm.add(rbtnQuick);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 2;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 0, 0);
        rightPanel.add(rbtnQuick, gridBag);
        // OPCION SHELL
        rbtnShell = new JRadioButton("Shellsort");
        rbtnShell.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
        btnGAlgorithm.add(rbtnShell);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 3;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 5, 0, 0);
        rightPanel.add(rbtnShell, gridBag);
        // SEPARADOR
        JSeparator sep0 = new JSeparator();
        sep0.setOrientation(SwingConstants.VERTICAL);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 1;
        gridBag.gridy = 0;
        gridBag.gridheight = 4;
        gridBag.fill = GridBagConstraints.BOTH;
        rightPanel.add(sep0, gridBag);
        // ETIQUETA SENTIDO DE ORDENACION
        JLabel lblSenseSort = new JLabel("Orden");
        lblSenseSort.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));

        gridBag = new GridBagConstraints();
        gridBag.gridx = 2;
        gridBag.gridy = 0;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(5, 0, 0, 5);
        rightPanel.add(lblSenseSort, gridBag);
        // OPCION ASC
        rbtnAsce = new JRadioButton("Ascendente");
        rbtnAsce.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
        btnGType.add(rbtnAsce);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 2;
        gridBag.gridy = 1;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 0, 0, 5);
        rightPanel.add(rbtnAsce, gridBag);
        // OPCION DESC
        rbtnDesc = new JRadioButton("Descendente");
        rbtnDesc.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
        btnGType.add(rbtnDesc);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 2;
        gridBag.gridy = 2;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 0, 0, 5);
        rightPanel.add(rbtnDesc, gridBag);
        // SEPARADOR
        JSeparator sep1 = new JSeparator();
        sep1.setOrientation(SwingConstants.HORIZONTAL);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 4;
        gridBag.gridwidth = 3;
        gridBag.fill = GridBagConstraints.BOTH;
        rightPanel.add(sep1, gridBag);
        // ETIQUETA VELOCIDAD
        JLabel lblVelocity = new JLabel("Velocidad");
        lblVelocity.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 5;
        gridBag.gridwidth = 3;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(5, 5, 0, 5);
        rightPanel.add(lblVelocity, gridBag);
        // SLIDER VELOCIDAD
        slidVelocity = new JSlider(0, 2, 1);
        slidVelocity.setPaintLabels(true);
        slidVelocity.setPaintTicks(false);
        slidVelocity.setSnapToTicks(true);
        var lblSlide = slidVelocity.createStandardLabels(1, 0);
        lblSlide.put(0, new JLabel(new ImageIcon("slow.png")));
        lblSlide.put(1, new JLabel(new ImageIcon("medium.png")));
        lblSlide.put(2, new JLabel(new ImageIcon("fast.png")));
        slidVelocity.setLabelTable(lblSlide);

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 6;
        gridBag.gridwidth = 3;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.weightx = 1.0;
        gridBag.insets = new Insets(0, 5, 0, 5);
        rightPanel.add(slidVelocity, gridBag);
        // BOTON ORDENAR
        btnSortGraph = new JButton("Ordenar gráfica");
        btnSortGraph.setEnabled(false);
        btnSortGraph.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
        btnSortGraph.setHorizontalAlignment(SwingConstants.CENTER);
        btnSortGraph.addActionListener((ActionEvent e) -> {
            btnSortGraphAction(e);
        });

        gridBag = new GridBagConstraints();
        gridBag.gridx = 0;
        gridBag.gridy = 7;
        gridBag.gridwidth = 3;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.weightx = 1.0;
        gridBag.insets = new Insets(5, 5, 5, 5);
        rightPanel.add(btnSortGraph, gridBag);

        pack();
    }

}
