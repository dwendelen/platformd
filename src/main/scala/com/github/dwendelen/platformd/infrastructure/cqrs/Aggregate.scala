package com.github.dwendelen.platformd.infrastructure.cqrs

trait Aggregate[T<: Aggregate[T]] {
    def handle(command: Any): AggregateResult
    def applyEvent(event: Any): T
}

case class AggregateResult(result: CommandResult, events: List[Any]);