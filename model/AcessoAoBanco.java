/*
 * Created on 14/10/2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author helio
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AcessoAoBanco {
	
	private String nomeDoBanco;
	private String usuario;
	private String senha;	
	private ResourceBundle rs = ResourceBundle.getBundle("model/config");
	
	public AcessoAoBanco()
	{
		nomeDoBanco = rs.getString( "nomeDoBanco" );
		usuario 	= rs.getString( "usuario" );
		senha 		= rs.getString( "senha" );		
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public Connection getConexao()
	{
		try
		{
			Connection conexao = DriverManager.getConnection( nomeDoBanco, usuario, senha );
			return conexao;
		}
		catch( SQLException e )
		{
			System.out.println( e );
		}
		return null; 
	}

}
