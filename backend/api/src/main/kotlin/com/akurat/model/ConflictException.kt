package com.akurat.model

class ConflictException(message: String? = "Resource already exist") : Exception(message)