function enviarPergunta() {
    var userInput = document.getElementById('userInput').value;
    adicionarMensagemDoUsuario(userInput);
    obterRespostaDoChatbot(userInput);
}

function adicionarMensagemDoUsuario(mensagem) {
    var chatBody = document.getElementById('chatBody');
    chatBody.innerHTML += '<div><strong>Você:</strong> ' + mensagem + '</div>';
}

function adicionarRespostaDoChatbot(resposta) {
    var chatBody = document.getElementById('chatBody');
    chatBody.innerHTML += '<div><strong>ChatBot:</strong> ' + resposta + '</div>';
}

function obterRespostaDoChatbot(pergunta) {
    // Aqui você pode adicionar a lógica para obter a resposta do seu chatbot.
    // Pode ser uma chamada a uma API ou um processamento local.

    // Exemplo simples:
    var resposta = "Desculpe, não sei a resposta para isso.";
    adicionarRespostaDoChatbot(resposta);
}
