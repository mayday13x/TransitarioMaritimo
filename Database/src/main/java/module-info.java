open module pt.ipvc.database {

    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.sql;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.data.jpa;
    requires spring.core;
    requires spring.beans;
    requires spring.jdbc;
    requires spring.data.commons;


    exports pt.ipvc.database.repository;
    exports pt.ipvc.database.entity;
    exports pt.ipvc.database;
}