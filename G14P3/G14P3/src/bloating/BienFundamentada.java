package bloating;

import java.util.Random;

import cromosoma.Cromosoma;

public class BienFundamentada {

	private double funcion;
	
	public BienFundamentada(Cromosoma[] pob) {
			double mediaFit = 0.0, mediaProf = 0.0, covarianza = 0.0, varianza = 0.0;
			
			for(Cromosoma c : pob) {
				mediaFit += c.getFitness();
				mediaProf += c.getFenotipo().getProfundidad();
			}
			
			mediaFit /= pob.length;
			mediaProf /= pob.length;
			
			for(Cromosoma c : pob) {
				covarianza += c.getFitness() * c.getFenotipo().getProfundidad();
				varianza = Math.pow(c.getFitness() - mediaFit, 2);
			}
			
			covarianza /= pob.length;
			covarianza -= mediaFit * mediaProf;
			
			varianza /= pob.length-1;
			
			if(varianza != 0)
				funcion = covarianza / varianza;
			else funcion = 0.0;
	}
	
	public double fitnessBloating (Cromosoma c, int fit) {
		return fit+funcion*c.getFenotipo().getNodos();
	}
}
