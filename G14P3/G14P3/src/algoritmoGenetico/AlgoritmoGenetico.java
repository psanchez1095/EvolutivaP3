package algoritmoGenetico;

import cruce.*;
import funcion.*;
import inicializacion.*;
import mutacion.*;
import cromosoma.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import seleccion.*;
import utils.*;

public class AlgoritmoGenetico {
	
	Cromosoma[] poblacion; //Poblacion total //Probamos con list
	ArrayList<Cromosoma> elite; //Elite seleccionada
	
	//Parametros del algoritmo.
	TipoMutacion mutacion;
	public TipoMutacion getFuncion() {
		return mutacion;
	}

	TipoSeleccion tipo_seleccion;
	TipoCruce tipo_cruce;
	TipoInicializacion tipo_ini;
	TipoMux tipo_mux;
	TipoBloating tipo_bl;
	int tamPoblacion, numGeneraciones, generacionActual, longMax;
	double probabilidadCruce, probabilidadMutacion, elitismo;
	String precision;
	boolean booleanElite, permiteIf;
	//Valores para escribir la grafica.
	double[] mediasGeneracion;
	double[] mejoresGeneracion;
	double[] mejoresAbsolutos;
	double[] peoresAbsolutos;
	double[] peoresGeneracion;
	
	double mediaGeneracion;
	double mejorGeneracion;
	double mejorAbsoluto;
	
	private Cromosoma elMejor; //Mejor cromosoma encontrado hasta ahora.
	private Cromosoma elPeor;
	int pos_mejorGeneracion;   //Posicion del mejor cromosoma de la generacion
	

	int numMutaciones = 0, numCruzes = 0,numSelecionados = 0;
	

	public AlgoritmoGenetico(TipoMutacion tMut, TipoSeleccion tSel, TipoCruce tCrux, TipoInicializacion ini,
							 int tPob, int nGeneracs, double pCruce, double pMutacion, double elit, int longMax,
							 TipoMux tMux, boolean permiteIf, TipoBloating tipo_bl) {

		this.generacionActual = 0;
		this.mutacion = tMut;
		this.tipo_seleccion = tSel;
		this.tipo_cruce = tCrux;
		this.tipo_ini = ini;
		this.tipo_bl = tipo_bl;
		this.numGeneraciones = nGeneracs;
		this.tamPoblacion = tPob;
		this.probabilidadCruce = pCruce;
		this.probabilidadMutacion = pMutacion;
		this.longMax = longMax;
		this.elitismo = elit;
		this.permiteIf = permiteIf;
		this.tipo_mux = tMux;
		
		this.poblacion = new Cromosoma[tPob];
		this.elite = new ArrayList<Cromosoma>();
		
		this.booleanElite = this.elitismo == 0.0 ? false : true; 
		// false en caso de que sea 0.0 y true en caso contrario
		
		this.mediasGeneracion = new double[nGeneracs];
		this.peoresGeneracion = new double[nGeneracs];
		this.mejoresGeneracion = new double[nGeneracs];
		this.mejoresAbsolutos = new double[nGeneracs];
		this.peoresAbsolutos = new double[nGeneracs];
		
		
	}
	
