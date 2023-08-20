package com.saddict.reinvent.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.saddict.reinvent.R
import com.saddict.reinvent.model.local.ProductEntity

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    refreshAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (homeUiState) {
        is HomeUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> ProductsScreen(
            homeUiState.products,
            refreshAction = refreshAction,
            modifier = modifier.fillMaxWidth()
        )

        is HomeUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
//    ProductsScreen(products = productsUiState., modifier.fillMaxSize())
}

@Composable
fun ProductsScreen(products: List<ProductEntity?>, refreshAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        LazyColumn(modifier = Modifier) {
            items(products) {product ->
                if (product != null) {
                    ProductCard(
                        product = product,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    )
                }
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(onClick = refreshAction) {
                Text(text = stringResource(id = R.string.refreshDb))
            }
        }
    }

}

@Composable
fun ProductCard(product: ProductEntity, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.padding_small))
    ) {
        var expanded by remember { mutableStateOf(false) }
        val color by animateColorAsState(
            targetValue = if (expanded) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.onPrimary,
            label = "expandColor",
        )
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
                .background(color = color)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_small)),
            ) {
                ProductImage(image = product.imageDetail)
                ProductInfo(product = product)
                Spacer(modifier = Modifier.weight(1f))
                ProductItemButton(expanded = expanded, onClick = { expanded = !expanded })
            }
            if (expanded) {
                ExtraProductInfo(
                    product = product,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
            }
        }
    }
}

@Composable
fun ProductImage(image: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(image)
            .crossfade(true).build(),
        error = painterResource(R.drawable.ic_broken_image),
        placeholder = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.p_image),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(dimensionResource(id = R.dimen.image_size))
            .padding(dimensionResource(id = R.dimen.padding_small))
            .clip(MaterialTheme.shapes.medium)
    )
}

@Composable
fun ProductInfo(product: ProductEntity, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        val productList = listOf(product)
        productList.forEach {
            Text(
                text = "${it.productName}",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
            )
            Text(
                text = "${it.modelNumber}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun ExtraProductInfo(product: ProductEntity, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        val productList = listOf(product)
        productList.forEach {
            Text(
                text = "${it.specifications}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${it.price}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${it.image}",
                style = MaterialTheme.typography.bodyLarge
            )
//            Text(
//                text = "${it.imageDetail}",
//                style = MaterialTheme.typography.bodyLarge
//            )
            Text(
                text = "${it.category}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${it.supplier}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun ProductItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "loader transition")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Restart
        ), label = "loader"
    )
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp)
                .rotate(angle),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}