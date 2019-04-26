package estructuradedatos.gui;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import estructuradedatos.grafo.Arista;
import estructuradedatos.grafo.Grafo;
import estructuradedatos.grafo.RutaMinimaGrafo;
import estructuradedatos.grafo.Vertice;

/**
 * Clase controladora del grafo en su parte visual
 * 
 * @author Pablo Herrera
 *
 */
public class GrafoManager {
	/**
	 * Estado de visualización (estado normal)
	 */
	public static final int VISUALIZACION = 0;
	/**
	 * Estado de adición de aristas
	 */
	public static final int ADICION_ARISTAS = 2;
	/**
	 * Estado de adición de vértices
	 */
	public static final int ADICION_VERTICES = 1;
	/**
	 * Estado de eliminación de vértices y aristas
	 */
	public static final int ELIMINACION = 3;
	/**
	 * Estado de selección de vértices para la ruta mínima
	 */
	public static final int RUTA_MINIMA = 4;
	/**
	 * Zona sensible para hacer clic sobre un vértice
	 */
	public static int hitboxVertice = 10;
	/**
	 * Zona sensible para hacer clic sobre una arista
	 */
	public static int hitboxArista = 10;

	private Grafo grafo;
	private int estado = 0;
	private Vertice verticeSeleccionado1;
	private Vertice verticeSeleccionado2;
	private Arista aristaSeleccionada;
	private List<GrafoEstadoListener> listeners;
	private GrafoInterfaz interfaz;
	private GrafoGraficador graficador;
	private int anchoGrafo;
	private int altoGrafo;
	private RutaMinimaGrafo rutaMinima;
	private List<Arista> aristasRutaMinima;
	private List<String> verticesRutaMinima;

	/**
	 * Constructor
	 */
	public GrafoManager() {
		grafo = new Grafo();
		listeners = new ArrayList<GrafoEstadoListener>();
		setEstado(VISUALIZACION);
	}

	private void setEstado(int estado) {
		int anterior = this.estado;
		this.estado = estado;
		for (GrafoEstadoListener listener : listeners) {
			listener.cambiaEstado(anterior, this.estado);
		}
		verticeSeleccionado1 = verticeSeleccionado2 = null;
		aristaSeleccionada = null;
		rutaMinima = null;
		aristasRutaMinima = null;
		verticesRutaMinima = null;
		if (graficador != null)
			graficador.actualizar();
	}

	/**
	 * Obtiene el estado del manejador
	 * 
	 * @return Estado
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * Cambia el estado a adición de vértices
	 */
	public void setEstadoAdicionVertices() {
		setEstado(ADICION_VERTICES);
	}

	/**
	 * Cambia el estado a adición de aristas
	 */
	public void setEstadoAdicionAristas() {
		setEstado(ADICION_ARISTAS);
	}

	/**
	 * Cambia el estado a eliminación de vértices y aristas
	 */
	public void setEstadoEliminacion() {
		setEstado(ELIMINACION);
	}

	/**
	 * Cambia el estado a selección de vértices para ruta mínima
	 */
	public void setEstadoRutaMinima() {
		setEstado(RUTA_MINIMA);
	}

	/**
	 * Cambia el estado a visualización (normal)
	 */
	public void setEstadoNormal() {
		setEstado(VISUALIZACION);
	}

	/**
	 * Adiciona un listener (GrafoEstadoListener) al manejador
	 * 
	 * @param listener Nuevo listener
	 */
	public void adicionarListener(GrafoEstadoListener listener) {
		listeners.add(listener);
	}

	/**
	 * Crea un vértice a partir de sus coordenadas. El nombre del nuevo vértice será
	 * preguntado a la interfaz
	 * 
	 * @param x Posición X
	 * @param y Posición Y
	 */
	public void crearVerticeEnCoordenadas(int x, int y) {
		String nombre = interfaz == null ? null : interfaz.obtenerNombreVertice();
		try {
			if (nombre == null || nombre.length() == 0)
				grafo.adicionarVertice(x, y);
			else
				grafo.adicionarVertice(nombre, x, y);
		} catch (Exception e) {
			if (interfaz != null)
				interfaz.mostrarError(e.getMessage());
		}
	}

	/**
	 * Obtiene la interfaz (GrafoInterfaz) del manejador
	 * 
	 * @return
	 */
	public GrafoInterfaz getInterfaz() {
		return interfaz;
	}

