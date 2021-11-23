package com.akurat

import com.akurat.plugins.appModule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkKoinModules

@Category(CheckModuleTest::class)
class CheckModulesTest {

    @Test
    fun `check modules`() = checkKoinModules {
        appModule
    }
}