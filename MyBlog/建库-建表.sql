create database My_blog charset utf8mb4;
use My_blog;

-- 建立用户表
create table user (
    id int primary key auto_increment,
    username varchar(40) not null unique,
    password varchar(100) not null
);

-- 建立文章表
create table article (
    id int primary key auto_increment,
    user_id int not null,
    title varchar(200) not null,
    content text not null,
    published_at datetime not null
);

select
    user.id author_id,
    user.username author_username,
    article.id,
    title,
    published_at
from article join user on article.user_id = user.id
order by published_at desc;