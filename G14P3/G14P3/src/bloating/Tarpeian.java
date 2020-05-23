package bloating;

import java.util.Random;

import cromosoma.Cromosoma;

public class Tarpeian {

	private int profMedia;
	private double prob = 0.1;
	
	public Tarpeian(Cromosoma[] pob) {
		profMedia = 0;
		for(Cromosoma c : pob) {
			profMedia += c.getFenotipo().getProfundidad();
		}
		profMedia /= pob.length;
	}
	
	public int fitnessBloating (Cromosoma c, int fit) {
		Random rn = new Random();
		if(c.getFenotipo().getProfundidad() > profMedia && prob < rn.nextDouble())//metodo tarpeian
			return fit / 10;
		return fit;
	}
	
}
