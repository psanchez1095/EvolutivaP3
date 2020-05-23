package seleccion;

import cromosoma.Cromosoma;

public class truncamiento {

	private Cromosoma[] poblacion;
	private int tamPob;
	boolean porc;  //si la probabilidad es 0.1 porc sera true y 0.5 sera false

	
	public truncamiento (Cromosoma[] pob, int tamPob, boolean porc) { 
		this.poblacion = pob;
		this.tamPob = tamPob;
		this.porc = porc;
	}
	
	public int seleccionaTruncamiento() {
		int numSel = 0;
		ordenarPoblacion(0, tamPob-1);
		int mod;
		if(porc)mod = 10;
		else mod = 50;
		numSel = mod;
		
		for (int i = 0; i < tamPob ; ++i) {
			this.poblacion[i] = this.poblacion[i%mod];
		}
		
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
	
	
}
