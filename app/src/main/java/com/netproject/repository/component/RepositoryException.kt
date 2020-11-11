package com.netproject.repository.component

data class RepositoryException
constructor(var errorCode: Int, var errorMessage: String = "RepositoryException") :
    Exception(errorMessage)
