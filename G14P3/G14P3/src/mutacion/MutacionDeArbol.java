package mutacion;

import cromosoma.Cromosoma;
import cromosoma.InfoNodo;
import cromosoma.Nodo;
import inicializacion.Completa;

public class MutacionDeArbol implements Mutacion {

	@Override
	public void muta(Cromosoma cromo) {
		
		Nodo<InfoNodo> arbol = cromo.getFenotipo();
		int aleatorio = (int) (Math.random() * arbol.numHijos());
		Completa ramaNueva = new Completa(cromo.getFenotipo().getProfundidad() -1, arbol.getInfo().isMultiplexorSize(), cromo.getPermiteIf());
		Nodo<InfoNodo> nodoAuxiliar = ramaNueva.inicializar();
		arbol.eliminaHijo(aleatorio);
		arbol.anadirHijoIndex(nodoAuxiliar, aleatorio);
		
	}

}
