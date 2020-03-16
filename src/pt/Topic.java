package pt;
import java.util.*;
public class Topic {
	public String name;
	public float cost;
	public float collection;
	
	public int totalMessage;
	
	public Map<String,Integer> messageCount;
	
	public Topic(String name, float cost) {
		this.name = name;
		this.cost = cost;
		collection =0f;
		totalMessage=0;
		messageCount = new HashMap<String,Integer>();
		
	}
}
