package cheese.squeeze.game;

public enum Achievement {
	
	FIRST_LEVEL_COMPLETE("id", "First level"),
	DREW_FIRST_LINE("id2", "First line");
	
	private String id;
	private String name;
	
	private Achievement(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return this.name();
	}

}
