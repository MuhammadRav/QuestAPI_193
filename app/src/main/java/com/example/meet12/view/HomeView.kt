package com.example.meet12.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meet12.CostumeTopAppBar
import com.example.meet12.navigasi.DestinasiNavigasi
import com.example.meet12.viewModel.HomeViewModel

object DestinasiHome : DestinasiNavigasi{
    override val route = "home"
    override val titleRes = "Home Mhs"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  navigateToItemEntry: () -> Unit,
  modifier: Modifier = Modifier,
  onDetailClick: (String) -> Unit = {},
  viewModel: HomeViewModel = viewModel()
//      factory = PenyediaViewModel.Factory
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getMhs()
                }
            )
        },
      floatingActionButton = {
          FloatingActionButton(
              onClick = navigateToItemEntry,
              shape = MaterialTheme.shapes.medium,
              modifier = Modifier.padding(18.dp)
          ) {
              Icon(imageVector = Icons.Default.Add,
                  contentDescription = "Add Mahasiswa")
          }
      },
    ){innerPadding ->
        HomeStatus(
            homeUiState = viewModel.mhsUiState,
            retryAction = { viewModel.getMhs()},
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDetailClick = {
                viewModel.deleteMhs(it.nim)
                viewModel.getMhs()
            }
        )
    }
}

