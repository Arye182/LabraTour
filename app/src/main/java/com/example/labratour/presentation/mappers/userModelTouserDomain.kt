package com.example.labratour.presentation.mappers

import com.example.labratour.domain.Entity.UserDomain
import com.example.labratour.presentation.models.UserModel

class userModelTouserDomain {

    fun toDomain(userModel: UserModel, userDomain: UserDomain): UserDomain {
        userDomain.email = userModel.email
        userDomain.userName = userModel.userName
        return userDomain
    }
}
