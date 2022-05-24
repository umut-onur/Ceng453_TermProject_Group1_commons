package types.rest.requests;

public class TimeInterval {
    private Long startDate;
    private Long endDate;
    
    public TimeInterval() {}
    
    public TimeInterval(Long startDate, Long endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public Long getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }
    
    public Long getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }
}