	public void inicializaPoblacion() {
		try {
		Inicializacion in;
		boolean mux = true;
		
		switch(tipo_mux) {
		case MUX6: mux = true; break;
		case MUX11: mux = false; break;
		}
		
		
		switch(tipo_ini) {
		case CRECIENTE:
			in = new Creciente(this.longMax, mux, this.permiteIf);
			break;
		case COMPLETA:
			in = new Completa(this.longMax, mux, this.permiteIf);
			break;
		case RAMPEDANDHALF:
			in = new RampedAndHalf(this.longMax, mux, this.permiteIf);
			break;
		default:
			in = new Creciente(this.longMax, mux, this.permiteIf);
		}
		
		switch(tipo_ini) {
		case RAMPEDANDHALF:
			List<Nodo<InfoNodo>> listaCromosomas = in.inicializarPoblacion(this.tamPoblacion);
			for(int i = 0; i < tamPoblacion; i++) {
				
				this.poblacion[i] = new Cromosoma(listaCromosomas.get(i), longMax, permiteIf);
			}
			break;
		case CRECIENTE:
			for(int i = 0; i < tamPoblacion; i++) {
				this.poblacion[i] = new Cromosoma(in.inicializar(), longMax, permiteIf);
			}
			break;
		case COMPLETA:
			for(int i = 0; i < tamPoblacion; i++) {
				this.poblacion[i] = new Cromosoma(in.inicializar(), longMax, permiteIf);
			}
			break;
		
		}
		
        this.elMejor = this.duplicarCromosoma(this.poblacion[poblacion.length - 1]);
        this.elPeor = this.duplicarCromosoma(this.poblacion[0]);
        fitness(elMejor);
        fitness(elPeor);
        this.generacionActual = 0;
		}catch(Exception e) {
			
		}

	}
	
	public int fitness(Cromosoma cromosoma) {
		
		int valor = 0;
		//cromosoma.calcularFenotipo();
		try {
		int numEntradas=6;
		switch(tipo_mux) {
		case MUX6: numEntradas = 6; break;
		case MUX11: numEntradas = 11; break;
		}
		valor = Funcion1.evalua(cromosoma,poblacion, numEntradas, tipo_bl);
		
		cromosoma.setFitness(valor);
		}catch(Exception e) {	
		}
		return valor;
	}

	public void evaluaPoblacion() {
		try {
		double fitness, fitness_best, worst_fitness,sum_fitness = 0;
		//int pos_fitness_best = 0;
		fitness_best = 100000;
		worst_fitness = 100;
		
		for(int i = 0; i < this.poblacion.length; i++) {
			fitness = fitness(this.poblacion[i]);
			sum_fitness += fitness;
		}
		
		ordenarPoblacion(0, this.tamPoblacion-1);

		worst_fitness = this.poblacion[0].getFitness();
		fitness_best = this.poblacion[this.poblacion.length-1].getFitness();
		
		//pos_fitness_best = (int)(elitismo*100.0d);
		if(fitness_best > elMejor.getFitness()) {
			elMejor = this.duplicarCromosoma(this.poblacion[this.poblacion.length-1]); 
		}
		/////Esto es lo del peor
		if(worst_fitness < elPeor.getFitness()) {
			elPeor = this.duplicarCromosoma(this.poblacion[0]); 
		}
		 
		double puntuacion = 0, puntuacion_acu = 0;
		
		for(int i = 0; i < this.tamPoblacion; i++) {
			
			puntuacion = fitness(this.poblacion[i]) / sum_fitness;
			puntuacion_acu += puntuacion;
			this.poblacion[i].setPuntuacion(puntuacion);
			this.poblacion[i].setPuntAcumulada(puntuacion_acu);
		}
	
	
		this.mediasGeneracion[this.generacionActual] = sum_fitness / (int)this.poblacion.length;
		this.mejoresGeneracion[this.generacionActual] = fitness_best;
		this.peoresGeneracion[this.generacionActual] = worst_fitness;
		this.mejoresAbsolutos[this.generacionActual] = this.elMejor.getFitness();
		this.peoresAbsolutos[this.generacionActual] = this.elPeor.getFitness();
		}catch(Exception e) {
			
		}
	}
	
