package inicializacion;

import java.util.Stack;

import cromosoma.InfoNodo;
import cromosoma.Nodo;

public class Creciente implements Inicializacion{
	
	
	private boolean longitudMultiplexor;
	private float probabilidadFuncion;
	private int profundidadArbol;
	private boolean permiteIf;
	
	public Creciente(int profunidadMaxima, boolean multiplexerSize, boolean permiteIff){
		
		this.profundidadArbol = profunidadMaxima;
		this.longitudMultiplexor = multiplexerSize;
		this.permiteIf = permiteIff;
		this.probabilidadFuncion = 0.65f;
		
	}
	
	
	@Override
	public boolean get_permiteIf(){
		return this.permiteIf;
	}

	@Override
	public Nodo<InfoNodo> inicializar(){
		
		int profundidadActual = 1;
		
		Nodo<InfoNodo> raiz = new Nodo<InfoNodo>(new InfoNodo(longitudMultiplexor, permiteIf, "randomFunction"));
		
		Stack<Nodo<InfoNodo>> actualNodo;
		Stack<Nodo<InfoNodo>> nextNodo = new Stack<>();
		
		nextNodo.add(raiz);
		
		while (profundidadActual < profundidadArbol - 1 && nextNodo.size() > 0){
			
			actualNodo = nextNodo;
			nextNodo = new Stack<>();
			
			while(actualNodo.size() > 0){
				
				Nodo<InfoNodo> node = actualNodo.pop();
				
				for (int i = 0; i < node.getInfo().getNumOp(); i++){
					
					Nodo<InfoNodo> hijo;
					
					if (Math.random() < probabilidadFuncion) hijo = new Nodo<InfoNodo>(new InfoNodo(longitudMultiplexor, permiteIf, "randomFunction"));
					else hijo = new Nodo<InfoNodo>(new InfoNodo(longitudMultiplexor, permiteIf, "randomTerminal"));

					if (hijo.getInfo().isFunc()) {
												nextNodo.add(hijo);
												}
					node.anadirHijo(hijo);
					
				}
			}
			
			profundidadActual++;
			
		}

		
		while(nextNodo.size() >0){
			
			Nodo<InfoNodo> node = nextNodo.pop();
			
			for (int i = 0; i < node.getInfo().getNumOp(); i++){
				
				Nodo<InfoNodo> hijo = new Nodo<InfoNodo>(new InfoNodo(longitudMultiplexor, permiteIf, "randomTerminal"));
				node.anadirHijo(hijo);
				
			}
		}
		
		return raiz;
		
		
	}

}
