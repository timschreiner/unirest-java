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
package com.mashape.unirest.http.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class MapUtil {

    public static List<NameValuePair> getList(Map<String, Object> parameters) {
        if (parameters == null) {
            return new ArrayList<NameValuePair>();
        }

        List<NameValuePair> result = new ArrayList<NameValuePair>(parameters.size());
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value != null) {
                result.add(new BasicNameValuePair(key, value.toString()));
            }
        }
        return result;
    }
}
