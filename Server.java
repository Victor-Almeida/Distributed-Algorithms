import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
	
public class Server implements Newsletter{
	ArrayList<Reader> readers;
	
    public Server() {
    	readers = new ArrayList<>();
    }
	
	public Boolean publish(String name, String topic, String text){
		for(int i = 0; i < readers.size(); i++){
			if(readers.get(i).getName().equals(name) == false && readers.get(i).isInterested(topic)){
				try{
					Registry registry = LocateRegistry.getRegistry("127.0.0.1");
					RemoteClient clientStub = (RemoteClient) registry.lookup(readers.get(i).getName());
					Boolean response = clientStub.push(topic, text);

					if(response){
						System.out.printf("'%s' just published on '%s'\n", name, topic);
					} else {
						System.out.printf("'%s' failed to publish on '%s'\n", name, topic);
					}
				} catch(Exception e){
					System.out.printf("An exception has happened when '%s' tried to publish on '%s'!", name, topic);
					return false;
				}
			}
		}

		return true;
	}

	public Boolean subscribe(String name){
		Reader reader = new Reader(name);
		readers.add(reader);
		System.out.printf("%s subscribed to the newsletter!\n", name);
		return true;
	}

	public Boolean addInterest(String name, String topic){
		for(int i = 0; i < readers.size(); i++){
			if(readers.get(i).getName().equals(name)){
				Boolean result = readers.get(i).addInterest(topic);

				if(result){
					System.out.printf("%s added '%s' to their interests' list!\n", name, topic);
				}
				
				return result;
			}
		}

		return false;
	}

	public Boolean removeInterest(String name, String topic){
		for(int i = 0; i < readers.size(); i++){
			if(readers.get(i).getName().equals(name)){
				Boolean result = readers.get(i).removeInterest(topic);

				if(result){
					System.out.printf("%s removed '%s' from their interests' list!\n", name, topic);
				}	
				return result;
			}
		}

		return false;
	}

    public static void main(String args[]) {
	
		try {
		    Server obj = new Server();
		    Newsletter stub = (Newsletter) UnicastRemoteObject.exportObject(obj, 0);

		    // Bind the remote object's stub in the registry
		    Registry registry = LocateRegistry.getRegistry("127.0.0.1");
		    registry.bind("Server", stub);
		} catch (Exception e) {
		    System.err.println("Server exception: " + e.toString());
		    e.printStackTrace();
		}
    }
}
