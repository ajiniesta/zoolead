package com.iniesta.zooleader;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;

public class ZKHelper {

	public static CuratorFramework getClient(String connectString) {
		return getClient(connectString, null, null);
	}
	
	public static CuratorFramework getClient(String connectString, String zkUser, String zkPass) {
		int retryInitialWaitMs = 60000;
		int maxRetryCount = 3;
		int connectionTimeoutMs = 60000;
		int sessionTimeoutMs = 60000;
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(connectString )
                .retryPolicy(new ExponentialBackoffRetry(retryInitialWaitMs , maxRetryCount ))
                .connectionTimeoutMs(connectionTimeoutMs )
                .sessionTimeoutMs(sessionTimeoutMs );
		
		if(zkUser!=null && zkPass!=null) {
			String authString = zkUser + ":" + zkPass;
			builder.authorization("digest", authString.getBytes()).aclProvider(new ACLProvider() {
				
				public List<ACL> getDefaultAcl() {
					return ZooDefs.Ids.CREATOR_ALL_ACL;
				}
				
				public List<ACL> getAclForPath(String arg0) {
					return ZooDefs.Ids.CREATOR_ALL_ACL;
				}
			});
		}
		return builder.build();
		
	}
}
