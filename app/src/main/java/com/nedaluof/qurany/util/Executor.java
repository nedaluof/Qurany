package com.nedaluof.qurany.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nedaluof on 6/26/2020.
 */
public class Executor {
    public static void IOThread(Runnable t) {
        ExecutorService IO_EXECUTOR = Executors.newSingleThreadExecutor();
        IO_EXECUTOR.execute(t);
    }
}
