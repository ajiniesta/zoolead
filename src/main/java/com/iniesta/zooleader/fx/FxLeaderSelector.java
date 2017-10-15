package com.iniesta.zooleader.fx;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import javafx.application.Platform;
import javafx.scene.shape.Circle;

public class FxLeaderSelector extends LeaderSelectorListenerAdapter implements Closeable {

	private final String name;
	private final LeaderSelector leaderSelector;
	private final AtomicInteger leaderCount = new AtomicInteger();
	private Circle circle;

	public FxLeaderSelector(CuratorFramework client, String path, String name, Circle circle) {
		this.name = name;
		this.circle = circle;
		this.leaderSelector = new LeaderSelector(client, path, this);
		leaderSelector.autoRequeue();
	}

	public void start() {
		leaderSelector.start();
	}

	public void close() throws IOException {
		leaderSelector.close();
	}

	public void takeLeadership(CuratorFramework client) throws Exception {
		final int waitSeconds = (int) (5 * Math.random()) + 1;

		System.out.println(name + " is now the leader. Waiting " + waitSeconds + " seconds...");
		System.out.println(name + " has been leader " + leaderCount.getAndIncrement() + " time(s) before.");
		try {
			update("green");
			Thread.sleep(TimeUnit.SECONDS.toMillis(waitSeconds));
			update("red");
		} catch (InterruptedException e) {
			System.err.println(name + " was interrupted.");
			Thread.currentThread().interrupt();
		} finally {
			System.out.println(name + " relinquishing leadership.\n");
		}

	}

	public void update(final String color) {
		Platform.runLater(new Runnable() {
			public void run() {
				circle.setStyle(String.format("-fx-fill:%s;", color));
			}
		});
	}
}
