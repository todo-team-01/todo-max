DROP TABLE IF EXISTS columns;
DROP TABLE IF EXISTS card;
DROP TABLE IF EXISTS history;


CREATE TABLE IF NOT EXISTS columns (
    id      bigint          NOT NULL auto_increment,
    name    varchar(50)     NOT NULL,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS card (
    id            bigint   NOT NULL auto_increment,
    title  varchar(50)      NOT NULL,
    content  varchar(50)      NOT NULL,
    column_id   bigint         NOT NULL,
    position bigint NOT NULL,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS history (
    id        bigint      NOT NULL auto_increment,
    content      varchar(500) NOT NULL,
    created_at datetime    NOT NULL,
    is_deleted boolean NOT NULL,
    PRIMARY KEY(id)
    );
