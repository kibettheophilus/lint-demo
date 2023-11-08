package com.theophiluskibet.lintrules

import com.android.tools.lint.checks.infrastructure.TestFiles.java
import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class AndroidLogDetectorTest {
    @Test
    fun testBasis() {
        lint().files(
//            java(
//                """
//                    package test.pkg;
//                    public class MainViewModel {
//
//                        void getData() {
//                            android.util.Log("HOME", "This is home");
//                        }
//                    }
//                """
//            ).indented()

            kotlin(
                """
                          package test.pkg
                          class HomeViewModel {
                               fun getData() {
                                   android.util.Log("HOME", "This is home")
                               }
                          }
                """
            ).indented()

        )
            .allowMissingSdk()
            .issues(AndroidLogDetector.ISSUE)
            .run()
            .expect(
                """
                   No warnings.
            """
            )

    }
}
