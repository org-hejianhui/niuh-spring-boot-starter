package com.zc.smartcity.redis.zookeeper;

import org.apache.zookeeper.WatchedEvent;


public interface ZkListener {
	
	public void dataEvent(WatchedEvent event);
}

