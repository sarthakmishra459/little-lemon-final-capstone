package com.example.littlelemoncoursera.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.littlelemoncoursera.R
import com.example.littlelemoncoursera.model.Dish
import com.example.littlelemoncoursera.navigation.Routes
import com.example.littlelemoncoursera.ui.screens.components.LogoImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(navController: NavController) {
    Scaffold(
        topBar = {
            HomeAppBar(
                onNavigateToSearchPage = {
                    navController.navigate(Routes.SEARCH.name)
                }
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            HomeHero(
                onSearch = {
                    navController.navigate(Routes.SEARCH.name)
                }
            )
            OrderForDelivery(categories = listOf("Starters","Main","Dessert","Size"))
            Divider()
            DishesList(
                dishesList = listOf<Dish>(
                    Dish(1,"Grilled Fish","Our Bruschetta is made from grilled bread that has been smeared with garlic and seasoned with salt and olive oil.","10","https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/grilledFish.jpg?raw=true","mains"),
                    Dish(2,"Pasta","Our Bruschetta is made from grilled bread that has been smeared with garlic and seasoned with salt and olive oil.","10","https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/pasta.jpg?raw=true","mains"),
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(onNavigateToSearchPage: () -> Unit) {
    TopAppBar(
        title = {
            LogoImage(size = 100.dp)
        },
        actions = {
            IconButton(
                onClick = { onNavigateToSearchPage() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "Search Items",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                )
            }
        }
    )
}

@Composable
fun HomeHero(onSearch: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(color = MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(horizontal = 15.dp)
            ) {
                Text(
                    text = "Little Lemon",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Text(
                    text = "Chicago",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = stringResource(id = R.string.hero_desc),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Little Lemon",
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(15))
                    .weight(1F)
                    .padding(horizontal = 15.dp),
                contentScale = ContentScale.Crop

            )
        }
        SearchBoxOnHero(onSearch = onSearch)
    }
}

@Composable
fun SearchBoxOnHero(onSearch:()->Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .clip(RoundedCornerShape(15))
            .clickable {
                onSearch()
            }
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = "Search",
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            Text(
                text = "Search Menu",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
fun OrderForDelivery(categories:List<String>) {
    Column(
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp)
    ) {
        Text(
            text = "Order For Delivery!".uppercase(),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
        )
        LazyRow(
            modifier = Modifier.padding(vertical = 20.dp)
        ){
            items(
                count = categories.size, 
                itemContent = {
                    CategoryPill(label = categories[it])
                }
            )
        }
    }
}

@Composable
fun CategoryPill(label:String) {
    Surface(
        color = MaterialTheme.colorScheme.primary.copy(alpha = .3F),
        modifier = Modifier
            .padding(end = 10.dp)
            .clip(RoundedCornerShape(50)),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        )
    }
}

@Composable
fun DishesList(dishesList:List<Dish>) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 10.dp)
    ){
        items(
            dishesList.size,
            itemContent = {
                DishItem(dishItem = dishesList[it])
            }
        )
    }
}

@Composable
fun DishItem(dishItem:Dish) {
    Column(
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(2F)
            ) {
                Text(
                    text = dishItem.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = dishItem.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = "$"+dishItem.price,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
            AsyncImage(
                model = dishItem.image,
                contentDescription = dishItem.title,
                modifier = Modifier
                    .weight(1F)
                    .clip(RoundedCornerShape(15))
            )
        }
        Divider(
            modifier = Modifier.padding(top = 15.dp)
        )
    }
}

