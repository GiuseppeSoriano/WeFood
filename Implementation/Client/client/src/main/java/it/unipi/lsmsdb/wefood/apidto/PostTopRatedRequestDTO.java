package it.unipi.lsmsdb.wefood.apidto;

public class PostTopRatedRequestDTO {
    Long hours;
    Integer limit;

    public PostTopRatedRequestDTO(Long hours, Integer limit) {
        this.hours = hours;
        this.limit = limit;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
