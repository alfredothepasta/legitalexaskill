package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.dto.ApiClient;
import com.weber.cs3230.adminapp.dto.IntentAnswerList;
import com.weber.cs3230.adminapp.dto.IntentDetailList;

public class FakeMain {

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        IntentDetailList intentDetailList = apiClient.getIntents();
        IntentAnswerList intentAnswerList = apiClient.getAnswers(1);
        System.out.println(intentAnswerList);
        System.out.println(intentDetailList);
    }
}