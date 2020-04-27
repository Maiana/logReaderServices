package models;

public class ServiceLogData {
    private String name;
    private long maxDuration;
    private int requestsCount;


    public ServiceLogData(String name) {
        this.name = name;
    }

    public ServiceLogData() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxDuration(long maxDuration) {
        this.maxDuration = maxDuration;
    }

    public String getName() {
        return name;
    }

    public int getRequestsCount() {
        return requestsCount;
    }

    public void increaseRequestsCount() {
        this.requestsCount++;
    }

    public long getMaxDuration() {
        return maxDuration;
    }
}
