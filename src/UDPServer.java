import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    private static final int bufferSize=32;
    public static void main(String[] args)throws IOException{
        if (args.length != 1)
            throw new IllegalArgumentException();
        String data_send ="Hello Client";
        byte[] buffer=new byte[bufferSize];
        int port = Integer.parseInt(args[0]);
        DatagramSocket ds = new DatagramSocket(port);
        DatagramPacket pkt_rece = new DatagramPacket(buffer , bufferSize);
        boolean flag=true;
        while(flag){
            ds.receive(pkt_rece);
            System.out.println("Server 取得來自Client封包");
            System.out.println("Client says "+new String(pkt_rece.getData(),0, pkt_rece.getLength()));
            System.out.println(String.format("Client ip:%s port:%s",
                    pkt_rece.getAddress(),
                    pkt_rece.getPort()));
            DatagramPacket pkt_send=new DatagramPacket(data_send.getBytes(),
                    data_send.length(),
                    pkt_rece.getAddress(),
                    pkt_rece.getPort());
            ds.send(pkt_send);
            pkt_rece.setLength(bufferSize);

        }
        ds.close();

    }
}