	/**
	 * Modifica la interfaz (GrafoInterfaz) del manejador
	 * 
	 * @param interfaz
	 */
	public void setInterfaz(GrafoInterfaz interfaz) {
		this.interfaz = interfaz;
	}

	/**
	 * Se indica que el usuario ha hecho clic en una coordenada
	 * 
	 * @param x Posición X
	 * @param y Posición Y
	 */
	public void clicEn(int x, int y) {
		if (estado == ADICION_VERTICES) {
			crearVerticeEnCoordenadas(x, y);
		} else if (estado == ADICION_ARISTAS) {
			seleccionar(x, y, true, false, false);
			if (verticeSeleccionado1 != null && interfaz != null)
				interfaz.verticeSeleccionado(verticeSeleccionado1);
			else if (interfaz != null)
				interfaz.seleccionaNada();
			if (verticeSeleccionado1 != null && verticeSeleccionado2 != null) {
				try {
					grafo.adicionarArista(verticeSeleccionado1, verticeSeleccionado2);
				} catch (Exception e) {
				}
				verticeSeleccionado1 = verticeSeleccionado2 = null;
			}
		} else if (estado == ELIMINACION) {
			int tipo = seleccionar(x, y, true, true, true);
			boolean eliminar = true;
			if (tipo == 1 && verticeSeleccionado1 != null) {
				if (interfaz != null)
					eliminar = interfaz.eliminarVertice(verticeSeleccionado1);
			} else if (tipo == 2 && aristaSeleccionada != null) {
				if (interfaz != null)
					eliminar = interfaz.eliminarArista(aristaSeleccionada);
			} else {
				eliminar = false;
			}
			if (eliminar) {
				if (tipo == 1)
					grafo.eliminarVertice(verticeSeleccionado1.getNombre());
				else if (tipo == 2)
					grafo.eliminarArista(aristaSeleccionada);
			}
			verticeSeleccionado1 = null;
			aristaSeleccionada = null;
			if (eliminar && graficador != null)
				graficador.actualizar();
		} else if (estado == RUTA_MINIMA) {
			int tipo = seleccionar(x, y, true, false, false);
			if (tipo == 1) {
				if (verticeSeleccionado2 == null && interfaz != null)
					interfaz.obtieneVerticeRutaMinima(verticeSeleccionado1, true);
				else {
					if (interfaz != null)
						interfaz.obtieneVerticeRutaMinima(verticeSeleccionado2, false);
					try {
						rutaMinima = grafo.getRutaMinima(verticeSeleccionado1.getNombre(),
								verticeSeleccionado2.getNombre());
						aristasRutaMinima = rutaMinima.getListaAristas();
						verticesRutaMinima = rutaMinima.getVertices();
						if (interfaz != null)
							interfaz.obtieneRutaMinima();
						if (graficador != null)
							graficador.actualizar();
						verticeSeleccionado1 = verticeSeleccionado2 = null;
						aristaSeleccionada = null;
					} catch (Exception e) {
						if (interfaz != null)
							interfaz.mostrarError(e.getMessage());
					}
				}
			}
		} else {
			verticeSeleccionado1 = verticeSeleccionado2 = null;
			aristaSeleccionada = null;
			int tipo = seleccionar(x, y, true, true, true);
			if (tipo == 1 && verticeSeleccionado1 != null && interfaz != null)
				interfaz.verticeSeleccionado(verticeSeleccionado1);
			else if (tipo == 2 && aristaSeleccionada != null && interfaz != null)
				interfaz.aristaSeleccionada(aristaSeleccionada);
			else if (interfaz != null) {
				interfaz.seleccionaNada();
			}
		}
		if (graficador != null)
			graficador.actualizar();
	}

	/**
	 * Devuelve el grafo
	 * 
	 * @return Grafo
	 */
	public Grafo getGrafo() {
		return grafo;
	}

