package ro.scoalainformala.studentmgmt.student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ro.scoalainformala.studentmgmt.exceptions.ValidationException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StudentValidatorImplUnitTest {

    private final StudentValidator studentValidator = new StudentValidatorImpl();



    @Test
    void testValidateStudentHappyPath() {
        final StudentDTO validStudent = StudentDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .phone("0712345678")
                .address("Some Street 123")
                .departmentId(1)
                .collegeYear(2)
                .build();

        assertDoesNotThrow(() -> studentValidator.validateStudent(validStudent));
    }

    @ParameterizedTest
    @MethodSource("testValidateDataProvider")
    void testValidate(final StudentDTO studentDTO, final Exception expectedException) {
        assertThrows(expectedException.getClass(), () -> studentValidator.validateStudent(studentDTO), expectedException.getMessage());
    }

    public static Stream<Arguments> testValidateDataProvider() {
        return Stream.of(
                Arguments.of(
                        StudentDTO.builder().build(),
                        new ValidationException("Address is null")
                ),
                Arguments.of(
                        StudentDTO.builder()
                                .address("")
                                .build(),
                        new ValidationException("Address is empty")
                ),
                Arguments.of(
                        StudentDTO.builder()
                                .address("Some Street")
                                .build(),
                        new ValidationException("First name is null")
                ),
                Arguments.of(
                        StudentDTO.builder()
                                .address("Some Street")
                                .firstName("")
                                .build(),
                        new ValidationException("First name is empty")
                ),
                Arguments.of(
                        StudentDTO.builder()
                                .address("Some Street")
                                .firstName("John")
                                .build(),
                        new ValidationException("Last name is null")
                ),
                Arguments.of(
                        StudentDTO.builder()
                                .address("Some Street")
                                .firstName("John")
                                .lastName("")
                                .build(),
                        new ValidationException("Last name is empty")
                ),
                Arguments.of(
                        StudentDTO.builder()
                                .address("Some Street")
                                .firstName("John")
                                .lastName("Doe")
                                .build(),
                        new ValidationException("College year is null")
                ),
                Arguments.of(
                        StudentDTO.builder()
                                .address("Some Street")
                                .firstName("John")
                                .lastName("Doe")
                                .collegeYear(2)
                                .build(),
                        new ValidationException("Department is null")
                ),
                Arguments.of(
                        StudentDTO.builder()
                                .address("Some Street")
                                .firstName("John")
                                .lastName("Doe")
                                .collegeYear(2)
                                .departmentId(1)
                                .build(),
                        new ValidationException("Email is null")
                ),
                Arguments.of(
                        StudentDTO.builder()
                                .address("Some Street")
                                .firstName("John")
                                .lastName("Doe")
                                .collegeYear(2)
                                .departmentId(1)
                                .email("invalid-email")
                                .build(),
                        new ValidationException("Email is not valid")
                )
        );

    }
}