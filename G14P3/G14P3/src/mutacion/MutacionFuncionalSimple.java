package mutacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cromosoma.Cromosoma;
import cromosoma.InfoNodo;
import cromosoma.Nodo;

public class MutacionFuncionalSimple implements Mutacion {

	@Override
	public void muta(Cromosoma cromo) {
		
		List<Nodo<InfoNodo>> listaFunciones = new ArrayList<>();
		
		Nodo<InfoNodo> arbol = cromo.getFenotipo();
		Iterator<Nodo<InfoNodo>> iterator = arbol.iteratorInOrder();
		
		while (iterator.hasNext()){
			
			Nodo<InfoNodo> nodoAuxiliar = iterator.next();
			if ( (nodoAuxiliar.toString() == "OR") || (nodoAuxiliar.toString() == "AND")) listaFunciones.add(nodoAuxiliar);
				
		}
		
		if(listaFunciones.size() > 0) {
			
			int aleatorio = (int) (Math.random() * listaFunciones.size());
			
			if(listaFunciones.get(aleatorio).toString() == "OR"){  
				
				InfoNodo auxInfoNodoAnd = new InfoNodo(arbol.getInfo().isMultiplexorSize(), cromo.getPermiteIf(), "AND" );
				listaFunciones.get(aleatorio).setInfo(auxInfoNodoAnd);
			
			}
			else if (listaFunciones.get(aleatorio).toString() == "AND"){
				
				InfoNodo auxInfoNodoOr =new InfoNodo(arbol.getInfo().isMultiplexorSize(), cromo.getPermiteIf(), "OR" );
				listaFunciones.get(aleatorio).setInfo(auxInfoNodoOr);
				
			}
			
		}

	}

}
