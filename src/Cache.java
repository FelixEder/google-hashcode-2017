import java.util.ArrayList;

public class Cache {
	
	// Every cache has the same capacity
	private static int capacity;
	
	// Each cache has a unique id
	private int id;
	
	private int usedStorage = 0;
	private int freeStorage;
	
	private ArrayList<Video> videos = new ArrayList<Video>();
	
	public Cache(int id, int cap){
		this.id = id;
		capacity = cap;
		
		// freeStorage is initially the capacity
		freeStorage = cap;
		
	}
	
	
	public boolean canUploadVideo(Video video){
		
		if (video.getSize() <= freeStorage){
			
			return true;
		}
		else
			return false;
		
	}
	
	
	public void uploadVideo(Video video){
		
		if (canUploadVideo(video)){
			
			videos.add(video);
			
			updateStorage(video);
		}
	}
	

	
	private void updateStorage(Video video){
		
		
		usedStorage += video.getSize();
		freeStorage = capacity-usedStorage;
		
		
	}
	

	
	
	// Setters
	
	public static void setCapacity(int capacity){
		Cache.capacity = capacity;
		
	}
	
	
	// Getters
	public int getId(){
		return id;
	}
	
	public ArrayList<Video> getVideoList(){
		
		return videos;
	}
	
	public int getUsedStorage(){
		return usedStorage;
	}
	
	public int getFreeStorage(){
		return freeStorage;
	}
	
	public boolean empty(){
		return usedStorage == 0;
	}
	
	public static int getCapacity(){
		return capacity;
		
	}
}
