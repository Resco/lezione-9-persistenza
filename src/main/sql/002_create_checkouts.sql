
create table checkouts(
	id varchar(255) not null,
	total integer,
	primary key (id)
);

update schema_info set version = 2;