package graficos;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class parabolapmedio extends JPanel {

    private int cols = 200; // Número de columnas
    private int rows = 200; // Número de filas
    private int cellSize = 20; // Tamaño de cada celda en píxeles

    private int xc; // Coordenada x del vértice de la parábola
    private int yc; // Coordenada y del vértice de la parábola
    private int a; // Coeficiente de la parábola (a en la forma y = ax^2)

    public parabolapmedio (int xc, int yc) {
        this.xc = xc;
        this.yc = yc;
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

                g.setColor(Color.BLACK);
            }
        }

        // Mostrar las coordenadas del vértice y el coeficiente de la parábola en el título de la ventana
        String title = "Parábola Punto Medio Vértice: (" + "X = " + xc + "," + "Y = " + yc + "), Coeficiente (a) = " + a;
        JFrame ventana = (JFrame) getTopLevelAncestor();
        ventana.setTitle(title);

        // Dibujar la parábola utilizando el algoritmo del punto medio
        drawMidPointParabola(g, xc, yc, a);

        g.setColor(Color.RED);
        int drawX = (xc - 1) * cellSize;
        int drawY = (yc - 1) * cellSize;
        g.fillRect(drawX, drawY, cellSize, cellSize);
    }

    private void plotPoint(Graphics g, int x, int y) {
        int drawX = x * cellSize;
        int drawY = y * cellSize;
        g.fillRect(drawX, drawY, cellSize, cellSize);
    }

    private void drawMidPointParabola(Graphics g, int xc, int yc, int a) {
        int x = 0;
        int y = 0;
        int p = 0;

        plotParabolaPoints(g, x, y);

        while (x <= 100) {
            x = x + 1;
            if (p <= 0) {
                p = p + (2 * y) + 1 ;
            } else {
                y = y + 1;
                p = p + 1;
            }
            plotParabolaPoints(g, x, y);
        }
    }

    private void plotParabolaPoints(Graphics g, int x, int y) {
        plotPoint(g, x + y);
    }

    public static void main(String[] args) {
        // Prompt the user to enter the vertex and coefficient of the parabola
        String inputXc = JOptionPane.showInputDialog("Ingresa X del vértice");
        String inputYc = JOptionPane.showInputDialog("Ingresa Y del vértice");

        // Parse the user inputs to integers
        int xc = Integer.parseInt(inputXc);
        int yc = Integer.parseInt(inputYc);


        JFrame ventana = new JFrame("Parábola Punto Medio");
        parabolapmedio objeto = new parabolapmedio (xc, yc);
        ventana.add(objeto);
        ventana.setSize(800, 800);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
