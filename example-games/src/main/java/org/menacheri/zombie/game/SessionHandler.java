package org.menacheri.zombie.game;

import org.menacheri.app.IGameCommandInterpreter;
import org.menacheri.app.impl.InvalidCommandException;
import org.menacheri.communication.IMessageBuffer;
import org.menacheri.communication.NettyMessageBuffer;
import org.menacheri.event.Events;
import org.menacheri.event.IEvent;
import org.menacheri.event.impl.NettySessionEventHandler;
import org.menacheri.zombie.domain.Defender;
import org.menacheri.zombie.domain.IAM;
import org.menacheri.zombie.domain.Zombie;
import org.menacheri.zombie.domain.ZombieCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//public class SessionHandler extends AsyncSessionListener implements IGameCommandInterpreter
@SuppressWarnings("rawtypes")
public class SessionHandler extends NettySessionEventHandler implements IGameCommandInterpreter
{
	private static final Logger LOG = LoggerFactory.getLogger(SessionHandler.class);
	volatile int cmdCount;
	
	private Defender defender;
	private Zombie zombie;
	private IAM iam;
	
	public SessionHandler(Defender defender, Zombie zombie, IAM iam)
	{
		this.defender = defender;
		this.zombie = zombie;
		this.iam = iam;
	}
	
	public void onDataIn(IEvent event)
	{
		onMessage((IMessageBuffer)event.getSource());
	}
	
	/**
	 * This method gets called due to dynamic dispatch. When the event handler
	 * does the connection.send(), the message gets routed here asynchronously.
	 * 
	 * @param message
	 */
	public void onMessage(IMessageBuffer message)
	{
		try
		{
			interpretCommand(message);
		}
		catch (InvalidCommandException e)
		{
			e.printStackTrace();
			LOG.error("{}",e);
		}
	}
	
	@Override
	public void interpretCommand(Object command) throws InvalidCommandException
	{
		cmdCount++;
		//IGameEvent event = (IGameEvent) command;
		IMessageBuffer buf = (IMessageBuffer) command;
		int type = buf.readInt();
		int operation = buf.readInt();
		IAM iam = IAM.getWho(type);
		ZombieCommands cmd = ZombieCommands.getCommand(operation);
		switch (iam)
		{
		case ZOMBIE:
			switch (cmd)
			{
			case EAT_BRAINS:
				//LOG.trace("Interpreted command EAT_BRAINS");
				zombie.eatBrains();
				break;
			case SELECT_TEAM:
				LOG.trace("Interpreted command ZOMBIE SELECT_TEAM");
				selectTeam(iam);
				break;
			}
			break;
		case DEFENDER:
			switch (cmd)
			{
			case SHOT_GUN:
				//LOG.trace("Interpreted command SHOT_GUN");
				defender.shotgun();
				break;
			case SELECT_TEAM:
				LOG.trace("Interpreted command DEFENDER SELECT_TEAM");
				selectTeam(iam);
				break;
			}
			break;
			default:
				LOG.error("Received invalid command {}",cmd);
				throw new InvalidCommandException("Received invalid command" + cmd);
		}
		
		if((cmdCount % 10000) == 0)
		{
			NettyMessageBuffer buffer = new NettyMessageBuffer();
			System.out.println("Command No: " + cmdCount);
			buffer.writeInt(cmdCount);
//			IEvent tcpEvent = Events.dataOutTcpEvent(buffer);
//			getSession().onEvent(tcpEvent);
			IEvent udpEvent = Events.dataOutUdpEvent(buffer);
			getSession().onEvent(udpEvent);
		}
	}

	public void selectTeam(IAM iam)
	{
		this.iam = iam;
	}
	
	public Defender getDefender()
	{
		return defender;
	}

	public void setDefender(Defender defender)
	{
		this.defender = defender;
	}

	public Zombie getZombie()
	{
		return zombie;
	}

	public void setZombie(Zombie zombie)
	{
		this.zombie = zombie;
	}

	public IAM getIam()
	{
		return iam;
	}

	public void setIam(IAM iam)
	{
		this.iam = iam;
	}

}
