package ro.scoalainformala.studentmgmt.student;

import org.springframework.stereotype.Component;
import ro.scoalainformala.studentmgmt.exceptions.ValidationException;


@Component
public class StudentValidatorImpl implements StudentValidator {
    @Override
    public void validateStudent(StudentDTO student) {

        if (student.getAddress() == null) {
            throw new ValidationException("Address is null");
        }

        if (student.getAddress().isEmpty()) {
            throw new ValidationException("Address is empty");
        }

        if (student.getFirstName() == null) {
            throw new ValidationException("First name is null");
        }

        if (student.getFirstName().isEmpty()) {
            throw new ValidationException("First name is empty");
        }

        if (student.getLastName() == null) {
            throw new ValidationException("Last name is null");
        }

        if (student.getLastName().isEmpty()) {
            throw new ValidationException("Last name is empty");
        }

        if (student.getCollegeYear() == null) {
            throw new ValidationException("College year is null");
        }


        if (student.getDepartmentId() == null) {
            throw new ValidationException("Department is null");
        }

        if (student.getEmail() == null) {
            throw new ValidationException("Email is null");
        }
    }
}
