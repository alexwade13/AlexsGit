package gui;

public class Postition {
	private final int[] coordinates = new int[2];

	public void setXY(int x, int y) {
		coordinates[0] = x;
		coordinates[1] = y;
	}

	public int getX() {
		return coordinates[0];
	}

	public int getY() {
		return coordinates[1];
	}
}
