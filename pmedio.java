package graficos;


import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class pmedio extends JPanel {

    private int cols = 80; // Número de columnas
    private int rows = 80; // Número de filas
    private int cellSize = 20; // Tamaño de cada celda en píxeles

    private int x1; // Coordenada x del primer punto
    private int y1; // Coordenada y del primer punto
    private int x2; // Coordenada x del segundo punto
    private int y2; // Coordenada y del segundo punto

    public pmedio(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
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

                // Mostrar las coordenadas de los extremos de la línea en el título de la ventana
                String title = "Linea Punto Medio Coordenadas:  - (" + x1 + "," + y1 + ") , (" + x2 + "," + y2 + ")";
                JFrame ventana = (JFrame) getTopLevelAncestor();
                ventana.setTitle(title);
        
        // Dibujar la línea utilizando el algoritmo de punto medio
        drawMidPointLine(g, x1, y1, x2, y2);
    }
    
    private void drawMidPointLine(Graphics g, int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int x = x1;
        int y = y1;
        int p = 2 * dy - dx;
        // incE e incNE son las operaciones para sacar 2x la diferencia de Y y 2x la diferencia de dy - dx
        int incE = 2 * dy;
        int incNE = 2 * (dy - dx);
        
        while (x <= x2) {
            int drawX = (x - 1) * cellSize;
            int drawY = (y - 1) * cellSize;
            g.fillRect(drawX, drawY, cellSize, cellSize);

            x = x + 1;
            if (p <= 0)
                p = p + incE;
            else {
                y = y + 1;
                p = p + incNE;
            }
        }
    }

    public static void main(String[] args) {
        
        String inputX1 = JOptionPane.showInputDialog("Ingresa x1:");
        String inputY1 = JOptionPane.showInputDialog("Ingresa y1:");
        String inputX2 = JOptionPane.showInputDialog("Ingresa x2:");
        String inputY2 = JOptionPane.showInputDialog("Ingresa y2:");
        
        
        int x1 = Integer.parseInt(inputX1);
        int y1 = Integer.parseInt(inputY1);
        int x2 = Integer.parseInt(inputX2);
        int y2 = Integer.parseInt(inputY2);

        JFrame ventana = new JFrame("Linea Punto Medio");
        pmedio objeto = new pmedio(x1, y1, x2, y2);
        ventana.add(objeto);
        ventana.setSize(800, 800);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
