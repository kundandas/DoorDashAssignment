package com.kdas.ddash.network;

import android.support.annotation.WorkerThread;

public interface NetworkServiceProvider {

    @WorkerThread
    NetworkResponse makeGETRequest(String url);
}
