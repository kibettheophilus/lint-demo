package com.theophiluskibet.lintrules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

class LintDemoIssueRegistry : IssueRegistry() {
    override val issues: List<Issue> = listOf(AndroidLogDetector.ISSUE)

    override val api: Int = CURRENT_API

    override val minApi: Int
        get() = 8

    override val vendor: Vendor = Vendor(
        vendorName = "Theophilus Kibet",
        feedbackUrl = "https://github.com/kibettheophilus/lint-demo/issues",
        contact = "kibettheophilus@gmail.com",
    )
}
