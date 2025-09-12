DELETE FROM student_assignments;
DELETE FROM grades;
DELETE FROM assignment;
DELETE FROM students;
DELETE FROM teachers;
DELETE FROM course;
DELETE FROM users;
DELETE FROM roles;

INSERT INTO roles (id, role_name) VALUES
                                      (1, 'ADMIN'),
                                      (2, 'TEACHER'),
                                      (3, 'STUDENT');

INSERT INTO users (id, username, password, role_id) VALUES
                                                        (1, 'admin@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 1),
                                                        (2, 'teacher.math@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 2),
                                                        (3, 'teacher.cs@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 2),
                                                        (4, 'teacher.physics@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 2),
                                                        (5, 'teacher.english@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 2),
                                                        (6, 'student1@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
                                                        (7, 'student2@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
                                                        (8, 'student3@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
                                                        (9, 'student4@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
                                                        (10, 'student5@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
                                                        (11, 'student6@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
                                                        (12, 'student7@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
                                                        (13, 'student8@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3);

INSERT INTO course (id, name, department_id) VALUES
                                                 (1, 'Mathematics', 0),
                                                 (2, 'Computer Science', 1),
                                                 (3, 'Physics', 0),
                                                 (4, 'English Literature', 3);

INSERT INTO teachers (id, first_name, last_name, email, phone, department, user_id, course_id) VALUES
                                                                                                   (1, 'John', 'Doe', 'john.doe@school.com', '0722123456', 'Physics', 2, 1),
                                                                                                   (2, 'Jane', 'Smith', 'jane.smith@school.com', '0732123456', 'PhysicsInformatics', 3, 2),
                                                                                                   (3, 'Robert', 'Johnson', 'robert.johnson@school.com', '0742123456', 'Physics', 4, 3),
                                                                                                   (4, 'Emily', 'Williams', 'emily.williams@school.com', '0752123456', 'Engineering', 5, 4);

INSERT INTO students (id, first_name, last_name, email, phone, address, department_id, college_year, user_id, course_id) VALUES
                                                                                                                             (1, 'Alice', 'Johnson', 'alice.johnson@school.com', '0745123456', 'Str. Academiei 10', 1, 1, 6, 1),
                                                                                                                             (2, 'Bob', 'Williams', 'bob.williams@school.com', '0745123457', 'Bd. Unirii 25', 1, 1, 7, 1),
                                                                                                                             (3, 'Charlie', 'Brown', 'charlie.brown@school.com', '0745123458', 'Calea Victoriei 42', 1, 2, 8, 2),
                                                                                                                             (4, 'Diana', 'Miller', 'diana.miller@school.com', '0745123459', 'Str. Libertății 7', 1, 2, 9, 2),
                                                                                                                             (5, 'Edward', 'Davis', 'edward.davis@school.com', '0745123460', 'Str. Mihai Eminescu 15', 2, 3, 10, 3),
                                                                                                                             (6, 'Fiona', 'Garcia', 'fiona.garcia@school.com', '0745123461', 'Bd. Magheru 30', 2, 3, 11, 3),
                                                                                                                             (7, 'George', 'Rodriguez', 'george.rodriguez@school.com', '0745123462', 'Str. Ion Creangă 5', 3, 4, 12, 4),
                                                                                                                             (8, 'Hannah', 'Martinez', 'hannah.martinez@school.com', '0745123463', 'Calea Dorobanți 22', 3, 4, 13, 4);

INSERT INTO assignment (id, name, description, start_date, expiration_date, stored_file_name, original_file_name, additional_info, course_id) VALUES
                                                                                                                                                  (1, 'Algebra Basics', 'Linear equations practice', '2023-09-01 10:00:00', '2023-09-15 23:59:59', 'algebra.pdf', 'assignment1.pdf', 'Show all steps', 1),
                                                                                                                                                  (2, 'Calculus Problems', 'Derivatives and integrals', '2023-09-10 10:00:00', '2023-09-25 23:59:59', 'calculus.pdf', 'assignment2.pdf', 'Use proper notation', 1),
                                                                                                                                                  (3, 'OOP Project', 'Java inheritance task', '2023-09-05 14:00:00', '2023-09-25 23:59:59', 'oop_project.zip', 'project.zip', 'Use design patterns', 2),
                                                                                                                                                  (4, 'Database Design', 'ER diagrams and normalization', '2023-09-15 14:00:00', '2023-10-05 23:59:59', 'database_design.pdf', 'assignment3.pdf', 'Include relationships', 2),
                                                                                                                                                  (5, 'Mechanics Problems', 'Newton laws applications', '2023-09-08 09:00:00', '2023-09-22 23:59:59', 'mechanics.pdf', 'physics_assignment1.pdf', 'Show free body diagrams', 3),
                                                                                                                                                  (6, 'Electromagnetism', 'Electric fields and circuits', '2023-09-20 09:00:00', '2023-10-10 23:59:59', 'electromagnetism.pdf', 'physics_assignment2.pdf', 'Include calculations', 3),
                                                                                                                                                  (7, 'Shakespeare Analysis', 'Hamlet character study', '2023-09-12 11:00:00', '2023-09-30 23:59:59', 'shakespeare.pdf', 'hamlet_analysis.pdf', 'Cite specific passages', 4),
                                                                                                                                                  (8, 'Modern Poetry', '20th century poetry analysis', '2023-09-25 11:00:00', '2023-10-15 23:59:59', 'modern_poetry.pdf', 'poetry_analysis.pdf', 'Compare at least two poets', 4);

INSERT INTO student_assignments (assignment_id, student_id) VALUES
                                                                (1, 1), (1, 2),
                                                                (2, 1), (2, 2),
                                                                (3, 3), (3, 4),
                                                                (4, 3), (4, 4),
                                                                (5, 5), (5, 6),
                                                                (6, 5), (6, 6),
                                                                (7, 7), (7, 8),
                                                                (8, 7), (8, 8);

INSERT INTO grades (id, grade, student_id, course_id) VALUES
                                                          (1, 9.5, 1, 1),
                                                          (2, 8.0, 2, 1),
                                                          (3, 9.0, 3, 2),
                                                          (4, 10.0, 4, 2),
                                                          (5, 8.5, 5, 3),
                                                          (6, 9.5, 6, 3),
                                                          (7, 7.5, 7, 4),
                                                          (8, 9.0, 8, 4);
# DELETE FROM student_assignments;
# DELETE FROM grades;
# DELETE FROM assignment;
# DELETE FROM students;
# DELETE FROM teachers;
# DELETE FROM course;
# DELETE FROM users;
# DELETE FROM roles;
#
# INSERT INTO roles (id, role_name) VALUES
#                                       (1, 'ADMIN'),
#                                       (2, 'TEACHER'),
#                                       (3, 'STUDENT');
#
# INSERT INTO users (id, username, password, role_id) VALUES
#                                                         (1, 'admin@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 1),
#                                                         (2, 'teacher.math@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 2),
#                                                         (3, 'teacher.cs@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 2),
#                                                         (4, 'teacher.physics@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 2),
#                                                         (5, 'teacher.english@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 2),
#                                                         (6, 'student1@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
#                                                         (7, 'student2@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
#                                                         (8, 'student3@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
#                                                         (9, 'student4@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
#                                                         (10, 'student5@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
#                                                         (11, 'student6@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
#                                                         (12, 'student7@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3),
#                                                         (13, 'student8@school.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 3);
#
# INSERT INTO course (id, name) VALUES
#                                   (1, 'Mathematics'),
#                                   (2, 'Computer Science'),
#                                   (3, 'Physics'),
#                                   (4, 'English Literature');
#
# INSERT INTO teachers (id, first_name, last_name, email, phone, department, user_id, course_id) VALUES
#                                                                                                    (1, 'John', 'Doe', 'john.doe@school.com', '0722123456', 'SCIENCE', 2, 1),
#                                                                                                    (2, 'Jane', 'Smith', 'jane.smith@school.com', '0732123456', 'TECHNOLOGY', 3, 2),
#                                                                                                    (3, 'Robert', 'Johnson', 'robert.johnson@school.com', '0742123456', 'SCIENCE', 4, 3),
#                                                                                                    (4, 'Emily', 'Williams', 'emily.williams@school.com', '0752123456', 'HUMANITIES', 5, 4);
#
# INSERT INTO students (id, first_name, last_name, email, phone, address, department_id, college_year, user_id, course_id) VALUES
#                                                                                                                              (1, 'Alice', 'Johnson', 'alice.johnson@school.com', '0745123456', 'Str. Academiei 10', 101, 1, 6, 1),
#                                                                                                                              (2, 'Bob', 'Williams', 'bob.williams@school.com', '0745123457', 'Bd. Unirii 25', 101, 1, 7, 1),
#                                                                                                                              (3, 'Charlie', 'Brown', 'charlie.brown@school.com', '0745123458', 'Calea Victoriei 42', 102, 2, 8, 2),
#                                                                                                                              (4, 'Diana', 'Miller', 'diana.miller@school.com', '0745123459', 'Str. Libertății 7', 102, 2, 9, 2),
#                                                                                                                              (5, 'Edward', 'Davis', 'edward.davis@school.com', '0745123460', 'Str. Mihai Eminescu 15', 103, 3, 10, 3),
#                                                                                                                              (6, 'Fiona', 'Garcia', 'fiona.garcia@school.com', '0745123461', 'Bd. Magheru 30', 103, 3, 11, 3),
#                                                                                                                              (7, 'George', 'Rodriguez', 'george.rodriguez@school.com', '0745123462', 'Str. Ion Creangă 5', 104, 4, 12, 4),
#                                                                                                                              (8, 'Hannah', 'Martinez', 'hannah.martinez@school.com', '0745123463', 'Calea Dorobanți 22', 104, 4, 13, 4);
#
# INSERT INTO assignment (id, name, description, start_date, expiration_date, stored_file_name, original_file_name, additional_info, course_id) VALUES
#                                                                                                                                                   (1, 'Algebra Basics', 'Linear equations practice', '2023-09-01 10:00:00', '2023-09-15 23:59:59', 'algebra.pdf', 'assignment1.pdf', 'Show all steps', 1),
#                                                                                                                                                   (2, 'Calculus Problems', 'Derivatives and integrals', '2023-09-10 10:00:00', '2023-09-25 23:59:59', 'calculus.pdf', 'assignment2.pdf', 'Use proper notation', 1),
#                                                                                                                                                   (3, 'OOP Project', 'Java inheritance task', '2023-09-05 14:00:00', '2023-09-25 23:59:59', 'oop_project.zip', 'project.zip', 'Use design patterns', 2),
#                                                                                                                                                   (4, 'Database Design', 'ER diagrams and normalization', '2023-09-15 14:00:00', '2023-10-05 23:59:59', 'database_design.pdf', 'assignment3.pdf', 'Include relationships', 2),
#                                                                                                                                                   (5, 'Mechanics Problems', 'Newton laws applications', '2023-09-08 09:00:00', '2023-09-22 23:59:59', 'mechanics.pdf', 'physics_assignment1.pdf', 'Show free body diagrams', 3),
#                                                                                                                                                   (6, 'Electromagnetism', 'Electric fields and circuits', '2023-09-20 09:00:00', '2023-10-10 23:59:59', 'electromagnetism.pdf', 'physics_assignment2.pdf', 'Include calculations', 3),
#                                                                                                                                                   (7, 'Shakespeare Analysis', 'Hamlet character study', '2023-09-12 11:00:00', '2023-09-30 23:59:59', 'shakespeare.pdf', 'hamlet_analysis.pdf', 'Cite specific passages', 4),
#                                                                                                                                                   (8, 'Modern Poetry', '20th century poetry analysis', '2023-09-25 11:00:00', '2023-10-15 23:59:59', 'modern_poetry.pdf', 'poetry_analysis.pdf', 'Compare at least two poets', 4);
#
# INSERT INTO student_assignments (assignment_id, student_id) VALUES
#                                                                 (1, 1), (1, 2),
#                                                                 (2, 1), (2, 2),
#                                                                 (3, 3), (3, 4),
#                                                                 (4, 3), (4, 4),
#                                                                 (5, 5), (5, 6),
#                                                                 (6, 5), (6, 6),
#                                                                 (7, 7), (7, 8),
#                                                                 (8, 7), (8, 8);
#
# INSERT INTO grades (id, grade, student_id, course_id) VALUES
#                                                           (1, 9.5, 1, 1),
#                                                           (2, 8.0, 2, 1),
#                                                           (3, 9.0, 3, 2),
#                                                           (4, 10.0, 4, 2),
#                                                           (5, 8.5, 5, 3),
#                                                           (6, 9.5, 6, 3),
#                                                           (7, 7.5, 7, 4),
#                                                           (8, 9.0, 8, 4);
