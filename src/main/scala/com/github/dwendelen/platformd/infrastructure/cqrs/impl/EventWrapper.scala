package com.github.dwendelen.platformd.infrastructure.cqrs.impl

/**
  * Created by xtrit on 14/01/17.
  */
class EventWrapper(val sequenceNumber: Long, val event: Any)