-- Queries Used

-- 

select * from movie 
where title = 'USERINPUT1' and releasedate = 'USERINPUT2';

select title, releasedate from movie;

select title, releasedate, 'USERINPUT-ATTRIBUTES' from movie 
where releasedate like 'USERINPUT1';

select * from actor where name = 'USERINPUT1' and birthday = 'USERINPUT2';

select * from review where id = 'USERINPUT1';

-- 

select m.title, m.releasedate from movie m 
where not exists((select u1.accountname from users u1)
minus (select u2.accountname from users u2, review r
where r.rating = 'USERINPUT' and u2.accountname = r.accountname and r.title = m.title and r.releasedate = m.releasedate));

-- 

insert into review (text, postdate, rating, id, accountname, title, releasedate) VALUES (?, ?, ?, seqR.NEXTVAL, ?, ?, ?);

update review set text = ?, rating = ?, postdate = ? where id = ?;

-- 

delete from movie where title = ? and releasedate = ?;

-- 

select 'USERINPUT'(rating) as rating from movie m, review r 
where m.title = r.title and m.releasedate = r.releasedate and m.title = 'USERINPUT1' and m.releasedate = 'USERINPUT2';

select count(title) as moviecount from acts_in a where a.name = 'USERINPUT1' and a.birthday = 'USERINPUT2';

-- 

SELECT temp.name, temp.birthday, temp.nationality
FROM (SELECT a.name, a.birthday, a.nationality, count(i.title) AS mcount
FROM actor a, acts_in i 
where a.name = i.name and a.birthday = i.birthday 
GROUP BY a.name, a.birthday, a.nationality) temp 
WHERE temp.mcount >= all (SELECT count(i.title) FROM actor a, acts_in i 
where a.name = i.name and a.birthday = i.birthday 
GROUP BY a.name, a.birthday);

SELECT temp.name, temp.birthday, temp.nationality
FROM (SELECT a.name, a.birthday, a.nationality, count(i.title) AS mcount
FROM actor a, acts_in i 
where a.name = i.name and a.birthday = i.birthday 
GROUP BY a.name, a.birthday, a.nationality) temp 
WHERE temp.mcount <= all (SELECT count(i.title) FROM actor a, acts_in i 
where a.name = i.name and a.birthday = i.birthday 
GROUP BY a.name, a.birthday);

SELECT temp.title as title, temp.releasedate as releasedate, temp.rating as rating
FROM (SELECT m.title, m.releasedate, 'USERINPUT'(rating) AS rating 
FROM movie m, review r 
where m.title = r.title and m.releasedate = r.releasedate 
GROUP BY m.title, m.releasedate) temp 
WHERE temp.rating <= all (SELECT 'USERINPUT'(rating) AS rating FROM movie m, review r 
where m.title = r.title and m.releasedate = r.releasedate 
GROUP BY m.title, m.releasedate);

SELECT temp.title as title, temp.releasedate as releasedate, temp.rating as rating
FROM (SELECT m.title, m.releasedate, 'USERINPUT'(rating) AS rating 
FROM movie m, review r 
where m.title = r.title and m.releasedate = r.releasedate 
GROUP BY m.title, m.releasedate) temp 
WHERE temp.rating >= all (SELECT 'USERINPUT'(rating) AS rating FROM movie m, review r 
where m.title = r.title and m.releasedate = r.releasedate 
GROUP BY m.title, m.releasedate);

SELECT 'USERINPUT1'(temp.rating) as rating 
FROM (SELECT m.title, m.releasedate, 'USERINPUT2'(rating) AS rating 
FROM movie m, review r where m.title = r.title and m.releasedate = r.releasedate 
GROUP BY m.title, m.releasedate) temp;

SELECT * FROM users WHERE accountname = 'USERINPUT';

