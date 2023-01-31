drop table categories;

create table categories
(
	category_id int primary key,
	category_name VARCHAR
);

select * from categories;

insert into categories values 
(1, 'Restroom'),
(2, 'Canteen'),
(3, 'Parking'),
(4, 'Reception'),
(5, 'Conference hall');