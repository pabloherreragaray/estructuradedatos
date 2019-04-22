package estructuradedatos.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class PanelControles extends JPanel implements ActionListener, ChangeListener, GrafoInterfaz {

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
	private JMenuItem miGuardar;
	private JMenuItem miGuardarComo;
	private JMenuItem miAbrir;
	private JMenuItem miCerrar;
	private JMenuItem miNuevo;

	private String archivoActual;

	public PanelControles(MainFrame mf) {
		super();
		mainFrame = mf;
		getGrafoManager().setInterfaz(this);
		crearUI();
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
		btCancelar.setVisible(false);
		chAutonombrar = new JCheckBox("Autonombrar vértices");
		chAutonombrar.setSelected(true);
		chAutonombrar.setVisible(false);
		chEliminarSinPreguntar = new JCheckBox("Eliminar sin preguntar");
		chEliminarSinPreguntar.setVisible(false);
		SpinnerNumberModel modeloAncho = new SpinnerNumberModel(PanelGrafo.anchoPorDefecto, PanelGrafo.anchoMinimo,
				PanelGrafo.anchoMinimo * 10, 50);
		txAnchoPanel = new JSpinner(modeloAncho);
		SpinnerNumberModel modeloAlto = new SpinnerNumberModel(PanelGrafo.altoPorDefecto, PanelGrafo.altoMinimo,
				PanelGrafo.altoMinimo * 10, 50);
		txAltoPanel = new JSpinner(modeloAlto);
		JPanel pnDimension = new JPanel();
		pnDimension.setLayout(new GridBagLayout());
		pnDimension.setBorder(BorderFactory.createTitledBorder("Tamaño del grafo"));

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
		panelSuperior.add(btCancelar, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 4;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panelSuperior.add(chAutonombrar, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 5;
		gc.fill = GridBagConstraints.HORIZONTAL;
		panelSuperior.add(chEliminarSinPreguntar, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 6;
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
	}

	private GrafoManager getGrafoManager() {
		return mainFrame.getGrafoManager();
	}

	private void accionAdicionarAristas() {
		getGrafoManager().setEstadoAdicionAristas();
		btAdicionarAristas.setEnabled(false);
		btAdicionarVertices.setEnabled(false);
		btEliminar.setEnabled(false);
		btCancelar.setVisible(true);
	}

	private void accionAdicionarVertices() {
		getGrafoManager().setEstadoAdicionVertices();
		btAdicionarAristas.setEnabled(false);
		btAdicionarVertices.setEnabled(false);
		btEliminar.setEnabled(false);
		btCancelar.setVisible(true);
		chAutonombrar.setVisible(true);
	}

	private void accionEliminacion() {
		getGrafoManager().setEstadoEliminacion();
		btAdicionarAristas.setEnabled(false);
		btAdicionarVertices.setEnabled(false);
		btEliminar.setEnabled(false);
		btCancelar.setVisible(true);
		chEliminarSinPreguntar.setVisible(true);
	}

	private void accionCancelar() {
		getGrafoManager().setEstadoNormal();
		btAdicionarAristas.setEnabled(true);
		btAdicionarVertices.setEnabled(true);
		btEliminar.setEnabled(true);
		btCancelar.setVisible(false);
		chAutonombrar.setVisible(false);
		chEliminarSinPreguntar.setVisible(false);
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

}
