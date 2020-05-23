package cromosoma;

import java.util.Random;

public class InfoNodo {

	public String[] func;
	public String[] term;
	public String info; 
	public boolean multiplexorSize;
	
	//multiplexSize = true : 6 entradas, false : 11 entradas
	public InfoNodo () {}
	public InfoNodo (boolean multiplexSize, boolean permiteIf, String info) {
		
		this.multiplexorSize = multiplexSize;
		
		if(permiteIf)  func = new String [] {"IF", "NOT", "OR", "AND"}; 
		else func = new String [] {"NOT", "OR", "AND"};
		
		if(multiplexSize) term = new String[] {"A0", "A1", "D0", "D1", "D2", "D3"};
		else term = new String[] {"A0", "A1","A3", "D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7", };
		
		switch(info) {
		case "random" : 
			this.info = genAleatorio();
			break;
		case "randomFunc":
			this.info = genAleatorioFunc();
			break;
		case "randomTerm":
			this.info = genAleatorioTerm();
			break;
		default:
			this.info = info;
			break;
		}
		
		if (info == "random")
			this.info = genAleatorio();
		else if (info == "randomFunction")
			this.info = genAleatorioFunc();
		else if (info == "randomTerminal")
			this.info = genAleatorioTerm();
		else
			this.info = info;
	}
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String genAleatorio() {
		if(Math.random() <= 0.5) return genAleatorioFunc();
		else return genAleatorioTerm();
	}
	
	public String genAleatorioTerm() {
		Random rn = new Random();
		return term[rn.nextInt(term.length)];
	}
	
	public String genAleatorioFunc() {
		Random rn = new Random();
		return func[rn.nextInt(func.length)];
	}
	
	public boolean isFunc() {
		for(int i = 0; i < func.length; ++i)
			if (func[i] == info) return true;
		
		return false;
	}
	
	public String imprimeArbol() {
		//TODO
		return null;
	}
	public int getNumOp()
	{
		if (!isFunc())  return -1;
		
		switch (this.info){
			case "NOT": return 1;
			case "AND": return 2;
			case "OR": return 2;
			default: return 3; 
		}
	}
	public boolean isMultiplexorSize() {
		return multiplexorSize;
	}
	public void setMultiplexorSize(boolean multiplexorSize) {
		this.multiplexorSize = multiplexorSize;
	}
	
	public String toString(){
		return this.info;
	}
	
}
