package inicializacion;

import java.util.ArrayList;
import java.util.List;

import cromosoma.InfoNodo;
import cromosoma.Nodo;

public class RampedAndHalf implements Inicializacion{
	
	private boolean permiteIf;
	private boolean longitudMultiplexor;
	private int profundidadMaxima;
	private List<Nodo<InfoNodo>> poblacion;
	
	public RampedAndHalf(int profunidadMaxima, boolean multiplexerSize, boolean permiteIff){
		
		this.profundidadMaxima = profunidadMaxima;
		this.longitudMultiplexor = multiplexerSize;
		this.permiteIf = permiteIff;
		
	}
	
	@Override
	public boolean get_permiteIf() {
		return this.permiteIf;
	}
	
	
	//No inicializa un nodo inicializa un arbol entero por lo tanto implementamos, inicializarPoblación
	@Override
	public Nodo<InfoNodo> inicializar() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Nodo<InfoNodo>> inicializarPoblacion(int sizePoblacion){ 
		
		poblacion = new ArrayList<>();
		
		int FirstDivision = (int) (sizePoblacion / (profundidadMaxima - 1));
		
		int subgroupFirstDivision = (int) (FirstDivision / 2);
		
		for (int i = 0; i < sizePoblacion; i++){
			
			int profundidadArbol = Math.min((int) 2 + (i / FirstDivision), profundidadMaxima);
			
			if (i % FirstDivision > subgroupFirstDivision)
				poblacion.add(new Creciente(profundidadArbol, longitudMultiplexor, permiteIf).inicializar());
			else
				poblacion.add(new Completa(profundidadArbol, longitudMultiplexor, permiteIf).inicializar());
			
		}

		
		return poblacion;
	}


}
