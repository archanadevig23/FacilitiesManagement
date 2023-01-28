drop table issues;

create table issues 
(
	issue_id varchar primary key,
	type varchar,
	category varchar,
	sla VARCHAR,
	description varchar,
	status varchar,
	comments varchar,
	assignee_user_id varchar,
	created_date timestamp,
	deadline_date timestamp,
	reviewed_date timestamp,
	completed_date timestamp,
	closed_date timestamp
);

select * from issues;