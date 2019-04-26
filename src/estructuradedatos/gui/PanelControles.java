package estructuradedatos.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import estructuradedatos.grafo.Arista;
import estructuradedatos.grafo.RutaMinimaGrafo;
import estructuradedatos.grafo.Vertice;

/**
 * Panel de controles para manipular el grafo
 * 
 * @author Pablo Herrera
 *
 */
public class PanelControles extends JPanel
		implements ActionListener, ChangeListener, GrafoInterfaz, GrafoEstadoListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1044928764332037111L;
	private MainFrame mainFrame;
	private JButton btAdicionarVertices;
	private JButton btAdicionarAristas;
	private JButton btEliminar;
	private JButton btCancelar;
	private JCheckBox chAutonombrar;
	private JCheckBox chEliminarSinPreguntar;
	private JSpinner txAnchoPanel;
	private JSpinner txAltoPanel;
	private JLabel txInfoSeleccion;
	private JPanel pnInfo;
	private JMenuItem miGuardar;
	private JMenuItem miGuardarComo;
	private JMenuItem miAbrir;
	private JMenuItem miCerrar;
	private JMenuItem miNuevo;
	private JPanel pnRutaMinima;
	private JButton btRutaMinima;
	private JLabel lbRutaMinimaVertice1;
	private JLabel lbRutaMinimaVertice2;
	private JLabel lbRutaMinimaDistancia;
	private JLabel lbRutaMinimaListaVertices;
	private JButton btVolverCalcularRM;

	private String archivoActual;

	/**
	 * Constructor
	 * 
	 * @param mf Ventana principal
	 */
	public PanelControles(MainFrame mf) {
		super();
		mainFrame = mf;
		getGrafoManager().setInterfaz(this);
		getGrafoManager().adicionarListener(this);
		crearUI();
		cambiaEstado(0, getGrafoManager().getEstado());
	}

	private void crearUI() {
		setLayout(new BorderLayout());
		// Se crean los páneles interiores
		JPanel panelSuperior = new JPanel();
		JPanel panelCentral = new JPanel();
		add(panelSuperior, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
		panelSuperior.setLayout(new GridBagLayout());
		GridBagConstraints gc;

		// Se crean los controles
		btAdicionarVertices = new JButton("Adicionar vértices");
		btAdicionarAristas = new JButton("Adicionar aristas");
		btEliminar = new JButton("Eliminar");
		btCancelar = new JButton("Cancelar");
		chAutonombrar = new JCheckBox("Autonombrar vértices");
		chAutonombrar.setSelected(true);
		chEliminarSinPreguntar = new JCheckBox("Eliminar sin preguntar");
		SpinnerNumberModel modeloAncho = new SpinnerNumberModel(PanelGrafo.ANCHO_POR_DEFECTO, PanelGrafo.ANCHO_MINIMO,
				PanelGrafo.ANCHO_MINIMO * 10, 50);
		txAnchoPanel = new JSpinner(modeloAncho);
		SpinnerNumberModel modeloAlto = new SpinnerNumberModel(PanelGrafo.ALTO_POR_DEFECTO, PanelGrafo.ALTO_MINIMO,
				PanelGrafo.ALTO_MINIMO * 10, 50);
		txAltoPanel = new JSpinner(modeloAlto);
		JPanel pnDimension = new JPanel();
		pnDimension.setLayout(new GridBagLayout());
		pnDimension.setBorder(BorderFactory.createTitledBorder("Tamaño del grafo"));
		txInfoSeleccion = new JLabel();
		pnInfo = new JPanel();
		pnInfo.setLayout(new BorderLayout());
		pnInfo.setBorder(BorderFactory.createTitledBorder("Ítem seleccionado"));
		pnInfo.setVisible(false);
		pnRutaMinima = new JPanel();
		pnRutaMinima.setLayout(new GridBagLayout());
		pnRutaMinima.setBorder(BorderFactory.createTitledBorder("Cálculo ruta mínima"));
		pnRutaMinima.setVisible(false);
		btRutaMinima = new JButton("Calcular Ruta mínima");
		btRutaMinima.setBackground(Color.ORANGE);
		lbRutaMinimaVertice1 = new JLabel();
		lbRutaMinimaVertice2 = new JLabel();
		lbRutaMinimaDistancia = new JLabel();
		lbRutaMinimaListaVertices = new JLabel();
		btVolverCalcularRM = new JButton("Volver a calcular");

		// Se adicionan al panel
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panelSuperior.add(btAdicionarVertices, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panelSuperior.add(btAdicionarAristas, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panelSuperior.add(btEliminar, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panelSuperior.add(btRutaMinima, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 4;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panelSuperior.add(btCancelar, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 5;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panelSuperior.add(chAutonombrar, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 6;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panelSuperior.add(chEliminarSinPreguntar, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 7;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panelSuperior.add(pnDimension, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		pnDimension.add(new JLabel("Ancho"), gc);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnDimension.add(txAnchoPanel, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		pnDimension.add(new JLabel("Alto"), gc);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnDimension.add(txAltoPanel, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 8;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panelSuperior.add(pnInfo, gc);
		pnInfo.add(txInfoSeleccion, BorderLayout.CENTER);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 9;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panelSuperior.add(pnRutaMinima, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnRutaMinima.add(new JLabel("Vértice inicial"), gc);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnRutaMinima.add(lbRutaMinimaVertice1, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnRutaMinima.add(new JLabel("Vértice final"), gc);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnRutaMinima.add(lbRutaMinimaVertice2, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnRutaMinima.add(new JLabel("Distancia"), gc);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 2;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnRutaMinima.add(lbRutaMinimaDistancia, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnRutaMinima.add(new JLabel("Lista de vértices"), gc);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 3;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnRutaMinima.add(lbRutaMinimaListaVertices, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 4;
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnRutaMinima.add(btVolverCalcularRM, gc);

		// Se crean los menús
		JMenuBar menuBar = new JMenuBar();
		JMenu menuArchivo = new JMenu("Archivo");
		menuBar.add(menuArchivo);
		miGuardar = new JMenuItem("Guardar");
		miGuardarComo = new JMenuItem("Guardar como");
		miAbrir = new JMenuItem("Abrir");
		miNuevo = new JMenuItem("Nuevo");
		miCerrar = new JMenuItem("Cerrar");
		menuArchivo.add(miNuevo);
		menuArchivo.add(miAbrir);
		menuArchivo.add(miGuardar);
		menuArchivo.add(miGuardarComo);
		menuArchivo.addSeparator();
		menuArchivo.add(miCerrar);
		mainFrame.setJMenuBar(menuBar);

		// Adición de listeners para los botones
		btAdicionarAristas.addActionListener(this);
		btAdicionarVertices.addActionListener(this);
		btEliminar.addActionListener(this);
		btCancelar.addActionListener(this);
		txAnchoPanel.addChangeListener(this);
		txAltoPanel.addChangeListener(this);
		miGuardar.addActionListener(this);
		miGuardarComo.addActionListener(this);
		miAbrir.addActionListener(this);
		miNuevo.addActionListener(this);
		miCerrar.addActionListener(this);
		btRutaMinima.addActionListener(this);
		btVolverCalcularRM.addActionListener(this);
	}

	private GrafoManager getGrafoManager() {
		return mainFrame.getGrafoManager();
	}

	private void accionAdicionarAristas() {
		getGrafoManager().setEstadoAdicionAristas();
	}

	private void accionAdicionarVertices() {
		getGrafoManager().setEstadoAdicionVertices();
	}

	private void accionEliminacion() {
		getGrafoManager().setEstadoEliminacion();
	}

	private void accionCancelar() {
		getGrafoManager().setEstadoNormal();
	}

	private void accionCambiaDimensionPanel() {
		int ancho = ((Integer) txAnchoPanel.getValue()).intValue();
		int alto = ((Integer) txAltoPanel.getValue()).intValue();
		getGrafoManager().setAnchoGrafo(ancho);
		getGrafoManager().setAltoGrafo(alto);
	}

	private void accionGuardar(boolean como) {
		if (como || archivoActual == null || archivoActual.length() == 0) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Guardar grafo como");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Xml", "xml");
			fc.addChoosableFileFilter(filter);
			fc.setFileFilter(filter);
			int option = fc.showSaveDialog(mainFrame);
			if (option == JFileChooser.APPROVE_OPTION) {
				archivoActual = fc.getSelectedFile().getAbsolutePath();
				if (!archivoActual.toLowerCase().endsWith(".xml"))
					archivoActual = archivoActual + ".xml";
			}
		}
		if (archivoActual == null || archivoActual.length() == 0)
			return;
		try {
			getGrafoManager().guardarGrafoXmlComo(archivoActual);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void accionAbrir() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Abrir grafo desde archivo");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Xml", "xml");
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		int option = fc.showOpenDialog(mainFrame);
		if (option == JFileChooser.APPROVE_OPTION) {
			archivoActual = fc.getSelectedFile().getAbsolutePath();
			if (!archivoActual.toLowerCase().endsWith(".xml"))
				archivoActual = archivoActual + ".xml";
		}
		if (archivoActual == null || archivoActual.length() == 0)
			return;
		try {
			getGrafoManager().abrirGrafoDesdeXml(archivoActual);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void accionNuevo() {
		archivoActual = null;
		getGrafoManager().nuevoGrafo();
	}

	private void accionCerrar() {
		mainFrame.setVisible(false);
		mainFrame.dispose();
	}

	private void accionRutaMinima() {
		lbRutaMinimaVertice1.setText("");
		lbRutaMinimaVertice2.setText("");
		lbRutaMinimaDistancia.setText("");
		lbRutaMinimaListaVertices.setText("");
		getGrafoManager().setEstadoRutaMinima();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btAdicionarAristas)
			accionAdicionarAristas();
		else if (e.getSource() == btAdicionarVertices)
			accionAdicionarVertices();
		else if (e.getSource() == btEliminar)
			accionEliminacion();
		else if (e.getSource() == btCancelar)
			accionCancelar();
		else if (e.getSource() == miGuardar)
			accionGuardar(false);
		else if (e.getSource() == miGuardarComo)
			accionGuardar(true);
		else if (e.getSource() == miAbrir)
			accionAbrir();
		else if (e.getSource() == miNuevo)
			accionNuevo();
		else if (e.getSource() == miCerrar)
			accionCerrar();
		else if (e.getSource() == btRutaMinima)
			accionRutaMinima();
		else if (e.getSource() == btVolverCalcularRM)
			accionRutaMinima();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == txAltoPanel || e.getSource() == txAnchoPanel)
			accionCambiaDimensionPanel();
	}

	@Override
	public String obtenerNombreVertice() {
		if (chAutonombrar.isSelected())
			return null;
		else {
			return JOptionPane.showInputDialog(mainFrame, "Introduzca el nombre del vértice", "");
		}
	}

	@Override
	public void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(mainFrame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void cambiaEstado(int estadoAnterior, int estadoActual) {
		btAdicionarAristas.setEnabled(estadoActual == GrafoManager.VISUALIZACION);
		btAdicionarVertices.setEnabled(estadoActual == GrafoManager.VISUALIZACION);
		btEliminar.setEnabled(estadoActual == GrafoManager.VISUALIZACION);
		btRutaMinima.setEnabled(estadoActual == GrafoManager.VISUALIZACION);
		btCancelar.setVisible(estadoActual != GrafoManager.VISUALIZACION);
		chAutonombrar.setVisible(estadoActual == GrafoManager.ADICION_VERTICES);
		chEliminarSinPreguntar.setVisible(estadoActual == GrafoManager.ELIMINACION);
		pnRutaMinima.setVisible(estadoActual == GrafoManager.RUTA_MINIMA);
	}

	private String getVerticeInfo(Vertice v) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><table>");
		sb.append("<tr><th colspan='2'>Vertice</th></tr>");
		sb.append("<tr><th>Nombre</th><td>").append(v.getNombre()).append("</td></tr>");
		sb.append("<tr><th>X</th><td>").append(v.getX()).append("</td></tr>");
		sb.append("<tr><th>Y</th><td>").append(v.getY()).append("</td></tr>");
		sb.append("<tr><th>Conectado con:</th><td>");
		List<String> verticesConectados = getGrafoManager().getGrafo().getVerticesConectadosCon(v.getNombre());
		boolean separador = false;
		for (String n : verticesConectados) {
			if (separador)
				sb.append("<br/>");
			else
				separador = true;
			sb.append(n);
		}
		sb.append("</td></tr>");
		sb.append("</table></html>");
		return sb.toString();
	}

	private String getAristaInfo(Arista a) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><table>");
		sb.append("<tr><th colspan='2'>Arista</th></tr>");
		sb.append("<tr><th>Vértice 1</th><td>").append(a.getVertice1().getNombre()).append("</td></tr>");
		sb.append("<tr><th>Vértice 2</th><td>").append(a.getVertice2().getNombre()).append("</td></tr>");
		sb.append("<tr><th>Distancia</th><td>").append(Math.round(a.getDistancia())).append("</td></tr>");
		sb.append("</table></html>");
		return sb.toString();
	}

	@Override
	public void verticeSeleccionado(Vertice v) {
		pnInfo.setVisible(true);
		txInfoSeleccion.setText(getVerticeInfo(v));
	}

	@Override
	public void aristaSeleccionada(Arista a) {
		pnInfo.setVisible(true);
		txInfoSeleccion.setText(getAristaInfo(a));
	}

	@Override
	public void seleccionaNada() {
		pnInfo.setVisible(false);
	}

	private boolean mostrarConfirmacion(String mensaje, String titulo) {
		int result = JOptionPane.showConfirmDialog(mainFrame, mensaje, titulo, JOptionPane.YES_NO_OPTION);
		return result == JOptionPane.YES_OPTION;
	}

	@Override
	public boolean eliminarArista(Arista a) {
		if (chEliminarSinPreguntar.isSelected())
			return true;
		else {
			String mensaje = "¿Desea eliminar la arista que conecta los vértices " + a.getVertice1().getNombre() + " y "
					+ a.getVertice2().getNombre() + "?";
			return mostrarConfirmacion(mensaje, "Eliminar arista");
		}
	}

	@Override
	public boolean eliminarVertice(Vertice v) {
		if (chEliminarSinPreguntar.isSelected())
			return true;
		else {
			String mensaje = "¿Desea eliminar el vértice " + v.getNombre() + "?";
			return mostrarConfirmacion(mensaje, "Eliminar vértice");
		}
	}

	@Override
	public void obtieneRutaMinima() {
		RutaMinimaGrafo r = getGrafoManager().getRutaMinima();
		if (r == null) {
			mostrarError("Ruta mínima es nula");
			return;
		}
		lbRutaMinimaDistancia.setText(String.valueOf((int) r.getDistancia()));
		StringBuilder sb = new StringBuilder();
		sb.append("<html><ol>");
		List<String> listaVertices = r.getVertices();
		for (String v : listaVertices) {
			sb.append("<li>").append(v).append("</li>");
		}
		sb.append("</ol></html>");
		lbRutaMinimaListaVertices.setText(sb.toString());
	}

	@Override
	public void obtieneVerticeRutaMinima(Vertice v, boolean inicial) {
		JLabel lb = inicial ? lbRutaMinimaVertice1 : lbRutaMinimaVertice2;
		lb.setText(v.getNombre());
	}

}
