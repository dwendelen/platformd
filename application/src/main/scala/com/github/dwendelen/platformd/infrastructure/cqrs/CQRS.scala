package com.github.dwendelen.platformd.infrastructure.cqrs

import java.util.UUID

trait CQRS {
    def execute[T <: Aggregate[T], R](identifier: UUID, command: Any, clazz: Class[T]): CommandResult
    def registerListener(listener: Listener)
}

abstract sealed class CommandResult
case class CommandSucces(result: Any) extends CommandResult
case class CommandFailed(errors: List[String]) extends CommandResult

abstract sealed class ApplyResult
case class ApplySuccess() extends ApplyResult
case class ApplyFailed(errors: List[String]) extends ApplyResult
