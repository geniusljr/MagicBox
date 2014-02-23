package main;

//import database.DatabaseMain;
import ui.MainWindow;

public class MagicBoard {
	public static MainWindow mv;
	public static void main(String[] args) throws Exception {
		//DatabaseMain.connect();
		mv = new MainWindow();
		mv.setVisible(true);
	}
}
