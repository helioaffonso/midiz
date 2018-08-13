package model.vo;

public class Nota {

	private int nota;

	private int duracao;

	private double posicao;

	private int canal;

	private int musicaId;

	private int id;

	private int instrumento;

	public int getCanal() {
		return canal;
	}

	public void setCanal(int canal) {
		this.canal = canal;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMusicaId() {
		return musicaId;
	}

	public void setMusicaId(int musicaId) {
		this.musicaId = musicaId;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public double getPosicao() {
		return posicao;
	}

	public void setPosicao(double posicao) {
		this.posicao = posicao;
	}

	public int getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(int instrumento) {
		this.instrumento = instrumento;
	}

}
