package LoginServlet;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.xml.internal.messaging.saaj.util.Base64;

import ArmazenaDadosUsuario.ArmaDadosUsuario;
import AutenticacaoJdbcDao.JDBCAutenticacaoDAO;
import Conexao.Conexao;

public class AutenticacaoServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void process(HttpServletRequest request, 
			HttpServletResponse response)
	throws ServletException, IOException{
		
		/*
		 * Desencriptando a senha enviada e armazenando na váriavel
		 * textodeserializado
		 */
	
		//imprimindo no console a variável contendo a senha desencriptada
		ArmaDadosUsuario dadosautentica = new ArmaDadosUsuario();
		
		dadosautentica.setUsuario(request.getParameter("usuario"));
		String textodeserializado = new String(Base64.base64Decode(request.getParameter("senha")));
		String senmd5 = "";
		MessageDigest md = null; 
		
		try {
			/*
			 * Inicializando a conversão para o padrão de criptografia md5
			 * Cado ocorra tudo certo codifica este padrão em bytes e armazena
			 * na variável nd
			 */
			md = MessageDigest.getInstance("MD5");
		}catch (NoSuchAlgorithmException e) {
			/*
			 * Caso de algum problema na conversão uma mensagem de erro será disparada
			 */
			e.printStackTrace();
		}
		/*
		 * Converte o valor do MD5 em bytes para um hash de inteiros longos para que possa
		 * trabalhar com uma representação mais próxima de alto nivel
		 */
		BigInteger hash = new BigInteger(1, md.digest(request.getParameter("senha").getBytes()));
		/*
		 * Converte esta representação em string para que possa armazenar a senha neste formato
		 */
		senmd5 = hash.toString(16);
		
		dadosautentica.setSenha(senmd5);// envia senha criptografada para o BD
		
		Conexao conec = new Conexao();
		Connection conexao = (Connection)conec.abrirConexao();

		JDBCAutenticacaoDAO jdbcAutenticacao = new JDBCAutenticacaoDAO(conexao);
		boolean retorno = jdbcAutenticacao.consultar(dadosautentica);
		System.out.println(dadosautentica.getSenha()+ dadosautentica.getUsuario());
		if(retorno) {
			
			HttpSession sessao = request.getSession();
			sessao.setAttribute("login", request.getParameter("usuario"));
			response.sendRedirect("Acesso/principal.html");	
		}else {
			response.sendRedirect("erro.html");
		}
		
		
		
	}

	
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		process(request,response);
	}
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response)throws ServletException, IOException{
		process(request,response);
	}
	

}

























