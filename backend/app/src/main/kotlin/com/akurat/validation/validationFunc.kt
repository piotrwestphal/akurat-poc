package com.akurat.validation

import io.konform.validation.Invalid
import io.konform.validation.Validation
import io.konform.validation.jsonschema.pattern
import io.ktor.features.*

fun <T> Validation<T>.validateAndThrowOnFailure(value: T) {
    val result = validate(value)
    if (result is Invalid<T>) {
        throw BadRequestException(result.errors.joinToString { "'${it.dataPath}' ${it.message}" })
    }
}

val validateUuid = Validation<String> {
    pattern(uuidPattern)
}
