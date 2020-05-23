package cromosoma;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Nodo<T> {

	public T info; //lo dejo en generico pero creo que siempre va a ser InfoNodo
	public ArrayList<Nodo<T>> hijos;
	public Nodo<T> padre;
	
	public Nodo (T info) {
		this.info = info;
		this.hijos = new ArrayList<Nodo<T>>();
		this.padre = null;
	}
	public Nodo (T info, Nodo<T> padre) {
		this.info = info;
		this.hijos = new ArrayList<Nodo<T>>();
		this.padre = padre;
	}
	
	
	public void anadirHijo(Nodo<T> hijo) { //Si ya tiene padre no se añade, robar niños esta mal
		if(hijo.getPadre() == null) {
			hijo.setPadre(this);
			hijos.add(hijo);
		}	
	}
	public void anadirHijoIndex(Nodo<T> hijo, int i) { //Si ya tiene padre no se añade, robar niños esta mal
		if(hijo.getPadre() == null) {
			hijo.setPadre(this);
			try {
			hijos.add(i, hijo);
			}catch(Exception e) {
				hijos.add(hijo);
			}
		}	
	}
	
	 public int getPosicionHijo(Nodo<T> n)
	    {
	    	for (int i = 0; i < hijos.size(); i++)
	    	{
	    		if (hijos.get(i) == n)
	    			return i;
	    	}
	    	return -1;
	}
	
	public Nodo<T> getHijo(int i) {
		return hijos.get(i);
	}
	
	public int numHijos() {
		return hijos.size();
	}
	
	public void eliminarHijos() { //elimina a todos los hijos 
		while(hijos.size() > 0) {
			hijos.remove(0);
		}
	}
	
	public void eliminaHijo(int i) {
		try {
		hijos.remove(i);
		}catch(Exception e) {
			
		}
	}
	
	
	
	
	public int getProfundidad() {
		int numHijos = this.numHijos(), max = 1;
		if (numHijos == 0) return 0;
		else {
			for(int i = 0; i < numHijos ; ++i) {
				int prof = this.getHijo(i).getProfundidad();
				if (prof > max) max = prof;
			}
			return max + 1;
		}
	}
	
	public int haciaLaRaiz () {
		if(this.padre == null)return 1;
		else return this.padre.haciaLaRaiz() +1;
	}
	
	
	
	public T getInfo() {
		return info;
	}

	public void setInfo(T info) {
		this.info = info;
	}


	public ArrayList<Nodo<T>> getHijos() {
		return hijos;
	}

	public void setHijos(ArrayList<Nodo<T>> hijos) {
		this.hijos = hijos;
	}

	public Nodo<T> getPadre() {
		return padre;
	}

	public void setPadre(Nodo<T> padre) {
		this.padre = padre;
	}
	
    public void desenlazar()
    {
		Nodo<T> parent = this.getPadre();
    	if (parent != null)
    	{
    		int numChild = parent.numHijos();
    		for (int i = 0; i < numChild; i++)
    		{
    			if (parent.getHijo(i) == this)
    			{
    				parent.eliminaHijo(i);
    				break;
    			}
    		}
    		this.padre = null;
    	}
    }
	
    public int getNodos()
    {
    	int hijos = this.hijos.size();
    	if (hijos > 0)
    	{
    		int arb = 0;
    		for (int i = 0; i < hijos; i++)
    		{
    			arb += this.getHijo(i).getNodos();
    		}
    		return arb + 1;
    	}
    	else return 1;
}
	
	
	

	class InOrderIterator implements Iterator<Nodo<T>> {         // DFS
        // constructor 
        List<Integer> indicePadre;
        Nodo<T> nodoActual;
        
    	public InOrderIterator() { 
    		indicePadre = new ArrayList<>();
    		nodoActual = Nodo.this;
    		while (nodoActual.numHijos() > 0)
    		{
    			indicePadre.add(0);
    			nodoActual = nodoActual.getHijo(0);
    		}
        }
          
        // Checks if the next element exists 
        public boolean hasNext() {
        	return nodoActual != null;
        } 
          
        // moves the cursor/iterator to next element 
        public Nodo<T> next() {
        	Nodo<T> nodo = nodoActual;
        	nodoActual = nodo.getPadre();
        	if (nodoActual == null)
        		return nodo;
        	
        	if (nodoActual.numHijos() - 1 > indicePadre.get(indicePadre.size() - 1))
        	{
        		int newNum = indicePadre.get(indicePadre.size() - 1) + 1;
        		indicePadre.set(indicePadre.size() - 1, newNum);
        		nodoActual = nodoActual.getHijo(newNum);
        		while (nodoActual.numHijos() > 0)
        		{
        			indicePadre.add(0);
        			nodoActual = nodoActual.getHijo(0);
        		}
        	}
        	else {
        		indicePadre.remove(indicePadre.size() - 1);
        		return nodo;
        	}
        	return nodo;
        } 

    }
    
    class LevelOrderIterator implements Iterator<Nodo<T>> {        // Mixto
        // constructor 
        List<Integer> indicePadre;
        Nodo<T> nodoActual;
        
    	public LevelOrderIterator() { 
    		indicePadre = new ArrayList<>();
    		nodoActual = Nodo.this;
    		indicePadre.add(0);
        }
          
        // Checks if the next element exists 
        public boolean hasNext() {
        	return nodoActual != Nodo.this || nodoActual.numHijos() - 1 >= indicePadre.get(indicePadre.size() - 1);
        } 
          
        // moves the cursor/iterator to next element 
        public Nodo<T> next() {
        	Nodo<T> node = nodoActual;
        	//currentNode = node.getParent();
        	if (nodoActual.numHijos() - 1 >= indicePadre.get(indicePadre.size() - 1))
        	{
        		int num = indicePadre.get(indicePadre.size() - 1);
        		nodoActual = nodoActual.getHijo(indicePadre.get(indicePadre.size() - 1));
        		indicePadre.set(indicePadre.size() - 1, num + 1);
        		indicePadre.add(0);
        		
        	}
        	else {
        		while ((nodoActual.numHijos() - 1 < indicePadre.get(indicePadre.size() - 1)) && (nodoActual.getPadre() != null))
        		{
        			indicePadre.remove((indicePadre.size() - 1));
        			nodoActual = nodoActual.getPadre();
        		}
        		if (nodoActual.numHijos() - 1 >= indicePadre.get(indicePadre.size() - 1))
            	{
        			int num = indicePadre.get(indicePadre.size() - 1);
            		nodoActual = nodoActual.getHijo(indicePadre.get(indicePadre.size() - 1));
            		indicePadre.set(indicePadre.size() - 1, num + 1);
            		indicePadre.add(0);
            	}
        	}
        	return node;
        } 

    }
    public Iterator<Nodo<T>> iteratorInOrder() {
		return new InOrderIterator();
	}  
	
	public Iterator<Nodo<T>> iteratorLevelOrder() {
		return new LevelOrderIterator();
	} 

public static String imprimeArbol(Nodo<InfoNodo> nodo) {
		
		String arbol = "";
		Iterator<Nodo<InfoNodo>> iterator = nodo.iteratorLevelOrder();
        List<Integer> lista = new ArrayList<Integer>();
		while (iterator.hasNext()){
			Nodo<InfoNodo> o = iterator.next();
			InfoNodo inf = o.getInfo();
			if (inf.isFunc())
			{
				if (lista.size() > 0)
				{
					lista.set(lista.size() - 1, lista.get(lista.size() - 1) - 1);
				}
				lista.add(inf.getNumOp());
				arbol += inf.toString() + "(";
			}
			else {
				arbol += inf.toString();
				lista.set(lista.size() - 1, lista.get(lista.size() - 1) - 1);
				if (lista.get(lista.size() - 1) > 0)
				{
					arbol += ", ";
				} else {
					while (lista.size() > 0 && lista.get(lista.size() - 1) == 0)
					{
						arbol += ")";
						lista.remove(lista.size() - 1);
					}
					if (lista.size() > 0)
					{
						arbol += ", ";
					}
				}
				
			}
		}
		return arbol;
		
	}
	
	public String toString(){
		return this.info.toString();
	}
	
	
}
