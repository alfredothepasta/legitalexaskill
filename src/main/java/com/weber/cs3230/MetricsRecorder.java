package com.weber.cs3230;

import com.google.gson.Gson;
import com.weber.cs3230.HttpCommunicator;
import com.weber.cs3230.dto.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MetricsRecorder {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    HttpCommunicator httpCommunicator = new HttpCommunicator();

    // ew...
    private final static ExecutorService executorService = Executors.newCachedThreadPool();

    public void recordMetric(String eventName){
        new Thread(()-> {
            Metric metric = new Metric();
            metric.setEventName(eventName);
            metric.setAppName("Whale Facts");
            final String json = new Gson().toJson(metric);
            try {
                Metric metricResult = httpCommunicator.communicate(HttpMethod.POST, "https://alexa-ghost.herokuapp.com/metric", json, Metric.class);
                log.info("Metric recorded: " + metric.getEventName());
                log.info("EventID: " + metricResult.getMetricID());
            } catch (Exception e) {
                log.error("The HTTP Communication Failed", e);
            }
        }).start();

//        System.out.println(metricResult.getMetricID());
    }
}
