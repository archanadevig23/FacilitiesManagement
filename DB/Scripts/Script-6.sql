drop table issues;

create table issues 
(
	id int primary key,
	issue_id varchar,
	type varchar,
	category varchar,
	sla VARCHAR,
	description varchar,
	priority VARCHAR,
	status varchar,
	comments varchar,
	assignee_user_id varchar,
	assignee_name varchar,
	created_date timestamp,
	deadline_date timestamp,
	reviewed_date timestamp,
	completed_date timestamp,
	closed_date timestamp
);

select * from issues;

create table categories
(
	category_id int primary key,
	category_name VARCHAR
);

select * from categories;

drop table closed_issues;

create table closed_issues 
(
	id int primary key,
	issue_id varchar,
	type varchar,
	category varchar,
	sla VARCHAR,
	description varchar,
	priority VARCHAR,
	status varchar,
	comments varchar,
	assignee_user_id varchar,
	assignee_name varchar,
	created_date timestamp,
	deadline_date timestamp,
	reviewed_date timestamp,
	completed_date timestamp,
	closed_date timestamp
);

select * from closed_issues;