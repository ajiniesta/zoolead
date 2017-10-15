package com.iniesta.zooleader;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;

public class SimpleZKTest {

	public static void main(String[] args) throws Exception {
		RetryPolicy retryPolicy = new RetryForever(1000);
		CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
		client.start();
		
		if(client.checkExists().forPath("/test")==null) {
			client.create().forPath("/test", "hello".getBytes());
		}
		
		byte[] bs = client.getData().forPath("/test");
		String s = new String(bs);
		System.out.println("Stored in /test " + s);
		
		client.close();
	}
}
