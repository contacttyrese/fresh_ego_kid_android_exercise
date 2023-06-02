package com.example.freshegokidproject.helpers

import java.util.concurrent.atomic.AtomicBoolean

class UiTestHelper {

    private var isRunningTest: AtomicBoolean = AtomicBoolean()

    @Synchronized
    fun isRunningTest(): Boolean {
        val isTest: Boolean = try {
            // "android.support.test.espresso.Espresso" if you haven't migrated to androidx yet
            Class.forName("androidx.test.espresso.Espresso")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
        isRunningTest = AtomicBoolean(isTest)
        return isRunningTest.get()
    }

}