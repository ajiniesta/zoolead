package com.iniesta.zooleader.acl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import com.iniesta.zooleader.ZKHelper;

public class PlayingAcls {

	public static void main(String[] args) throws Exception {
		
		CuratorFramework client = ZKHelper.getClient("localhost:2181", "user", "pass");
		
		client.start();
		
		if(client.checkExists().forPath("/testacl")==null) {
			client.create().withMode(CreateMode.PERSISTENT).forPath("/testacl", "hello".getBytes());
		}
		
		String data = "Time... " + System.currentTimeMillis();
		try{
			String previousData = new String(client.getData().forPath("/testacl"));
			System.out.println("Previous data... " + previousData);
			System.out.println("Sleeping...");
			Thread.sleep(10000);
			System.out.println("...wake up");
			client.setData().forPath("/testacl",data.getBytes());
			String incominData = new String(client.getData().forPath("/testacl"));
			System.out.println("Successfuly updated and retrieved... " + incominData);
		}catch(Exception e) {
			System.out.println("Couldn't set data");
			e.printStackTrace();
		}finally {
			client.close();	
		}
		
	}

}
