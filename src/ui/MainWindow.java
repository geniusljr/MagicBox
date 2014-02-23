package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import main.MagicBoard;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = -4498191099906225953L;
	
	public static int LEVEL;
	
	//菜单栏
	private JMenuBar menuBar = new JMenuBar();
	private JMenu game = new JMenu("Game");
	private JMenuItem gameStart = new JMenuItem("New Game");
	private JMenuItem gamePause = new JMenuItem("Pause");
	private JMenuItem gameContinue = new JMenuItem("Continue");
	private JMenuItem gameStop = new JMenuItem("Stop");
	private JMenuItem gameExit = new JMenuItem("Exit");
	private JMenu rank = new JMenu("Rank");
	private JMenuItem showRank = new JMenuItem("RankBoard");
	private JMenu level = new JMenu("Level");
	private JMenuItem level1 = new JMenuItem("1.Average");
	private JMenuItem level2 = new JMenuItem("2.Hard");
	private JMenuItem level3 = new JMenuItem("3.Expert");
	
	//主画板，游戏界面
	private MainBoard mainBoard;
	private StopPane stopBoard;
	
	//游戏进程
	Thread thread;
	
	//游戏状态
	int status;
	final int GAME_RUN = 1;
	final int GAME_PAUSE = 2;
	final int GAME_STOP = 3;
	
	ActionListener startListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (status == GAME_STOP) {
				stopBoard.setVisible(false);
				mainBoard.setVisible(true);
				gameRun();
			}
			mainBoard.init();
			status = GAME_RUN;
			gamePause.setEnabled(true);
			gameStop.setEnabled(true);
			gameContinue.setEnabled(false);
			gameStart.setEnabled(false);
			level1.setEnabled(false);
			level2.setEnabled(false);
			level3.setEnabled(false);
		}
	};
	
	ActionListener pauseListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (status == GAME_RUN) {
				mainBoard.pause();
				status = GAME_PAUSE;
				gamePause.setEnabled(false);
				gameContinue.setEnabled(true);
			}
		}
	};
		
	ActionListener continueListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (status == GAME_PAUSE) {
				mainBoard.cont();
				status = GAME_RUN;
				gamePause.setEnabled(true);
				gameContinue.setEnabled(false);
			}
		}
	};
	
	ActionListener stopListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			stopBoard.setVisible(true);
			mainBoard.setVisible(false);
			status = GAME_STOP;
			gameStop.setEnabled(false);
			gamePause.setEnabled(false);
			gameContinue.setEnabled(false);
			gameStart.setEnabled(true);
			level1.setEnabled(true);
			level2.setEnabled(true);
			level3.setEnabled(true);
		}
	};
	
	ActionListener exitListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			MagicBoard.mv.dispose();
		}
	};
	
	ActionListener showRankListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			/*mainBoard.showRank();*/
		}
	};
	
	
	ActionListener changeLevel1 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			LEVEL = 3;
			if (status == GAME_STOP) {
				stopBoard.resize();
				resize();
			}
		}
	}; 
	
	ActionListener changeLevel2 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			LEVEL = 4;
			if (status == GAME_STOP) {
				stopBoard.resize();
				resize();
			}
		}
	}; 
	
	ActionListener changeLevel3 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			LEVEL = 5;
			if (status == GAME_STOP) {
				stopBoard.resize();
				resize();
			}
		}
	}; 
	
	public void resize() {
		this.setBounds(400, 100, BoxNode.SIZE*LEVEL + 70, BoxNode.SIZE * LEVEL + 120);
	}
	
	public void init() {
		
		LEVEL = 3;
		
		mainBoard = new MainBoard();
		stopBoard = new StopPane();
		
		this.setBackground(MainBoard.backColor);
		this.setBounds(400, 100, BoxNode.SIZE*LEVEL + 70, BoxNode.SIZE * LEVEL + 120);
		this.setResizable(false);
		
		gameStart.setMnemonic(KeyEvent.VK_N);
		gameStart.addActionListener(startListener);
		gameStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		game.add(gameStart);
		
		gamePause.setMnemonic(KeyEvent.VK_P);
		gamePause.addActionListener(pauseListener);
		gamePause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
		game.add(gamePause);
		
		gameContinue.setMnemonic(KeyEvent.VK_C);
		gameContinue.addActionListener(continueListener);
		gameContinue.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		game.add(gameContinue);
		
		gameStop.setMnemonic(KeyEvent.VK_S);
		gameStop.addActionListener(stopListener);
		gameStop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		game.add(gameStop);
		
		gameExit.setMnemonic(KeyEvent.VK_E);
		gameExit.addActionListener(exitListener);
		gameExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		game.add(gameExit);
		
		showRank.setMnemonic(KeyEvent.VK_R);
		showRank.addActionListener(showRankListener);
		showRank.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		rank.add(showRank);
		
		level1.setMnemonic(KeyEvent.VK_1);
		level1.addActionListener(changeLevel1);
		level.add(level1);
		
		level2.setMnemonic(KeyEvent.VK_2);
		level2.addActionListener(changeLevel2);
		level.add(level2);
		
		level3.setMnemonic(KeyEvent.VK_3);
		level3.addActionListener(changeLevel3);
		level.add(level3);
		
		menuBar.add(game);
		menuBar.add(rank);
		menuBar.add(level);
		
		this.setJMenuBar(menuBar);
		
		stopBoard.setVisible(true);
		this.add(stopBoard);
		
		mainBoard.setVisible(false);
		this.add(mainBoard);
		status = GAME_STOP;
		gamePause.setEnabled(false);
		gameContinue.setEnabled(false);
		gameStop.setEnabled(false);
	}
	
	public void gameRun() {
		status = GAME_RUN;
		thread = new Thread(mainBoard, "new");
		thread.start();
		mainBoard.setVisible(true);		
	}
	
	public MainWindow() throws ParseException {
		super("MagicBoard");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
	}	
	
	public MainBoard getMainBoard() {
		return mainBoard;
	}
	
	public Thread getThread() {
		return thread;
	}
}
