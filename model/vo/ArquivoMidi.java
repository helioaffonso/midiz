package model.vo;

import java.util.List;

public class ArquivoMidi {

	
	
	/**
	 * @directed true
	 */
	
	private model.vo.Nota lnkNota;

	private Musica musica;

	private List listaNotas;

	public List getListaNotas() {
		return listaNotas;
	}

	public void setListaNotas(List listaNotas) {
		this.listaNotas = listaNotas;
	}

	public Musica getMusica() {
		return musica;
	}

	public void setMusica(Musica musica) {
		this.musica = musica;
	}

}
