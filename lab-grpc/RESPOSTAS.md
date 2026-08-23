### Perguntas - Parte A

1. O endereço do servidor (localhost, IP, grupo multicast) está escrito diretamente no código do cliente? Isso favorece ou prejudica a transparência de localização?
2. Para “perguntar uma coisa” ao servidor, o cliente precisa montar uma string de texto manualmente (e o servidor precisa interpretá-la/fazer parsing)? Isso é meio-termo, presença ou ausência de transparência de acesso?
3. O que aconteceria com o cliente se o servidor mudasse de máquina amanhã? Alguma dessas quatro soluções sobreviveria a essa mudança sem alterar o código-fonte do cliente?

### Respostas - Parte A

1. Sim, para todos menos multicast, no código do cliente é identificado e isso atrapalha a transparência, pois congela a informação no código fonte. Para o multicast, o endereço não é uma máquina, portanto a informação não está travada no código fonte.
2. Ausência de transparência de informação, todos fazem conversão durante a execução.
3. Somente multicast por citar um endereço de um grupo e não de uma máquina específica.

### Perguntas - Parte B

1. No laboratório anterior, cada um de vocês definiu o formato das mensagens de forma implícita (comentários e convenção entre quem escreveu o cliente e o servidor). Aqui, o formato está no central.proto. Qual a vantagem de ter esse contrato explícito e gerado automaticamente em vez de combinado apenas “de boca”?
2. O mesmo arquivo central.proto gerou código para Java e para Python. O que isso sugere sobre como equipes que usam linguagens diferentes podem se comunicar em um sistema distribuído real?
3. Observe os arquivos gerados (target/generated-sources/.../CentralAtendimentoGrpc.java ou central_pb2_grpc.py). Sem entender todo o código gerado, você consegue identificar onde ficam definidas as operações ConsultarHorario e AcompanharAvisos? Cite o nome de pelo menos uma classe ou método gerado que você reconheceu.

### Respostas - Parte B

1. A vantagem é o erro no tempo de compilação que deixa de ser silencioso.
2. Sugere que linguagens deixam de ser ponto central da arquitetura para assumir um papel de decisões locais de cada equipe, optando por trade-offs vantajosos. O que deve ser compartilhado é o contrato.
3. py: self.ConsultarHorario = channel.unary_unary(...) && self.AcompanharAvisos = channel.unary_stream(...); java: CentralAtendimentoServicer (linha 49) é o lado do servidor: traz ConsultarHorario e AcompanharAvisos (métodos vazios), é disso que o servidor python herda.

### Perguntas - Parte C

1. No cliente, a linha stub.consultarHorario(pergunta) (Java) ou stub.ConsultarHorario(...) (Python) parece uma chamada de método comum. Cite, em alto nível, pelo menos três coisas que acontecem “por baixo dos panos” entre essa chamada e o return da função no servidor.
2. Compare esta implementação com o ClienteTCP do roteiro anterior. Onde estava, no TCP, o equivalente a “montar a mensagem” e “interpretar a resposta”? Quem faz esse trabalho agora, no gRPC?
3. O que aconteceria se você chamasse stub.consultarHorario(pergunta) com o servidor desligado? Teste e descreva o comportamento observado (em qualquer uma das duas linguagens).

### Respostas - Parte C

1. Check timestamp, ?
2. TCP o código montava, grpC vem do contrato
3. Status UNAVAIABLE 


### Perguntas - Parte D

1. No laboratório anterior, o Multicast usava um endereço de grupo (230.0.0.1) para alcançar vários clientes com um único envio; aqui, o streaming gRPC é um servidor conversando com um cliente por vez, só que ao longo de uma conexão só. Se você quisesse que vários clientes gRPC recebessem os mesmos avisos ao mesmo tempo, o que precisaria mudar na implementação do servidor?
2. Compare o método de streaming em Java (StreamObserver, chamando onNext() repetidamente) com o de Python (uma função geradora usando yield). Os dois alcançam o mesmo resultado - qual das duas abordagens você achou mais natural de entender? Justifique.
3. No método acompanharAvisos/AcompanharAvisos, o que aconteceria se o cliente fechasse a conexão (por exemplo, fechando o terminal) no meio do envio dos 5 avisos? Pesquise ou teste o comportamento e descreva o que observou.

### Respostas - Parte D

1. Precisaria de manter a lista de inscritos e separar os avisos, dessa forma seria possível múltiplos alunos inscritos e que seus registros não sejam perdidos.
2. Java pela familiaridade com a sintaxe.
3. O servidor para de gerar avisos e não declara erros.
