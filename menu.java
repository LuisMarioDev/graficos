package graficos;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class menu extends JPanel {

    public static void main(String[] args) {
        while (true) {
            String inputOption = JOptionPane.showInputDialog(
                "Seleccione el algoritmo a ejecutar:\n" +
                "1. Circulo Punto Medio\n" +
                "2. Salir\n" +
                "Opción:"
            );

            int option = Integer.parseInt(inputOption);
            switch (option) {
                case 1:
                    executelineaDDA();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Saliendo del programa...");
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida. Intente nuevamente.");
            }
        }
    }

    private int cols = 80; // Columnas
    private int rows = 80; // Filas
    private int cellSize = 20; // Tamaño de los cuadros
    private int x1, y1, x2, y2; // Coordenadas de la línea

    public menu(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellSize;
                int y = row * cellSize;
                g.drawRect(x, y, cellSize, cellSize);
            }
        }

        g.setColor(Color.BLACK);
        drawDDALinea(g, x1, y1, x2, y2);
    }
    
    private void drawDDALinea(Graphics g, int x1, int y1, int x2, int y2) {
        // ... Código del algoritmo DDA para dibujar la línea ...
        // Código no mostrado para mantener la simplicidad en esta respuesta
    }

    public static void executelineaDDA() {
        String inputX1 = JOptionPane.showInputDialog("Ingresa X1:");
        String inputY1 = JOptionPane.showInputDialog("Ingresa Y1:");
        String inputX2 = JOptionPane.showInputDialog("Ingresa X2:");
        String inputY2 = JOptionPane.showInputDialog("Ingresa Y2:");

        int x1 = Integer.parseInt(inputX1);
        int y1 = Integer.parseInt(inputY1);
        int x2 = Integer.parseInt(inputX2);
        int y2 = Integer.parseInt(inputY2);

        int defaultCols = 80;
        int defaultRows = 80;

        JFrame coordenadasFrame = new JFrame("Coordenadas DDA");
        coordenadasFrame.setSize(700, 800);
        coordenadasFrame.setLocationRelativeTo(null);
        coordenadasFrame.setLayout(null);

        DefaultTableModel coordenadasModel = getCoordenadasTableModel(defaultCols, defaultRows, x1, y1, x2, y2);
        JTable coordenadasTable = new JTable(coordenadasModel);

        JScrollPane scrollPane = new JScrollPane(coordenadasTable);
        scrollPane.setBounds(10, 10, 650, 600);
        coordenadasFrame.add(scrollPane);

        // Crear la ventana de gráfica
        JFrame graficaFrame = new JFrame("Gráfica DDA");
        menu objeto = new menu(x1, y1, x2, y2);
        graficaFrame.add(objeto);
        graficaFrame.setSize(defaultCols * objeto.cellSize, defaultRows * objeto.cellSize + 50);
        graficaFrame.setLocationRelativeTo(null);

        // Mostrar ambas ventanas simultáneamente
        graficaFrame.setVisible(true);
        coordenadasFrame.setVisible(true);

        // Asignar el mismo evento de cierre a ambas ventanas para que ambas se cierren al cerrar una de ellas
        coordenadasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graficaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        coordenadasFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mostrarVentanaGrafica(defaultCols, defaultRows, x1, y1, x2, y2);
            }
        });
    }

    private static DefaultTableModel getCoordenadasTableModel(int cols, int rows, int x1, int y1, int x2, int y2) {
        DefaultTableModel model = new DefaultTableModel();
        // ... Código para generar las coordenadas de la línea ...
        // Código no mostrado para mantener la simplicidad en esta respuesta
        return model;
    }

    private static void mostrarVentanaGrafica(int cols, int rows, int x1, int y1, int x2, int y2) {
        String title = "Linea DDA Coordenadas: (" + x1 + "," + y1 + ") , (" + x2 + "," + y2 + ")";
        JFrame ventana = new JFrame(title);
        menu objeto = new menu(x1, y1, x2, y2);

        ventana.add(objeto);
        ventana.setSize(cols * objeto.cellSize, rows * objeto.cellSize + 50);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
