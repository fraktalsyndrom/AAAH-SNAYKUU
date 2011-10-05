package gameLogic;

import java.util.LinkedList;

public class RecordedGame
{
	private Metadata metadata;
	private LinkedList<Frame> frames = new LinkedList<Frame>();
	
	public RecordedGame(Metadata metadata, Board startBoard)
	{
		this.metadata = metadata;
		this.frames.addFirst(new Frame(startBoard));
	}
	
	public int getTurnCount()
	{
		return frames.size();
	}
	
	public void addFrame(Frame frame)
	{
		frames.addLast(frame);
	}
}
