### Perguntas — Parte A

1. O que acontece se você iniciar o **cliente** antes do **servidor**? Por que isso
ocorre, considerando o funcionamento do TCP?
2. O TCP garante que as mensagens cheguem **na ordem** em que foram enviadas. Qual
mecanismo do protocolo é responsável por isso?
3. Na sua implementação, o que aconteceria se dois clientes tentassem se conectar
ao mesmo tempo? O código atual suporta isso? Justifique observando o código do
servidor.

### Respostas - Parte A

1. A conexão falha pela exigência de um servidor em escuta.
2. O sequenciamento do número dos segmentos, permite que mesmo foram de ordem (pela forma de transmissão da rede IP) respeite a ordem exata da trasnmissão. 
3. Não aceita, pois só uma vez no código o servidor aceita uma conexão, ou seja, o primeiro a conectar fecha o servidor.

### Perguntas - Parte B

1. No passo 2 da tarefa, o que aconteceu quando você enviou uma mensagem com o
servidor desligado? Compare com o que aconteceria em TCP e explique a diferença
observada, relacionando com o conceito de "sem conexão".
2. Cite **dois exemplos de aplicações reais** que usam UDP e explique, para cada
uma, por que a confiabilidade do TCP não é essencial (ou até atrapalharia).
3. No código, o servidor UDP não mantém nenhum registro de "quem está conectado".
Isso seria possível de implementar? O que mudaria na arquitetura da aplicação?

### Respostas - Parte B

1. O cliente continuou mandando mensagem mesmo com o servidor desligado. A diferença observada em relação ao TCP é a não necessidade de conexão no protocolo UDP.
2. Chamada de vídeo e jogos online. O protocolo UDP é essencial, pois garante a continuidade da entrega dessa maneira travas e perca de pacotes não travam o sistema por completo.
3. É possível desde que seja criado um timer que descarta quem parou de responder. O cliente seria obrigado a mandar sinais de vida para manter as conexões.

### Perguntas - Parte C

1. Qual é a diferença fundamental entre enviar a mesma mensagem para 3 clientes
usando **unicast repetido 3 vezes** e enviar **uma única vez** via multicast? Pense
em termos de tráfego de rede.
2. O que é o **TTL** (time-to-live) configurado no socket multicast e por que ele é
importante para controlar o alcance dos pacotes na rede?
3. Se um dos clientes ficar temporariamente offline e voltar depois, ele recebe os
avisos que perdeu? Por quê? Relacione com a arquitetura de comunicação em grupo.


### Respostas - Parte C

1. A principal diferença é o custo o multicast permite o envio de somente uma mensagem e a duplicação é feita pela rede.
2. É o contador que quando == 0 o pacote é descartado, funciona para limitar ou definir o número para que o pacote não se espalhe por toda a rede.
3. Não recebe o multicast funciona sem estado e sem histórico. Caso o cliente volta ele recebe os avisos enviados dali em diante.

### 7.6 Perguntas — Parte D
1. O WebSocket começa com uma requisição HTTP contendo o cabeçalho `Upgrade:
websocket`. O que exatamente "muda" na conexão depois que esse handshake é
concluído?
2. Compare o mural via WebSocket (Parte D) com o aviso via Multicast (Parte C).
Ambos entregam uma mensagem a vários destinatários — qual a diferença na forma como
cada um descobre e alcança os destinatários?
3. Por que o WebSocket é mais adequado do que TCP "cru" (como o da Parte A) para
este cenário de mural em tempo real, mesmo os dois sendo, no fundo, conexões TCP
contínuas?

### Respostas - Parte D

1. É a mudança de protocolo TCP para WebSocket, acontece para permitir o handshake e desfaz para abrir canal para frames WebSocket.
2. A principal diferença acontece na lista de destinatários mantida pelo WebSocket, até pelo output: Clientes Conextados X
3. Por entregar mensagens a clientes simultâneos, o que precisaria ser feito a mão no código ServidorTCP.