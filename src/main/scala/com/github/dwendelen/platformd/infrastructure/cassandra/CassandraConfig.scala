package com.github.dwendelen.platformd.infrastructure.cassandra

import java.nio.ByteBuffer
import java.util.concurrent.{Executor, Executors, ThreadPoolExecutor}

import com.datastax.driver.core._
import com.datastax.driver.extras.codecs.jdk8.InstantCodec
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec
import com.datastax.driver.extras.codecs.jdk8.LocalTimeCodec
import com.datastax.driver.mapping.MappingManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CassandraConfig {
    @Bean
    def session(): Session = {
        val cluster = Cluster.builder()
            .addContactPoint("localhost")
            .build()

        cluster.getConfiguration.getCodecRegistry
            .register(InstantCodec.instance)
            .register(LocalDateCodec.instance)
            .register(LocalTimeCodec.instance)
            .register(BigDecimalCodec)
        cluster.connect("platformd")
    }

    @Bean
    def mappingManager(session: Session): MappingManager =
        new MappingManager(session)

    @Bean
    def cassandraExecutor: Executor =
        Executors.newSingleThreadExecutor()
}

object BigDecimalCodec extends TypeCodec[BigDecimal](DataType.decimal(), classOf[BigDecimal]) {
    val decimalCodec: TypeCodec[java.math.BigDecimal] = TypeCodec.decimal()

    override def serialize(value: BigDecimal, protocolVersion: ProtocolVersion): ByteBuffer =
        decimalCodec.serialize(value.bigDecimal, protocolVersion)
    override def parse(value: String): BigDecimal = BigDecimal(decimalCodec.parse(value))
    override def format(value: BigDecimal): String = decimalCodec.format(value.bigDecimal)
    override def deserialize(bytes: ByteBuffer, protocolVersion: ProtocolVersion): BigDecimal =
        BigDecimal(decimalCodec.deserialize(bytes, protocolVersion))
}