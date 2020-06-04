package gameLogic;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

/**
 * This class represents the entire game board through a 2D-array of Square objects.
 * Each Square, in turn, contains GameObjects, such as fruits, walls, or other snakes.
 * 
 * @author 	Sixten Hilborn
 * @author	Arian Jafari
 * @see		Square
 */

public class Board implements Serializable
{
	private Square[][] board;
	private int cols, rows;
	
	Board(int width, int height)
	{
		if (width < 1 || height < 1)
			throw new IllegalArgumentException("Board size must be greater than 0");
		board = new Square[width][height];
		cols = width;
		rows = height;
		for (int x = 0; x < width; ++x)
			for (int y = 0; y < height; ++y)
				board[x][y] = new Square();
	}
	
	/**
	 * Copy constructor.
	 */
	Board(Board other)
	{
		this.board = new Square[other.getWidth()][other.getHeight()];
		for (int x = 0; x < getWidth(); ++x)
			for (int y = 0; y < getHeight(); ++y)
				board[x][y] = new Square(other.board[x][y]);
	}
	
	/**
	 * Gets the width of the board (the 2D-array).
	 * 
	 * @return	The width of the board.
	 */
	public int getWidth()
	{
		return board.length;
	}
	
	/**
	 * Gets the height of the board (the 2D-array).
	 * 
	 * @return	The height of the board.
	 */
	public int getHeight()
	{
		return board[0].length;
	}
	
	/**
	 * Returns whether or not the board contains any game object at the given position.
	 * Doesn't perform any checks on what type of object it is, like if it is lethal or not.
	 * 
	 * @param	p	The position we want to check for game objects.
	 * @return	Whether or not the board contains a game object at the given position.
	 */
	public boolean hasGameObject(Position p)
	{
		return (!getSquare(p).isEmpty());
	}
	
	/**
	 * Returns whether or not the board contains a fruit at the given position.
	 * 
	 * @param	p	The position we want to check for fruit.
	 * @return	Whether or not the board contains a fruit at the given position.
	 */
	public boolean hasFruit(Position p)
	{
		return (getSquare(p).hasFruit());
	}
	
	/**
	 * Returns whether or not the board contains a wall at the given position.
	 * 
	 * @param	p	The position we want to check for walls.
	 * @return	Whether or not the board contains a wall at the given position.
	 */
	public boolean hasWall(Position p)
	{
		return (getSquare(p).hasWall());
	}
	
	/**
	 * Returns whether or not the board contains a snake at the given position.
	 * 
	 * @param	p	The position we want to check for snakes.
	 * @return	Whether or not the board contains a snake at the given position.
	 * @see		Square
	 */
	public boolean hasSnake(Position p)
	{
		return (getSquare(p).hasSnake());
	}
	
	/**
	 * Returns whether or not the board contains a lethal game object at the given position.
	 * 
	 * @param	p	The position we want to check for lethal objects.
	 * @return	Whether or not the board contains a lethal game object at the given position.
	 * @see		Square
	 */
	public boolean isLethal(Position p)
	{
		return (getSquare(p).isLethal());
	}
	
	/**
	 * Gets a Square at 
	 * 
	 * @param	p	The position in the board where we want to get the Square from.
	 * @return	The Square at the specified position.
	 * @see 		Square
	 */
	public Square getSquare(Position p)
	{
		return board[p.getX()][p.getY()];
	}
	
	/**
	 * Calculates whether or not the board contains a lethal object within a given radius of
	 * a certain square. Works by using a depth-first search.
	 * 
	 * @param	pos		The position which we want to check.
	 * @param	range	The number of squares we wish to examine, e.g.
	 *					the radius of the area we want to check.
	 * @return	A boolean indicating whether or not there is a lethal object
	 *			within the given range of the specified position.
	 */
	public boolean hasLethalObjectWithinRange(Position pos, int range)
	{
		Set<Position> visited = new HashSet<Position>();
		depthFirstSearch(pos, visited, range);
		return (containsLethalObject(visited));
	}
	
	private void depthFirstSearch(Position from, Set<Position> visited, int range)
	{
		if (range-- < 0)
			return;
			
		visited.add(from);
		
		for (Position neighbour : from.getNeighbours())
		{
			if (!visited.contains(neighbour))
				depthFirstSearch(neighbour, visited, range);
		}
	}
	
	private boolean containsLethalObject(Set<Position> positions)
	{
		for (Position pos : positions)
		{
			int x = pos.getX();
			int y = pos.getY();
			if (board[x][y].hasSnake() || board[x][y].hasWall())
				return true;
		}
		return false;
	}
	
	void addGameObject(GameObjectType obj, Position p)
	{
		board[p.getX()][p.getY()].addGameObject(new GameObject(obj));
	}
	
	void addGameObject(GameObject obj, Position p)
	{
		board[p.getX()][p.getY()].addGameObject(obj);
	}
	
	void clearSquare(Position p)
	{
		board[p.getX()][p.getY()].clear();
	}
	
	void removeGameObject(GameObject obj, Position p)
	{
		board[p.getX()][p.getY()].removeGameObject(obj);
	}
	
	void removeFruit(Position p)
	{
		board[p.getX()][p.getY()].removeFruit();
	}

	//Roterar 90 grader medsols /bubblan
	Square[][] Rotate90DegreesRight(){
		Square[][] rotatedBoard = new Square[rows][cols];
		for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                rotatedBoard[rows - 1 - row][col] = board[col][row];
            }
		}
		return rotatedBoard;
	}

	//Roterar 180 grader /bubblan
	Square[][] Rotate180Degrees(){
		Square[][] rotatedBoard = new Square[cols][rows];
        for (int col = 0; col < cols; col++){
            for (int row = 0; row < rows; row++){
                rotatedBoard[cols - 1 - col][rows - 1 - row] = board[col][row];
            }
        }
		return rotatedBoard;
	}

	//Roterar 90 grader motsols /bubblan
	Square[][] Rotate90DegreesLeft(){
		Square[][] rotatedBoard = new Square[rows][cols];
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                rotatedBoard[row][cols - 1 - col] = board[col][row];
            }
        }
		return rotatedBoard;
	}

	//Används om spawnande frukt ska bete sig som old school Snake /bubblan
	boolean hasAnyFruit(){
		for (int col = 0; col < cols; col++){
			for (int row = 0; row < rows; row++){
				if (board[col][row].hasFruit())
					return true;
			}
		}
		return false;
	}
}
