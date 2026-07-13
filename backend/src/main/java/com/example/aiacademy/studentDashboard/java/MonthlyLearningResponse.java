package com.example.aiacademy.studentDashboard.java;

public class MonthlyLearningResponse {

    private int totalVideos;
    private int expectedVideos;
    private String status;
  public MonthlyLearningResponse() {}
    public MonthlyLearningResponse(int totalVideos, int expectedVideos, String status) {
        this.totalVideos = totalVideos;
        this.expectedVideos = expectedVideos;
        this.status = status;
    }

    public int getTotalVideos() {
        return totalVideos;
    }

    public int getExpectedVideos() {
        return expectedVideos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setExpectedVideos(int expectedVideos) {
        this.expectedVideos = expectedVideos;
    }

    public void setTotalVideos(int totalVideos) {
        this.totalVideos = totalVideos;
    }
}
