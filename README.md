# Laboratório de Desenvolvimento de Aplicações Móveis

Repositório das atividades da disciplina **Laboratório de Desenvolvimento de Aplicações Móveis**, do curso de Engenharia de Software da PUC Minas. Este repositório abrange o Roteiro 02 e 03 da disciplina.

## Conteúdo

### Lab de Redes

Implementações introdutórias de comunicação entre processos, em Java e Python:

- **TCP**: comunicação orientada a conexão entre cliente e servidor;
- **UDP**: troca de datagramas sem conexão;
- **Multicast**: distribuição de mensagens para um grupo de clientes;
- **WebSocket**: mural de avisos em tempo real sobre uma conexão persistente.

Veja as [respostas do roteiro de Redes](lab-redes/RESPOSTAS.md) e as [evidências da execução](lab-redes/evidencias/).

### Lab de gRPC

Implementação de uma Central de Atendimento usando um contrato [Protocol Buffers](lab-grpc/proto/central.proto), com duas operações:

- `ConsultarHorario`: chamada RPC unária que retorna o horário atual;
- `AcompanharAvisos`: chamada RPC com streaming do servidor, que envia cinco avisos.

Há clientes e servidores equivalentes em Java e Python. As [respostas do roteiro de gRPC](lab-grpc/RESPOSTAS.md) e as [evidências da execução](lab-grpc/evidencias/) complementam o código.

## Estrutura

```text
.
├── lab-redes/
│   ├── java/       # TCP, UDP, multicast e WebSocket
│   ├── python/     # TCP, UDP, multicast e WebSocket
│   ├── evidencias/
│   └── RESPOSTAS.md
└── lab-grpc/
	├── java/       # Projeto Maven com cliente e servidor gRPC
	├── python/     # Cliente, servidor e código gerado pelo protoc
	├── proto/      # Contrato central.proto
	├── evidencias/
	└── RESPOSTAS.md
```

## Pré-requisitos

- Java 17 ou superior;
- Maven 3.8 ou superior;
- Python 3.9 ou superior;
- `grpcio` para executar a versão Python do laboratório gRPC.

Para instalar a dependência Python:

```bash
python3 -m pip install grpcio
```

## Executando o gRPC

### Java

O projeto Maven gera automaticamente as classes Java a partir de `central.proto` durante a compilação. Em dois terminais, execute:

```bash
cd lab-grpc/java/grpc-central
mvn compile
```

Terminal 1, servidor:

```bash
mvn exec:java -Dexec.mainClass=br.pucminas.labdamd.central.ServidorCentral
```

Terminal 2, cliente:

```bash
mvn exec:java -Dexec.mainClass=br.pucminas.labdamd.central.ClienteCentral
```

Na implementação Java, o servidor escuta na porta `50140` (`50051 + OFFSET`, com `OFFSET = 89`).

### Python

Entre no diretório que contém os módulos gerados antes de iniciar os processos:

```bash
cd lab-grpc/python/grpc_central
```

Terminal 1, servidor:

```bash
python3 servidor_central.py
```

Terminal 2, cliente:

```bash
python3 cliente_central.py
```

Na implementação Python, o servidor escuta na porta `50061` (`50061 + OFFSET`, com `OFFSET = 0`). Cliente e servidor devem usar o mesmo valor de `OFFSET` dentro da mesma implementação.

## Executando os exemplos de Redes

Cada pasta em `lab-redes/java/` e `lab-redes/python/` contém um par cliente/servidor independente. Inicie primeiro o servidor e, em outro terminal, o cliente correspondente. Os arquivos seguem o padrão:

```text
<protocolo>/Servidor<Protocolo>.*
<protocolo>/Cliente<Protocolo>.*
```

Os exemplos Python podem ser executados diretamente a partir da raiz do protocolo:

```bash
cd lab-redes/python/tcp
python3 servidor_tcp.py
python3 cliente_tcp.py
```

Para Java, compile os dois arquivos do protocolo e execute as classes em terminais separados:

```bash
cd lab-redes/java/tcp
javac *.java
java ServidorTCP
java ClienteTCP
```

Repita o procedimento trocando `tcp` pelo protocolo desejado. O exemplo de WebSocket possui um projeto Maven próprio em [lab-redes/java/websocket](lab-redes/java/websocket).

## Observações

- Os arquivos `*_pb2.py` e `*_pb2_grpc.py` são gerados a partir do contrato Protocol Buffers; não edite esses arquivos manualmente.
- Os exemplos usam `localhost` e foram preparados para execução local. Para executar em máquinas diferentes, ajuste o endereço do servidor e as portas nos clientes.
- Os relatórios de execução e capturas estão organizados nas pastas `evidencias/` de cada laboratório.

## Disclaimer sobre o uso de IA

Conforme solicitado nos roteiros, ferramentas de Inteligência Artificial foram utilizadas neste projeto. Foi utilizado o **Claude Sonnet 5**, modelo da Anthropic indicado para tarefas de revisão de código e produção de documentação.

O uso da IA ficou restrito à **revisão do código** e à **criação da documentação do repositório raiz**, especialmente este README. A IA não foi utilizada para substituir a implementação, a execução dos experimentos ou a elaboração das respostas dos roteiros. O conteúdo da tarefa foi revisado pelo autor antes de ser incorporado ao repositório.
