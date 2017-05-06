package com.lejoow.votebot.commons

import akka.Done
import com.datastax.driver.core.{Session, TypeCodec}
import com.datastax.driver.extras.codecs.MappingCodec
import com.datastax.driver.extras.codecs.jdk8.{InstantCodec, LocalDateCodec, LocalTimeCodec}

import scala.concurrent.Future

/**
  * Created by Joo on 6/5/2017.
  */
