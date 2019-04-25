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

import javax.swing.JPanel;

import estructuradedatos.grafo.Arista;
import estructuradedatos.grafo.Vertice;

public class PanelGrafo extends JPanel implements MouseListener, GrafoGraficador {
	public static final int ANCHO_MINIMO = 500;
	public static final int ALTO_MINIMO = 500;
	public static final int ANCHO_POR_DEFECTO = 1200;
	public static final int ALTO_POR_DEFECTO = 650;
	public static final Color COLOR_FONDO = Color.WHITE;
	public static final Color COLOR_VERTICE = Color.RED;
	public static final Color COLOR_VERTICE_SELECCIONADO = Color.BLUE;
	public static final Color COLOR_BORDE_VERTICE = Color.BLACK;
	public static final Color COLOR_LINEA_ARISTA = Color.LIGHT_GRAY;
	public static final Color COLOR_LINEA_ARISTA_SELECCIONADA = Color.DARK_GRAY;
	public static final int RADIO_VERTICE = 8;
	public static final Color COLOR_TEXTO_VERTICE = Color.BLACK;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1660198222004003847L;
	private MainFrame mainFrame;

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
		for (Arista a : getGrafoManager().getGrafo().getAristas()) {
			Vertice v1 = a.getVertice1();
			Vertice v2 = a.getVertice2();
			if (v1.getX() > ancho || v1.getY() > alto || v2.getX() > ancho || v2.getY() > alto)
				continue;
			boolean seleccionada = getGrafoManager().aristaEstaSeleccionada(a);
			g.setPaint(seleccionada ? COLOR_LINEA_ARISTA_SELECCIONADA : COLOR_LINEA_ARISTA);
			g.drawLine((int) v1.getX(), (int) v1.getY(), (int) v2.getX(), (int) v2.getY());
		}
		g.setStroke(oldStroke);
		for (Vertice v : getGrafoManager().getGrafo().getVertices()) {
			if (v.getX() > ancho || v.getY() > alto)
				continue;
			boolean seleccionado = getGrafoManager().verticeEstaSeleccionado(v);
			g.setPaint(seleccionado ? COLOR_VERTICE_SELECCIONADO : COLOR_VERTICE);
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
