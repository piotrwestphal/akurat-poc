package com.akurat.profiles

import com.akurat.model.Role
import com.akurat.validation.validateAndThrowOnFailure
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Serializable

@Serializable
data class CreateProfileRequest(val name: String, val role: Role) {
    init {
        Validation<CreateProfileRequest> {
            CreateProfileRequest::name required {
                minLength(1)
                maxLength(100)
            }
        }.validateAndThrowOnFailure(this)
    }
}

