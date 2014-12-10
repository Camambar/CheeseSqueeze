package cheese.squeeze.tweenAccessors;

public interface ActionResolver {
	
	public void reportAnalytics(String action,String cat,long value);
	
	public boolean getSignedInGPGS();
	public void loginGPGS();
	public void submitScoreGPGS(int score);
	public void getLeaderboardGPGS();
	public void unlockAchievementGPGS(String achievementId);
	public void getAchievementsGPGS();


}
