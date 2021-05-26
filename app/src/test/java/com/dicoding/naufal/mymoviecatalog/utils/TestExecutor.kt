package com.dicoding.naufal.mymoviecatalog.utils

import java.util.concurrent.Executor

class TestExecutor: Executor {
    override fun execute(command: Runnable?) {
        if (command != null) {
            command.run()
        }
    }
}