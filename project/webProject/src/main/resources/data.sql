-- Insert one course
INSERT INTO course (id, name) VALUES (1, 'Java Basics');

-- Insert two lectures for the course
INSERT INTO lecture (id, title, course_id) VALUES ('lecture1', 'Introduction to Java', 1);
INSERT INTO lecture (id, title, course_id) VALUES ('lecture2', 'Java Variables', 1);

-- Insert two polls for the course
INSERT INTO poll (id, question, course_id) VALUES ('poll1', 'What is Java?', 1);
INSERT INTO poll (id, question, course_id) VALUES ('poll2', 'Which is a Java keyword?', 1);