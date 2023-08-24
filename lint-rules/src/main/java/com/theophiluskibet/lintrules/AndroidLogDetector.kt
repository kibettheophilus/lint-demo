package com.theophiluskibet.lintrules

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

class AndroidLogDetector : Detector(), SourceCodeScanner {
    override fun getApplicableMethodNames(): List<String> =
        listOf("tag", "format", "v", "d", "i", "w", "e", "wtf")

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        super.visitMethodCall(context, node, method)
        val evaluator = context.evaluator
        if (evaluator.isMemberInClass(method, "android.util.Log")) {
            reportUsage(context, node)
        }
    }

    private fun reportUsage(context: JavaContext, node: UCallExpression) {
        context.report(
            issue = ISSUE,
            scope = node,
            location = context.getCallLocation(
                call = node,
                includeReceiver = true,
                includeArguments = true,
            ),
            message = "",
            quickfixData = LintFix.create().replace().text("Log.d").with("Timber.d").build(),
        )
    }

    companion object {
        private val IMPLEMENTATION = Implementation(
            AndroidLogDetector::class.java,
            Scope.JAVA_FILE_SCOPE,
        )

        val ISSUE = Issue.create(
            id = "AndroidLogDetector",
            briefDescription = "The android log should not be used.",
            explanation = """
                Using android log brings in inconsistency in our codebase, because some other parts are using Timber.
            """.trimIndent(),
            category = Category.CORRECTNESS,
            priority = 5,
            implementation = IMPLEMENTATION,
            severity = Severity.WARNING,
        )
    }
}
