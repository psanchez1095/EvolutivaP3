package inicializacion;

import java.util.Stack;

import cromosoma.InfoNodo;
import cromosoma.Nodo;

public class Completa implements Inicializacion {
	
	private boolean permiteIf;
	private boolean longitudMultiplexor;
	private int profundidadArbol;
	
	public Completa(int profunidadMaxima, boolean multiplexerSize, boolean permiteIff){
		
		this.profundidadArbol = profunidadMaxima;
		this.longitudMultiplexor = multiplexerSize;
		this.permiteIf = permiteIff;
		
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
		
		while(profundidadActual < profundidadArbol - 1){
			
			actualNodo = nextNodo;
			nextNodo = new Stack<>();
			
			while(actualNodo.size() >= 1){
				
				Nodo<InfoNodo> node = actualNodo.pop();
				
				for (int i = 0; i < node.getInfo().getNumOp(); i++){
					
					Nodo<InfoNodo> hijo = new Nodo<InfoNodo>(new InfoNodo(longitudMultiplexor, permiteIf, "randomFunction"));
					
					nextNodo.add(hijo);
					node.anadirHijo(hijo);
					
				}
			}
			
			profundidadActual++;
			
		}

		//Terminales solo
		while(nextNodo.size() >= 1){
			
			Nodo<InfoNodo> node = nextNodo.pop();
			
			for (int i = 0; i < node.getInfo().getNumOp(); i++){
				
				Nodo<InfoNodo> hijo = new Nodo<InfoNodo>(new InfoNodo(longitudMultiplexor, permiteIf, "randomTerminal"));
				node.anadirHijo(hijo);
				
			}
			
		}
		
		return raiz;
	}

}
