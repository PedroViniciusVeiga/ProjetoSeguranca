function ValidaDados(){
	/*
	 * Cria a variável senhaembase64 e atribui a ela
	 * o valor do campo senha do formulário convertido
	 * para base64 a partir da função JavaScript btoa().
	 */
	
	var senhaembase64 = btoa(document.frmLogin.senha.value);
	//Armazena a senha convertida em base 64 no campo senha do formulário
	document.frmLogin.senha.value = senhaembase64;
	/*
	 * Retorna verdadeiro permitindo que o botão submit possa dar prosseguimento
	 * ao envio dos dados do formulário.
	 * 
	 */
	
	return true;
	
}