create table if not exists user (
    userId          varchar2(250) primary key,
    login           varchar2(250) not null,
    password        varchar2(250) not null,
    email           varchar2(250) not null
);

create table if not exists session (
   userId          varchar2(250) primary key,
   sessionId       varchar2(500) not null
);

ALTER TABLE session ADD FOREIGN KEY (userId)
    REFERENCES user(userId);