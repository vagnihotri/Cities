package com.assignment.repository;

import com.assignment.repository.model.City;

import java.util.List;

import rx.Observable;

/**
 * Created by agni on 24/01/18.
 */

public interface AppDataStore {
    Observable<List<City>> getCities(int limit, int offset);
}
