package ro.scoalainformala.studentmgmt.department;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DepartmentEnum {

    Physics(0),
    PhysicsInformatics(1),
    PhysicsInEnglish(2),
    Engineering(3);

    private final int id;


    public static DepartmentEnum fromId(int id) {
        return Arrays.stream(values())
                .filter(enumValue -> enumValue.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
