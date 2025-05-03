ALTER TABLE test_shift_and_timestamp2504 DROP FOREIGN KEY test_shift_and_timestamp2504_ibfk_1;
ALTER TABLE test_shift_and_timestamp2504 drop employee_id;
DROP TABLE IF EXISTS test_employees_list;
DROP TABLE IF EXISTS test_shift_and_timestamp2504;
DROP table IF EXISTS requests;

create table requests(
    id int primary key auto_increment,
    employee_id int not null,
    date date not null,
    start time,
    end time
--    FOREIGN KEY(employee_id) REFERENCES test_employees_list(id)
    );
--従業員リストテーブルの作成
CREATE TABLE test_employees_list(
	id INT not null primary key auto_increment,
	password varchar(100) not null,
	name varchar(50) not null,
	tel varchar(13) not null,
	address varchar(150) not null,
	authority ENUM('ADMIN','USER') NOT NULL
	)
auto_increment=1001;

--当日出勤者リストの作成
CREATE TABLE test_shift_and_timestamp2504(
	id INT PRIMARY KEY AUTO_INCREMENT,
	employee_id int NOT NULL,
	date DATE NOT NULL,
	start TIME,
	end TIME,
	FOREIGN KEY(employee_id) REFERENCES test_employees_list(id)
);