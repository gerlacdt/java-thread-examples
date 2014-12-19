package show.model;

public class MultiCounter {
	
	private int first;
	private int second;
	private int third;
	private int fourth;
	
	/**
	 * Increments all vars by 1.
	 */
	public void incrementAll() {
		first++;
		second++;
		third++;
		fourth++;
	}
	
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public int getThird() {
		return third;
	}
	public void setThird(int third) {
		this.third = third;
	}
	public int getFourth() {
		return fourth;
	}
	public void setFourth(int fourth) {
		this.fourth = fourth;
	}
}
