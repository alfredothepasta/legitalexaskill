package com.weber.cs3230;

import com.google.gson.Gson;
import com.weber.cs3230.HttpCommunicator;
import com.weber.cs3230.dto.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

public class MetricsRecorder {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    HttpCommunicator httpCommunicator = new HttpCommunicator();
    public void recordMetric(String eventName){
        Metric metric = new Metric();
        metric.setEventName(eventName);
        metric.setAppName("Whale Facts");
        final String json = new Gson().toJson(metric);
        Metric metricResult = httpCommunicator.communicate(HttpMethod.POST, "https://alexa-ghost.herokuapp.com/metric", json, Metric.class);
        // TODO log this
        log.info("Metric recorded: " + metric.getEventName());
        log.info("EventID: " + metricResult.getMetricID());
//        System.out.println(metricResult.getMetricID());
    }
}
