package com.steve_md.socialsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.steve_md.socialsapp.model.Posts
import com.steve_md.socialsapp.ui.theme.SocialsAppTheme
import com.steve_md.socialsapp.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalCoilApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val postsViewModel : PostsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val allPosts = postsViewModel.postsResponse
                    postsViewModel.getAllPosts()

                    // Get a list of all Posts
                    AllPosts(postList = allPosts)
                }
            }
        }
    }
}


@ExperimentalCoilApi
@Composable
fun AllPosts(postList: List<Posts>) {
    LazyColumn {
        items(postList) { item: Posts ->
            Posts(posts = item)
        }
    }
}


@ExperimentalCoilApi
@Composable
fun Posts(posts: Posts) {
  Card(
      modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
          .height(150.dp),
      shape = RoundedCornerShape(10.dp),
      elevation = 6.dp
  ) {
      Surface {
          Row(
              modifier = Modifier
                  .padding(8.dp)
                  .fillMaxSize()
          ) {
           
              Image(
                  painter = rememberAsyncImagePainter(
                      ImageRequest.Builder(LocalContext.current).data(data = posts.url).apply(block = fun ImageRequest.Builder.() {
                          Scale.FILL
                          transformations(CircleCropTransformation())
                      }).build()
                  ),
                  contentDescription = posts.title
              )
              Column(
                  verticalArrangement = Arrangement.Center,
                  modifier = Modifier
                      .fillMaxHeight()
                      .padding(4.dp)
              ) {
                  Text(
                      modifier = Modifier
                          .padding(2.dp),
                      style = MaterialTheme.typography.subtitle1,
                      fontWeight = FontWeight.Bold,
                      text = posts.title
                  )
                  Text(
                      modifier = Modifier
                          .padding(2.dp),
                      style = MaterialTheme.typography.h3,
                      fontWeight = FontWeight.Medium,
                      text = "${posts.albumId}"
                  )
                  Text(
                      modifier = Modifier
                          .padding(2.dp),
                      style = MaterialTheme.typography.h3,
                      fontWeight = FontWeight.Medium,
                      text = "${posts.id}"
                  )
              }
           }
      } 
  }
}


