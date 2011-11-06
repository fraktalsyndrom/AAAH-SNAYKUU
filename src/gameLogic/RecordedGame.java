package gameLogic;

import java.util.LinkedList;
import java.util.Set;

public class RecordedGame implements Game
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
	
	public int getCurrentReplayFrame()
	{
		return currentFrameIndex;
	}
	
	public void setCurrentReplayFrame(int index)
	{
		currentFrameIndex = index;
	}
	
}
