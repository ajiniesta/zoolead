package com.iniesta.zooleader;

import org.apache.curator.test.TestingServer;

/**
 * Hello world!
 *
 */
public class StartTestZKServer {
	public static void main(String[] args) throws Exception {
		TestingServer ts = new TestingServer(Integer.parseInt(args[0]));
		ts.start();
	}
}
