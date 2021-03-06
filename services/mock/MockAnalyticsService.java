/*
Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy of this
software and associated documentation files (the "Software"), to deal in the Software
without restriction, including without limitation the rights to use, copy, modify,
merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.distracteddevelopment.peptalk.services.mock;

import android.util.Log;

import com.distracteddevelopment.peptalk.services.AnalyticsService;

import java.util.Locale;
import java.util.Map;

public class MockAnalyticsService implements AnalyticsService {
    private static final String TAG = "MockAnalyticsService";

    @Override
    public void startSession() {
        Log.v(TAG, "startSession()");
    }

    @Override
    public void stopSession() {
        Log.v(TAG, "stopSession()");

    }

    @Override
    public void recordEvent(String eventName, Map<String, String> attributes, Map<String, Double> metrics) {
        StringBuilder event = new StringBuilder("");
        if (attributes != null) {
            for (Map.Entry<String,String> entry : attributes.entrySet()) {
                event.append(String.format(Locale.US, ", %s=\"%s\"", entry.getKey(), entry.getValue()));
            }
        }
        if (metrics != null) {
            for (Map.Entry<String,Double> entry : metrics.entrySet()) {
                event.append(String.format(Locale.US, ", %s=%.2f", entry.getKey(), entry.getValue()));
            }
        }
        if (!event.toString().isEmpty()) {
            event.setCharAt(0, ':');
        }
        Log.v(TAG, String.format(Locale.US, "recordEvent(%s)%s", eventName, event.toString()));
    }
}
