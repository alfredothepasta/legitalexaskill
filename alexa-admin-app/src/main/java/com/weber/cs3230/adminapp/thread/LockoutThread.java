package com.weber.cs3230.adminapp.thread;

import com.sun.tools.javac.Main;
import com.weber.cs3230.adminapp.MainFrame;

public class LockoutThread extends Thread{

    private final long threadSleepTime = 1_000;
    private final long lockoutTime = 10_000;

    private MainFrame mainFrame;

    public LockoutThread(MainFrame mainFrame){
        this.mainFrame = mainFrame;
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

            if((System.currentTimeMillis() - mainFrame.getStartTime()) >= lockoutTime){
                // do things
                mainFrame.logout();
                waitingForLockout = false;
            }

        }

    }
}
