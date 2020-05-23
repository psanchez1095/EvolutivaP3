package seleccion;

import cromosoma.Cromosoma;
import utils.TipoMutacion;

public class EstocasticoUniversal {
	Cromosoma[] pob;
	private int tamPob;
	
	public EstocasticoUniversal(Cromosoma[] pob, int tamPob) {
		this.pob = pob;
		this.tamPob = tamPob;
	}
	

	public int seleccionEstocastico() {
		int numSel = 0;
        double total = 0.0;
        double[] fitnessIndiv = new double[this.tamPob];
        double[] valores = new double[this.tamPob];
        boolean encontrado = false;
        Cromosoma[] generacionNueva = new Cromosoma[this.tamPob];
        
        
        for (int i = 0; i < this.tamPob; i++) {
            //calculamos el total
        	fitnessIndiv[i] = this.pob[i].getFitness();
            total += fitnessIndiv[i];
        }
        
        for (int i = 0; i < this.tamPob; i++) {
            //calculamos los valores de cada individuo
            valores[i] = fitnessIndiv[i] / total;
   
        }
        
        //calculamos los valores acumulados
        for (int i = 1; i < this.tamPob; i++) {
            valores[i] = valores[i] + valores[i - 1];
        }
        //elegimos los individuos
        for (int i = 0; i < this.tamPob; i++) {
            int j = 0;
            double valor = (1.0 * i) / this.tamPob;
            while (!encontrado && j<this.tamPob) {
                if (valor < valores[j]) {
                    generacionNueva[i] = duplicarCromosoma(this.pob[j]);
                    encontrado = true;
                    numSel++;
                } else {
                    j++;
                }
            }
            if(j > this.tamPob)
            	generacionNueva[i] = duplicarCromosoma(this.pob[j-1]);
            encontrado = false;
        }
        this.pob = generacionNueva;

        return numSel;
	}
	
	private Cromosoma duplicarCromosoma(Cromosoma c) {
		 Cromosoma nuevo = new Cromosoma(c);       
	    	return nuevo;
	    }

}
