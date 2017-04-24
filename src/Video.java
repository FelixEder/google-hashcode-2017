
public class Video {
	
	private int index;
	private int size;
	
	
	public Video(int index, int size) {
		this.size = size;
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
