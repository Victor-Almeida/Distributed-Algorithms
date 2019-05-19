import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteClient extends Remote {
	Boolean push(String topic, String text) throws RemoteException;
}