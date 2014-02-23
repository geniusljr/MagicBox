package ui;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class StopPane extends JPanel{

	private static final long serialVersionUID = -3679825119712547434L;

	public StopPane() {
		this.setBounds(0, 0, MainWindow.LEVEL * BoxNode.SIZE, MainWindow.LEVEL * BoxNode.SIZE);
	}
	
	public void resize() {
		this.setBounds(0, 0, MainWindow.LEVEL * BoxNode.SIZE, MainWindow.LEVEL * BoxNode.SIZE);
		repaint();
	}
	
	public void paint(Graphics g) {
		g.setColor(MainBoard.fontColor);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
		g.drawString("Magic Board", MainWindow.LEVEL * BoxNode.SIZE / 2 - 80, 150);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		g.drawString("Ma Xiaoxiao", MainWindow.LEVEL * BoxNode.SIZE / 2 - 20, 220);
	}
}
