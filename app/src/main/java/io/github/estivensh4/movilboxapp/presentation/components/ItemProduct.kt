package io.github.estivensh4.movilboxapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.estivensh4.movilboxapp.domain.model.Product
import io.github.estivensh4.movilboxapp.presentation.theme.MovilBoxAppTheme
import io.github.estivensh4.movilboxapp.util.DataSource

@Composable
fun ItemProduct(
    product: Product,
    onClickItem: () -> Unit
) {
    Box {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            modifier = Modifier.padding(top = 30.dp),
            onClick = onClickItem
        ) {
            Column(
                modifier = Modifier
                    .width(140.dp)
                    .height(IntrinsicSize.Min)
                    .padding(
                        start = 32.dp,
                        end = 32.dp,
                        top = 100.dp,
                        bottom = 32.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = "\$ ${product.price}")
            }
        }
        CustomImageNetwork(
            imageUrl = product.thumbnail,
            modifier = Modifier.align(Alignment.TopCenter),
            size = 100.dp
        )
    }
}

@Preview
@Composable
fun ItemProductPrev() {
    MovilBoxAppTheme {
        ItemProduct(
            product = DataSource.product,
            onClickItem = {
                
            }
        )
    }
}