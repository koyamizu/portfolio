DROP TABLE IF EXISTS test_employees_list;
DROP TABLE IF EXISTS test_shift_table2504;
DROP TABLE IF EXISTS test_timestamp2504;

//従業員リストテーブルの作成
CREATE TABLE test_employees_list
(id char(8) not null primary key,
password varchar(16) not null,
name varchar(50) not null,
tel varchar(13) not null,
address varchar(150) not null
);

//当日出勤者リストの作成
CREATE TABLE test_shift_table2504
(id INT PRIMARY KEY AUTO_INCREMENT,
date date not null,
employee_id char(8) not null,
FOREIGN KEY(employee_id) REFERENCES test_employees_list(id)
);

//出勤状況リストの作成
CREATE TABLE test_timestamp2504
(id INT PRIMARY KEY AUTO_INCREMENT,
//employee_id char(8) not null,
shift_id INT NOT NULL,
start datetime,
end datetime,
//FOREIGN KEY(employee_id) REFERENCES test_employees_list(id)
FOREIGN KEY(shift_id) REFERENCES test_shift_table2504(id)
);
