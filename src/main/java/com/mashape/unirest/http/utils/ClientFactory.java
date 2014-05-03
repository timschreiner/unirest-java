package com.mashape.unirest.http.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

import com.mashape.unirest.http.options.Options;

public class ClientFactory {

	public static HttpClient getHttpClient() {
		return Options.HTTPCLIENT;
	}
	
	public static CloseableHttpAsyncClient getAsyncHttpClient() {
		return Options.ASYNCHTTPCLIENT;
	}
}
