DELETE FROM employees;
DELETE FROM companies;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO companies (name, address, tin, phone) VALUES
  ('Company1', 'Address1', '123123319876', '89076283792'),
  ('Company2', 'Address2', '999887631234', '89062333718');

INSERT INTO employees (name, email, birthday, company_id) VALUES
  ('qwe', 'qwe@gmail.com', '2019-01-01', 100000),
  ('asd', 'asd@gmail.com', '2019-03-03', 100001);

