package net.dirox.c4p.di.module

import net.dirox.c4p.ui.activity.demo.UserGitHubViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<UserGitHubViewModel>()
}