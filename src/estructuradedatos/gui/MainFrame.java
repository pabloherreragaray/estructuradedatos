package estructuradedatos.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Ventana principal del aplicativo
 * 
 * @author Pablo Herrera
 *
 */
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6235750142117653161L;
	private GrafoManager grafoManager;
	private PanelGrafo panelGrafo;
	private PanelControles panelControles;
	private PanelBarraEstado panelBarraEstado;

	/**
	 * Constructor
	 */
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

	/**
	 * Devuelve el manejador del grafo
	 * 
	 * @return Manejador del grafo
	 */
	public GrafoManager getGrafoManager() {
		return grafoManager;
	}

	/**
	 * Devuelve el panel que grafica
	 * 
	 * @return Panel que grafica
	 */
	public PanelGrafo getPanelGrafo() {
		return panelGrafo;
	}

	/**
	 * Devuelve el panel de controles
	 * 
	 * @return Panel de controles
	 */
	public PanelControles getPanelControles() {
		return panelControles;
	}

	/**
	 * Devuelve el panel que muestra el estado
	 * 
	 * @return Panel que muestra el estado
	 */
	public PanelBarraEstado getPanelBarraEstado() {
		return panelBarraEstado;
	}

}
