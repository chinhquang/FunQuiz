package com.example.chinhtrinhquang.funquiz;

public class TackerActivity {
    private static int tracker = 0;

    public static void activityStarted()
    {
        if (tracker == 0) {
            // Do something
        }
        tracker++;
    }

    public static void activityStopped()
    {
        tracker--;
        if (tracker == 0) {
            // Do something
        }
    }
}
