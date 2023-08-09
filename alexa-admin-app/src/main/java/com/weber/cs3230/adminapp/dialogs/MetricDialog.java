package com.weber.cs3230.adminapp.dialogs;

import com.weber.cs3230.adminapp.ApplicationController;
import com.weber.cs3230.adminapp.dto.MetricDetail;
import com.weber.cs3230.adminapp.dto.MetricDetailList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MetricDialog extends JDialog {
    private final MetricDetailList metricDetailList;
    private final ApplicationController applicationController;
    private final String[] columnNames = {"Count", "Message"};

    public MetricDialog(MetricDetailList metricDetailList, ApplicationController applicationController){
        this.metricDetailList = metricDetailList;
        this.applicationController = applicationController;
        setPreferredSize(new Dimension(700,  200));
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        add(getMetricDialog());
        pack();
    }

    private JComponent getMetricDialog(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Metrics."), BorderLayout.NORTH);
        panel.add(createTablePanel(), BorderLayout.CENTER);
        return panel;
    }

    private JComponent createTablePanel() {
        DefaultTableModel tableModel = new DefaultTableModel(getTableData(), columnNames);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setVisible(true);
        return scrollPane;
    }

    private Object[][] getTableData(){
        java.util.List<Object[]> rows = new ArrayList<>();

        for (MetricDetail metric : metricDetailList.getMetrics()) {
            rows.add(new Object[]{metric.getCount(), metric.getEventName()});
        }
        return rows.toArray(new Object[0][0]);
    }


}
