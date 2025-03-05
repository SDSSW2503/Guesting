-- Insert sample data into `house`
INSERT INTO `sds`.`house` (`addr`, `name`) VALUES
('123 Main St, Seoul', 'Sunrise Villa'),
('456 Park Ave, Busan', 'Ocean View'),
('789 Maple Rd, Incheon', 'Green Heights'),
('1011 Cherry Ln, Daegu', 'Cherry Blossom Residence'),
('2022 River St, Daejeon', 'Riverside Mansion');

-- Insert sample data into `team`
INSERT INTO `sds`.`team` (`name`) VALUES
('Alpha Team'),
('Beta Team'),
('Gamma Team'),
('Delta Team'),
('Epsilon Team');

-- Insert sample data into `member`
INSERT INTO `sds`.`member` (`gender`, `intro`, `name`, `password`, `team_id`) VALUES
(1, 'I love coding and problem-solving.', 'John Doe', 'securepass1', 1),
(0, 'Data science enthusiast.', 'Jane Smith', 'securepass2', 2),
(1, 'Cloud computing specialist.', 'David Kim', 'securepass3', 3),
(0, 'Machine learning researcher.', 'Emily Park', 'securepass4', 4),
(1, 'Cybersecurity expert.', 'Michael Lee', 'securepass5', 5),
(0, 'Software engineer with a passion for AI.', 'Sophia Choi', 'securepass6', 1),
(1, 'Database administrator with 5 years of experience.', 'Daniel Kang', 'securepass7', 2),
(0, 'Frontend developer who loves UX/UI.', 'Hannah Lee', 'securepass8', 3),
(1, 'DevOps engineer working with cloud technologies.', 'Chris Jung', 'securepass9', 4),
(0, 'Backend engineer with strong Java experience.', 'Jessica Han', 'securepass10', 5);

-- Insert sample data into `regist`
INSERT INTO `sds`.`regist` (`reg_date`, `status`, `house_id`, `receive_team_id`, `send_team_id`) VALUES
(NOW(), 1, 2, 2, 1),
(NOW(), 1, 2, 4, 2),
(NOW(), 3, 2, 5, 3),
(NOW(), 1, 2, 1, 4),
(NOW(), 2, 2, 1, 5);



