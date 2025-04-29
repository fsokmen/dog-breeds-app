package com.example.myapplication.ui.breeds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.Screen

@Composable
fun BreedsScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: BreedsViewModel = hiltViewModel(),
) {

    LaunchedEffect(viewModel) {
        viewModel.event.collect { event ->
            when (event) {
                is BreedsViewModel.Event.NavigateToGallery -> {
                    navController.navigate(Screen.Gallery.createRoute(breed = event.breed))
                }
            }
        }
    }

    when (val uiState = viewModel.uiState.value) {
        BreedsViewModel.UiState.Loading -> {
            BreedLoadingView(
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifier)
            )
        }

        is BreedsViewModel.UiState.Success -> {
            BreedListView(
                breeds = uiState.breeds,
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifier),
                onClick = viewModel::onBreedClicked,
            )
        }

        is BreedsViewModel.UiState.Error -> {
            BreedLoadingErrorView(
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifier),
                onRetry = viewModel::onRefresh
            )
        }

    }
}

@Composable
fun BreedListView(breeds: List<String>, modifier: Modifier, onClick: (String) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(breeds) { breed ->
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.size_m))
                    .clickable(onClick = { onClick(breed) })
            ) {
                Text(
                    text = breed,
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.size_s))
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.size_m),
                    vertical = dimensionResource(R.dimen.size_zero),
                )
            )
        }
    }
}

@Composable
fun BreedLoadingView(modifier: Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .then(modifier)
    ) {
        CircularProgressIndicator(modifier = Modifier.size(32.dp))
    }
}

@Composable
fun BreedLoadingErrorView(modifier: Modifier = Modifier, onRetry: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                stringResource(R.string.error_loading_content_title),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                stringResource(R.string.error_loading_content_description),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text(stringResource(R.string.error_loading_retry))
            }
        }
    }
}