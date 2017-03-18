--
-- 	Database Table Creation
--
--  run script using START file_name
--
--  First drop any existing tables. Any errors are ignored.
--
drop table movie cascade constraints;
drop table actor cascade constraints;
drop table acts_in cascade constraints;
drop table users cascade constraints;
drop table favourite cascade constraints;
drop table comments cascade constraints;
drop table review cascade constraints;
drop table upvote cascade constraints;

drop sequence seqC;
drop sequence seqR;

--
-- Now, add each table.
--
create table movie ( 
	title varchar(50) not null, 
	releasedate varchar(10) not null, 
	director varchar(20), 
	distributedcompany varchar(30), 
	PRIMARY KEY (title, releasedate) 
);
create table actor ( 
	name varchar(40) not null, 
	birthday varchar(10) not null, 
	nationality varchar(30), 
	PRIMARY KEY (name, birthday) 
);
create table users ( 
	accountname varchar(25) not null PRIMARY KEY, 
	email varchar(35), 
	birthday varchar(10) 
);
create table favourite ( 
	title varchar(50) not null, 
	releasedate varchar(10) not null, 
	accountname varchar(25) not null, 
	foreign key (title, releasedate) references movie on delete cascade, 
	foreign key (accountname) references users on delete cascade, 
	PRIMARY KEY (title, releasedate, accountname) 
);
create table acts_in ( 
	title varchar(50) not null,  
	releasedate varchar(10) not null, 
	name varchar(40) not null, 
	birthday varchar(10) not null, 
	foreign key (title, releasedate) references movie on delete cascade, 
	foreign key (name, birthday) references actor on delete cascade, 
	PRIMARY KEY (title, releasedate, name, birthday) 
);
create table comments ( 
	text varchar(500), 
	postdate varchar(10), 
	upvotes integer, 
	ID integer not null PRIMARY KEY, 
	accountname varchar(25) not null, 
	title varchar(50) not null, 
	releasedate varchar(10) not null, 
	foreign key (accountname) references users on delete cascade, 
	foreign key (title, releasedate) references movie on delete cascade 
);
create table review ( 
	text varchar(800), 
	postdate varchar(10), 
	rating integer CHECK (rating>=1 AND rating<=5), 
	ID integer not null PRIMARY KEY, 
	accountname varchar(25) not null, 
	title varchar(50) not null, 
	releasedate varchar(10) not null, 
	foreign key (accountname) references users on delete cascade, 
	foreign key (title, releasedate) references movie on delete cascade 
);
create table upvote ( 
	accountname varchar(25) not null, 
	id integer not null, 
	foreign key (accountname) references users on delete cascade, 
	foreign key (id) references comments on delete cascade, 
	PRIMARY KEY (accountname, ID) 
);

