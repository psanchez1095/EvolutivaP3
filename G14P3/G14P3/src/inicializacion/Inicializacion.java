package inicializacion;

import cromosoma.Nodo;
import cromosoma.InfoNodo;
import java.util.ArrayList;
import java.util.List;

public interface Inicializacion {
	
	public default List<Nodo<InfoNodo>> inicializarPoblacion(int tamanoPoblacion){
		
		List<Nodo<InfoNodo>> poblacion = new ArrayList<Nodo<InfoNodo>>();
		
		for (int i = 0; i < tamanoPoblacion; i++) poblacion.add(inicializar());
		
		return poblacion;
		
	}
	
	public boolean get_permiteIf();
	
	public Nodo<InfoNodo> inicializar();
	
	
}