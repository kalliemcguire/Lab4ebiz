drop table Employee;
drop table Hourly_Employee;
drop table Salary_Employee;
drop table Timecard;

CREATE TABLE Employee (
  Employee_ID INT NOT NULL,
  Employee_Type INT,
  First_Name VARCHAR(50),
  Last_Name VARCHAR(50),
  SSN BIGINT,
  User_ID VARCHAR(20),
  Password VARCHAR(20),
  
  PRIMARY KEY(Employee_ID) 
);

INSERT INTO Employee 
  (Employee_ID, Employee_Type, First_Name, Last_Name, SSN, User_ID, Password)
VALUES 
  (1001, 1, 'Bob', 'Smith', 555121234, 'User1', 'user1'),
  (1002, 1, 'Jane', 'Doe', 555124321, 'User2', 'user2'),
  (1003, 2, 'John', 'Doe', 333125678, 'User3', 'user3'),
  (1004, 2, 'James', 'Wilson', 444342345, 'User4', 'user4');

CREATE TABLE Hourly_Employee (
  Employee_ID INT NOT NULL,
  Hourly_Rate DOUBLE,
  Overtime_Rate DOUBLE
);

INSERT INTO Hourly_Employee
  (Employee_ID, Hourly_Rate, Overtime_Rate)
VALUES
  (1001, 12.75, 1.5),
  (1002, 18.75, 1.5);

CREATE TABLE Salary_Employee (
  Employee_ID INT NOT NULL,
  Salary DOUBLE
);

INSERT INTO Salary_Employee 
  (Employee_ID, Salary)
VALUES
  (1003, 55000),
  (1004, 60000);

--generated always as identity automatically assigns value to Timecard_ID
CREATE TABLE Timecard (
  Timecard_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
  Timecard_Date DATE,
  Employee_ID INT,
  Hours_Worked DOUBLE,
  Overtime_Hours DOUBLE,
  
  PRIMARY KEY(Timecard_ID)
);

INSERT INTO Timecard
  (Timecard_Date, Employee_ID, Hours_Worked, Overtime_Hours)
VALUES
  ('03/25/2020', 1001, 8, 2.5),
  ('03/26/2020', 1001, 8, 1.0),
  ('03/25/2020', 1002, 8, 2.75),
  ('03/26/2020', 1002, 8, 1.75);