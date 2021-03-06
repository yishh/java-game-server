package org.menacheri.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jetlang.fibers.Fiber;
import org.jetlang.fibers.PoolFiberFactory;
import org.jetlang.fibers.ThreadFiber;

/**
 * This class acts as a factory for creating <a
 * href="http://code.google.com/p/jetlang/">jetlang</a> {@link Fiber}s.
 * 
 * @author Abraham Menacherry
 * 
 */
public class Fibers
{
	// TODO inject this from spring or AppContext
	private static final ExecutorService SERVICE = Executors.newSingleThreadExecutor();
	private static final PoolFiberFactory FACT = new PoolFiberFactory(SERVICE);

	/**
	 * Creates and starts a fiber and returns the created instance.
	 * @return The created fiber.
	 */
	public static Fiber pooledFiber()
	{
		Fiber fiber = FACT.create();
		fiber.start();
		return fiber;
	}
	
	public static Fiber threadFiber()
	{
		Fiber fiber = new ThreadFiber();
		fiber.start();
		return fiber;
	}
}
