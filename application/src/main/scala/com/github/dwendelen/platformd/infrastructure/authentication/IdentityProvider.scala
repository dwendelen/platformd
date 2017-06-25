package com.github.dwendelen.platformd.infrastructure.authentication

import com.github.dwendelen.platformd.rest.domain.user.User
import org.springframework.stereotype.Component

@Component
class IdentityProvider {
  private val currentUser = new ThreadLocal[User]

  def getCurrentUser: User = {
    val user = currentUser.get
    if (user == null) {
      throw new UnsupportedOperationException("Decent exception")
    }
    user
  }

  def setCurrentUser(user: User) {
    currentUser.set(user)
  }

  def clearCurrentUser() {
    currentUser.remove()
  }
}
