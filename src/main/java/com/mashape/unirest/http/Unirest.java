/*
The MIT License

Copyright (c) 2013 Mashape (http://mashape.com)

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.mashape.unirest.http;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.http.impl.client.CloseableHttpClient;

public class Unirest {
	
	/**
	 * Set the HttpClient implementation to use for every synchronous request
	 */
	public static void setHttpClient(HttpClient httpClient) {
        if(httpClient==null){
            throw new IllegalArgumentException("httpClient cannot be null");
	}
		Options.HTTPCLIENT = httpClient;
	}
	
	/**
	 * Set the connection timeout and socket timeout
	 */
	public static void setTimeouts(long connectionTimeout, long socketTimeout) {
        Options.CONNECTION_TIMEOUT = connectionTimeout;
        Options.SOCKET_TIMEOUT = socketTimeout;
		
		// Reload the client implementations
		Options.refresh();
	}
	
	/**
	 * Clear default headers
	 */
	public static void clearDefaultHeaders() {
        Options.DEFAULT_HEADERS.clear();
	}
	
	/**
	 * Set default header
	 */
	public static void setDefaultHeader(String name, String value) {
        Options.DEFAULT_HEADERS.put(name, value);
    }
	
	/**
	 * Set the asynchronous AbstractHttpAsyncClient implementation to use for every asynchronous request
	 */
	public static void setAsyncHttpClient(CloseableHttpAsyncClient asyncHttpClient) {
        if(asyncHttpClient == null){
            throw new IllegalArgumentException("asyncHttpClient cannot be null");
        }
        Options.ASYNCHTTPCLIENT = asyncHttpClient;
	}
	
	/**
	 * Close the asynchronous client and its event loop. Use this method to close all the threads and allow an application to exit.  
	 */
	public static void shutdown() throws IOException {
		// Closing the sync client
        if(Options.HTTPCLIENT instanceof CloseableHttpClient){
            ((CloseableHttpClient)Options.HTTPCLIENT).close();    
        }
		
		Options.SYNC_MONITOR.shutdown();
		
		// Closing the async client (if running)
        CloseableHttpAsyncClient asyncClient = Options.ASYNCHTTPCLIENT;
		if (asyncClient.isRunning()) {
			asyncClient.close();
			Options.ASYNC_MONITOR.shutdown();
		}
	}
	
	public static GetRequest get(String url) {
		return new GetRequest(HttpMethod.GET, url);
	}
	
	public static GetRequest head(String url) {
		return new GetRequest(HttpMethod.HEAD, url);
	}
	
	public static HttpRequestWithBody options(String url) {
		return new HttpRequestWithBody(HttpMethod.OPTIONS, url);
	}
	
	public static HttpRequestWithBody post(String url) {
		return new HttpRequestWithBody(HttpMethod.POST, url);
	}
	
	public static HttpRequestWithBody delete(String url) {
		return new HttpRequestWithBody(HttpMethod.DELETE, url);
	}
	
	public static HttpRequestWithBody patch(String url) {
		return new HttpRequestWithBody(HttpMethod.PATCH, url);
	}
	
	public static HttpRequestWithBody put(String url) {
		return new HttpRequestWithBody(HttpMethod.PUT, url);
	}
}
