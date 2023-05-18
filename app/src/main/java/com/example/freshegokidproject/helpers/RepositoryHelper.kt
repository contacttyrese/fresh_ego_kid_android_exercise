package com.example.freshegokidproject.helpers

import android.util.Log

class RepositoryHelper {
     fun checkQueryIsValid(query: String): Boolean {
        val isQueryValid = query.isNotBlank() && query.all { char ->
            !char.isDigit() || char.isLetter() || char.isWhitespace()
        }
        when (isQueryValid) {
            true -> Log.i("repo_query_valid", "query is valid, making network call")
            false -> Log.e("repo_query_invalid", "query is invalid, returning empty search results")
        }
        return isQueryValid
    }
}