/**
 * CSCI 205 - Software Design and Engineering
 * Names: Alex Wade, Anna Zeveney, JB Ring
 *
 * Team name: Chase's minions
 * Project: Solitaire
 */

package gui;

public class MyPosition {
	private double x;
	private double y;

	public MyPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
