package com.github.dwendelen.platformd.infrastructure.cqrs

trait Listener {
    def handle(event: Any): Unit
}