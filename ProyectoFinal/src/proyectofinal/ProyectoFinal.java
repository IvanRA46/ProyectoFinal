/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectofinal;

/**
 *
 * @author ivann
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ProyectoFinal extends JFrame {

    private String[] items;      // Nombres de los ítems
    private int[] valores;       // Valores capturados para cada ítem
    private String tipoGrafica;  // "Barras" o "Pastel"
    private final int ancho = 800;
    private final int alto = 600;
    private final Color[] colores = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA}; // Colores para el pastel

    public ProyectoFinal() {
        setTitle("Graficador de Barras y Pastel");
        setSize(ancho, alto);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fondo con imagen
        setContentPane(new JLabel(new ImageIcon("C:\\Users\\ivann\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoFinal\\src\\proyectofinal\\sun.jpg")));
        setLayout(new BorderLayout());

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        JButton botonBarras = new JButton("Gráfico de Barras");
        JButton botonPastel = new JButton("Gráfico de Pastel");
        panelBotones.add(botonBarras);
        panelBotones.add(botonPastel);
        add(panelBotones, BorderLayout.SOUTH);

        // Acciones para los botones
        botonBarras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipoGrafica = "Barras";
                capturarDatos();  // Solicitar datos y graficar
                repaint();        // Redibujar la gráfica
            }
        });

        botonPastel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipoGrafica = "Pastel";
                capturarDatos();  // Solicitar datos y graficar
                repaint();        // Redibujar la gráfica
            }
        });
    }

    // Método para capturar datos
    private void capturarDatos() {
        int numItems = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos ítems desea graficar? (Entre 3 y 5)"));
        if (numItems < 3 || numItems > 5) {
            JOptionPane.showMessageDialog(this, "Número de ítems no válido.");
            return;
        }

        items = new String[numItems];
        valores = new int[numItems];

        for (int i = 0; i < numItems; i++) {
            items[i] = JOptionPane.showInputDialog("Nombre del ítem " + (i + 1));
            valores[i] = Integer.parseInt(JOptionPane.showInputDialog("Valor del ítem " + items[i]));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (items != null && valores != null) {
            if (tipoGrafica.equals("Barras")) {
                dibujarGraficoBarras(g);
            } else if (tipoGrafica.equals("Pastel")) {
                dibujarGraficoPastel(g);
            }
        }
    }

    // Método para dibujar gráfico de barras
    private void dibujarGraficoBarras(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int anchoBarra = (ancho - 100) / valores.length;
        int maxVal = obtenerMaximoValor();

        // Dibujar ejes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(50, alto - 100, ancho - 50, alto - 100); // Eje X
        g2d.drawLine(50, 100, 50, alto - 100);               // Eje Y

        // Dibujar barras con colores diferentes
        for (int i = 0; i < valores.length; i++) {
            int alturaBarra = (valores[i] * (alto - 200)) / maxVal;
            // Seleccionar color para la barra
            g2d.setColor(colores[i % colores.length]);
            g2d.fillRect(50 + (i * anchoBarra), alto - 100 - alturaBarra, anchoBarra - 10, alturaBarra);
            g2d.setColor(Color.BLACK);
            g2d.drawString(items[i], 55 + (i * anchoBarra), alto - 85);
        }
    }

    // Método para dibujar gráfico de pastel
    private void dibujarGraficoPastel(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int total = obtenerTotalValores();
        int anguloInicio = 0;

        for (int i = 0; i < valores.length; i++) {
            int angulo = (int) ((valores[i] * 360.0) / total);

            // Aplicar degradado con colores diferentes
            GradientPaint gradient = new GradientPaint(0, 0, colores[i], 100, 100, Color.WHITE, true);
            g2d.setPaint(gradient);
            g2d.fillArc(200, 200, 300, 300, anguloInicio, angulo);

            // Dibujar cuadro con degradado
            g2d.setPaint(gradient);
            g2d.fillRect(550, 200 + (i * 30), 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawString(items[i], 580, 215 + (i * 30));

            anguloInicio += angulo;
        }
    }

    // Método para obtener el valor máximo
    private int obtenerMaximoValor() {
        int max = 0;
        for (int valor : valores) {
            if (valor > max) {
                max = valor;
            }
        }
        return max;
    }

    // Método para obtener la suma de los valores
    private int obtenerTotalValores() {
        int total = 0;
        for (int valor : valores) {
            total += valor;
        }
        return total;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProyectoFinal app = new ProyectoFinal();
            app.setVisible(true);
        });
    }
}





