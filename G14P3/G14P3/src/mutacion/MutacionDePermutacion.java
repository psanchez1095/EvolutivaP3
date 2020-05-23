package mutacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cromosoma.Cromosoma;
import cromosoma.InfoNodo;
import cromosoma.Nodo;

public class MutacionDePermutacion implements Mutacion {
	
	
	@Override
	public void muta(Cromosoma cromo) {
		
		List<Nodo<InfoNodo>> functions = new ArrayList<>();
		int posicionCambio = 0;
		
		Nodo<InfoNodo> arbol = cromo.getFenotipo();
		Iterator<Nodo<InfoNodo>> iterator = arbol.iteratorInOrder();
	
		while (iterator.hasNext()) {
			
			Nodo<InfoNodo> nodoAuxiliar = iterator.next();
			if (nodoAuxiliar.toString() == "IF") functions.add(nodoAuxiliar);
			
		}

		if(functions.size() > 0) {
			
			int aleatorio = (int) (Math.random() * functions.size());
			
			Nodo<InfoNodo> toChange = functions.get(aleatorio);
			Nodo<InfoNodo> nodoAux = toChange.getHijo(posicionCambio);	
			
			toChange.eliminaHijo(posicionCambio);
			
			nodoAux.setPadre(null);	
			toChange.anadirHijo(nodoAux);

	}
		
	}
}
