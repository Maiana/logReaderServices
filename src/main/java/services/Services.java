package services;

public class Services {
    private String name;
    private long maxDuration;
    private int requestsCount;


    public Services(String name) {
        this.name = name;
    }

    public Services() {

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
