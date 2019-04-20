package estructuradedatos.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import estructuradedatos.grafo.Vertice;

public class PanelGrafo extends JPanel implements MouseListener {
	public static final int anchoMinimo = 500;
	public static final int altoMinimo = 500;
	public static final int anchoPorDefecto = 1600;
	public static final int altoPorDefecto = 900;
	public static final Color colorFondo = Color.WHITE;
	public static final Color colorVertice = Color.RED;
	public static final int radioVertice = 8;
	public static final Color colorTextoVertice = Color.BLACK;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1660198222004003847L;
	private MainFrame mainFrame;

	public PanelGrafo(MainFrame mf) {
		super();
		mainFrame = mf;
		crearUI();
	}

	private void crearUI() {
		setPreferredSize(new Dimension(anchoPorDefecto, altoPorDefecto));
		setMinimumSize(new Dimension(anchoMinimo, altoMinimo));
		addMouseListener(this);
	}

	@Override
	public void paint(Graphics gr) {
		super.paint(gr);
		Graphics2D g = (Graphics2D) gr;
		// Relleno de la pantalla
		g.setPaint(colorFondo);
		int ancho = (int) getPreferredSize().getWidth();
		int alto = (int) getPreferredSize().getHeight();
		g.fillRect(0, 0, ancho, alto);
		for (Vertice v : getGrafoManager().getGrafo().getVertices()) {
			if (v.getX() > ancho || v.getY() > alto)
				continue;
			g.setPaint(colorVertice);
			g.fillOval((int) (v.getX() - radioVertice), (int) (v.getY() - radioVertice), radioVertice * 2,
					radioVertice * 2);
			g.setFont(new Font("Arial", Font.BOLD, 12));
			g.setPaint(colorTextoVertice);
			g.drawString(v.getNombre(), (int) (v.getX()), (int) (v.getY() + radioVertice * 2 + 5));
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
		repaint();
	}

}
