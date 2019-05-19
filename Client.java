import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client implements RemoteClient{
    public Client() {}

    public Boolean push(String topic, String text){
    	System.out.printf("New post on '%s' : \n\n%s\n\n", topic, text);
    	return true;
    }

    public static void main(String[] args) {
    	Scanner type = new Scanner(System.in);
		try {
			Client obj = new Client();
			RemoteClient serverStub = (RemoteClient) UnicastRemoteObject.exportObject(obj, 0);

		    Registry registry = LocateRegistry.getRegistry("127.0.0.1");
		    Newsletter stub = (Newsletter) registry.lookup("Server");

		    System.out.printf("Choose a name : ");
		    String name = type.nextLine();
		    registry.bind(name, serverStub);
		    if(stub.subscribe(name)){
		    	System.out.printf("\nSubscribed to Newsletter!\n\n");
		    } else {
		    	System.out.printf("Name already taken\n");
		    	return;
		    }

		    String topic, text;
		    int menu = 0;
		    Boolean response = false, quit = false;

		    while(!quit){
		    	System.out.printf("\n\n[1] add an interest \n[2] remove an interest \n[3] publish something \n[4] quit\n\nOption : ");
		    	menu = type.nextInt();
		    	type.nextLine();

		    	switch(menu){
		    		case 1:
		    			System.out.printf("\n\nWhich topic would you like to add? ");
		    			topic = type.nextLine();
		    			try{
		    				response = stub.addInterest(name, topic);
		    			}catch(Exception e){
		    				System.err.println("Client exception: " + e.toString());
		    				e.printStackTrace();
		    			}

		    			if(response){
		    				System.out.printf("\n\nSuccesfully added '%s' to your list!\n", topic);
		    			} else {
		    				System.out.printf("\n\nSomething went wrong...\n", topic);
		    			}

		    			break;

		    		case 2:
		    			System.out.printf("\n\nWhich topic would you like to remove? ");
		    			topic = type.nextLine();
		    			try{
		    				response = stub.removeInterest(name, topic);
		    			}catch(Exception e){
		    				System.err.println("Client exception: " + e.toString());
		    				e.printStackTrace();
		    			}

		    			if(response){
		    				System.out.printf("\n\nSuccesfully removed '%s' from your list!\n", topic);
		    			} else {
		    				System.out.printf("\n\nSeems like '%s' was not on your list...\n", topic);
		    			}

		    			break;

		    		case 3:
		    			System.out.printf("\n\nAbout which topic would you like to publish? ");
		    			topic = type.nextLine();
		    			System.out.printf("\nWhat would you like to publish? ");
		    			text = type.nextLine();
		    			try{
		    				response = stub.publish(name, topic, text);
		    			}catch(Exception e){
		    				System.err.println("Client exception: " + e.toString());
		    				e.printStackTrace();
		    			}

		    			if(response){
		    				System.out.printf("\n\nSuccesfully published at '%s'!\n", topic);
		    			} else {
		    				System.out.printf("\n\nSomething went wrong...\n", topic);
		    			}

		    			break;

		    		case 4:
		    			System.out.printf("\nBye!");
		    			quit = true;
		    			break;
		    		default:
		    			System.out.printf("\n\nInvalid option, try again!");
		    			break;
		    	}
		    }		    

		} catch (Exception e) {
		    System.err.println("Client exception: " + e.toString());
		    e.printStackTrace();
		}
    }
}
