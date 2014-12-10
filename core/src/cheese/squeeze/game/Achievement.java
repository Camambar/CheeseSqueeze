package cheese.squeeze.game;

public enum Achievement {
	
	FIRST_LEVEL_COMPLETE("CgkItIOKwawYEAIQAQ", "First level"),
	DREW_FIRST_LINE("CgkItIOKwawYEAIQAg", "First line"),
	ALL_LEVELS_COMPLETE("CgkItIOKwawYEAIQAw", "All levels complete"),
	TEN_LEVELS_COMPLETE("CgkItIOKwawYEAIQBA", "Ten levels complete"),
	FULL_LINES("CgkItIOKwawYEAIQBQ", "Board full with lines");
	
	private String id;
	private String name;
	
	private Achievement(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return this.name();
	}
	
	public String getId() {
		return this.id;
	}

}
