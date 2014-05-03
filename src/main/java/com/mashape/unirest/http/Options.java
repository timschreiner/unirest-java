package com.mashape.unirest.http;

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
import java.util.HashMap;
import org.apache.http.client.HttpClient;

class Options {

    static volatile long CONNECTION_TIMEOUT = 10000;
    static volatile long SOCKET_TIMEOUT = 60000;
    static volatile AsyncIdleConnectionMonitorThread ASYNC_MONITOR;
    static volatile SyncIdleConnectionMonitorThread SYNC_MONITOR;
    static final Map<String, String> DEFAULT_HEADERS = new HashMap<String, String>();
    static volatile CloseableHttpAsyncClient ASYNCHTTPCLIENT;
    static volatile HttpClient HTTPCLIENT;

    static {
        refresh();
    }

    static void refresh() {
        // Create common default configuration
        RequestConfig clientConfig = RequestConfig.custom().setConnectTimeout((int) CONNECTION_TIMEOUT).setSocketTimeout((int) SOCKET_TIMEOUT).setConnectionRequestTimeout((int) SOCKET_TIMEOUT).build();

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
