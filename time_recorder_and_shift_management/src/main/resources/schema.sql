DROP TABLE IF EXISTS test_employees_list;
DROP TABLE IF EXISTS test_shift_and_timestamp2504;

--従業員リストテーブルの作成
CREATE TABLE test_employees_list(
	id char(8) not null primary key,
	password varchar(16) not null,
	name varchar(50) not null,
	tel varchar(13) not null,
	address varchar(150) not null
);

--当日出勤者リストの作成
CREATE test_shift_and_timestamp2504(
	id INT PRIMARY KEY AUTO_INCREMENT,
	employee_id CHAR(8) NOT NULL,
	date DATE NOT NULL,
	start TIME,
	end TIME,
	FOREIGN KEY(employee_id) REFERENCES employees_list(id)
);