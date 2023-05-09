CREATE TABLE student
(
    id SERIAL PRIMARY KEY ,
    name varchar (128),
    phone varchar (128),
    email varchar (128),
    course_id int references course(id)

);

CREATE  TABLE course
(
    id SERIAL PRIMARY KEY ,
    name varchar (128)
);

CREATE  TABLE  student_profile
(
    id SERIAL PRIMARY KEY ,
    mark numeric not null,
    student_id int references student(id)
);
CREATE  TABLE  trainer
(
    id SERIAL PRIMARY KEY ,
    name varchar (128),
    phone varchar (128),
    email varchar (128)
);
CREATE TABLE info
(
    id serial primary key ,
    trainer_id int references trainer(id),
    course_id int references course(id)
);

