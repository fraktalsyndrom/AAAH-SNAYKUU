import gameLogic.Session;
import gameLogic.GameResult;
import userInterface.SettingsWindow;
import userInterface.MainWindow;
import userInterface.PostGameWindow;
import userInterface.GameEndType;

class Main
{
	
	public static void main(String[] args)
	{
		SettingsWindow settingsWindow = new SettingsWindow();
		
		Session session = prepareSession(settingsWindow);
		
		settingsWindow.dispose();
		
		GameEndType gameEndType;
		do
		{
			int gameSpeed = settingsWindow.getGameSpeed();
			gameEndType = runGame(session, gameSpeed);
			
			if (gameEndType == GameEndType.NEW_GAME)
				session = prepareSession(settingsWindow);
			else if (gameEndType == GameEndType.REMATCH)
				session = settingsWindow.generateSession();
		}
		while (gameEndType != GameEndType.EXIT);
	}
	
	private static Session prepareSession(SettingsWindow settingsWindow)
	{
		try
		{
			settingsWindow.putThisDamnWindowInMyFace();
			
			while (!settingsWindow.isDone());
			
			return settingsWindow.generateSession();
		}
		catch (Exception e)
		{
			javax.swing.JOptionPane.showMessageDialog(settingsWindow, e);
			return prepareSession(settingsWindow);
		}
	}

	
	private static GameEndType runGame(Session session, int gameSpeed)
	{
		MainWindow mainWindow = new MainWindow(session, 12);
		
		while (!session.hasEnded())
		{
			session.tick();
			mainWindow.repaint();
			
			try
			{
				Thread.currentThread().sleep(gameSpeed);
			}
			catch (InterruptedException e)
			{
				break;
			}
		}
		
		PostGameWindow postGameWindow = new PostGameWindow(session.getGameResult());
		GameEndType gameEndType = postGameWindow.getGameEndType();
		mainWindow.dispose();
		
		return gameEndType;
	}
}
