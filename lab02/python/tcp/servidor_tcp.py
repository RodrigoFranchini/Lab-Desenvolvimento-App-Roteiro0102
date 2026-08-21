import socket
import datetime

HOST = "0.0.0.0"
PORTA = 5089  # Porta padrão TCP usada 5000 + 89 dois digitos finais do RA 76628

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as servidor:
    servidor.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    servidor.bind((HOST, PORTA))
    servidor.listen(1)
    print(f"[TCP] Servidor aguardando conexões na porta {PORTA}...")
    conexao, endereco = servidor.accept()
    with conexao:
        print(f"[TCP] Cliente conectado: {endereco}")
        while True:
            dados = conexao.recv(1024).decode("utf-8").strip()
            if not dados:
                break
            print(f"[TCP] Recebido: {dados}")
            if dados.lower() == "sair":
                conexao.sendall("Encerrando conexão. Até mais!\n".encode("utf-8"))
                break
            if dados.lower() == "hora":
                hora_atual = datetime.datetime.now().strftime("%H:%M:%S")
                conexao.sendall(f"Hora atual: {hora_atual}\n".encode("utf-8"))
                continue

            resposta = f'Monitor responde: recebi sua mensagem -> "{dados}"\n'
            conexao.sendall(resposta.encode("utf-8"))

print("[TCP] Servidor encerrado.")
