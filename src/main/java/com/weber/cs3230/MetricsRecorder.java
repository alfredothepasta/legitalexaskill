package com.weber.cs3230;

import com.google.gson.Gson;
import com.weber.cs3230.HttpCommunicator;
import com.weber.cs3230.dto.Metric;
import org.springframework.http.HttpMethod;

public class MetricsRecorder {

    HttpCommunicator httpCommunicator = new HttpCommunicator();
    public void recordMetric(String eventName){
        Metric metric = new Metric();
        metric.setEventName(eventName);
        metric.setAppName("Whale Facts");
        final String json = new Gson().toJson(metric);
        Metric metricResult = httpCommunicator.communicate(HttpMethod.POST, "https://alexa-ghost.herokuapp.com/metric", json, Metric.class);
        // TODO log this
        System.out.println(metricResult.getMetricID());
    }
}
