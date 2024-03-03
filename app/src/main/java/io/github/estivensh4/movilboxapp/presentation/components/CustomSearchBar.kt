package io.github.estivensh4.movilboxapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.estivensh4.movilboxapp.util.FilterList

@Composable
fun CustomSearchBar(
    searchProduct: String,
    modifier: Modifier = Modifier,
    onSearchProductChange: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    searchHistory: SnapshotStateList<String>,
    active: Boolean,
    onClickFilter: (String) -> Unit
) {
    var showPopup by rememberSaveable { mutableStateOf(false) }

    Column {
        SearchBar(
            modifier = modifier.fillMaxWidth(),
            query = searchProduct,
            onQueryChange = onSearchProductChange,
            onSearch = onSearch,
            active = active,
            onActiveChange = onActiveChange,
            placeholder = {
                Text(text = "Search product")
            },
            trailingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (active) {
                        Icon(
                            modifier = Modifier.clickable {
                                if (searchProduct.isNotEmpty()) {
                                    onSearchProductChange("")
                                } else {
                                    onActiveChange(false)
                                }
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close icon"
                        )
                    }
                    IconButton(onClick = {
                        showPopup = !showPopup
                    }) {
                        Icon(imageVector = Icons.Rounded.FilterList, contentDescription = null)
                    }
                }
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
            }
        ) {
            searchHistory.forEach {
                if (it.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .padding(all = 14.dp)
                            .clickable {
                                onSearchProductChange(it)
                                onActiveChange(false)
                            }
                    ) {
                        Icon(imageVector = Icons.Default.History, contentDescription = null)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = it)
                    }
                }
            }
        }
        PopupBox(showPopup = showPopup, onClickOutside = { showPopup = false }) {
            FilterList.getList().forEach {
                TextButton(onClick = {
                    showPopup = false
                    onClickFilter(it.name)
                }) {
                    Text(text = it.name)
                }
            }
        }
    }
}