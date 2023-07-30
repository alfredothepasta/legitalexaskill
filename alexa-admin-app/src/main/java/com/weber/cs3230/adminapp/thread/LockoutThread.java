package com.weber.cs3230.adminapp.thread;

import com.sun.tools.javac.Main;
import com.weber.cs3230.adminapp.ApplicationController;
import com.weber.cs3230.adminapp.MainFrame;

public class LockoutThread extends Thread{

    private final long threadSleepTime = 30_000;
    private final long lockoutTime = 600_000;

    private ApplicationController applicationController;

    public LockoutThread(ApplicationController applicationController){
        this.applicationController = applicationController;
    }
    public void run() {

        boolean waitingForLockout = true;

        while(waitingForLockout) {
            try {
                Thread.sleep(threadSleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // get startTime from MainPanel

            // subtract from current time
            // check if 10 minutes have passed.
            // if so, yeet the joptionwarning and kick back to login
            ;

            if((System.currentTimeMillis() - applicationController.getStartTime()) >= lockoutTime){
                // do things
                applicationController.logout();
                waitingForLockout = false;
            }

        }

    }
}
