package io.github.estivensh4.movilboxapp.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.github.estivensh4.movilboxapp.presentation.components.CustomImageNetwork
import io.github.estivensh4.movilboxapp.presentation.theme.MovilBoxAppTheme
import io.github.estivensh4.movilboxapp.presentation.viewmodels.ProductDetailEvent
import io.github.estivensh4.movilboxapp.presentation.viewmodels.ProductDetailViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: Int,
    productDetailViewModel: ProductDetailViewModel = koinViewModel()
) {
    var favorite by remember { mutableStateOf(false) }
    val productDetailState by productDetailViewModel.productDetailState.collectAsStateWithLifecycle()
    val product = productDetailState.product
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        productDetailViewModel.onEvent(ProductDetailEvent.GetSingleProduct(productId))
        productDetailViewModel.onEvent(ProductDetailEvent.GetSingleLocalProduct(productId))
    }

    LaunchedEffect(productDetailState.localProduct) {
        favorite = productDetailState.localProduct != null
    }

    val pagerState = rememberPagerState { product?.images?.size ?: 0 }

    LaunchedEffect(productDetailState) {
        when (productDetailState.error.isNotEmpty()) {
            true -> scope.launch { snackbarHostState.showSnackbar(productDetailState.error) }
            false -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = product?.title ?: "...",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconToggleButton(
                        checked = favorite,
                        onCheckedChange = { newValue ->
                            favorite = newValue
                            product?.let {
                                productDetailViewModel.onEvent(
                                    ProductDetailEvent.ToggleLocalProduct(
                                        favorite,
                                        it
                                    )
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (favorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item {
                when {
                    productDetailState.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    product != null -> {
                        Card(
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomEnd = 16.dp,
                                bottomStart = 16.dp
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (product.images.isNotEmpty()) {
                                    HorizontalPager(state = pagerState) { page ->
                                        val item = product.images[page]
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CustomImageNetwork(
                                                imageUrl = item,
                                                size = 150.dp
                                            )
                                        }
                                    }
                                }
                                Row(
                                    Modifier
                                        .wrapContentHeight()
                                        .fillMaxWidth()
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 8.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    repeat(pagerState.pageCount) { iteration ->
                                        val color =
                                            if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                                        Box(
                                            modifier = Modifier
                                                .padding(2.dp)
                                                .clip(CircleShape)
                                                .background(color)
                                                .size(8.dp)
                                        )
                                    }
                                }
                            }

                        }
                        Column(modifier = Modifier.padding(24.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = product.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Box(
                                    modifier = Modifier.background(
                                        MaterialTheme.colorScheme.primary,
                                        RoundedCornerShape(6.dp)
                                    )
                                ) {
                                    Text(
                                        text = "\$${product.price}",
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.padding(
                                            horizontal = 4.dp,
                                            vertical = 2.dp
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFECD826)
                                )
                                Text(text = "${product.rating}")
                                Text(text = "|", modifier = Modifier.padding(horizontal = 8.dp))
                                if (product.stock <= 0) {
                                    Box(
                                        modifier = Modifier.background(
                                            MaterialTheme.colorScheme.error,
                                            RoundedCornerShape(6.dp)
                                        )
                                    ) {
                                        Text(
                                            text = "No stock",
                                            color = MaterialTheme.colorScheme.onError,
                                            modifier = Modifier.padding(
                                                horizontal = 4.dp,
                                                vertical = 2.dp
                                            )
                                        )
                                    }
                                } else {
                                    Text(
                                        text = "${product.stock} QTY"
                                    )
                                }

                            }
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                text = product.description
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductDetailScreenPrev() {
    MovilBoxAppTheme {
        ProductDetailScreen(navController = rememberNavController(), 1)
    }
}