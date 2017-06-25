package com.github.dwendelen.platformd.rest.domain.user

import com.datastax.driver.mapping.Mapper
import com.datastax.driver.mapping.MappingManager
import org.springframework.stereotype.Component

@Component
class UserDao(mappingManager: MappingManager) {
    private val mapper: Mapper[User] = mappingManager.mapper(classOf[User])

    def get(googleId: String ): User = {
        mapper.get(googleId)
    }
}