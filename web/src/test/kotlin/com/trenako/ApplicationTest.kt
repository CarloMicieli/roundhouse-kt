package com.trenako

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class ApplicationTest : ShouldSpec({
    should("simply work") {
        val app = Application()
        app.run() shouldBe "works"
    }
})