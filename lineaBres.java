package graficos;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class lineaBres extends JPanel {

    private int cols = 80; // Número de columnas
    private int rows = 80; // Número de filas
    private int cellSize = 20; // Tamaño de cada cuadro en píxeles

    private int x1; // Coordenada X del primer punto
    private int y1; // Coordenada Y del primer punto
    private int x2; // Coordenada X del segundo punto
    private int y2; // Coordenada Y del segundo punto

    private VentanaCoordenadas ventanaCoordenadas; // Referencia a la ventana de coordenadas

    public lineaBres(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        // Crear la ventana de coordenadas
        ventanaCoordenadas = new VentanaCoordenadas();
        ventanaCoordenadas.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellSize;
                int y = row * cellSize;

                g.setColor(Color.BLACK);

                // Dibujar un pequeño cuadro en (x, y)
                g.drawRect(x, y, cellSize, cellSize);
            }
        }

        // Mostrar las coordenadas de los puntos de la línea en el título de la ventana
        drawBresenhamLine(g, x1, y1, x2, y2);

    }

    private void drawBresenhamLine(Graphics g, int x0, int y0, int x1, int y1) {
        int x, y, dx, dy, xend, p, incE, incNE;
        dx = Math.abs(x1 - x0);
        dy = Math.abs(y1 - y0);
        p = 2 * dy - dx;
        incE = 2 * dy;
        incNE = 2 * (dy - dx);

        // Determinar qué punto usar para empezar, cuál para terminar
        if (x0 > x1) {
            x = x1;
            y = y1;
            xend = x0;
        } else {
            x = x0;
            y = y0;
            xend = x1;
        }

        // Se cicla hasta llegar al extremo de la línea
        while (x <= xend) {
            int drawX = (x - 1) * cellSize;
            int drawY = (y - 1) * cellSize;
            g.fillRect(drawX, drawY, cellSize, cellSize);

            // Agregar las coordenadas a la ventana de VentanaCoordenadas
            ventanaCoordenadas.agregarCoordenada(x, y);

            x = x + 1;
            if (p < 0)
                p = p + incE;
            else {
                y = y + 1;
                p = p + incNE;
            }
        }
    }

    public static void main(String[] args) {
        // Pedir al usuario que ingrese las coordenadas x1, y1, x2, y2
        String inputX1 = JOptionPane.showInputDialog("Ingresa x1:");
        String inputY1 = JOptionPane.showInputDialog("Ingresa y1:");
        String inputX2 = JOptionPane.showInputDialog("Ingresa x2:");
        String inputY2 = JOptionPane.showInputDialog("Ingresa y2:");

        // Convertir las entradas del usuario a enteros
        int x1 = Integer.parseInt(inputX1);
        int y1 = Integer.parseInt(inputY1);
        int x2 = Integer.parseInt(inputX2);
        int y2 = Integer.parseInt(inputY2);

        String title = "Linea Bresenham Coordenadas: (" + x1 + "," + y1 + ") , (" + x2 + "," + y2 + ")";
    JFrame ventana = new JFrame(title);
    lineaBres objeto = new lineaBres(x1, y1, x2, y2);
    ventana.add(objeto);
    ventana.setSize(800, 800);
    ventana.setLocationRelativeTo(null); // Centra la ventana principal en la pantalla

    // Centrar la ventana de coordenadas en la pantalla
    objeto.ventanaCoordenadas.setLocationRelativeTo(null);

    ventana.setVisible(true);
    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Mostrar la ventana de coordenadas en primer plano
    objeto.ventanaCoordenadas.setVisible(true);
    }
}

class VentanaCoordenadas extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;

    public VentanaCoordenadas() {
        // Configuración de la ventana
        setTitle("Tabla de Coordenadas");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear el modelo de la tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("X");
        tableModel.addColumn("Y");

        // Crear la tabla con el modelo
        table = new JTable(tableModel);

        // Agregar la tabla a un JScrollPane para que sea desplazable
        JScrollPane scrollPane = new JScrollPane(table);

        // Agregar el JScrollPane al contenido de la ventana
        add(scrollPane);
    }

    public void agregarCoordenada(int x, int y) {
        // Agregar una nueva fila a la tabla con las coordenadas
        tableModel.addRow(new Object[] { x, y });
    }
}
