package ro.scoalainformala.studentmgmt.scheduler;

import lombok.Data;

@Data
public class ImgbbResponse {
    private ImgbbData data;
    private boolean success;
    private int status;
}