CREATE SEQUENCE seqC start with 1 increment by 1;
CREATE SEQUENCE seqR start with 1 increment by 1;
--
-- done adding all of the tables, now add in some tuples
-- insert into movie
insert into movie values('Remember the Titans', '2000-09-23', 'Boaz Yakin', 'Buena Vista Pictures');
insert into movie values('Mona Lisa Smile', '2003-12-19', 'Mike Newell', 'Columbia Pictures');
insert into movie values('Saving Private Ryan', '1998-07-24', 'Steven Spielberg', 'DreamWorks Pictures');
insert into movie values('Harry Potter', '2001-11-04', 'Chris Columbus', 'Warner Bros. Pictures');
insert into movie values('Coming Home', '2014-05-16', 'Zhang Yimou', 'Sony Pictures Classics');
-- insert into actor
insert into actor values('Tom Hanks', '1956-07-09', 'American');
insert into actor values('Gong Li', '1965-12-31', 'Singaporean');
insert into actor values('Daniel Radcliffe', '1989-07-23', 'British');
insert into actor values('Edward Burns', '1968-01-29', 'American');
insert into actor values('Julia Roberts', '1967-10-28', 'American');
-- insert into users
insert into users values('foobar', 'foo_bar@gmail.com', '1989-02-15');
insert into users values('helloworld', 'helloworld123@hotmail.com', '1992-08-18');
insert into users values('deadbeef', '404notfound@gmail.com', '1995-01-02');
insert into users values('gameofthreads', 'diningphilosophers@hotmail.com', '1986-06-30');
insert into users values('bobbytables', 'xkcd@hotmail.com', '1964-12-24');
-- insert into favourite
insert into favourite values('Coming Home', '2014-05-16', 'bobbytables');
insert into favourite values('Harry Potter', '2001-11-04', 'bobbytables');
insert into favourite values('Mona Lisa Smile', '2003-12-19', 'deadbeef');
insert into favourite values('Saving Private Ryan', '1998-07-24', 'foobar');
insert into favourite values('Remember the Titans', '2000-09-23', 'helloworld');
-- insert into acts_in
insert into acts_in values('Harry Potter', '2001-11-04', 'Daniel Radcliffe', '1989-07-23');
insert into acts_in values('Coming Home', '2014-05-16', 'Gong Li', '1965-12-31');
insert into acts_in values('Saving Private Ryan', '1998-07-24', 'Edward Burns', '1968-01-29');
insert into acts_in values('Mona Lisa Smile', '2003-12-19', 'Julia Roberts', '1967-10-28');
insert into acts_in values('Saving Private Ryan', '1998-07-24', 'Tom Hanks', '1956-07-09');
-- insert into comments
insert into comments values('hahaha', '2003-03-05', 2, seqC.NEXTVAL, 'foobar', 'Remember the Titans', '2000-09-23');
insert into comments values('Well,well,well', '2005-10-03', 0, seqC.NEXTVAL, 'bobbytables', 'Harry Potter', '2001-11-04');
insert into comments values('omg', '2004-01-25', 2, seqC.NEXTVAL, 'gameofthreads', 'Mona Lisa Smile', '2003-12-19');
insert into comments values('hehehe', '1998-08-13', 1, seqC.NEXTVAL, 'helloworld', 'Saving Private Ryan', '1998-07-24');
insert into comments values('what a movie', '2016-01-10', 0, seqC.NEXTVAL, 'helloworld', 'Coming Home', '2014-05-16');
-- insert into review
insert into review values ('The actors did a phenomenal job.', '2000-10-30', 4, seqR.NEXTVAL, 'helloworld', 'Remember the Titans', '2000-09-23');
insert into review values ('I didn’t think I would like a movie about football, but it was amazing!', '2001-02-16', 3, seqR.NEXTVAL, 'foobar', 'Remember the Titans', '2000-09-23');
insert into review values ('Watched it in the cinema, watched again at home', '2012-08-25', 4, seqR.NEXTVAL, 'foobar', 'Saving Private Ryan', '1998-07-24');
insert into review values ('It was ok. I liked the book better.', '2016-12-12', 2, seqR.NEXTVAL, 'deadbeef', 'Harry Potter', '2001-11-04');
insert into review values ('This movie is amazing. I love the way how the director tells the story..', '2008-03-25', 5, seqR.NEXTVAL, 'deadbeef', 'Mona Lisa Smile', '2003-12-19');

insert into review values ('Good', '2007-03-25', 5, seqR.NEXTVAL, 'foobar', 'Mona Lisa Smile', '2003-12-19');
insert into review values ('Thumbs up', '2002-03-25', 5, seqR.NEXTVAL, 'helloworld', 'Mona Lisa Smile', '2003-12-19');
insert into review values ('Best', '2001-03-25', 5, seqR.NEXTVAL, 'bobbytables', 'Mona Lisa Smile', '2003-12-19');
insert into review values ('Great', '2000-03-25', 5, seqR.NEXTVAL, 'gameofthreads', 'Mona Lisa Smile', '2003-12-19');

-- insert into upvote
insert into upvote values('helloworld', 2);
insert into upvote values('gameofthreads', 2);
insert into upvote values('bobbytables', 3);
insert into upvote values('deadbeef', 4);
insert into upvote values('foobar', 3);
