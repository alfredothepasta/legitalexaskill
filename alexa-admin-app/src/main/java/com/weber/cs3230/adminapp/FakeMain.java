package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.thread.LockoutThread;

import javax.swing.*;

/**
 * Bypasses login for Alex's Sanity
 * Don't let David see it.
 * Who am I kidding, I'll forget to delete it.
 *
 * todo: delete this, maybe figure out how git ignore works
 */
public class FakeMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}
