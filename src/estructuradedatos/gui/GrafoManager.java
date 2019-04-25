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
import estructuradedatos.grafo.Vertice;

public class GrafoManager {
	public static final int VISUALIZACION = 0;
	public static final int ADICION_ARISTAS = 2;
	public static final int ADICION_VERTICES = 1;
	public static final int ELIMINACION = 3;
	public static final int RUTA_MINIMA = 4;
	public static int hitboxVertice = 10;
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
		if (graficador != null)
			graficador.actualizar();
	}

	public int getEstado() {
		return estado;
	}

	public void setEstadoAdicionVertices() {
		setEstado(ADICION_VERTICES);
	}

	public void setEstadoAdicionAristas() {
		setEstado(ADICION_ARISTAS);
	}

	public void setEstadoEliminacion() {
		setEstado(ELIMINACION);
	}

	public void setEstadoRutaMinima() {
		setEstado(RUTA_MINIMA);
	}

	public void setEstadoNormal() {
		setEstado(VISUALIZACION);
	}

	public void adicionarListener(GrafoEstadoListener listener) {
		listeners.add(listener);
	}

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

	public GrafoInterfaz getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(GrafoInterfaz interfaz) {
		this.interfaz = interfaz;
	}

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

	public Grafo getGrafo() {
		return grafo;
	}

	public Document getGrafoComoXml() throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element root = doc.createElement("grafo");
		root.setAttribute("ancho", String.valueOf(getAnchoGrafo()));
		root.setAttribute("alto", String.valueOf(getAltoGrafo()));
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

	public void nuevoGrafo() {
		grafo = new Grafo();
		if (graficador != null)
			graficador.actualizar();
	}

	public int getAnchoGrafo() {
		return anchoGrafo;
	}

	public void setAnchoGrafo(int anchoGrafo) {
		this.anchoGrafo = anchoGrafo;
		if (graficador != null)
			graficador.setDimension(anchoGrafo, altoGrafo);
	}

	public int getAltoGrafo() {
		return altoGrafo;
	}

	public void setAltoGrafo(int altoGrafo) {
		this.altoGrafo = altoGrafo;
		if (graficador != null)
			graficador.setDimension(anchoGrafo, altoGrafo);
	}

	public GrafoGraficador getGraficador() {
		return graficador;
	}

	public void setGraficador(GrafoGraficador graficador) {
		this.graficador = graficador;
	}

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

	public static boolean puntoColisionaVertice(int x, int y, Vertice v) {
		double distancia = Point2D.distance(v.getX(), v.getY(), x, y);
		return distancia <= hitboxVertice;
	}

	public static boolean puntoColisionaArista(int x, int y, Arista a) {
		double distancia = Line2D.ptSegDist(a.getVertice1().getX(), a.getVertice1().getY(), a.getVertice2().getX(),
				a.getVertice2().getY(), x, y);
		return distancia <= hitboxArista;
	}

	public boolean aristaEstaSeleccionada(Arista a) {
		return a == aristaSeleccionada;
	}

	public boolean verticeEstaSeleccionado(Vertice v) {
		return v == verticeSeleccionado1 || v == verticeSeleccionado2;
	}

}
