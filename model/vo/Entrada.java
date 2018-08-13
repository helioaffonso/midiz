package model.vo;

public class Entrada {

	private int numResultados;
	private int numVizinhos;
	
	private double pesoNota;
	private double pesoDuracao;
	
	public boolean possuiDuracao; 
	
	private String n1;
	private String n2;
	private String n3;
	private String n4;
	private String n5;
	private String n6;
	private String n7;
	private String n8;

	private String d1;
	private String d2;
	private String d3;
	private String d4;
	private String d5;
	private String d6;
	private String d7;
	private String d8;
		
	private Ponto pontoTipoNota;
	private Ponto pontoTipoDuracao;
	
	private String erro;

	public Entrada( String an1,String an2,String an3,String an4,String an5,String an6,String an7,String an8,
					String ad1,String ad2,String ad3,String ad4,String ad5,String ad6,String ad7,String ad8, String sErro )
	{
		n1 = an1;
		n2 = an2;
		n3 = an3;
		n4 = an4;
		n5 = an5;
		n6 = an6;
		n7 = an7;
		n8 = an8;
		d1 = ad1;
		d2 = ad2;
		d3 = ad3;
		d4 = ad4;
		d5 = ad5;
		d6 = ad6;
		d7 = ad7;
		d8 = ad8;
		erro = sErro;
	}
	
	public Ponto getPontoTipoDuracao() {
		return pontoTipoDuracao;
	}

	public void setPontoTipoDuracao(Ponto pontoTipoDuracao) {
		this.pontoTipoDuracao = pontoTipoDuracao;
	}

	public Ponto getPontoTipoNota() {
		return pontoTipoNota;
	}



	public void setPontoTipoNota(Ponto pontoTipoNota) {
		this.pontoTipoNota = pontoTipoNota;
	}

	// TODO: implementar validação da entrada
	public boolean validate()
	{
		try 
		{
			Integer.parseInt(this.getN1());
			Integer.parseInt(this.getN2());
			Integer.parseInt(this.getN3());
			Integer.parseInt(this.getN4());
			Integer.parseInt(this.getN5());
			Integer.parseInt(this.getN6());
			Integer.parseInt(this.getN7());
			Integer.parseInt(this.getN8());
			
			if( d1 != null && !d1.equals("") && d2 != null && !d2.equals("") && d3 != null && !d3.equals("") &&
				d4 != null && !d4.equals("") && d5 != null && !d5.equals("") && d6 != null && !d6.equals("") &&
				d7 != null && !d7.equals("") && d8 != null && !d8.equals("") )
			{
				Double.parseDouble(this.getD1());
				Double.parseDouble(this.getD2());
				Double.parseDouble(this.getD3());
				Double.parseDouble(this.getD4());
				Double.parseDouble(this.getD5());
				Double.parseDouble(this.getD6());
				Double.parseDouble(this.getD7());
				Double.parseDouble(this.getD8());
				
				possuiDuracao = true;
			}
			else
			{
				possuiDuracao = false;
			}
		}
		catch( Exception e )
		{
			return false;
		}
		
		return true;
	}
	
	public String getD1() {
		return d1;
	}

	public void setD1(String d1) {
		this.d1 = d1;
	}

	public String getD2() {
		return d2;
	}

	public void setD2(String d2) {
		this.d2 = d2;
	}

	public String getD3() {
		return d3;
	}

	public void setD3(String d3) {
		this.d3 = d3;
	}

	public String getD4() {
		return d4;
	}

	public void setD4(String d4) {
		this.d4 = d4;
	}

	public String getD5() {
		return d5;
	}

	public void setD5(String d5) {
		this.d5 = d5;
	}

	public String getD6() {
		return d6;
	}

	public void setD6(String d6) {
		this.d6 = d6;
	}

	public String getD7() {
		return d7;
	}

	public void setD7(String d7) {
		this.d7 = d7;
	}

	public String getD8() {
		return d8;
	}

	public void setD8(String d8) {
		this.d8 = d8;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getN1() {
		return n1;
	}

	public void setN1(String n1) {
		this.n1 = n1;
	}

	public String getN2() {
		return n2;
	}

	public void setN2(String n2) {
		this.n2 = n2;
	}

	public String getN3() {
		return n3;
	}

	public void setN3(String n3) {
		this.n3 = n3;
	}

	public String getN4() {
		return n4;
	}

	public void setN4(String n4) {
		this.n4 = n4;
	}

	public String getN5() {
		return n5;
	}

	public void setN5(String n5) {
		this.n5 = n5;
	}

	public String getN6() {
		return n6;
	}

	public void setN6(String n6) {
		this.n6 = n6;
	}

	public String getN7() {
		return n7;
	}

	public void setN7(String n7) {
		this.n7 = n7;
	}

	public String getN8() {
		return n8;
	}

	public void setN8(String n8) {
		this.n8 = n8;
	}

	public int getNumResultados() {
		//TODO + 1 todo
		return 1;
	}

	public void setNumResultados(int numResultados) {
		this.numResultados = numResultados;
	}

	public int getNumVizinhos() {
		// TODO + 1 todo
		return 10;
	}

	public void setNumVizinhos(int numVizinhos) {
		this.numVizinhos = numVizinhos;
	}

	public double getPesoDuracao() {
		// TODO
		return 0.1;
	}

	public void setPesoDuracao(double pesoDuracao) {
		this.pesoDuracao = pesoDuracao;
	}

	public double getPesoNota() {
		return 0.9;
	}

	public void setPesoNota(double pesoNota) {
		this.pesoNota = pesoNota;
	}	
}
