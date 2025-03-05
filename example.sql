-- Insert sample data into Team
INSERT INTO Team (team_id, name) VALUES (1, 'Red Warriors'), (2, 'Blue Titans'), (3, 'Green Dragons');

-- Insert sample data into House
INSERT INTO House (house_id, name, addr) VALUES (1, 'Sunset Villa', '123 Palm Street'), (2, 'Ocean Breeze', '456 Sea Avenue');

-- Insert sample data into Member
INSERT INTO Member (Member_id, password, name, intro, gender, team_id) VALUES
(1, 'pass123', 'Alice', 'Loves coding', 0, 1),
(2, 'secure456', 'Bob', 'Soccer fan', 1, 2),
(3, 'mypass789', 'Charlie', 'Enjoys reading', 0, 3);

-- Insert sample data into Regist
INSERT INTO Regist (regist_id, send_team_id, receive_team_id, reg_date, status, house_id) VALUES
(1, 1, 2, '2025-03-05 10:00:00', 0, 1),
(2, 2, 3, '2025-03-05 11:00:00', 1, 2);
