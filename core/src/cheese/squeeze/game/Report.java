package cheese.squeeze.game;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;



public class Report {
	
	public static HashMap<Level,HashMap<GameState,Integer>> map = new HashMap<Level,HashMap<GameState,Integer>>();
	public static HashMap<Level,Integer> score = new HashMap<Level,Integer>();
	public static HashMap<Level,Integer> gameScore = new HashMap<Level,Integer>();
	
	public static void reportGameScore(Level level, int sc) {
		gameScore.put(level, sc);
	}
	
	public static void reportScore(Level level, int sc) {
			score.put(level,sc);
	}
	
	public static void report(Level level, GameState state) {
			if(!map.containsKey(level)) {
				HashMap<GameState,Integer> maps = new HashMap<GameState,Integer>();
				maps.put(state, 1);
				map.put(level, maps);
			}
			else if(!map.get(level).containsKey(state)) {
				map.get(level).put(state, 1);
			}
			else {
				int val = map.get(level).get(state);
				map.get(level).put(state, val+1);
			}
	}
	
	public static String str() {
		String res = "";
		for(Entry<Level,HashMap<GameState,Integer>> e : map.entrySet()) {
			res += "------------------------------ \n";
			res += e.getKey().toString() + " \n";
			for(Entry<GameState,Integer> e2 : e.getValue().entrySet()) {
				res += e2.getKey().toString() + ": " + e2.getValue().toString() + " times \n";
			}
		}
		return res;
	}
	
	public static int getScore(Level level) {
		return score.get(level);
	}
	
	public static Map<Level, Integer> getAllScores() {
		return score;
	}
	
	public static void clear() {
		map = new HashMap<Level,HashMap<GameState,Integer>>();
		score = new HashMap<Level,Integer>();
		gameScore = new HashMap<Level, Integer>();
	}

}
