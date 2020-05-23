package cruce;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cromosoma.*;

public class CruceSimple {

	public void cruza(Cromosoma in1, Cromosoma in2, double probCruce, int MaxProf) {
		
		Nodo<InfoNodo> fen1 = in1.getFenotipo();
		Nodo<InfoNodo> fen2 = in2.getFenotipo();
		Random rand = new Random();

		if (Math.random() <= probCruce && fen1.getProfundidad() > 2 && 
				fen2.getProfundidad() > 2) 
		{
			int puntoCorte1 = rand.nextInt(fen1.numHijos());
			Nodo<InfoNodo> corte1 = fen1.getHijo(puntoCorte1);
			corte1.desenlazar();
			
			int puntoCorte2 = rand.nextInt(fen2.numHijos());
			Nodo<InfoNodo> corte2 = fen2.getHijo(puntoCorte2);
			corte2.desenlazar();
			
			fen1.anadirHijoIndex(corte2, puntoCorte1);
			fen2.anadirHijoIndex(corte1, puntoCorte2);
		} else {
			List<Nodo<InfoNodo>> funciones1 = getNodosDeFuncion(fen1,-1, -1, MaxProf);
			int randValue;
			if (funciones1.size() > 1)
				randValue = rand.nextInt(funciones1.size() - 1) + 1; 
			else
				randValue = 0;
			Nodo<InfoNodo> nodoCortado = funciones1.get(randValue);
			int maxDepth = MaxProf - nodoCortado.haciaLaRaiz() + 1; 
			Nodo<InfoNodo> padre = nodoCortado.getPadre();
			int pCorte = 0; //inicializa a 0
			if (padre != null)
				pCorte = padre.getPosicionHijo(nodoCortado);
			nodoCortado.desenlazar();
			
			
			List<Nodo<InfoNodo>> funciones2 = getNodosDeFuncion(fen2, maxDepth, nodoCortado.getProfundidad(), MaxProf);
			if (funciones2.size() > 1)
				randValue = rand.nextInt(funciones2.size() - 1) + 1;
			else
				randValue = 0;
			Nodo<InfoNodo> nodoCortado2 = funciones2.get(randValue);
			Nodo<InfoNodo> padre2 = nodoCortado2.getPadre();
			int pCorte2 = 0;
			if (padre2 != null)
				pCorte2 = padre2.getPosicionHijo(nodoCortado2);
			nodoCortado2.desenlazar();

			if (padre != null) 
				padre.anadirHijoIndex(nodoCortado2, pCorte);
			else
				in1.setFenotipo(nodoCortado2);
			
			if (padre2 != null)
				padre2.anadirHijoIndex(nodoCortado, pCorte2);
			else
				in2.setFenotipo(nodoCortado);
		}

}
	
	private List<Nodo<InfoNodo>> getNodosDeFuncion(Nodo<InfoNodo> raiz, int maxDepth, int requiredSpace, int maxTreeDepth )
	{
		Iterator<Nodo<InfoNodo>> it = raiz.iteratorLevelOrder();
		List<Nodo<InfoNodo>> functions = new ArrayList<>();
		while (it.hasNext())
		{
			Nodo<InfoNodo> node = it.next();
			if (node.getInfo().isFunc())
			{
				if (maxDepth == -1)
					functions.add(node);
				else 
				{
					if (node.getProfundidad() <= maxDepth 
							&& node.haciaLaRaiz() <= maxTreeDepth - requiredSpace + 1)
						functions.add(node);
					

				}

			}
		}
		return functions;
}
	
	
}
