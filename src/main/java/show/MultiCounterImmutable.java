package show;


/**
 * Thread safe without locks! => Immutable Class
 */
public class MultiCounterImmutable {

	private final int first;
	private final int second;
	private final int third;
	private final int fourth;

	public MultiCounterImmutable(int first, int second, int third, int fourth) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
	}

	/**
	 * Increments all vars by 1 and return new instance.
	 */
	public MultiCounterImmutable incrementAll() {
		return new MultiCounterImmutable(first + 1, second + 1, third + 1,
				fourth + 1);
	}

	public int getFirst() {
		return first;
	}

	public int getSecond() {
		return second;
	}

	public int getThird() {
		return third;
	}

	public int getFourth() {
		return fourth;
	}
}
