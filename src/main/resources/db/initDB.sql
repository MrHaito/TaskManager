DROP TABLE IF EXISTS tasks;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE tasks
(
    id   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name VARCHAR NOT NULL,
    section VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    is_weekly BOOL DEFAULT FALSE NOT NULL,
    is_monthly BOOL DEFAULT TRUE NOT NULL
);
CREATE UNIQUE INDEX tasks_unique_name_idx ON tasks (name);