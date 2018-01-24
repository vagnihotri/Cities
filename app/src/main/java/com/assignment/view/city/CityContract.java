package com.assignment.view.city;

import com.assignment.repository.model.City;
import com.assignment.view.BasePresenter;
import com.assignment.view.BaseView;

import java.util.List;

/**
 * Created by agni on 24/01/18.
 */

public class CityContract {
    interface View extends BaseView<Presenter> {

        void showCities(List<City> cities);

        void showError(String message);

        void showComplete();
    }

    interface Presenter extends BasePresenter {
        void loadCities();
        void loadCitiesFromNetwork(int limit, int offset);
    }
}
