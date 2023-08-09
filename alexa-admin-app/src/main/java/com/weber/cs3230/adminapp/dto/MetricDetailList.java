package com.weber.cs3230.adminapp.dto;

import java.util.ArrayList;
import java.util.List;

public class MetricDetailList {
    private List<MetricDetail> metrics = new ArrayList<>();


    public List<MetricDetail> getMetrics() {return metrics;}


    public void setMetrics(List<MetricDetail> metricsDetails) {this.metrics = metricsDetails;}
}

