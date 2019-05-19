import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Newsletter extends Remote {
    Boolean addInterest(String name, String topic) throws RemoteException;
    Boolean removeInterest(String name, String topic) throws RemoteException;
    Boolean subscribe(String name) throws RemoteException;
    Boolean publish(String name, String topic, String text) throws RemoteException;
}
