package com.theophiluskibet.lintrules

import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.checks.infrastructure.TestFiles.java
import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class AndroidLogDetectorTest {
    @Test
    fun testBasis() {
        lint()
            .issues(AndroidLogDetector.ISSUE)
            .files(
                LOG_STUB,
                kotlin(
                    """
                          package test.pkg

                          import android.util.Log

                          class HomeViewModel {
                               fun getData() {
                                   android.util.Log.d("HOME", "This is home")
                               }
                          }
                """
                ).indented()

            )
            .allowMissingSdk()
            .run()
            .expect(
                """
                   No warnings.
            """
            )

    }

    private companion object {
        private val LOG_STUB: TestFile = java(
            """
                package android.util;

                interface NonNull{}
                interface Nullable{}
         
                public final class Log {
                    public static int d(@Nullable String tag, @NonNull String msg) {
                        throw new RuntimeException("Stub!");
                    }
                }
            """.trimIndent()
        )
    }
}

