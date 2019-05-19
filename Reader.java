import java.util.ArrayList;

public class Reader {
	String name;
	ArrayList<String> interests;

	public Reader(String name){
		this.name = name;
		interests = new ArrayList<>();
	}

	public String getName(){
		return this.name;
	}

	public Boolean isInterested(String topic){
		for(int i = 0; i < interests.size(); i++){
			if(interests.get(i).equals(topic)){
				return true;
			}
		}

		return false;
	}

	public Boolean addInterest(String topic){
		interests.add(topic);
		return true;
	}

	public Boolean removeInterest(String topic){
		Boolean removed = false;
		ArrayList<String> new_interests = new ArrayList<>();
		for(int i = 0; i < interests.size(); i++){
			if(interests.get(i).equals(topic) == false){
				new_interests.add(interests.get(i));
			}else{
				removed = true;
			}
		}

		if(removed){
			interests = new_interests;
		}

		return removed;
	}
}