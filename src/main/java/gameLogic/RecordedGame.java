package gameLogic;

import java.io.*;
import java.util.LinkedList;
import java.util.Set;

public class RecordedGame implements Game, Serializable
{
	private Metadata metadata;
	private LinkedList<Frame> frames = new LinkedList<Frame>();
	private int currentFrameIndex = 0;
	
	public RecordedGame(Metadata metadata)
	{
		this.metadata = metadata;
	}
	
	public RecordedGame(Metadata metadata, Board startBoard, Set<Snake> snakes)
	{
		this.metadata = metadata;
		this.frames.addFirst(new Frame(startBoard, snakes));
	}
	
	public int getTurnCount()
	{
		return frames.size();
	}
	
	public void addFrame(Frame frame)
	{
		frames.addLast(frame);
	}
	
	public GameState getCurrentState()
	{
		Frame currentFrame = frames.get(currentFrameIndex);
		
		return new GameState(currentFrame.getBoard(), currentFrame.getSnakes(), metadata, ErrorState.NO_ERROR);
	}
	
	public Metadata getMetadata()
	{
		return metadata;
	}
	
	public GameResult getGameResult()
	{
		Frame currentFrame = frames.get(currentFrameIndex);
		return new GameResult(currentFrame.getSnakes(), metadata, this);
	}
	
	public int getCurrentReplayFrame()
	{
		return currentFrameIndex;
	}
	
	public void setCurrentReplayFrame(int index)
	{
		if (index < 0)
			index = 0;
		else if (index > getTurnCount() - 1)
			index = getTurnCount() - 1;
		
		currentFrameIndex = index;
	}
	
	public void saveToFile(File file) throws IOException
	{
		FileOutputStream fileStream = new FileOutputStream(file);
		ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
		objectStream.writeObject(this);
		objectStream.close();
	}
	
	public static RecordedGame loadFromFile(File file) throws IOException, ClassNotFoundException
	{
		FileInputStream fileStream = new FileInputStream(file);;
		ObjectInputStream objectStream = new ObjectInputStream(fileStream);
		RecordedGame recordedGame = (RecordedGame)objectStream.readObject();
		objectStream.close();
		return recordedGame;
	}
}
