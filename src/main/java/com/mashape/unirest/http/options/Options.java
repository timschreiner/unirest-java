package com.mashape.unirest.http.options;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import com.mashape.unirest.http.async.utils.AsyncIdleConnectionMonitorThread;
import com.mashape.unirest.http.utils.SyncIdleConnectionMonitorThread;
import org.apache.http.client.HttpClient;

public class Options {

    public static volatile long CONNECTION_TIMEOUT = 10000;
    public static volatile long SOCKET_TIMEOUT = 60000;
    public static volatile AsyncIdleConnectionMonitorThread ASYNC_MONITOR;
    public static volatile SyncIdleConnectionMonitorThread SYNC_MONITOR;
    public static volatile Map<String, String> DEFAULT_HEADERS = new HashMap<String, String>();
    public static CloseableHttpAsyncClient ASYNCHTTPCLIENT;
    public static HttpClient HTTPCLIENT;

    static {
        refresh();
    }

    public static void refresh() {
        // Create common default configuration
        RequestConfig clientConfig = RequestConfig.custom().setConnectTimeout(((Long) CONNECTION_TIMEOUT).intValue()).setSocketTimeout(((Long) SOCKET_TIMEOUT).intValue()).setConnectionRequestTimeout(((Long) SOCKET_TIMEOUT).intValue()).build();

        PoolingHttpClientConnectionManager syncConnectionManager = new PoolingHttpClientConnectionManager();
        syncConnectionManager.setMaxTotal(10000);
        syncConnectionManager.setDefaultMaxPerRoute(10000);

        // Create clients
        HTTPCLIENT = HttpClientBuilder.create().setDefaultRequestConfig(clientConfig).setConnectionManager(syncConnectionManager).build();
        SYNC_MONITOR = new SyncIdleConnectionMonitorThread(syncConnectionManager);
        SYNC_MONITOR.start();

        DefaultConnectingIOReactor ioreactor;
        PoolingNHttpClientConnectionManager asyncConnectionManager;
        try {
            ioreactor = new DefaultConnectingIOReactor();
            asyncConnectionManager = new PoolingNHttpClientConnectionManager(ioreactor);
        } catch (IOReactorException e) {
            throw new RuntimeException(e);
        }

        ASYNCHTTPCLIENT = HttpAsyncClientBuilder.create().setDefaultRequestConfig(clientConfig).setConnectionManager(asyncConnectionManager).build();
        ASYNC_MONITOR = new AsyncIdleConnectionMonitorThread(asyncConnectionManager);
    }

}
