### Perguntas - Parte A

1. O endereço do servidor (localhost, IP, grupo multicast) está escrito diretamente no código do cliente? Isso favorece ou prejudica a transparência de localização?
2. Para “perguntar uma coisa” ao servidor, o cliente precisa montar uma string de texto manualmente (e o servidor precisa interpretá-la/fazer parsing)? Isso é meio-termo, presença ou ausência de transparência de acesso?
3. O que aconteceria com o cliente se o servidor mudasse de máquina amanhã? Alguma dessas quatro soluções sobreviveria a essa mudança sem alterar o código-fonte do cliente?

### Respostas - Parte A

1. Sim, para todos menos multicast, no código do cliente é identificado e isso atrapalha a transparência, pois congela a informação no código fonte. Para o multicast, o endereço não é uma máquina, portanto a informação não está travada no código fonte.
2. Ausência de transparência de informação, todos fazem conversão durante a execução.
3. Somente multicast por citar um endereço de um grupo e não de uma máquina específica.