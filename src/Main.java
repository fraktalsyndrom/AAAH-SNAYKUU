import gameLogic.Session;
import gameLogic.GameResult;
import userInterface.SettingsWindow;
import userInterface.MainWindow;
import userInterface.PostGameWindow;

class Main
{		
	public static void main(String[] args)
	{
		SettingsWindow settingsWindow = new SettingsWindow();
		
		Session session = prepareSession(settingsWindow);
		
		settingsWindow.dispose();
		
		runGame(session);
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

	
	private static void runGame(Session session)
	{
		MainWindow mainWindow = new MainWindow(session, 12);
		
		while (!session.hasEnded())
		{
			session.tick();
			mainWindow.repaint();
			
			try
			{
				Thread.currentThread().sleep(400);
			}
			catch (InterruptedException e)
			{
				break;
			}
		}
		new PostGameWindow(session.getGameResult());
	}
}
