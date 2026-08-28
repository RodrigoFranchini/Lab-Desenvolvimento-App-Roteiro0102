import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class ServidorMulticast {

    // TODO: substitua pelo seu OFFSET pessoal (ver seção 3.3)
    static final int OFFSET = 89;

    // O macOS não escolhe sozinho por onde mandar o multicast (dá NoRouteToHost),
    // então pegamos a primeira interface ativa que suporta multicast.
    static NetworkInterface interfaceMulticast() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            if (!ni.isUp() || !ni.supportsMulticast() || ni.isLoopback()) {
                continue;
            }
            // Precisa ter IPv4: interfaces só-IPv6 (utun, anpi...) falham no joinGroup.
            Enumeration<InetAddress> enderecos = ni.getInetAddresses();
            while (enderecos.hasMoreElements()) {
                if (enderecos.nextElement() instanceof Inet4Address) {
                    return ni;
                }
            }
        }
        throw new SocketException("Nenhuma interface de rede com suporte a multicast");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String grupoMulticast = "230.0.0.1";
        int porta = 4446 + OFFSET;
        InetAddress grupo = InetAddress.getByName(grupoMulticast);
        try (MulticastSocket socket = new MulticastSocket()) {
            socket.setNetworkInterface(interfaceMulticast());
            int contador = 1;
            System.out.println("[Multicast] Enviando avisos para o grupo "
                    + grupoMulticast + ":" + porta);
            while (contador <= 5) {
                String mensagem = "Aviso #" + contador + ": a aula começa em " + (5
                        - contador) + " minuto(s)!";
                byte[] dados = mensagem.getBytes();
                DatagramPacket pacote = new DatagramPacket(dados, dados.length,
                        grupo, porta);
                socket.send(pacote);
                System.out.println("[Multicast] Enviado: " + mensagem);
                contador++;
                Thread.sleep(2000);
            }
        }
    }
}
