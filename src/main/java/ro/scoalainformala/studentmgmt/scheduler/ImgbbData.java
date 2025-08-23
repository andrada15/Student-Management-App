package ro.scoalainformala.studentmgmt.scheduler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImgbbData {

    private String id;
    private String title;

    @JsonProperty("url_viewer")
    private String urlViewer;
    private String url;

    @JsonProperty("display_url")
    private String displayUrl;
    private int width;
    private int height;
    private int size;
    private int time;
    private int expiration;

}
