package graficos;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class elipsepmedio extends JPanel {
    
    private int cols = 200; // Número de columnas
    private int rows = 200; // Número de filas
    private int cellSize = 20; // Tamaño de cada celda en píxeles

    private int xc; // Coordenada x del centro de la elipse
    private int yc; // Coordenada y del centro de la elipse
    private int rx; // Radio en el eje x de la elipse
    private int ry; // Radio en el eje y de la elipse

    public elipsepmedio(int xc, int yc, int rx, int ry) {
        this.xc = xc;
        this.yc = yc;
        this.rx = rx;
        this.ry = ry;
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

        // Mostrar las coordenadas del centro y los radios de la elipse en el título de la ventana
        String title = "Elipse Punto Medio Centro: (" + "X = " + xc + "," + "Y = " + yc + "), Radio en X (Rx) = " + rx + ", Radio en Y (Ry) = " + ry;
        JFrame ventana = (JFrame) getTopLevelAncestor();
        ventana.setTitle(title);
        
        // Dibujar la elipse utilizando el algoritmo de punto medio
        drawMidPointEllipse(g, xc, yc, rx, ry);

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

    private void drawMidPointEllipse(Graphics g, int xc, int yc, int rx, int ry) {
        int x = 0;
        int y = ry;
        int rx2 = rx * rx;
        int ry2 = ry * ry;
        int tworx2 = 2 * rx2;
        int twory2 = 2 * ry2;
        int p = round(ry2 - rx2 * ry + (0.25 * rx2));

        plotEllipsePoints(g, xc, yc, x, y);

        // Región 1
        int px = 0;
        int py = tworx2 * y;
        while (px < py) {
            x = x + 1;
            px = px + twory2;
            if (p < 0) {
                p = p + ry2 + px;
            } else {
                y = y - 1;
                py = py - tworx2;
                p = p + ry2 + px - py;
            }
            plotEllipsePoints(g, xc, yc, x, y);
        }

        // Región 2
        p = round(ry2 * (x + 0.5) * (x + 0.5) + rx2 * (y - 1) * (y - 1) - rx2 * ry2);
        while (y > 0) {
            y = y - 1;
            py = py - tworx2;
            if (p > 0) {
                p = p + rx2 - py;
            } else {
                x = x + 1;
                px = px + twory2;
                p = p + rx2 - py + px;
            }
            plotEllipsePoints(g, xc, yc, x, y);
        }
    }

    private void plotEllipsePoints(Graphics g, int xc, int yc, int x, int y) {
        plotPoint(g, xc + x, yc + y);
        plotPoint(g, xc - x, yc + y);
        plotPoint(g, xc + x, yc - y);
        plotPoint(g, xc - x, yc - y);
    }

    private int round(double num) {
        return (int) (num + 0.5);
    }

    public static void main(String[] args) {
        // Prompt the user to enter the center and radii of the ellipse
        String inputXc = JOptionPane.showInputDialog("Ingresa X del centro");
        String inputYc = JOptionPane.showInputDialog("Ingresa Y del centro");
        String inputRx = JOptionPane.showInputDialog("Ingresa el Radio en X (Rx)");
        String inputRy = JOptionPane.showInputDialog("Ingresa el Radio en Y (Ry)");

        // Parse the user inputs to integers
        int xc = Integer.parseInt(inputXc);
        int yc = Integer.parseInt(inputYc);
        int rx = Integer.parseInt(inputRx);
        int ry = Integer.parseInt(inputRy);

        JFrame ventana = new JFrame("Elipse Punto Medio");
        elipsepmedio objeto = new elipsepmedio(xc, yc, rx, ry);
        ventana.add(objeto);
        ventana.setSize(800, 800);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Nueva ventana con la tabla de coordenadas
        JFrame tablaVentana = new JFrame("Tabla de Coordenadas");
        tablaVentana.setSize(400, 300);
        tablaVentana.setLocation(850, 200);

        // Crear la tabla con las coordenadas
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("X");
        modelo.addColumn("Y");
        
        // Calcular las coordenadas y agregarlas a la tabla
        int x = 0;
        int y = ry;
        int rx2 = rx * rx;
        int ry2 = ry * ry;
        int tworx2 = 2 * rx2;
        int twory2 = 2 * ry2;
        int p = ry2 - rx2 * ry + (rx2 / 4);

        while (twory2 * x < tworx2 * y) {
            // Región 1
            x = x + 1;
            if (p < 0) {
                p = p + ry2 + twory2 * x;
            } else {
                y = y - 1;
                p = p + ry2 + twory2 * x - tworx2 * y;
            }
            modelo.addRow(new Object[]{xc + x, yc + y});
            modelo.addRow(new Object[]{xc - x, yc + y});
            modelo.addRow(new Object[]{xc + x, yc - y});
            modelo.addRow(new Object[]{xc - x, yc - y});
        }

        p = ry2 * (x + 1/2) * (x + 1/2) + rx2 * (y - 1) * (y - 1) - rx2 * ry2;

        while (y > 0) {
            // Región 2
            y = y - 1;
            if (p > 0) {
                p = p + rx2 - tworx2 * y;
            } else {
                x = x + 1;
                p = p + rx2 - tworx2 * y + twory2 * x;
            }
            modelo.addRow(new Object[]{xc + x, yc + y});
            modelo.addRow(new Object[]{xc - x, yc + y});
            modelo.addRow(new Object[]{xc + x, yc - y});
            modelo.addRow(new Object[]{xc - x, yc - y});
        }

        JTable tabla = new JTable(modelo);
        tablaVentana.add(new JScrollPane(tabla));
        tablaVentana.setVisible(true);
        tablaVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
