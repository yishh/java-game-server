package org.menacheri.app.impl;

import java.util.HashSet;
import java.util.Set;

import org.menacheri.app.IPlayer;
import org.menacheri.app.IPlayerSession;


public class Player implements IPlayer
{
	/**
	 * This variable could be used as a database key.
	 */
	private long id;

	/**
	 * The unique identifier of the Player.This can be same as email id.
	 */
	private String uniqueKey;
	/**
	 * The name of the gamer.
	 */
	private String name;
	/**
	 * Email id of the gamer.
	 */
	private String emailId;

	/**
	 * One player can be connected to multiple games at the same time. Each
	 * session in this set defines a connection to a game. TODO, each player
	 * should not have multiple sessions to the same game.
	 */
	private Set<IPlayerSession> playerSessions;
	
	public Player()
	{
		playerSessions = new HashSet<IPlayerSession>();
	}
	
	public Player(long id, String uniqueKey, String name, String emailId)
	{
		super();
		this.id = id;
		this.uniqueKey = uniqueKey;
		this.name = name;
		this.emailId = emailId;
		playerSessions = new HashSet<IPlayerSession>();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((uniqueKey == null) ? 0 : uniqueKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		if (uniqueKey == null)
		{
			if (other.uniqueKey != null)
				return false;
		}
		else if (!uniqueKey.equals(other.uniqueKey))
			return false;
		return true;
	}
	
	
	public long getId()
	{
		return id;
	}

	
	public void setId(long id)
	{
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see org.menacheri.app.impl.IGamer#getUniqueKey()
	 */
	public String getUniqueKey()
	{
		return uniqueKey;
	}

	/* (non-Javadoc)
	 * @see org.menacheri.app.impl.IGamer#setUniqueKey(java.lang.String)
	 */
	public void setUniqueKey(String uniqueKey)
	{
		this.uniqueKey = uniqueKey;
	}

	/* (non-Javadoc)
	 * @see org.menacheri.app.impl.IGamer#getName()
	 */
	public String getName()
	{
		return name;
	}

	/* (non-Javadoc)
	 * @see org.menacheri.app.impl.IGamer#setName(java.lang.String)
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.menacheri.app.impl.IGamer#getEmailId()
	 */
	public String getEmailId()
	{
		return emailId;
	}

	/* (non-Javadoc)
	 * @see org.menacheri.app.impl.IGamer#setEmailId(java.lang.String)
	 */
	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	@Override
	public synchronized boolean addSession(IPlayerSession session)
	{
		return playerSessions.add(session);
	}

	@Override
	public synchronized boolean removeSession(IPlayerSession session)
	{
		boolean remove = playerSessions.remove(session);
		if(playerSessions.size() == 0){
			logout(session);
		}
		return remove;
	}
	
	@Override
	public synchronized void logout(IPlayerSession session)
	{
		session.close();
		 if(null != playerSessions)
		 {
			 playerSessions.remove(session);
		 }
	}

	public Set<IPlayerSession> getPlayerSessions()
	{
		return playerSessions;
	}

	public void setPlayerSessions(Set<IPlayerSession> playerSessions)
	{
		this.playerSessions = playerSessions;
	}

}
