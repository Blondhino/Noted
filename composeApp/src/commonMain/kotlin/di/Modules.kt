package di

import com.eniobiondic.noted.TestDiViewmodel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule =
    module {
        viewModelOf(::TestDiViewmodel)
    }
