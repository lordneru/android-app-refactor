package es.ipm.unir.weatherapp.utilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.ipm.unir.weatherapp.solution.model.APIManagerResponse;

public class APIManager {
    ExecutorService executorService;
    APIManagerResponse listener;

    public APIManager(APIManagerResponse listener) {
        this.listener = listener;
        executorService = Executors.newSingleThreadExecutor();
    }

    public <Data> void executeAPIRequest(final APIBaseRunnable<Data> request) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Data response = request.run();
                listener.onAPIResponse(response);
            }
        });
    }
}
