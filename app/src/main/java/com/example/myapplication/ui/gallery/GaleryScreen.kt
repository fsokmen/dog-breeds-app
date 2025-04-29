package com.example.myapplication.ui.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.myapplication.R

@Composable
fun GalleryScreen(
    modifier: Modifier,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    when (val uiState = viewModel.uiState.value) {
        GalleryViewModel.UiState.Loading -> {
            GalleryLoadingView(
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifier)
            )
        }

        is GalleryViewModel.UiState.Success -> {
            GalleryView(
                galleryUrls = uiState.urls,
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifier),
            )
        }

        is GalleryViewModel.UiState.Error -> {
            GalleryLoadingErrorView(
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifier),
                onRetry = viewModel::onRefresh
            )
        }

    }
}

@Composable
fun GalleryView(galleryUrls: List<String>, modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        items(galleryUrls, key = { it }) { url ->
            GalleryItemView(url)
        }
    }
}

@Composable
fun GalleryItemView(url: String) {
    val sizeResolver = rememberConstraintsSizeResolver()
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(url)
            .crossfade(true)
            .build(),
    )
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = sizeResolver.fillMaxWidth()
    )
}

@Composable
fun GalleryLoadingView(modifier: Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .then(modifier)
    ) {
        CircularProgressIndicator(modifier = Modifier.size(dimensionResource(R.dimen.size_l)))
    }
}

@Composable
fun GalleryLoadingErrorView(modifier: Modifier = Modifier, onRetry: () -> Unit) {
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
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_m)))
            Button(onClick = onRetry) {
                Text(stringResource(R.string.error_loading_retry))
            }
        }
    }
}