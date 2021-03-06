package org.menacheri.app;

import org.menacheri.service.ITaskManagerService;

/**
 * Represents a task that can be executed in the game system. Any class that
 * implements this interface and submits instances to the
 * {@link ITaskManagerService} instance will be managed by the container. It
 * will automatically store the task such that restarts of the server do not
 * stop recurring tasks from stopping. In future, this may also be used for
 * sending tasks from one server node to another during node shutdown etc.
 * 
 * @author Abraham Menacherry
 * 
 */
public interface ITask
{
	/**
	 * The method that will be executed
	 */
	public void run();

	/**
	 * @return returns the unique task id of the task. For future
	 *         implementations, this value has to be unique across multiple
	 *         server nodes.
	 */
	public String getTaskId();

	/**
	 * @param taskId
	 *            Set the unique task id.
	 */
	public void setTaskId(String taskId);
}
