package com.akurat.profiles

import com.akurat.model.ProfileUpdate
import com.akurat.model.Role
import com.akurat.validation.validateAndThrowOnFailure
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(val name: String? = null, val role: Role? = null) {
    init {
        Validation<UpdateProfileRequest> {
            UpdateProfileRequest::name ifPresent  {
                minLength(1)
                maxLength(100)
            }
        }.validateAndThrowOnFailure(this)
    }
}

fun UpdateProfileRequest.toProfileUpdate() = ProfileUpdate(name, role)

