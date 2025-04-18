-- Insert app_user first to satisfy foreign key constraints
INSERT INTO app_user (username, password, full_name, email, phone_number, role)
VALUES ('student1', '$2a$10$o4MmQdSiRNbWJ4BaSVyxeOJ/LhDpJ3d5uKgx7f8eVUoUwZLUIrjiO', 'John Doe', 'john.doe@example.com', '1234567890', 'ROLE_STUDENT');
INSERT INTO app_user (username, password, full_name, email, phone_number, role)
VALUES ('teacher1', '$2a$10$o4MmQdSiRNbWJ4BaSVyxeOJ/LhDpJ3d5uKgx7f8eVUoUwZLUIrjiO', 'Jane Smith', 'jane.smith@example.com', '0987654321', 'ROLE_TEACHER');

-- Insert course
INSERT INTO course (id, name) VALUES (1, 'Java Basics');

-- Insert lectures
INSERT INTO lecture (id, title, course_id) VALUES ('lecture1', 'Introduction to Java', 1);
INSERT INTO lecture (id, title, course_id) VALUES ('lecture2', 'Java Variables', 1);

-- Insert lecture notes
INSERT INTO lecture_note (id, file_name, file_url, lecture_id) VALUES ('note1', 'Intro Slides.pdf', '/files/intro_slides.pdf', 'lecture1');
INSERT INTO lecture_note (id, file_name, file_url, lecture_id) VALUES ('note2', 'Variables Guide.pdf', '/files/variables_guide.pdf', 'lecture2');

-- Insert comments (after app_user to satisfy author_username FK)
INSERT INTO comment (id, author_username, content, lecture_id) VALUES ('comment1', 'teacher1', 'Great intro!', 'lecture1');
INSERT INTO comment (id, author_username, content, lecture_id) VALUES ('comment2', 'student1', 'Very clear.', 'lecture1');
INSERT INTO comment (id, author_username, content, lecture_id) VALUES ('comment3', 'student1', 'Need examples.', 'lecture2');

-- Insert polls
INSERT INTO poll (id, question, course_id) VALUES ('poll1', 'Which date do you prefer for the mid-term test?', 1);
INSERT INTO poll (id, question, course_id) VALUES ('poll2', 'Which is a Java keyword?', 1);

-- Insert poll options
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt1', 'October 10', 5, 'poll1');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt2', 'October 15', 3, 'poll1');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt3', 'October 20', 2, 'poll1');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt4', 'October 25', 1, 'poll1');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt5', 'class', 4, 'poll2');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt6', 'variable', 3, 'poll2');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt7', 'method', 2, 'poll2');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt8', 'loop', 1, 'poll2');

-- Insert poll comments (after app_user to satisfy author_username FK)
INSERT INTO poll_comment (id, author_username, content, poll_id) VALUES ('pcomment1', 'teacher1', 'Please choose a date.', 'poll1');
INSERT INTO poll_comment (id, author_username, content, poll_id) VALUES ('pcomment2', 'student1', 'October 15 works for me.', 'poll1');
INSERT INTO poll_comment (id, author_username, content, poll_id) VALUES ('pcomment3', 'student1', 'Class is correct!', 'poll2');