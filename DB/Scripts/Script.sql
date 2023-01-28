DROP TABLE users;

CREATE TABLE users
( 
user_id varchar primary key,  
user_name varchar,
password varchar,
role varchar,
designation varchar,
phone bigint,
email varchar (40),
rep_manager_id varchar(30)
);

INSERT INTO users VALUES 
('E001','Archana Devi','archu@123', 'Employee', 'Back end Developer', '9876543210','archu@gmail.com','E014'),
('E002','Vasunthra','vasu@123', 'Employee','Back end Developer', '8765432109','vasu@gmail.com','E014'),
('E003','Harish','hariz@123', 'Employee','Back end Developer', '8654321099','hariz@gmail.com','E014'),
('E004','Kowreesh','kow@123', 'Employee','Back end Developer', '8908908663','kow@gmail.com','E014'),
('E005','Ruthsan','ruthsan@123', 'Employee','Back end Developer', '9878978977','ruthsan@gmail.com','E014'),
('E006','Suthir','suthir@123', 'Employee','Back end Developer', '8766555738','suthir@gmail.com','E014'),
('E007','Abhinesh','abhinesh@123', 'Employee','Back end Developer', '9293474647','abhinesh@gmail.com','E014'),
('E008','Jaisri','jaisri@123', 'Employee','Front end Developer', '9878436282','jaisri@gmail.com','E015'),
('E009','Vishnupriya','vishnupriya@123', 'Employee','Front end Developer', '7896785674','vishnupriya@gmail.com','E015'),
('E010','Brundha','brundha@123', 'Employee','Front end Developer', '7689302222','brundha@gmail.com','E015'),
('E011','Hariharan','hari@123', 'Employee','Front end Developer', '6788493022','hari@gmail.com','E015'),
('E012','Nagadeepan','nagadeepan@123', 'Employee','Front end developer', '9234563822','nagadeepan@gmail.com','E015'),
('E013','Dhanush Praveen','ddp@123', 'Employee','Front end Developer', '9983438827','dhanushpraveen@gmail.com','E015'),
('E014','Jayaramu','jayaramu@123', 'Employee','Manager', '9999937644','jayaramu@gmail.com','E016'),
('E015','Prabhakar','prabhakar@123', 'Employee','Manager', '9891234562','prabhakar@gmail.com','E016'),
('E016','Praveen','praveen@123', 'Admin','Office Administrator', '6987738291','praveen@gmail.com','E016'),
('E017','Ashok','ashok@123', 'Employee','Senior Manager', '9234567822','ashok@gmail.com',null);

select * from users;