package com.assignment.view.city;

import com.assignment.repository.AppRepository;
import com.assignment.repository.model.City;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.Observable;
import rx.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Created by agni on 25/01/18.
 */
public class CityPresenterTest {

    @Mock
    AppRepository appRepository;

    @Mock
    CityContract.View view;

    private TestScheduler testScheduler;

    private CityPresenter presenter;

    @Mock
    List<City> cities;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Mock scheduler using RxJava TestScheduler.
        testScheduler = new TestScheduler();

        presenter = new CityPresenter(appRepository, view, testScheduler, testScheduler);
    }

    @Test
    public void loadCities_WhenDataIsAvailable_ShouldUpdateViews() {

        doReturn(Observable.just(cities)).when(appRepository).getCities(0,0);
        presenter.loadCities();
        testScheduler.triggerActions();

        verify(view).showCities(cities);
    }

}