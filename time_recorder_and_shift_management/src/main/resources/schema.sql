ALTER TABLE test_shifts_and_timestamps DROP FOREIGN KEY test_shifts_and_timestamps_ibfk_1;
--ALTER TABLE test_shifts_and_timestamps drop employee_id;
DROP TABLE IF EXISTS test_employees_list;
DROP TABLE IF EXISTS test_shifts_and_timestamps;
DROP table IF EXISTS requests;

create table requests(
    id int primary key auto_increment,
    employee_id int not null,
    date date not null
--    FOREIGN KEY(employee_id) REFERENCES test_employees_list(id)
    );

INSERT INTO requests
SELECT * FROM temp_requests;    
--従業員リストテーブルの作成
CREATE TABLE test_employees_list
LIKE employees_list;

INSERT INTO test_employees_list
SELECT * FROM employees_list;

--パスワード
--"yoshizuka01","koga09","kurosaki21","togo13","komorie30","hakozaki02"

--当日出勤者リストの作成
CREATE TABLE test_shifts_and_timestamps
LIKE shifts_and_timestamps;

ALTER TABLE test_shifts_and_timestamps ADD FOREIGN KEY(employee_id) REFERENCES test_employees_list(id);

INSERT INTO test_shifts_and_timestamps
SELECT * FROM shifts_and_timestamps;