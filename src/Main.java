
import gameLogic.Session;
import userInterface.SettingsWindow;
import userInterface.MainWindow;

class Main
{		
	public static void main(String[] args)
	{
		SettingsWindow settingsWindow = new SettingsWindow();
		
		while (settingsWindow.isVisible());
		
		Session session = initializeSession();
		
		runGame(session);
	}
	
	private static Session initializeSession()
	{
		return new Session(20, 40, 3, 10, 100);
	}
	
	
	private static void runGame(Session session)
	{
		MainWindow mainWindow = new MainWindow(session);
		
		while(true)
		{
			session.tick();
			mainWindow.repaint();
			
			try
			{
				Thread.currentThread().sleep(500);
			}
			catch (InterruptedException e)
			{
				break;
			}
			
		}
	}
}