	public void seleccionaPoblacion() {
		try {
		switch(tipo_seleccion) {
		case ESTOCASTICO: 
			EstocasticoUniversal estocastUniv = new EstocasticoUniversal(this.poblacion, this.tamPoblacion);
			this.numSelecionados += estocastUniv.seleccionEstocastico();
			break;
		case RULETA: 
			Ruleta ruleta = new Ruleta(this.poblacion, this.tamPoblacion);
			this.numSelecionados += ruleta.seleccionRuleta();
			break;
		case TORNEO:
			Torneos torneos = new Torneos(this.poblacion, this.tamPoblacion);
			this.numSelecionados += torneos.seleccionTorneos();
			break;
		case RANKING: 
			Ranking ranking = new Ranking(this.poblacion, this.tamPoblacion, 2);
			this.numSelecionados += ranking.seleccionRanking();
			break;
		case TRUNCAMIENTO10: 
			truncamiento tr= new truncamiento(this.poblacion, this.tamPoblacion, true);
			this.numSelecionados += tr.seleccionaTruncamiento();
			break;
		case TRUNCAMIENTO50: 
			truncamiento tru= new truncamiento(this.poblacion, this.tamPoblacion, false);
			this.numSelecionados += tru.seleccionaTruncamiento();
			break;
		case SELPROPIA: 
			SelPropia slp= new SelPropia(this.poblacion, this.tamPoblacion);
			this.numSelecionados += slp.seleccionPropia();
			break;
		default:
			Ruleta r = new Ruleta(this.poblacion, this.tamPoblacion);
			this.numSelecionados += r.seleccionRuleta();
			break;
		}
		}catch(Exception e) {
			
		}
	}
	

	public void reproducePoblacion() {
		try {
		ArrayList<Cromosoma> poblacionCruce = new ArrayList<Cromosoma>();
		ArrayList<Integer>   indicePadres = new ArrayList<Integer>();
		CruceSimple cruce = new CruceSimple();

		for( int i =0; i <this.poblacion.length ;i++) {
			double valor = Math.random();
				if (valor <= this.probabilidadCruce) {
					poblacionCruce.add(this.poblacion[i]);
					indicePadres.add(i);
				}
			}
	
			Random rand = new Random();
			
			while (poblacionCruce.size() > 1)
			{
				int indicePadre1 = Math.abs(rand.nextInt() % poblacionCruce.size());
				Cromosoma padre1 = poblacionCruce.get(indicePadre1);
				int padre1IndividuoIndex = indicePadres.get(indicePadre1);
				
				indicePadres.remove(indicePadre1);
				poblacionCruce.remove(indicePadre1);

				int indicePadre2 = Math.abs(rand.nextInt() % poblacionCruce.size());
				Cromosoma padre2 = poblacionCruce.get(indicePadre2);
				int padre2IndividuoIndex = indicePadres.get(indicePadre2);
				
				indicePadres.remove(indicePadre2);
				poblacionCruce.remove(indicePadre2);

				this.numCruzes += this.tamPoblacion/2-1;
				cruce.cruza(duplicarCromosoma(padre1), duplicarCromosoma(padre2), probabilidadCruce, longMax);

				this.poblacion[padre1IndividuoIndex] = padre1;
				this.poblacion[padre2IndividuoIndex] = padre2;
				
			}
		}catch(Exception e) {
			
		}
	}

		
		
		
	
	
	
	public void mutaPoblacion() {
		try {
		Mutacion m;
		Random rn = new Random();
		switch(mutacion) {
		case ARBOL:
				m = new MutacionDeArbol();
			break;
		case PERMUTACION:
				m = new MutacionDePermutacion();
			break;
		case FUNCIONALSIMPLE:
				m = new MutacionFuncionalSimple();
			break;
		case TERMINALSIMPLE:
			m = new MutacionTerminalSimple();
			break;
		default:
			m = new MutacionDeArbol();
			break;
		}
			
		for(Cromosoma c : poblacion) {
			if(probabilidadMutacion < rn.nextDouble())
				++this.numMutaciones;
				m.muta(c);
		}
		}catch(Exception e) {
			
		}
	}
	
	public void seleccionaElite() {
    	int num_elites = (int) (this.tamPoblacion * this.elitismo);
    	if (num_elites > this.tamPoblacion) num_elites = this.tamPoblacion;
    	else if(num_elites == 0 && this.elitismo  != 0)num_elites = 1;
    	this.ordenarPoblacion(0, this.tamPoblacion-1);
    	this.elite.clear();
    	for (int i = 0 ; i < num_elites; i++)
    		this.elite.add(duplicarCromosoma(this.poblacion[this.poblacion.length -(i+1)]));
	}
	
