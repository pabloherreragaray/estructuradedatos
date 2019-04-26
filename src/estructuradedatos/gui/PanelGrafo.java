package estructuradedatos.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;

import estructuradedatos.grafo.Arista;
import estructuradedatos.grafo.Vertice;

/**
 * Panel que grafica el grafo
 * 
 * @author Pablo Herrera
 *
 */
public class PanelGrafo extends JPanel implements MouseListener, GrafoGraficador {
	/**
	 * Ancho mínimo que puede tener un grafo
	 */
	public static final int ANCHO_MINIMO = 500;
	/**
	 * Alto mínimo que puede tener un grafo
	 */
	public static final int ALTO_MINIMO = 500;
	/**
	 * Ancho por defecto del grafo
	 */
	public static final int ANCHO_POR_DEFECTO = 1200;
	/**
	 * Alto por defecto del grafo
	 */
	public static final int ALTO_POR_DEFECTO = 650;
	/**
	 * Color del fondo del grafo
	 */
	public static final Color COLOR_FONDO = Color.WHITE;
	/**
	 * Color normal de un vértice
	 */
	public static final Color COLOR_VERTICE = Color.RED;
	/**
	 * Color de un vértice seleccionado
	 */
	public static final Color COLOR_VERTICE_SELECCIONADO = Color.BLUE;
	/**
	 * Color de un vértice en ruta mínima
	 */
	public static final Color COLOR_VERTICE_RUTA_MINIMA = Color.BLUE;
	/**
	 * Color del borde del vértice
	 */
	public static final Color COLOR_BORDE_VERTICE = Color.BLACK;
	/**
	 * Color de línea de la arista normal
	 */
	public static final Color COLOR_LINEA_ARISTA = Color.LIGHT_GRAY;
	/**
	 * Color de línea de la arista seleccionada
	 */
	public static final Color COLOR_LINEA_ARISTA_SELECCIONADA = Color.DARK_GRAY;
	/**
	 * Color de línea de la arista en ruta mínima
	 */
	public static final Color COLOR_LINEA_ARISTA_RUTA_MINIMA = Color.BLUE;
	/**
	 * Radio del vértice
	 */
	public static final int RADIO_VERTICE = 8;
	/**
	 * Color de texto del vértice
	 */
	public static final Color COLOR_TEXTO_VERTICE = Color.BLACK;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1660198222004003847L;
	private MainFrame mainFrame;

	/**
	 * Constructor
	 * 
	 * @param mf Ventana principal
	 */
	public PanelGrafo(MainFrame mf) {
		super();
		mainFrame = mf;
		getGrafoManager().setGraficador(this);
		GrafoManager.hitboxVertice = RADIO_VERTICE + 4;
		crearUI();
	}

	private void crearUI() {
		setMinimumSize(new Dimension(ANCHO_MINIMO, ALTO_MINIMO));
		getGrafoManager().setAnchoGrafo(ANCHO_POR_DEFECTO);
		getGrafoManager().setAltoGrafo(ALTO_POR_DEFECTO);
		addMouseListener(this);
	}

	@Override
	public void paint(Graphics gr) {
		super.paint(gr);
		Graphics2D g = (Graphics2D) gr;
		// Relleno de la pantalla
		g.setPaint(COLOR_FONDO);
		int ancho = (int) getPreferredSize().getWidth();
		int alto = (int) getPreferredSize().getHeight();
		g.fillRect(0, 0, ancho, alto);
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(2));
		List<Arista> aristasRutaMinima = getGrafoManager().getAristasRutaMinima();
		List<String> verticesRutaMinima = getGrafoManager().getVerticesRutaMinima();
		for (Arista a : getGrafoManager().getGrafo().getAristas()) {
			Vertice v1 = a.getVertice1();
			Vertice v2 = a.getVertice2();
			if (v1.getX() > ancho || v1.getY() > alto || v2.getX() > ancho || v2.getY() > alto)
				continue;
			boolean seleccionada = getGrafoManager().aristaEstaSeleccionada(a);
			boolean enRutaMinima = aristasRutaMinima.contains(a);
			Color color;
			if (enRutaMinima)
				color = COLOR_LINEA_ARISTA_RUTA_MINIMA;
			else if (seleccionada)
				color = COLOR_LINEA_ARISTA_SELECCIONADA;
			else
				color = COLOR_LINEA_ARISTA;
			g.setPaint(color);
			g.drawLine((int) v1.getX(), (int) v1.getY(), (int) v2.getX(), (int) v2.getY());
		}
		g.setStroke(oldStroke);
		for (Vertice v : getGrafoManager().getGrafo().getVertices()) {
			if (v.getX() > ancho || v.getY() > alto)
				continue;
			boolean seleccionado = getGrafoManager().verticeEstaSeleccionado(v);
			boolean enRutaMinima = verticesRutaMinima.contains(v.getNombre());
			Color color;
			if (enRutaMinima)
				color = COLOR_VERTICE_RUTA_MINIMA;
			else if (seleccionado)
				color = COLOR_VERTICE_SELECCIONADO;
			else
				color = COLOR_VERTICE;
			g.setPaint(color);
			g.fillOval((int) (v.getX() - RADIO_VERTICE), (int) (v.getY() - RADIO_VERTICE), RADIO_VERTICE * 2,
					RADIO_VERTICE * 2);
			g.setPaint(COLOR_BORDE_VERTICE);
			g.drawOval((int) (v.getX() - RADIO_VERTICE), (int) (v.getY() - RADIO_VERTICE), RADIO_VERTICE * 2,
					RADIO_VERTICE * 2);
			g.setFont(new Font("Arial", Font.BOLD, 12));
			g.setPaint(COLOR_TEXTO_VERTICE);
			g.drawString(v.getNombre(), (int) (v.getX()), (int) (v.getY() + RADIO_VERTICE * 2 + 5));
		}
	}

	private GrafoManager getGrafoManager() {
		return mainFrame.getGrafoManager();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		getGrafoManager().clicEn(e.getX(), e.getY());
	}

	@Override
	public void setDimension(int ancho, int alto) {
		setPreferredSize(new Dimension(ancho, alto));
		actualizar();
	}

	@Override
	public void actualizar() {
		repaint();
		revalidate();
	}

}
