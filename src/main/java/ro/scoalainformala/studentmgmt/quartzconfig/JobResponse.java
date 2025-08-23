package ro.scoalainformala.studentmgmt.quartzconfig;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class JobResponse {

    private final String jobName;
    private final String groupName;
    private final String scheduleTime;
}
