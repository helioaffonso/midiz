package control;
import model.Indexacao;
import view.MidizUI;

public class Main {

	/*#model.Indexacao Dependency20*/
public static void main(String[] args)
	{
		Indexacao indexacao = new Indexacao();
		indexacao.indexaArquivos();
		indexacao.salvaIndice();
		
		MidizUI midiz = new MidizUI(indexacao.getArvoreNotas(),indexacao.getArvoreDuracoes());
	}
}
