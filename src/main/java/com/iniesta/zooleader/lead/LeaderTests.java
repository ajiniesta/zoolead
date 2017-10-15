package com.iniesta.zooleader.lead;

import org.apache.curator.framework.CuratorFramework;

import com.iniesta.zooleader.ZKHelper;

public class LeaderTests {

	public static void main(String[] args) {
		CuratorFramework client = ZKHelper.getClient("localhost:2181", "user", "pass");
		client.start();

		
		
		client.close();
	}
}
