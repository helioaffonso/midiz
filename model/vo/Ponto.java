package model.vo;

public class Ponto implements Comparable{

	private int idPonto;
	private int idPontoRef;
	private int coord1;
	private int coord2;
	private int coord3;
	private int coord4;
	private int coord5;
	private int coord6;
	private int coord7;
	
	private double similaridade;

	private int coordDuracao1;
	private int coordDuracao2;
	private int coordDuracao3;
	private int coordDuracao4;
	private int coordDuracao5;
	private int coordDuracao6;
	private int coordDuracao7;	
	
	private String tipoPonto;
	
	public int getCoord1() {
		return coord1;
	}
	public void setCoord1(int coord1) {
		this.coord1 = coord1;
	}
	public int getCoord2() {
		return coord2;
	}
	public void setCoord2(int coord2) {
		this.coord2 = coord2;
	}
	public int getCoord3() {
		return coord3;
	}
	public void setCoord3(int coord3) {
		this.coord3 = coord3;
	}
	public int getCoord4() {
		return coord4;
	}
	public void setCoord4(int coord4) {
		this.coord4 = coord4;
	}
	public int getCoord5() {
		return coord5;
	}
	public void setCoord5(int coord5) {
		this.coord5 = coord5;
	}
	public int getCoord6() {
		return coord6;
	}
	public void setCoord6(int coord6) {
		this.coord6 = coord6;
	}
	public int getCoord7() {
		return coord7;
	}
	public void setCoord7(int coord7) {
		this.coord7 = coord7;
	}
	public int getIdPonto() {
		return idPonto;
	}
	public void setIdPonto(int idPonto) {
		this.idPonto = idPonto;
	}
	public String getTipoPonto() {
		return tipoPonto;
	}
	public void setTipoPonto(String tipoPonto) {
		this.tipoPonto = tipoPonto;
	}
	public int getCoordDuracao1() {
		return coordDuracao1;
	}
	public void setCoordDuracao1(int coordDuracao1) {
		this.coordDuracao1 = coordDuracao1;
	}
	public int getCoordDuracao2() {
		return coordDuracao2;
	}
	public void setCoordDuracao2(int coordDuracao2) {
		this.coordDuracao2 = coordDuracao2;
	}
	public int getCoordDuracao3() {
		return coordDuracao3;
	}
	public void setCoordDuracao3(int coordDuracao3) {
		this.coordDuracao3 = coordDuracao3;
	}
	public int getCoordDuracao4() {
		return coordDuracao4;
	}
	public void setCoordDuracao4(int coordDuracao4) {
		this.coordDuracao4 = coordDuracao4;
	}
	public int getCoordDuracao5() {
		return coordDuracao5;
	}
	public void setCoordDuracao5(int coordDuracao5) {
		this.coordDuracao5 = coordDuracao5;
	}
	public int getCoordDuracao6() {
		return coordDuracao6;
	}
	public void setCoordDuracao6(int coordDuracao6) {
		this.coordDuracao6 = coordDuracao6;
	}
	public int getCoordDuracao7() {
		return coordDuracao7;
	}
	public void setCoordDuracao7(int coordDuracao7) {
		this.coordDuracao7 = coordDuracao7;
	}
	public int getIdPontoRef() {
		return idPontoRef;
	}
	public void setIdPontoRef(int idPontoRef) {
		this.idPontoRef = idPontoRef;
	}
	public double getSimilaridade() {
		return similaridade;
	}
	public void setSimilaridade(double similaridade) {
		this.similaridade = similaridade;
	}
	
	public int compareTo(Object obj)
	{
		Ponto ponto = (Ponto)obj;
		int iReturn = 0;
		
		if( ponto.similaridade < this.similaridade )
			iReturn = 1;
		else if( ponto.similaridade > this.similaridade )
			iReturn = -1;
			
		return iReturn;
	}
	
}
