package cheese.squeeze.game;

public class ReportStatus {
	
	private Level l;
	private GameState g;
	
	public ReportStatus(GameState g,Level l) {
		this.g=g;
		this.l=l;
	}
	
	public ReportStatus(GameState g) {
		this.g=g;
		this.l=Level.NOTHING;
	}
	
	public void setState(GameState g) {
		this.g = g;
	}
	
	public Level getLevel() {
		return l;
	}
	
	public GameState getGameState() {
		return g;
	}
	
	@Override
	public boolean equals(Object o) {
		ReportStatus r = (ReportStatus) o;
		return l != null ? l.equals(r.getLevel()) && g.equals(r.getGameState())
				: g.equals(r.getGameState());
	}
	
	@Override
	public String toString() {
		return l != null ? g.toString() + " " +l.toString() : g.toString();
	}
	
}
