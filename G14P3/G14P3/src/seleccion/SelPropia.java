package seleccion;

import java.util.Random;

import cromosoma.Cromosoma;
import utils.TipoMutacion;

public class SelPropia {

	Cromosoma[] pob;
	private int tamPob;
	
	public SelPropia(Cromosoma[] pob, int tamPob) {
		this.pob = pob;
		this.tamPob = tamPob;
	}
	

	public int seleccionPropia() {
		Random rn = new Random();
		int numSel = rn.nextInt(10);
		ordenarPoblacion(0, this.tamPob-1);
		for (int i = 0; i < numSel; i ++) {
			this.pob[this.tamPob -i -1] = duplicarCromosoma(this.pob[i]);
		}
		return numSel;
	}
	
	 private void ordenarPoblacion(int izq, int der) {
    	 
	        int i = izq;
	        int j = der;
	        
	        Cromosoma pivote = this.pob[(i+j)/2];
	        
	        do {
	            while (this.pob[i].getFitness() < pivote.getFitness()){
	                i++;
	            }
	            while (this.pob[j].getFitness() > pivote.getFitness()){
	                j--;
	            }
	            if (i<=j){
	                Cromosoma aux = duplicarCromosoma(this.pob[i]);
	                this.pob[i] = duplicarCromosoma(this.pob[j]);
	                this.pob[j] = duplicarCromosoma(aux);
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
	
	
}
