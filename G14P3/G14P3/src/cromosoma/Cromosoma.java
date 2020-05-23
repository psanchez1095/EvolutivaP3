	package cromosoma;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class Cromosoma implements Comparable<Cromosoma>{
	
	Nodo<InfoNodo> fenotipo;
	int longitud; 
    int fitness;
    boolean permiteIf;
    double puntuacion;
    double punt_Acumulada;
    
    public Cromosoma(Nodo<InfoNodo> fenotipo, int longitud, boolean permiteIf) {	//No se inicializa aqui, en Generacion
		this.fenotipo = fenotipo; 
		this.longitud = longitud;
		this.fitness = 0;
		this.puntuacion = 0.0;
		this.punt_Acumulada = 0.0;
	}

    public Cromosoma (Cromosoma c) {
    	this.fenotipo = c.getFenotipo();
    	this.longitud = c.getLongitud();
		this.fitness = c.getFitness();
		this.puntuacion = c.getPuntuacion();
		this.punt_Acumulada = c.getPuntAcumulada();
    }
    
	public Nodo<InfoNodo> getFenotipo() {
		return fenotipo;
	}




	public void setFenotipo(Nodo<InfoNodo> fenotipo) {
		this.fenotipo = fenotipo;
	}




	public int getLongitud() {
		return longitud;
	}




	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}




	public int getFitness() {
		return fitness;
	}




	public void setFitness(int fitness) {
		this.fitness = fitness;
	}


	public boolean getPermiteIf() {
		return permiteIf;
	}




	public void setPermiteIf(boolean permiteIf) {
		this.permiteIf = permiteIf;
	}
 
	 public double getPuntuacion() {
	        return this.puntuacion;
	    }
	    
	    public void setPuntuacion(double puntuacion) {
	    	this.puntuacion = puntuacion;
	    }
	    
	    public double getPuntAcumulada() {
	        return this.punt_Acumulada;
	    }
	    
	    public void setPuntAcumulada(double punt_Acumulada) {
	        this.punt_Acumulada = punt_Acumulada;
	    }
	
	

    public int compareTo(Cromosoma cromosoma) {
    	double fitness_this = this.getFitness();
		double fitness_other = cromosoma.getFitness();
		if (fitness_this > fitness_other)
			return 1;
		else if (fitness_this < fitness_other)
			return -1;
		return 0;
    }


    public String toString() {
    	
    	return Nodo.imprimeArbol(this.fenotipo);
    	
    }

	
	

}

