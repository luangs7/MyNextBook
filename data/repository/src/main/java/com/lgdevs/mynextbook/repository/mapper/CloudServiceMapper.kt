package com.lgdevs.mynextbook.repository.mapper

import com.lgdevs.mynextbook.cloudservices.auth.CurrentUser
import com.lgdevs.mynextbook.domain.model.User

object CloudServiceMapper {
    fun CurrentUser.toDomain() = User(
        this.uuid,
        this.name,
        this.email,
        this.avatar
    )
}