INSERT INTO course (id, name) VALUES (1, 'Java Basics');

INSERT INTO lecture (id, title, course_id) VALUES ('lecture1', 'Introduction to Java', 1);
INSERT INTO lecture (id, title, course_id) VALUES ('lecture2', 'Java Variables', 1);

INSERT INTO lecture_note (id, file_name, file_url, lecture_id) VALUES ('note1', 'Intro Slides.pdf', '/files/intro_slides.pdf', 'lecture1');
INSERT INTO lecture_note (id, file_name, file_url, lecture_id) VALUES ('note2', 'Variables Guide.pdf', '/files/variables_guide.pdf', 'lecture2');

INSERT INTO comment (id, author, content, lecture_id) VALUES ('comment1', 'Teacher', 'Great intro!', 'lecture1');
INSERT INTO comment (id, author, content, lecture_id) VALUES ('comment2', 'Student', 'Very clear.', 'lecture1');
INSERT INTO comment (id, author, content, lecture_id) VALUES ('comment3', 'Student', 'Need examples.', 'lecture2');

INSERT INTO poll (id, question, course_id) VALUES ('poll1', 'Which date do you prefer for the mid-term test?', 1);
INSERT INTO poll (id, question, course_id) VALUES ('poll2', 'Which is a Java keyword?', 1);

INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt1', 'October 10', 5, 'poll1');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt2', 'October 15', 3, 'poll1');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt3', 'October 20', 2, 'poll1');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt4', 'October 25', 1, 'poll1');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt5', 'class', 4, 'poll2');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt6', 'variable', 3, 'poll2');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt7', 'method', 2, 'poll2');
INSERT INTO poll_option (id, option_text, vote_count, poll_id) VALUES ('opt8', 'loop', 1, 'poll2');

INSERT INTO poll_comment (id, author, content, poll_id) VALUES ('pcomment1', 'Teacher', 'Please choose a date.', 'poll1');
INSERT INTO poll_comment (id, author, content, poll_id) VALUES ('pcomment2', 'Student', 'October 15 works for me.', 'poll1');
INSERT INTO poll_comment (id, author, content, poll_id) VALUES ('pcomment3', 'Student', 'Class is correct!', 'poll2');

INSERT INTO app_user (username, password, full_name, email, phone_number, role)
VALUES ('student1', 'pass123', 'John Doe', 'john.doe@example.com', '1234567890', 'ROLE_STUDENT');
INSERT INTO app_user (username, password, full_name, email, phone_number, role)
VALUES ('teacher1', 'pass123', 'Jane Smith', 'jane.smith@example.com', '0987654321', 'ROLE_TEACHER');