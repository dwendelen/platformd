/**
  * Copyright 2014 Netflix, Inc.
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package rx.observable

import com.google.common.util.concurrent.AbstractFuture
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicInteger

import rx.exceptions.{Exceptions, OnErrorThrowable}
import rx.lang.scala.{Observable, Observer, Producer, Subscriber}

object ListenableFutureObservable {


    def from[T](future: ListenableFuture[T], executor: Executor): Observable[T] = {
        Observable.apply[T](sub => {
            val sdp: SingleDelayedProducer[T] = new SingleDelayedProducer[T](sub)
            sub.setProducer(sdp)

            future.addListener(new Runnable {
                override def run() = {
                    try {
                        val t: T = future.get()
                        sdp.setValue(t)
                    } catch {
                        case e: Exception => sub.onError(e)
                    }
                }
            }, executor)
        })
    }

}


object SingleDelayedProducer {
    val NO_REQUEST_NO_VALUE = 0
    val NO_REQUEST_HAS_VALUE = 1
    val HAS_REQUEST_NO_VALUE = 2
    val HAS_REQUEST_HAS_VALUE = 3

    /**
      * Emits the given value to the child subscriber and completes it
      * and checks for unsubscriptions eagerly.
      *
      * @param c the target Subscriber to emit to
      * @param v the value to emit
      */
    private def emit[T](c: Subscriber[_ >: T], v: T) {
        if (c.isUnsubscribed) return
        try
            c.onNext(v)

        catch {
            case e: Throwable =>
                Exception.throwOrReport(e, c, v)
                return
        }
        if (c.isUnsubscribed) return
        c.onCompleted()
    }
}


class SingleDelayedProducer[T](val child: Subscriber[_ >: T]) extends AtomicInteger with Producer {
    /** The value to emit. */
    private var value: T = _

    override def request(n: Long) {
        if (n < 0) throw new IllegalArgumentException("n >= 0 required")
        if (n == 0) return
        while (true) {
            if (while1()) {
                return
            }
        }
    }

    def while1(): Boolean = {
        val s = get
        if (s == SingleDelayedProducer.NO_REQUEST_NO_VALUE)
            if (!compareAndSet(SingleDelayedProducer.NO_REQUEST_NO_VALUE, SingleDelayedProducer.HAS_REQUEST_NO_VALUE))
                return false //todo: continue is not supported
            else if (s == SingleDelayedProducer.NO_REQUEST_HAS_VALUE)
                if (compareAndSet(SingleDelayedProducer.NO_REQUEST_HAS_VALUE, SingleDelayedProducer.HAS_REQUEST_HAS_VALUE))
                    SingleDelayedProducer.emit(child, value)
        return true // NOPMD
    }

    def setValue(value: T) {
        while (true) {
            if (while2(value)) {
                return
            }
        }
    }

    def while2(value: T): Boolean = {
        val s = get
        if (s == SingleDelayedProducer.NO_REQUEST_NO_VALUE) {
            this.value = value
            if (!compareAndSet(SingleDelayedProducer.NO_REQUEST_NO_VALUE, SingleDelayedProducer.NO_REQUEST_HAS_VALUE))
                return false
        }
        else if (s == SingleDelayedProducer.HAS_REQUEST_NO_VALUE)
            if (compareAndSet(SingleDelayedProducer.HAS_REQUEST_NO_VALUE, SingleDelayedProducer.HAS_REQUEST_HAS_VALUE))
                SingleDelayedProducer.emit(child, value)
        return true
    }
}

object Exception {
    /**
      * Forwards a fatal exception or reports it along with the value
      * caused it to the given Observer.
      *
      * @param t     the exception
      * @param o     the observer to report to
      * @param value the value that caused the exception
      * @since (if this graduates from Experimental/Beta to supported, replace this parenthetical with the release number)
      */
    def throwOrReport(t: Throwable, o: Observer[_], value: Any) {
        Exceptions.throwIfFatal(t)
        o.onError(OnErrorThrowable.addValueAsLastCause(t, value))
    }


}