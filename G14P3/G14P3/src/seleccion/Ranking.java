package seleccion;

import java.util.ArrayList;
import java.util.Random;

import cromosoma.Cromosoma;

public class Ranking {
	
	private Cromosoma[] poblacion;
	private int tamPob, numSeleccionados;
	
	public Ranking (Cromosoma[] pob, int tamPob, int numSeleccionados) { 
		this.poblacion = pob;
		this.tamPob = tamPob;
		this.numSeleccionados = numSeleccionados;
	}
	
	public int seleccionRanking() {
		int numSel = 0;
		ArrayList<Integer> selected = new ArrayList<Integer>();
		ArrayList<Double> proporciones = new ArrayList<>();
		int size = tamPob;
		Random rn = new Random();
		double tram = 0.0, Beta = rn.nextDouble()+ 1.0;
		
		ordenarPoblacion(0, size-1);
		for (int i = 0; i < size; i++) {
			double probOflth = (double)i/size;
			probOflth = probOflth * 2 * (Beta-1);
			probOflth = Beta - probOflth;
			probOflth = (double)probOflth*((double)1/size);
			
			tram += probOflth;
			proporciones.add(tram);
		}
		
		for (int i = 0; i < numSeleccionados; i++) {
			selected.add(choose(proporciones, tram));	
		}
		int aux = 0;
		for (int j = size-1; j > size - numSeleccionados-1; --j) {
			poblacion[j] = poblacion[selected.get(aux)];
			aux++;
		}
		
		numSel = numSeleccionados;
		return numSel;
	}
	
	 private void ordenarPoblacion(int izq, int der) {
    	 
	        int i = izq;
	        int j = der;
	        
	        Cromosoma pivote = this.poblacion[(i+j)/2];
	        
	        do {
	            while (this.poblacion[i].getFitness() < pivote.getFitness()){
	                i++;
	            }
	            while (this.poblacion[j].getFitness() > pivote.getFitness()){
	                j--;
	            }
	            if (i<=j){
	                Cromosoma aux = duplicarCromosoma(this.poblacion[i]);
	                this.poblacion[i] = duplicarCromosoma(this.poblacion[j]);
	                this.poblacion[j] = duplicarCromosoma(aux);
	                i++;
	                j--;
	            }
	        }while(i<=j);
	        if (izq<j){
	            ordenarPoblacion(izq, j);
	        }
	        if (i<der){
	            ordenarPoblacion(i, der);
	        }
	    }
	 
	 
	private Cromosoma duplicarCromosoma(Cromosoma c) {
	    Cromosoma nuevo = new Cromosoma(c);
        return nuevo;
	    }
		
		
		private int choose(ArrayList<Double> proporciones, double sum) {
			int aux = 0;
			Double seleccion = Math.random() * sum;
			while((aux != (proporciones.size() - 1)) && seleccion >= proporciones.get(aux)) {
				aux++;		
			}
			return aux;		
	}
}
