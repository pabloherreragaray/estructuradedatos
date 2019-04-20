package estructuradedatos.gui;

import java.util.ArrayList;
import java.util.List;

import estructuradedatos.grafo.Grafo;
import estructuradedatos.grafo.Vertice;

public class GrafoManager {
	public static final int VISUALIZACION = 0;
	public static final int ADICION_ARISTAS = 2;
	public static final int ADICION_VERTICES = 1;
	public static final int ELIMINACION = 3;
	public static final int RUTA_MINIMA = 4;

	private Grafo grafo;
	private int estado = 0;
	private Vertice vertice1;
	private Vertice vertice2;
	private List<GrafoListener> listeners;
	private GrafoInterfaz interfaz;

	public GrafoManager() {
		grafo = new Grafo();
		listeners = new ArrayList<GrafoListener>();
	}

	private void setEstado(int estado) {
		int anterior = this.estado;
		this.estado = estado;
		for (GrafoListener listener : listeners) {
			listener.cambiaEstado(anterior, this.estado);
		}
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

	public void adicionarListener(GrafoListener listener) {
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
		}
	}

	public Grafo getGrafo() {
		return grafo;
	}

}
