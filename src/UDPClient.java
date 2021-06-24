import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class UDPClient {
    private static final int bufferSize=32;
    private static final int Timeout=5000;
    private static final int MAXNUM=5;
    public static void main(String[] args)throws IOException{
        if (args.length != 2)
            throw new IllegalArgumentException();
        String data_send = "Hello Server";
        String server_ip = args[0];
        int port = Integer.parseInt(args[1]);
        byte[] buffer = new byte[bufferSize];
        InetAddress loc = InetAddress.getByName(server_ip);
        DatagramSocket ds = new DatagramSocket(69);
        DatagramPacket pkt_send = new DatagramPacket(data_send.getBytes(),data_send.length(), loc,port);
        DatagramPacket pkt_rece = new DatagramPacket(buffer,bufferSize);
        ds.setSoTimeout(Timeout);
        int Num_try = 0;
        while(Num_try<MAXNUM){
            ds.send(pkt_send);
            try{
                ds.receive(pkt_rece);
                if(!pkt_rece.getAddress().equals(loc)){
                    throw new IOException("收到未知來源的封包");
                }
                System.out.println("Server says "+new String(pkt_rece.getData(),0,pkt_rece.getLength()));
                break;
            }catch (InterruptedIOException e) {
                Num_try += 1;
                System.out.println("Client取得來自Server的封包");
                System.out.println("Time out ! 將重新傳送 次數為 : " + Num_try);
            }
        }
        if(Num_try>=MAXNUM){
            System.out.println("未收到來自Server的回覆");
        }
        ds.close();
    }

}