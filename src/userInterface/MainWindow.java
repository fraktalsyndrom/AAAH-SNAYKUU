import javax.swing.*;

class MainWindow extends JFrame
{
	private GameBoard gameBoard;
	
	public MainWindow()
	{
		gameBoard = new GameBoard(400, 300);
		
		setLayoutManager(null);
		
		add(gameBoard);
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new MainWindow();
	}
}
