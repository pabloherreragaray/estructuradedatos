package estructuradedatos.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6235750142117653161L;
	private GrafoManager grafoManager;
	private PanelGrafo panelGrafo;
	private PanelControles panelControles;
	private PanelBarraEstado panelBarraEstado;

	public MainFrame() {
		super();
		grafoManager = new GrafoManager();
		crearUI();
		setTitle("Grafos y Cálculo de Ruta Mínima");
		setMinimumSize(new Dimension(1200, 800));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private void crearUI() {
		Container cont = getContentPane();
		cont.setLayout(new BorderLayout());
		panelGrafo = new PanelGrafo(this);
		JScrollPane scroll = new JScrollPane(panelGrafo);
		scroll.setAutoscrolls(false);
		add(scroll, BorderLayout.CENTER);
		panelControles = new PanelControles(this);
		add(panelControles, BorderLayout.EAST);
		panelBarraEstado = new PanelBarraEstado(this);
		add(panelBarraEstado, BorderLayout.SOUTH);
	}

	public GrafoManager getGrafoManager() {
		return grafoManager;
	}

	public PanelGrafo getPanelGrafo() {
		return panelGrafo;
	}

	public PanelControles getPanelControles() {
		return panelControles;
	}

	public PanelBarraEstado getPanelBarraEstado() {
		return panelBarraEstado;
	}

}