	public void incluyeElite() {
		
    	this.ordenarPoblacion(0, this.tamPoblacion-1);
    	if(this.elite.size() > 0) {
    		
    		for(int i = 0; i <this.elite.size(); i++) {
				this.poblacion[i] = elite.get(i);
				
			}
    	}
	}
	
	public int getNumElites() {
		return (int) Math.ceil(this.getTamPoblacion() * this.elitismo / 100);
	}
	
	/**
	 * Devuelve true si se cumple la condicion de
	 * terminacion del bucle; false en c/c.
	 * @param generacion_actual
	 * @return
	 */
	public boolean terminado(int generacion_actual) {
		return generacion_actual >= this.numGeneraciones;
	}
	
	
    /**
     * Ordena de menor a mayor fitness
     * @param poblacion
     * @param izq
     * @param der
     */
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

	
    public boolean getBooleanElite() {
    	return this.booleanElite;
    }
    
    public int getTamPoblacion() {
    	return this.tamPoblacion;
    }
    
    public int getNumGeneraciones() {
    	return this.numGeneraciones;
    }
    
    public double getProbabilidadCruce() {
    	return this.probabilidadCruce;
    }
    
    public double getProbabilidadMutacion() {
    	return this.probabilidadMutacion;
    }
    
    public String getPrecision() {
    	return this.precision;
    }

	public double[] getMejoresAbsolutos() {
		return this.mejoresAbsolutos;
	}

	public double[] getMejoresGeneracion() {
		return this.mejoresGeneracion;
	}

	public double[] getMedias() {
		return this.mediasGeneracion;
	}

	public Cromosoma getMejor() {
		return duplicarCromosoma(this.elMejor);
	}
	public Cromosoma getPeor() {
		return duplicarCromosoma(this.elPeor);
	}

	public void aumentaGeneracion() {
		this.generacionActual++;
	}
	
//	private Cromosoma duplicarCromosoma(Cromosoma c) {
//	    Inicializacion in;
//		boolean mux = true;
//		
//		switch(tipo_mux) {
//		case MUX6: mux = true; break;
//		case MUX11: mux = false; break;
//		}
//		
//		switch(tipo_ini) {
//		case CRECIENTE:
//			in = new Creciente(this.longMax, mux, this.permiteIf);
//			break;
//		case COMPLETA:
//			in = new Completa(this.longMax, mux, this.permiteIf);
//			break;
//		case RAMPEDANDHALF:
//			in = new RampedAndHalf(this.longMax, mux, this.permiteIf);
//			break;
//		default:
//			in = new Creciente(this.longMax, mux, this.permiteIf);
//		}
//		
//		
//		Cromosoma nuevo = new Cromosoma(in.inicializar(), longMax, permiteIf);
//		
//	    nuevo.setFitness(c.getFitness());
//	    nuevo.setFenotipo(c.getFenotipo());	                
//	    return nuevo;
//	    }
	
	
	private Cromosoma duplicarCromosoma(Cromosoma c) {
		 Cromosoma nuevo = new Cromosoma(c);       
	    	return nuevo;
	    }
		

		public double[] getPeoresAbsolutos() {
			return peoresAbsolutos;
		}

		public void setPeoresAbsolutos(double[] peoresAbsolutos) {
			this.peoresAbsolutos = peoresAbsolutos;
		}


		public int getNumMutaciones() {
			return numMutaciones;
		}

		public void setNumMutaciones(int numMutaciones) {
			this.numMutaciones = numMutaciones;
		}

		public int getNumCruzes() {
			return numCruzes;
		}

		public void setNumCruzes(int numCruzes) {
			this.numCruzes = numCruzes;
		}

		public int getNumSelecionados() {
			return numSelecionados;
		}

		public void setNumSelecionados(int numSelecionados) {
			this.numSelecionados = numSelecionados;
		}

		public double[] getPeoresGeneracion() {
			return peoresGeneracion;
		}

		public void setPeoresGeneracion(double[] peoresGeneracion) {
			this.peoresGeneracion = peoresGeneracion;
		}

		

}
