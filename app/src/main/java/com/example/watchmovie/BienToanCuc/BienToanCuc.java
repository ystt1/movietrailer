package com.example.watchmovie.BienToanCuc;

public class BienToanCuc {
    private static BienToanCuc instance;
    private int loggedInUserID;

    private BienToanCuc() {

        loggedInUserID = -1;
    }

    public static BienToanCuc getInstance() {
        if (instance == null) {
            instance = new BienToanCuc();
        }
        return instance;
    }

    public int getLoggedInUserID() {
        return loggedInUserID;
    }

    public void setLoggedInUserID(int id) {
        loggedInUserID = id;
    }
}
