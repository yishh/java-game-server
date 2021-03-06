package org.menacheri.event;

/**
 * Abstract event handler is a helper class which must be overriden by classes
 * which need to implement the IEventHandler interface. The
 * {@link #onEvent(IEvent)} method needs to be implemented by such classes.
 * 
 * @author Abraham Menacherry
 * 
 */
public abstract class AbstractEventHandler implements IEventHandler
{
	private final int EVENT_TYPE;
	
	public AbstractEventHandler(int eventType)
	{
		this.EVENT_TYPE = eventType;
	}
	
	@Override
	public int getEventType()
	{
		return EVENT_TYPE;
	}

}
