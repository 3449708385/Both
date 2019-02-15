package com.yishenxiao.digitalwallet.service;

import java.io.Serializable;
import java.util.Map;

public interface IMessageDelegate {
	void handleMessage(String message);
	void handleMessage(Map<String, Object> message);
	void handleMessage(byte[] message);
	void handleMessage(Serializable message);
	void handleMessage(Serializable message, String channel);
}
