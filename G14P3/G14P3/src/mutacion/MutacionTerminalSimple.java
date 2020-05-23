package mutacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import cromosoma.Cromosoma;
import cromosoma.InfoNodo;
import cromosoma.Nodo;

public class MutacionTerminalSimple implements Mutacion {

	@Override
	public void muta(Cromosoma cromo) {
		
		Random rand = new Random();
		List<Nodo<InfoNodo>> listaTerminales = new ArrayList<>();
		
		Nodo<InfoNodo> arbol = cromo.getFenotipo();
		Iterator<Nodo<InfoNodo>> iterator = arbol.iteratorInOrder();
		
		
		while (iterator.hasNext()){
			
			Nodo<InfoNodo> nodoAuxiliar = iterator.next();
			if (!nodoAuxiliar.getInfo().isFunc()) listaTerminales.add(nodoAuxiliar);
			
			
		}
		
		int aleatorio = rand.nextInt(listaTerminales.size());
		InfoNodo terminalSimpleAux = new InfoNodo(arbol.getInfo().isMultiplexorSize(), cromo.getPermiteIf(),"randomTerm");
		listaTerminales.get(aleatorio).setInfo(terminalSimpleAux);
		
	}

}
