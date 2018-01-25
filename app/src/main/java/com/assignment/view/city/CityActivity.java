package com.assignment.view.city;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.assignment.MainApplication;
import com.assignment.R;
import com.assignment.repository.AppRepository;
import com.assignment.repository.model.City;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by agni on 24/01/18.
 */

public class CityActivity extends AppCompatActivity implements CityContract.View, SwipeRefreshLayout.OnRefreshListener {

    private CityContract.Presenter presenter;
    private RecyclerView recyclerView;
    private CityAdapter cityAdapter;
    private ArrayList<String> list;
    private LinearLayoutManager layoutManager;
    private static final int RESULT_LIMIT = 10;
    private DividerItemDecoration divider;
    private ProgressDialog progressDialog;
    private int totalCount;
    private boolean isLoading;

    @Inject
    AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_city);
        MainApplication.getAppComponent().inject(this);

        recyclerView = findViewById(R.id.recycler_cities);
        list = new ArrayList<>();
        layoutManager = new LinearLayoutManager(CityActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        divider = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());

        new CityPresenter(repository, this,
                Schedulers.newThread(), AndroidSchedulers.mainThread());
        showLoading();
    }

    @Override
    public void showCities(final List<City> cities) {
        totalCount = cities.get(0).count;
        stopLoading();
        list.clear();
        for (int i = 0; i < cities.size(); i++) {
            list.add(cities.get(i).getName());
        }
        cityAdapter = new CityAdapter(this, list);
        recyclerView.removeItemDecoration(divider);
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(cityAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                int totalItemCount = layoutManager.getItemCount();
                int visibleItemCount = layoutManager.getChildCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && dy >= 0 && (visibleItemCount + firstVisibleItem) >= totalItemCount)
                {
                    if(cityAdapter.getItemCount() < totalCount) {
                        showLoading();
                        presenter.loadCitiesFromNetwork(RESULT_LIMIT, cityAdapter.getItemCount());
                    }
                }
            }
        });
    }

    private void showLoading() {
        isLoading = true;
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading");
        }
        progressDialog.show();
    }

    private void stopLoading() {
        isLoading = false;
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError(String message) {
        stopLoading();
    }

    @Override
    public void showComplete() {
        stopLoading();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unSubscribe();
    }

    @Override
    public void setPresenter(CityContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onRefresh() {
        showLoading();
        presenter.loadCitiesFromNetwork(0,0);
    }
}

