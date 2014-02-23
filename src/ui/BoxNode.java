package ui;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import main.MagicBoard;

public class BoxNode {
	
	public static final int SIZE = 100;
	
	//Î»ÖÃÆ«ÒÆ
	public static final int XOFFSET = 30;
	public static final int YOFFSET = 50;
	
	private int x;
	private int y;
	
	private int value;
	private String name;
	
	public BoxNode(int _value, int _x, int _y) {
		value = _value;
		x = _x + XOFFSET;
		y = _y + YOFFSET;
		name = value == 0 ? "" : (new Integer(value)).toString();
	}
	
	public BoxNode() {
		// TODO Auto-generated constructor stub
	}

	public int getValue() {
		return value;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getName() {
		return name;
	}
	public Rectangle getBounds() {
		return new Rectangle(x, y, SIZE, SIZE);
	}
	
	private boolean swap(BoxNode src, BoxNode dst) {
		if (src == null || dst == null) 
			return false;
		if (!dst.getName().equals(""))
			return false;
		int tmpX = src.getX();
		int tmpY = src.getY();
		src.x = dst.getX();
		src.y = dst.getY();
		dst.x = tmpX;
		dst.y = tmpY;
		return true;
	}
	
	public boolean move() {
		BoxNode node1 = MagicBoard.mv.getMainBoard().getNode(x-SIZE, y);
		BoxNode node2 = MagicBoard.mv.getMainBoard().getNode(x+SIZE, y);
		BoxNode node3 = MagicBoard.mv.getMainBoard().getNode(x, y-SIZE);
		BoxNode node4 = MagicBoard.mv.getMainBoard().getNode(x, y+SIZE);
		if (swap(this, node1)) return true;
		if (swap(this, node2)) return true;
		if (swap(this, node3)) return true;
		if (swap(this, node4)) return true;
		return false;
	}
	
	public boolean movebyDir(int dir) {
		BoxNode node = new BoxNode();
		switch (dir) {
			case KeyEvent.VK_UP:
				node = MagicBoard.mv.getMainBoard().getNode(x, y-SIZE);
				if (swap(this, node)) return true;						
				break;
			case KeyEvent.VK_DOWN:
				node = MagicBoard.mv.getMainBoard().getNode(x, y+SIZE);
				if (swap(this, node)) return true;						
				break;
			case KeyEvent.VK_LEFT:
				node = MagicBoard.mv.getMainBoard().getNode(x-SIZE, y);
				if (swap(this, node)) return true;						
				break;
			case KeyEvent.VK_RIGHT:
				node = MagicBoard.mv.getMainBoard().getNode(x+SIZE, y);
				if (swap(this, node)) return true;						
				break;
		}
		return false;
	}
	
}
