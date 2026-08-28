import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class ClienteMulticast {
    static final int OFFSET = 89;

    // Precisa ser a MESMA interface usada pelo servidor para enviar.
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

    public static void main(String[] args) throws IOException {
        String grupoMulticast = "230.0.0.1";
        int porta = 4446 + OFFSET;
        try (MulticastSocket socket = new MulticastSocket(porta)) {
            InetAddress grupo = InetAddress.getByName(grupoMulticast);
            InetSocketAddress endpointGrupo = new InetSocketAddress(grupo, porta);
            NetworkInterface interfaceRede = interfaceMulticast();
            socket.joinGroup(endpointGrupo, interfaceRede);
            System.out.println("[Multicast] Inscrito no grupo " + grupoMulticast
                    + ":" + porta + " via " + interfaceRede.getName()
                    + ". Aguardando avisos...");
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);
                socket.receive(pacote);
                String mensagem = new String(pacote.getData(), 0, pacote.getLength());
                System.out.println("[Multicast] Recebido: " + mensagem);
            }
        }
    }
}
