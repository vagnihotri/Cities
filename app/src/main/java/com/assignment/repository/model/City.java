package com.assignment.repository.model;

import com.assignment.repository.database.Contract;
import com.pushtorefresh.storio.contentresolver.annotations.StorIOContentResolverColumn;
import com.pushtorefresh.storio.contentresolver.annotations.StorIOContentResolverType;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by agni on 24/01/18.
 */

@StorIOSQLiteType(table = Contract.City.TABLE_NAME)
@StorIOContentResolverType(uri = Contract.City.CONTENT_URI_STRING)
public class City {

    @StorIOSQLiteColumn(name = Contract.City.COLUMN_ID, key = true)
    @StorIOContentResolverColumn(name = Contract.City.COLUMN_ID, key = true)
    public Integer id;

    @StorIOSQLiteColumn(name = Contract.City.COLUMN_NAME, key = true)
    @StorIOContentResolverColumn(name = Contract.City.COLUMN_NAME, key = true)
    public String name;

    public City() {

    }

    public City(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
