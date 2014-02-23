package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

//import database.*;

public class MainBoard extends JPanel implements Runnable{
	
	private static final long serialVersionUID = -7980689628180628761L;
	
	private int boxnum;
	private int borderlength;
	
	//���ִ�С
	static final int NUMBERSIZE = 40;
	
	//�������ִ�С��ʱ�䣬�Ʋ�����
	static final int FONTSIZE = 20;
	
	//��ť�������ַ���
	private int[] index;
	private BoxNode[] boxnodes;
	
	//�Ʋ���
	private int count;
	
	//��ʱ��
	public Timer timer;
	private final int countTime = 1000;
	
	//�ж��Ƿ�Ӯ
	public Timer winTimer;
	private final int winTime = 1;
	
	private int hours;
	private int minutes;
	private int seconds;
	private String hourString;
	private String minuteString;
	private String secondString;
	
	//������ɫ
	public static final Color backColor = new Color(233, 237, 238);
	
	//������ɫ
	public static final Color boxColor = new Color(89, 128, 238);
	
	//������ɫ
	public static final Color fontColor = new Color(20, 66, 100);
	
	//�ж��Ƿ�����ͣ״̬
	private boolean pauseFlag = false;
		
	MouseListener mousepress = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			int tmpX = e.getX();
			int tmpY = e.getY();
			for (int i = 0; i < boxnum; i++) {
				BoxNode node = boxnodes[i];
				if (node.getBounds().contains(tmpX, tmpY)) {
					if (node.move()) {
						count++;
						repaint();
						return;
					}
				}
			}
		}
	};
	
	KeyListener keypress = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			int dir = 0;
			dir = e.getKeyCode();
			if (dir >= KeyEvent.VK_LEFT && dir <= KeyEvent.VK_DOWN) {
				for (int i = 0; i < boxnum; i++) {
					BoxNode node = boxnodes[i];
					if (node.movebyDir(dir)) {
						count++;
						repaint();
						return;
					}
				}
			}
		}
	};
	
	ActionListener taskPerformer_time = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			seconds++;
			if (seconds == 60) {
				seconds = 0;
				minutes++;
				if (minutes == 60) {
					minutes = 0;
					hours++;
				}
			}
			repaint();
		}
	};
	
	ActionListener winPerformer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (win()) {
				System.out.println("Win");
				winTimer.stop();
				timer.stop();
				removeKeyListener(keypress);
				removeMouseListener(mousepress);
				
				/*if (name == null || name.equals(""))
					return;
				try {
					DatabaseMain.insertRankRecord(new RankRecord(name, hourString+":"+minuteString+":"+secondString, count));
					showRank();
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}*/
			}
		}
	};

	public MainBoard() {
	}
	
	public boolean win() {
		for (int i = 0; i < boxnum; i++) {
			if (boxnodes[i].getValue() == 0)
				continue;
			int dstX = (int)(boxnodes[i].getValue()-1)%borderlength*BoxNode.SIZE+BoxNode.XOFFSET;
			int dstY = (int)(boxnodes[i].getValue()-1)/borderlength*BoxNode.SIZE+BoxNode.YOFFSET;
			if (boxnodes[i].getX() != dstX || boxnodes[i].getY() != dstY)
				return false;
		}
		return true;
	}
	
	public BoxNode getNode(int x, int y){
		for (int i = 0; i < boxnum; i++) {
			if (boxnodes[i].getX() == x && boxnodes[i].getY() == y)
				return boxnodes[i];
		}
		return null;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if (pauseFlag) {
			g.setColor(backColor);
			g.fillRect(BoxNode.XOFFSET, BoxNode.YOFFSET, borderlength*BoxNode.SIZE, borderlength*BoxNode.SIZE);
		}
		else {
			for (int i = 0; i < boxnum; i++) {
				if (!boxnodes[i].getName().equals(""))
					g.setColor(boxColor);
				g.fill3DRect(boxnodes[i].getX(), boxnodes[i].getY(), BoxNode.SIZE, BoxNode.SIZE, true);
				g.setFont(new Font("Comic Sans MS", Font.PLAIN, NUMBERSIZE));
				g.setColor(fontColor);
				g.drawString(boxnodes[i].getName(), boxnodes[i].getX()+NUMBERSIZE, boxnodes[i].getY()+BoxNode.SIZE-NUMBERSIZE);
			}
		}
		g.setColor(fontColor);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, FONTSIZE));
		g.drawString("Steps: "+count+" ", BoxNode.XOFFSET, BoxNode.YOFFSET*3/4);
		hourString = hours >= 10 ? new Integer(hours).toString() : "0"+ new Integer(hours).toString();
		minuteString = minutes >= 10 ? new Integer(minutes).toString() : "0"+ new Integer(minutes).toString();
		secondString = seconds >= 10 ? new Integer(seconds).toString() : "0"+ new Integer(seconds).toString();
		g.drawString(hourString+":"+minuteString+":"+secondString, BoxNode.XOFFSET+2*BoxNode.SIZE, BoxNode.YOFFSET*3/4);
	}
	
	private void getRandSeq() {
		Random rand = new Random();
		boolean[] flag = new boolean[boxnum];
		for (int i = 0; i < boxnum; i++) {
			flag[i] = false;
		}
		for (int i = 0; i < boxnum; i++) {
			index[i] = (rand.nextInt(1000))%boxnum;
		}
		for (int i = 0; i < boxnum; i++) {
			int j = i;
			while (flag[index[j]]) index[j] = (index[j]+1)%boxnum;
			flag[index[j]] = true;
		}
	}

	public void init() {
		
		boxnum = MainWindow.LEVEL * MainWindow.LEVEL;
		borderlength = (int)Math.sqrt((double)boxnum);
		index = new int[boxnum];
		boxnodes = new BoxNode[boxnum];
		
		this.grabFocus();
		this.removeMouseListener(mousepress);
		this.addMouseListener(mousepress);
		this.removeKeyListener(keypress);
		this.addKeyListener(keypress);
		count = 0;
		pauseFlag = false;
		getRandSeq();
		for (int i = 0; i < boxnum; i++) {
			boxnodes[i] = new BoxNode(index[i], (int)(i%borderlength)*BoxNode.SIZE, (int)(i/borderlength)*BoxNode.SIZE);
		}
		if (timer != null)
			timer.stop();
		timer = new Timer(countTime, taskPerformer_time);
		timer.start();
		if (winTimer != null)
			winTimer.stop();
		winTimer = new Timer(winTime, winPerformer);
		winTimer.start();
		
		hours = 0;
		minutes = 0;
		seconds = 0;
		repaint();
	}
	
	public void pause() {
		pauseFlag = true;
		timer.stop();
		this.removeKeyListener(keypress);
		this.removeMouseListener(mousepress);
		repaint();
		
	}
	
	public void cont() {
		pauseFlag = false;	
		timer.start();
		this.addKeyListener(keypress);
		this.addMouseListener(mousepress);
		repaint();
	}
	
	public void showStop() {
		Graphics g = this.getGraphics();
		g.setColor(MainBoard.fontColor);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
		g.drawString("Magic Board", MainWindow.LEVEL * BoxNode.SIZE / 2 - 80, 170);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		g.drawString("Ma Xiaoxiao", MainWindow.LEVEL * BoxNode.SIZE / 2 - 20, 230);
	}
	
	/*
	public void showRank() {
		Vector<RankRecord> records = new Vector<RankRecord>();
		try {
			Vector<String> columnTitle = new Vector<String>();
			columnTitle.add("����");
			columnTitle.add("����");
			columnTitle.add("ʱ��");
			columnTitle.add("�ƶ�����");
			records = DatabaseMain.getRank();
			Iterator<RankRecord> it = records.iterator();
			int index = 1;
			Vector<Vector<String>> recordsV = new Vector<Vector<String>>();
			while (it.hasNext()) {
				Vector<String> temp = new Vector<String>();
				RankRecord cur = it.next();
				temp.add(new Integer(index).toString());
				temp.add(cur.name);
				temp.add(cur.time);
				temp.add(new Integer(cur.steps).toString());
				recordsV.add(temp);
				index++;
			}
			JTable table = new JTable(recordsV, columnTitle);
			JFrame jf = new JFrame();
			jf.setTitle("���а�");
			jf.setBounds(560, 250, 240, 200);
			jf.add(new JScrollPane(table));
			jf.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}*/
	
	@Override
	public void run() {
	}
	
}
