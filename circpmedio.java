package graficos;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class circpmedio extends JPanel {

    

    private int cols = 200; // Número de columnas
    private int rows = 200; // Número de filas
    private int cellSize = 20; // Tamaño de cada celda en píxeles

    private int xc; // Coordenada x del centro del círculo
    private int yc; // Coordenada y del centro del círculo
    private int r;  // Radio del círculo

    // Creamos ocho modelos de tabla, uno para cada octante
    private DefaultTableModel[] tableModels;

    public circpmedio(int xc, int yc, int r) {
        this.xc = xc;
        this.yc = yc;
        this.r = r;

        // Creamos el modelo de tabla para cada octante
        String[] columnNames = {"Iteración", "X", "Y", "P"};
        Object[][] data = new Object[0][columnNames.length];
        tableModels = new DefaultTableModel[8];
        for (int i = 0; i < 8; i++) {
            tableModels[i] = new DefaultTableModel(data, columnNames);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Dibujar el grid de celdas
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellSize;
                int y = row * cellSize;

                // Dibujar cuadrado pequeño en (x, y)
                g.drawRect(x, y, cellSize, cellSize);
            }
        }

        // Mostrar las coordenadas del centro y el radio del círculo en el título de la ventana
        String title = "Circulo Punto Medio Centro: (" + "X = " + xc + "," + "Y = " + yc + "), Radio (R) = " + r;
        JFrame ventana = (JFrame) getTopLevelAncestor();
        ventana.setTitle(title);

        // Dibujar el círculo utilizando el algoritmo del punto medio
        drawMidPointCircle(g, xc, yc, r);

        g.setColor(Color.RED);
        int drawX = (xc - 1) * cellSize;
        int drawY = (yc - 1) * cellSize;
        g.fillRect(drawX, drawY, cellSize, cellSize);

        // Dibujar los puntos medios para los ocho octantes
        for (int i = 0; i < 8; i++) {
            drawPointsInOctant(g, xc, yc, i);
        }
    }

    private void drawPointsInOctant(Graphics g, int xc, int yc, int octante) {
        DefaultTableModel tableModel = tableModels[octante];
        int x = 0;
        int y = r;
        int p = 1 - r;
        int iteration = 0;

        // Dibujar punto medio inicial en el octante
        addDataToTable(tableModel, iteration++, x, y, p, octante);

        // Iterar hasta trazar todo un octante
        while (x < y) {
            x = x + 1;
            if (p < 0)
                p = p + 2 * x + 1;
            else {
                y = y - 1;
                p = p + 2 * (x - y) + 1;
            }
            addDataToTable(tableModel, iteration++, x, y, p, octante);
        }
    }

    private void drawMidPointCircle(Graphics g, int xc, int yc, int r) {
        int x = 0;
        int y = r;
        int p = 1 - r;
        plotPoint(g, xc + x, yc + y);
        plotPoint(g, xc - x, yc + y);
        plotPoint(g, xc + x, yc - y);
        plotPoint(g, xc - x, yc - y);
        plotPoint(g, xc + y, yc + x);
        plotPoint(g, xc - y, yc + x);
        plotPoint(g, xc + y, yc - x);
        plotPoint(g, xc - y, yc - x);

        /* se cicla hasta trazar todo un octante */
        while (x < y) {
            x = x + 1;
            if (p < 0)
                p = p + 2 * x + 1;
            else {
                y = y - 1;
                p = p + 2 * (x - y) + 1;
            }
            plotPoint(g, xc + x, yc + y);
            plotPoint(g, xc - x, yc + y);
            plotPoint(g, xc + x, yc - y);
            plotPoint(g, xc - x, yc - y);
            plotPoint(g, xc + y, yc + x);
            plotPoint(g, xc - y, yc + x);
            plotPoint(g, xc + y, yc - x);
            plotPoint(g, xc - y, yc - x);
        }
    }

    private void plotPoint(Graphics g, int x, int y) {
        int drawX = x * cellSize;
        int drawY = y * cellSize;
        g.fillRect(drawX, drawY, cellSize, cellSize);
    }

    private void addDataToTable(DefaultTableModel tableModel, int iteration, int x, int y, int p, int octante) {
        int realX = 0, realY = 0;
        switch (octante) {
            case 0:
                realX = x;
                realY = y;
                break;
            case 1:
                realX = y;
                realY = x;
                break;
            case 2:
                realX = -y;
                realY = x;
                break;
            case 3:
                realX = -x;
                realY = y;
                break;
            case 4:
                realX = -x;
                realY = -y;
                break;
            case 5:
                realX = -y;
                realY = -x;
                break;
            case 6:
                realX = y;
                realY = -x;
                break;
            case 7:
                realX = x;
                realY = -y;
                break;
        }

        tableModel.addRow(new Object[]{iteration, realX, realY, p});
    }

    public static void main(String[] args) {
        // Prompt the user to enter the center and radius of the circle
        String inputXc = JOptionPane.showInputDialog("Ingresa X");
        String inputYc = JOptionPane.showInputDialog("Ingresa Y");
        String inputR = JOptionPane.showInputDialog("Ingresa el Radio (R)");

        // Parse the user inputs to integers
        int xc = Integer.parseInt(inputXc);
        int yc = Integer.parseInt(inputYc);
        int r = Integer.parseInt(inputR);

        JFrame ventana = new JFrame("Circulo Punto Medio");
        circpmedio objeto = new circpmedio(xc, yc, r);
        ventana.add(objeto);
        ventana.setSize(800, 800);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear una nueva ventana para mostrar la tabla con los cálculos para cada octante
        for (int i = 0; i < 8; i++) {
            JFrame tableFrame = new JFrame("Tabla de Coordenadas Punto Medio - Octante " + i);
            JTable table = new JTable();
            table.setModel(objeto.tableModels[i]);
            JScrollPane scrollPane = new JScrollPane(table);
            tableFrame.add(scrollPane);
            tableFrame.setSize(300, 400);
            tableFrame.setLocation(900 + i % 2 * 300, 100 + i / 2 * 400);
            tableFrame.setVisible(true);
            tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }
}