	/**
	 * Devuelve el grafo como un documento XML
	 * 
	 * @return Documento XML
	 * @throws Exception Error al componer el XML
	 */
	public Document getGrafoComoXml() throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element root = doc.createElement("grafo");
		root.setAttribute("ancho", String.valueOf(getAnchoGrafo()));
		root.setAttribute("alto", String.valueOf(getAltoGrafo()));
		root.setAttribute("contadorletra", String.valueOf(Vertice.getContadorLetra()));
		guardarVertices(doc, root);
		guardarAristas(doc, root);
		doc.appendChild(root);
		return doc;
	}

	private void guardarVertices(Document doc, Element docroot) {
		List<Vertice> vertices = grafo.getVertices();
		if (vertices.size() == 0)
			return;
		Element root = doc.createElement("vertices");
		for (Vertice v : vertices) {
			Element e = doc.createElement("vertice");
			e.setAttribute("nombre", v.getNombre());
			e.setAttribute("x", String.valueOf(v.getX()));
			e.setAttribute("y", String.valueOf(v.getY()));
			root.appendChild(e);
		}
		docroot.appendChild(root);
	}

	private void guardarAristas(Document doc, Element docroot) {
		List<Arista> aristas = grafo.getAristas();
		if (aristas.size() == 0)
			return;
		Element root = doc.createElement("aristas");
		for (Arista a : aristas) {
			Element e = doc.createElement("arista");
			Element ev = doc.createElement("vertice");
			ev.setAttribute("nombre", a.getVertice1().getNombre());
			e.appendChild(ev);
			ev = doc.createElement("vertice");
			ev.setAttribute("nombre", a.getVertice2().getNombre());
			e.appendChild(ev);
			root.appendChild(e);
		}
		docroot.appendChild(root);
	}

	/**
	 * Guarda el grafo como un archivo XML
	 * 
	 * @param archivo Ruta del archivo
	 * @throws Exception Error al componer el XML o guardarlo
	 */
	public void guardarGrafoXmlComo(String archivo) throws Exception {
		Document doc;
		try {
			doc = getGrafoComoXml();
		} catch (Exception e) {
			throw new Exception("Error al crear el Xml: " + e.getMessage());
		}
		if (doc == null)
			throw new Exception("El Xml es nulo");
		try {
			FileWriter fw = new FileWriter(archivo);
			XMLSerializer ser = new XMLSerializer(fw, null);
			ser.serialize(doc);
		} catch (Exception e) {
			throw new Exception("Error al guardar el Xml: " + e.getMessage());
		}
	}

	/**
	 * Construye el grafo desde un elemento XML
	 * 
	 * @param root Elemento XML
	 * @throws Exception Error al construir el grafo o de sintaxis del XML
	 */
	public void setGrafoDesdeXml(Element root) throws Exception {
		grafo = new Grafo();
		try {
			int ancho = Integer.parseInt(root.getAttribute("ancho"));
			setAnchoGrafo(ancho);
		} catch (Exception e) {
		}
		try {
			int alto = Integer.parseInt(root.getAttribute("alto"));
			setAltoGrafo(alto);
		} catch (Exception e) {
		}
		try {
			int contadorLetra = Integer.parseInt(root.getAttribute("contadorletra"));
			Vertice.setContadorLetra(contadorLetra);
		} catch (Exception e) {
		}
		NodeList nodos = root.getChildNodes();
		Element eVertices = null;
		Element eAristas = null;
		for (int i = 0; i < nodos.getLength(); i++) {
			Node nodo = nodos.item(i);
			if (nodo instanceof Element) {
				Element e = (Element) nodo;
				if (e.getNodeName().equals("vertices"))
					eVertices = e;
				else if (e.getNodeName().equals("aristas"))
					eAristas = e;
			}
		}
		if (eVertices == null || eVertices.getChildNodes().getLength() == 0)
			return;
		cargarVertices(eVertices);
		if (eAristas != null)
			cargarAristas(eAristas);
		if (graficador != null)
			graficador.actualizar();
	}

	private void cargarVertices(Element root) throws Exception {
		NodeList nodos = root.getChildNodes();
		for (int i = 0; i < nodos.getLength(); i++) {
			Node nodo = nodos.item(i);
			if (nodo instanceof Element) {
				Element e = (Element) nodo;
				cargarVertice(e);
			}
		}
	}

	private void cargarVertice(Element e) throws Exception {
		if (!e.hasAttribute("nombre"))
			throw new Exception("Un Xml de vértice no tiene nombre");
		if (!e.hasAttribute("x"))
			throw new Exception("Un Xml de vértice no tiene X");
		if (!e.hasAttribute("y"))
			throw new Exception("Un Xml de vértice no tiene Y");
		String nombre = e.getAttribute("nombre");
		if (nombre.length() == 0)
			throw new Exception("El nombre del vértice no puede estar vacío");
		double x = 0.0, y = 0.0;
		String sx = e.getAttribute("x");
		String sy = e.getAttribute("y");
		try {
			x = Double.parseDouble(sx);
		} catch (Exception er) {
			throw new Exception("No se puede convertir la X '" + sx + "' en número");
		}
		try {
			y = Double.parseDouble(sy);
		} catch (Exception er) {
			throw new Exception("No se puede convertir la Y '" + sy + "' en número");
		}
		grafo.adicionarVertice(nombre, x, y);
	}

	private void cargarAristas(Element root) throws Exception {
		NodeList nodos = root.getChildNodes();
		for (int i = 0; i < nodos.getLength(); i++) {
			Node nodo = nodos.item(i);
			if (nodo instanceof Element) {
				Element e = (Element) nodo;
				cargarArista(e);
			}
		}
	}

	private void cargarArista(Element e) throws Exception {
		Element eVertice1 = null;
		Element eVertice2 = null;
		NodeList nodos = e.getChildNodes();
		for (int i = 0; i < nodos.getLength(); i++) {
			Node nodo = nodos.item(i);
			if (nodo instanceof Element) {
				Element ev = (Element) nodo;
				if (ev.getNodeName().equals("vertice") && ev.hasAttribute("nombre")) {
					if (eVertice1 == null)
						eVertice1 = ev;
					else {
						eVertice2 = ev;
						break;
					}
				}
			}
		}
		if (eVertice1 == null)
			return;
		String nombre1 = eVertice1.getAttribute("nombre");
		if (eVertice2 == null)
			throw new Exception("Una arista debe tener dos vértices, solo se encontró: " + nombre1);
		String nombre2 = eVertice2.getAttribute("nombre");
		if (nombre1.equals(nombre2))
			throw new Exception("Una arista no puede iniciar y finalizar en el mismo vértice: " + nombre1);
		grafo.adicionarArista(nombre1, nombre2);
	}

	/**
	 * Abre el grafo desde un archivo XML
	 * 
	 * @param archivo Nombre del archivo XML
	 * @throws Exception Error al construir el grafo, de sintaxis XML o al abrir el
	 *                   archivo
	 */
	public void abrirGrafoDesdeXml(String archivo) throws Exception {
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(archivo));
		} catch (Exception e) {
			throw new Exception("Error al abrir el archivo Xml: " + e.getMessage());
		}
		if (doc == null)
			throw new Exception("El documento es nulo");
		NodeList nodos = doc.getElementsByTagName("grafo");
		if (nodos.getLength() == 0)
			throw new Exception("El Xml no tiene una raíz para el grafo");
		try {
			Element root = (Element) nodos.item(0);
			setGrafoDesdeXml(root);
		} catch (Exception e) {
			throw new Exception("Error al procesar el Xml: " + e.getMessage());
		}
	}

	/**
	 * Crea un nuevo grafo
	 */
	public void nuevoGrafo() {
		grafo = new Grafo();
		if (graficador != null)
			graficador.actualizar();
	}

	/**
	 * Devuelve el ancho del grafo
	 * 
	 * @return Ancho del grafo
	 */
	public int getAnchoGrafo() {
		return anchoGrafo;
	}

	/**
	 * Modifica el ancho del grafo
	 * 
	 * @param anchoGrafo Nuevo ancho del grafo
	 */
	public void setAnchoGrafo(int anchoGrafo) {
		this.anchoGrafo = anchoGrafo;
		if (graficador != null)
			graficador.setDimension(anchoGrafo, altoGrafo);
	}

	/**
	 * Devuelve el alto del grafo
	 * 
	 * @return Alto del grafo
	 */
	public int getAltoGrafo() {
		return altoGrafo;
	}

	/**
	 * Modifica el alto del grafo
	 * 
	 * @param altoGrafo Nuevo alto del grafo
	 */
	public void setAltoGrafo(int altoGrafo) {
		this.altoGrafo = altoGrafo;
		if (graficador != null)
			graficador.setDimension(anchoGrafo, altoGrafo);
	}

	/**
	 * Devuelve el graficador (GrafoGraficador)
	 * 
	 * @return Graficador
	 */
	public GrafoGraficador getGraficador() {
		return graficador;
	}

	/**
	 * Modifica el graficador (GrafoGraficador)
	 * 
	 * @param graficador Nuevo graficador
	 */
	public void setGraficador(GrafoGraficador graficador) {
		this.graficador = graficador;
	}

	/**
	 * Selecciona el vértice o arista en una coordenada dada
	 * 
	 * @param x             Posición X
	 * @param y             Posición Y
	 * @param vertices      Si es true, selecciona vértices
	 * @param aristas       Si es true, selecciona aristas
	 * @param soloUnVertice Si es true, selecciona un solo vértice
	 *                      (verticeSeleccionado1). De lo contrario, selecciona
	 *                      primero verticeSeleccionado1 y luego
	 *                      verticeSeleccionado2
	 * @return 1 si seleccionó un vértice, 2 si seleccionó una arista, 0 si no
	 *         seleccionó nada
	 */
	public int seleccionar(int x, int y, boolean vertices, boolean aristas, boolean soloUnVertice) {
		int tipo = 0;
		if (vertices) {
			Vertice v = getVerticeEnCoordenadas(x, y);
			if (v != null) {
				if (soloUnVertice)
					verticeSeleccionado1 = v;
				else {
					if (verticeSeleccionado1 == null) {
						verticeSeleccionado1 = v;
					} else {
						verticeSeleccionado2 = v;
					}
				}
				tipo = 1;
			}
		}
		if (aristas && tipo != 1) {
			Arista a = getAristaEnCoordenadas(x, y);
			if (a != null) {
				aristaSeleccionada = a;
				tipo = 2;
			}
		}
		return tipo;
	}

	private Vertice getVerticeEnCoordenadas(int x, int y) {
		Vertice sel = null;
		List<Vertice> vertices = grafo.getVertices();
		for (Vertice v : vertices) {
			if (puntoColisionaVertice(x, y, v)) {
				sel = v;
				break;
			}
		}
		return sel;
	}

	private Arista getAristaEnCoordenadas(int x, int y) {
		Arista sel = null;
		List<Arista> aristas = grafo.getAristas();
		for (Arista a : aristas) {
			if (puntoColisionaArista(x, y, a)) {
				sel = a;
				break;
			}
		}
		return sel;
	}

	/**
	 * Indica si una coordenada colisiona con un vértice dado
	 * 
	 * @param x Posición X
	 * @param y Posición Y
	 * @param v Vértice
	 * @return true si colisiona
	 */
	public static boolean puntoColisionaVertice(int x, int y, Vertice v) {
		double distancia = Point2D.distance(v.getX(), v.getY(), x, y);
		return distancia <= hitboxVertice;
	}

	/**
	 * Indica si una coordenada colisiona con una arista dada
	 * 
	 * @param x Posición X
	 * @param y Posición Y
	 * @param a Arista
	 * @return true si colisiona
	 */
	public static boolean puntoColisionaArista(int x, int y, Arista a) {
		double distancia = Line2D.ptSegDist(a.getVertice1().getX(), a.getVertice1().getY(), a.getVertice2().getX(),
				a.getVertice2().getY(), x, y);
		return distancia <= hitboxArista;
	}

	/**
	 * Indica si una arista está seleccionada
	 * 
	 * @param a Arista
	 * @return true si está seleccionada
	 */
	public boolean aristaEstaSeleccionada(Arista a) {
		return a == aristaSeleccionada;
	}

	/**
	 * Indica si un vértice está seleccionado
	 * 
	 * @param v Vértice
	 * @return true si está seleccionado
	 */
	public boolean verticeEstaSeleccionado(Vertice v) {
		return v == verticeSeleccionado1 || v == verticeSeleccionado2;
	}

	/**
	 * Devuelve la ruta mínima calculada
	 * 
	 * @return Ruta mínima calculada
	 */
	public RutaMinimaGrafo getRutaMinima() {
		return rutaMinima;
	}

	/**
	 * Devuelve la lista de aristas de la ruta mínima
	 * 
	 * @return Lista de aristas
	 */
	public List<Arista> getAristasRutaMinima() {
		return aristasRutaMinima == null ? new ArrayList<Arista>() : aristasRutaMinima;
	}

	/**
	 * Devuelve la lista de nombres de los vértices de la ruta mínima
	 * 
	 * @return Lista de nombres de vértices
	 */
	public List<String> getVerticesRutaMinima() {
		return verticesRutaMinima == null ? new ArrayList<String>() : verticesRutaMinima;
	}

}
