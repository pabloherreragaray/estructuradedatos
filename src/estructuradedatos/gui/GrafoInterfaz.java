package estructuradedatos.gui;

import estructuradedatos.grafo.Arista;
import estructuradedatos.grafo.Vertice;

public interface GrafoInterfaz {
	
	String obtenerNombreVertice();
	
	void mostrarError(String mensaje);
	
	void verticeSeleccionado(Vertice v);
	
	void aristaSeleccionada(Arista a);
	
	void seleccionaNada();
	
	boolean eliminarArista(Arista a);
	
	boolean eliminarVertice(Vertice v);

}
