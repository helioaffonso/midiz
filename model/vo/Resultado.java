package model.vo;

public class Resultado implements Comparable{

	private String nomeMusica;
	private String artista;
	private String posicao;
	private String instrumento;
	private double similaridade;
	
	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	public String getInstrumento() {
		return instrumento;
	}
	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}
	public String getNomeMusica() {
		return nomeMusica;
	}
	public void setNomeMusica(String nomeMusica) {
		this.nomeMusica = nomeMusica;
	}
	public String getPosicao() {
		return posicao;
	}
	public void setPosicao(String posicao) {
		this.posicao = posicao;
	}
	public double getSimilaridade() {
		return similaridade;
	}
	public void setSimilaridade(double similaridade) {
		this.similaridade = similaridade;
	}
	
	public int compareTo(Object obj)
	{
		Resultado resultado = (Resultado)obj;
		int iReturn = 0;
		
		if( resultado.similaridade > this.similaridade )
			iReturn = 1;
		else if( resultado.similaridade < this.similaridade )
			iReturn = -1;
			
		return iReturn;
	}
	
}
