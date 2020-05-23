package seleccion;


import cromosoma.Cromosoma;

public class Ruleta {
	Cromosoma[] pob, nuevaPob;
	int tamPob;
	double prob;
	int posSuperviviente;
	int[] supervivientes;
	
	public Ruleta (Cromosoma[] pob, int tamPob) {
		this.pob = pob;
		this.nuevaPob = new Cromosoma[tamPob];
		this.tamPob = tamPob;
		this.prob = 0;
		this.supervivientes = new int[this.tamPob];
	}
	

	public int seleccionRuleta() {
		int numSel = 0;
		for(int i = 0; i < this.tamPob; i++) {
			this.prob = Math.random();
			this.posSuperviviente = 0;
			
			while((posSuperviviente < this.tamPob) && 
					(prob > this.pob[posSuperviviente].getPuntAcumulada())) {
				posSuperviviente++;
			}
			if(posSuperviviente < 99 )
				supervivientes[i] = posSuperviviente;
			else
				supervivientes[i] = 99;
		}
		
		for(int j = 0; j < this.tamPob-1; j++) {
			this.nuevaPob[j] = this.pob[this.supervivientes[j]];
		}
		numSel = 99;
		this.pob = this.nuevaPob;
		return numSel;
	}
}
