package mx.com.wolf.shop.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * Created by Jose Barrera on 23/06/2018.
 * Copyright (c) 2018 Anzen Digital All rights reserved.
 */
@Singleton
class ApplicationExecutors(
        val diskIO: Executor,
        val mainThread: Executor
) {
    companion object {
        const val TREAD_LIMIT = 3
    }

    class MainThreadExecutor(
            private val mainHandler: Handler = Handler(Looper.getMainLooper())
    ): Executor {
        override fun execute(command: Runnable?) {
            mainHandler.post(command)
        }
    }

    class DiskIOThreadExecutor(
            private val diskIO: Executor = Executors.newSingleThreadExecutor()
    ): Executor {
        override fun execute(command: Runnable?) = diskIO.execute(command)
    }
}