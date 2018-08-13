package model.vo;

public class PontoNotaDuracao {

	/*private int n1;
	private int n2;
	private int n3;
	private int n4;
	private int n5;
	private int n6;
	private int n7;

	private int d1;
	private int d2;
	private int d3;
	private int d4;
	private int d5;
	private int d6;
	private int d7;*/
	
	private int idNota;
	private int idDuracao;
	
	private Ponto pontoNota;
	private Ponto pontoDuracao;
	
	public PontoNotaDuracao()
	{		
	}
	
	public PontoNotaDuracao(Ponto pNota, Ponto pDuracao)
	{
		pontoNota = new Ponto();
		pontoDuracao = new Ponto();
		
		pontoNota.setCoord1(pNota.getCoord1());
		pontoNota.setCoord2(pNota.getCoord2());
		pontoNota.setCoord3(pNota.getCoord3());
		pontoNota.setCoord4(pNota.getCoord4());
		pontoNota.setCoord5(pNota.getCoord5());
		pontoNota.setCoord6(pNota.getCoord6());
		pontoNota.setCoord7(pNota.getCoord7());
		
		pontoDuracao.setCoordDuracao1(pDuracao.getCoordDuracao1());
		pontoDuracao.setCoordDuracao2(pDuracao.getCoordDuracao2());
		pontoDuracao.setCoordDuracao3(pDuracao.getCoordDuracao3());
		pontoDuracao.setCoordDuracao4(pDuracao.getCoordDuracao4());
		pontoDuracao.setCoordDuracao5(pDuracao.getCoordDuracao5());
		pontoDuracao.setCoordDuracao6(pDuracao.getCoordDuracao6());
		pontoDuracao.setCoordDuracao7(pDuracao.getCoordDuracao7());		
		
		idNota = pNota.getIdPonto();
		idDuracao = pNota.getIdPontoRef();
	}
	/*
	public int getD1() {
		return d1;
	}
	public void setD1(int d1) {
		this.d1 = d1;
	}
	public int getD2() {
		return d2;
	}
	public void setD2(int d2) {
		this.d2 = d2;
	}
	public int getD3() {
		return d3;
	}
	public void setD3(int d3) {
		this.d3 = d3;
	}
	public int getD4() {
		return d4;
	}
	public void setD4(int d4) {
		this.d4 = d4;
	}
	public int getD5() {
		return d5;
	}
	public void setD5(int d5) {
		this.d5 = d5;
	}
	public int getD6() {
		return d6;
	}
	public void setD6(int d6) {
		this.d6 = d6;
	}
	public int getD7() {
		return d7;
	}
	public void setD7(int d7) {
		this.d7 = d7;
	}
	public int getIdDuracao() {
		return idDuracao;
	}
	public void setIdDuracao(int idDuracao) {
		this.idDuracao = idDuracao;
	}
	public int getIdNota() {
		return idNota;
	}
	public void setIdNota(int idNota) {
		this.idNota = idNota;
	}
	public int getN1() {
		return n1;
	}
	public void setN1(int n1) {
		this.n1 = n1;
	}
	public int getN2() {
		return n2;
	}
	public void setN2(int n2) {
		this.n2 = n2;
	}
	public int getN3() {
		return n3;
	}
	public void setN3(int n3) {
		this.n3 = n3;
	}
	public int getN4() {
		return n4;
	}
	public void setN4(int n4) {
		this.n4 = n4;
	}
	public int getN5() {
		return n5;
	}
	public void setN5(int n5) {
		this.n5 = n5;
	}
	public int getN6() {
		return n6;
	}
	public void setN6(int n6) {
		this.n6 = n6;
	}
	public int getN7() {
		return n7;
	}
	public void setN7(int n7) {
		this.n7 = n7;
	}*/

	public int getIdDuracao() {
		return idDuracao;
	}

	public void setIdDuracao(int idDuracao) {
		this.idDuracao = idDuracao;
	}

	public int getIdNota() {
		return idNota;
	}

	public void setIdNota(int idNota) {
		this.idNota = idNota;
	}

	public Ponto getPontoDuracao() {
		return pontoDuracao;
	}

	public void setPontoDuracao(Ponto pontoDuracao) {
		this.pontoDuracao = pontoDuracao;
	}

	public Ponto getPontoNota() {
		return pontoNota;
	}

	public void setPontoNota(Ponto pontoNota) {
		this.pontoNota = pontoNota;
	}
}
