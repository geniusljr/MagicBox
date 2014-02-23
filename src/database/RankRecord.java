package database;

public class RankRecord {
	public int rank;
	public String name;
	public String time;
	public int steps;
	
	public RankRecord(String _name, String _time, int _steps) {
		name = _name;
		time = _time;
		steps = _steps;
	}
}
