package ro.scoalainformala.studentmgmt.quartzconfig;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BaseJobRequest {
    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "group cannot be null")
    private String group;

    private String description;

}
