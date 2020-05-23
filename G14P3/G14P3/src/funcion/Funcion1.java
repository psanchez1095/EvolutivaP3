package funcion;

import bloating.BienFundamentada;
import bloating.Tarpeian;
import cromosoma.*;
import utils.TipoBloating;

public class Funcion1 {
	public static int evalua(Cromosoma c,Cromosoma[] pob, int numEntrdas, TipoBloating tb) {
		boolean entradas[] = null, noAcabado = true, resultado;
		int aciertosTotales = 0;
		
		if(numEntrdas == 6)entradas = new boolean[] 
				{ false, false, false, false, false, false};
		else if(numEntrdas == 11)entradas = new boolean[] 
				{ false, false, false, false, false, false, false, false, false, false, false};
		
		Nodo<InfoNodo> f = c.getFenotipo();
		
		while(noAcabado) {
			resultado = evalua(f, entradas, numEntrdas);
			if (resultado == valorReal(entradas, numEntrdas)){
				aciertosTotales++;
			}
			noAcabado = incrementarTabla(entradas);
		}
		switch(tb) {
		case TARPEIAN:
			Tarpeian tp = new Tarpeian(pob);
			aciertosTotales = tp.fitnessBloating(c, aciertosTotales);
			break;
		case FUNDAMENTADA:
			BienFundamentada bf = new BienFundamentada(pob);
			//double a = bf.fitnessBloating(c, aciertosTotales);
			aciertosTotales = (int) bf.fitnessBloating(c, aciertosTotales);
			break;
		default:
			BienFundamentada bfd = new BienFundamentada(pob);
			aciertosTotales = (int) bfd.fitnessBloating(c, aciertosTotales);
			break;
		}
		
		return aciertosTotales;
	}
	
	private static boolean evalua(Nodo<InfoNodo> n, boolean entradas[], int numEntradas) {
		try {
		InfoNodo value = n.getInfo();
		if (!value.isFunc())
			return entradas(value.getInfo(), entradas, numEntradas);
		if (value.getInfo() == "IF") {
			if (evalua(n.getHijo(0), entradas, numEntradas))
				return evalua(n.getHijo(1), entradas, numEntradas);
			else
				return evalua (n.getHijo(2), entradas, numEntradas);
		} 
		else if (value.getInfo() == "AND") {
			if (!evalua(n.getHijo(0), entradas, numEntradas)) //Evaluacion cortocircuitada
				return false;
			return evalua(n.getHijo(1), entradas, numEntradas);
			
		} 
		else if (value.getInfo() == "OR") {
			if (evalua(n.getHijo(0), entradas, numEntradas)) //Evaluacion cortocircuitada
				return true;
			return evalua(n.getHijo(1), entradas, numEntradas);
		} 
		else {
			return !evalua(n.getHijo(0), entradas, numEntradas);
		}
		}catch(Exception e) {
			return false;
		}
		
	}
	
	
	
	
	private static boolean incrementarTabla(boolean entradas[]){
		for (int i = entradas.length - 1; i >= 0; i--){
			if (entradas[i])
				entradas[i] = false;
			else{
				entradas[i] = true;
				return true;
			}
		}
		return false;
	}
	

	private static boolean valorReal(boolean entradas[], int numEntradas){
		int sum;
		if (numEntradas == 11){ // Entradas D0, D1 y D2
			sum = 4 * (entradas[0] ? 1 : 0) + 2 * (entradas[1] ? 1 : 0) + (entradas[2] ? 1 : 0);
			return entradas[numEntradas - sum -1];
		} else if (numEntradas == 6) {
			sum = 2 * (entradas[0] ? 1 : 0) + (entradas[1] ? 1 : 0);
			return entradas[numEntradas - sum -1];
		}
		return false; //En caso de error asumimos fallo
	}
	
	
	
	private static boolean entradas(String info, boolean entradas[], int numEntradas)
	{
		if (numEntradas == 6){
			switch (info){
				case "A1": return entradas[0];
				case "A0": return entradas[1];
				case "D3": return entradas[2];
				case "D2": return entradas[3];
				case "D1": return entradas[4];
				case "D0": return entradas[5];
				default: return false; //Asuminos false en caso de error
			}
		} else {
			switch (info){
				case "A2": return entradas[0];
				case "A1": return entradas[1];
				case "A0": return entradas[2];
				case "D7": return entradas[3];
				case "D6": return entradas[4];
				case "D5": return entradas[5];
				case "D4": return entradas[6];
				case "D3": return entradas[7];
				case "D2": return entradas[8];
				case "D1": return entradas[9];
				case "D0": return entradas[10];
				default: return false; //Asuminos false en caso de error
				}
			
		} 
	}
	
}
