package estructuradedatos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Es el panel que muestra el estado del manejador y los mensajes
 * correspondientes para guiar al usuario
 * 
 * @author Pablo Herrera
 *
 */
public class PanelBarraEstado extends JPanel implements GrafoEstadoListener {
	/**
	 * Mensaje al añadir vértices
	 */
	public static final String MENSAJE_ADICION_VERTICES = "Haga clic dentro del panel para adicionar "
			+ "nuevos vértices al grafo";
	/**
	 * Mensaje al añadir aristas
	 */
	public static final String MENSAJE_ADICION_ARISTAS = "Haga clic sobre un vértice para seleccionarlo, "
			+ "luego haga clic sobre otro vértice para conectar ambos con una arista. "
			+ "Para cancelar la selección, haga clic por fuera del grafo";
	/**
	 * Mensaje al eliminar vértices y aristas
	 */
	public static final String MENSAJE_ELIMINACION = "Haga clic sobre el vértice o arista que desea eliminar";
	/**
	 * Mensaje previo al cálculo de la ruta mínima
	 */
	public static final String MENSAJE_RUTA_MINIMA = "Haga clic sobre el vértice inicial y luego sobre el vértice "
			+ "final para calcular la ruta mínima";
	/**
	 * Mensaje en estado normal
	 */
	public static final String MENSAJE_VISUALIZACION = " ";

	/**
	 * 
	 */
	private static final long serialVersionUID = -7291819830267864586L;
	private MainFrame mainFrame;
	private JLabel label;

	/**
	 * Constructor
	 * 
	 * @param mf Ventana principal
	 */
	public PanelBarraEstado(MainFrame mf) {
		super();
		mainFrame = mf;
		crearUI();
		getGrafoManager().adicionarListener(this);
	}

	private void crearUI() {
		setLayout(new BorderLayout());
		label = new JLabel(MENSAJE_VISUALIZACION);
		label.setBorder(BorderFactory.createEtchedBorder());
		label.setFont(new Font(label.getFont().getName(), Font.BOLD, 16));
		label.setForeground(Color.BLUE);
		add(label, BorderLayout.CENTER);
	}

	private GrafoManager getGrafoManager() {
		return mainFrame.getGrafoManager();
	}

	private void setMensaje(String mensaje) {
		label.setText(mensaje);
	}

	@Override
	public void cambiaEstado(int estadoAnterior, int estadoActual) {
		String mensaje;
		switch (estadoActual) {
		case GrafoManager.ADICION_VERTICES:
			mensaje = MENSAJE_ADICION_VERTICES;
			break;
		case GrafoManager.ADICION_ARISTAS:
			mensaje = MENSAJE_ADICION_ARISTAS;
			break;
		case GrafoManager.ELIMINACION:
			mensaje = MENSAJE_ELIMINACION;
			break;
		case GrafoManager.RUTA_MINIMA:
			mensaje = MENSAJE_RUTA_MINIMA;
			break;
		default:
			mensaje = MENSAJE_VISUALIZACION;
		}
		setMensaje(mensaje);
	}

}
