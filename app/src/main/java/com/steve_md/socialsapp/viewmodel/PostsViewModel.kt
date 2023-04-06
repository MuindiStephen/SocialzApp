package com.steve_md.socialsapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steve_md.socialsapp.data.api.SocialsRetrofitService
import com.steve_md.socialsapp.model.Posts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostsViewModel @Inject constructor(
  private val socialsRetrofitService: SocialsRetrofitService
) : ViewModel(){

    // VieMmodel to call the ApiService
    var postsResponse : List<Posts> by mutableStateOf(listOf())
    var errorMessage : String by mutableStateOf("")

    fun getAllPosts () {
        viewModelScope.launch {
           try {
               val postList = socialsRetrofitService.getAllPosts()
               postsResponse = postList
           } catch (e: java.lang.Exception) {
               errorMessage = e.message.toString()
           }
        }
    }

}