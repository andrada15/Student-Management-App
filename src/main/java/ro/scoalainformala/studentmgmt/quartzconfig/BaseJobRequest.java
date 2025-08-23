package ro.scoalainformala.studentmgmt.quartzconfig;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class BaseJobRequest {
    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "group cannot be null")
    private String group;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseJobRequest that = (BaseJobRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(group, that.group) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, group, description);
    }
}
