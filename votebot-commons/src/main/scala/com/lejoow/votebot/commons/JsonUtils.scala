package com.lejoow.votebot.commons

import java.time._
import java.time.format.DateTimeFormatter
import java.time.temporal.Temporal
import java.util.{Currency, UUID}

import play.api.data.validation.ValidationError
import play.api.libs.json._

import scala.math.BigDecimal.RoundingMode.RoundingMode
import scala.util.Try

/**
  * Created by jooan on 27/12/2016.
  */

object JsonUtils {

  def enumReads[E <: Enumeration](enum: E): Reads[E#Value] = Reads {
    case JsString(s) =>
      try {
        JsSuccess(enum.withName(s).asInstanceOf[E#Value])
      } catch {
        case _: NoSuchElementException =>
          JsError(s"Enumeration expected of type: '${enum.getClass}', but it does not contain '$s'")
      }
    case _ => JsError("String value expected")
  }

  def enumWrites[E <: Enumeration]: Writes[E#Value] = Writes(v => JsString(v.toString))

  def enumFormat[E <: Enumeration](enum: E): Format[E#Value] = {
    Format(enumReads(enum), enumWrites)
  }

  def singletonReads[O](singleton: O): Reads[O] = {
    (__ \ "value").read[String].collect(
      ValidationError(s"Expected a JSON object with a single field with key 'value' and value '${singleton.getClass.getSimpleName}'")
    ) {
      case s if s == singleton.getClass.getSimpleName => singleton
    }
  }

  def singletonWrites[O]: Writes[O] = Writes { singleton =>
    Json.obj("value" -> singleton.getClass.getSimpleName)
  }

  def singletonFormat[O](singleton: O): Format[O] = {
    Format(singletonReads(singleton), singletonWrites)
  }

  implicit val uuidReads: Reads[UUID] = implicitly[Reads[String]]
    .collect(ValidationError("Invalid UUID"))(Function.unlift { str =>
      Try(UUID.fromString(str)).toOption
    })
  implicit val uuidWrites: Writes[UUID] = Writes { uuid =>
    JsString(uuid.toString)
  }

  implicit val durationReads: Reads[Duration] = implicitly[Reads[String]]
    .collect(ValidationError("Invalid duration"))(Function.unlift { str =>
      Try(Duration.parse(str)).toOption
    })

  implicit val durationWrites: Writes[Duration] = Writes { duration =>
    JsString(duration.toString)
  }

  implicit val yearMonthReads: Reads[YearMonth] = implicitly[Reads[String]]
    .collect(ValidationError("Invalid month"))(Function.unlift { str =>
      Try(YearMonth.parse(str)).toOption
    })

  implicit val yearMonthWrites: Writes[YearMonth] = Writes { yearMonth =>
    JsString(yearMonth.toString)
  }


  implicit val currencyReads: Reads[Currency] = implicitly[Reads[String]]
    .collect(ValidationError("Invalid Currency"))(Function.unlift { str => Try(Currency.getInstance(str)).toOption })

  implicit val currencyWrites: Writes[Currency] = Writes { currency => JsString(currency.toString) }

  /** Typeclass to implement way of formatting of Java8 temporal types. */
  trait TemporalFormatter[T <: Temporal] {
    def format(temporal: T): String
  }

  def temporalWrites[A <: Temporal, B](formatting: B)(implicit f: B => TemporalFormatter[A]): Writes[A] = new Writes[A] {
    def writes(temporal: A): JsValue = JsString(f(formatting) format temporal)
  }

  /** Formatting companion */
  object TemporalFormatter {

    implicit def DefaultLocalTimeFormatter(formatter: DateTimeFormatter): TemporalFormatter[LocalTime] = new TemporalFormatter[LocalTime] {
      def format(temporal: LocalTime): String = formatter.format(temporal)
    }

    implicit def PatternLocalTimeFormatter(pattern: String): TemporalFormatter[LocalTime] = new TemporalFormatter[LocalTime] {
      def format(temporal: LocalTime): String =
        DateTimeFormatter.ofPattern(pattern).format(temporal)
    }

    implicit def DefaultLocalDateTimeFormatter(formatter: DateTimeFormatter): TemporalFormatter[LocalDateTime] = new TemporalFormatter[LocalDateTime] {
      def format(temporal: LocalDateTime): String = formatter.format(temporal)
    }

    implicit def PatternLocalDateTimeFormatter(pattern: String): TemporalFormatter[LocalDateTime] = new TemporalFormatter[LocalDateTime] {
      def format(temporal: LocalDateTime): String =
        DateTimeFormatter.ofPattern(pattern).format(temporal)
    }

    implicit def DefaultZonedDateTimeFormatter(formatter: DateTimeFormatter): TemporalFormatter[ZonedDateTime] = new TemporalFormatter[ZonedDateTime] {
      def format(temporal: ZonedDateTime): String = formatter.format(temporal)
    }

    implicit def PatternZonedDateTimeFormatter(pattern: String): TemporalFormatter[ZonedDateTime] = new TemporalFormatter[ZonedDateTime] {
      def format(temporal: ZonedDateTime): String =
        DateTimeFormatter.ofPattern(pattern).format(temporal)
    }

    implicit def DefaultDateFormatter(formatter: DateTimeFormatter): TemporalFormatter[LocalDate] = new TemporalFormatter[LocalDate] {
      def format(temporal: LocalDate): String = formatter.format(temporal)
    }

    implicit def PatternDateFormatter(pattern: String): TemporalFormatter[LocalDate] = new TemporalFormatter[LocalDate] {
      def format(temporal: LocalDate): String =
        DateTimeFormatter.ofPattern(pattern).format(temporal)
    }

    implicit def DefaultInstantFormatter(formatter: DateTimeFormatter): TemporalFormatter[Instant] = new TemporalFormatter[Instant] {
      def format(temporal: Instant): String =
        formatter format LocalDateTime.ofInstant(temporal, ZoneId.systemDefault)
    }

    implicit def PatternInstantFormatter(pattern: String): TemporalFormatter[Instant] = new TemporalFormatter[Instant] {
      def format(temporal: Instant): String =
        DateTimeFormatter.ofPattern(pattern).
          format(LocalDateTime.ofInstant(temporal, ZoneId.systemDefault))
    }

  }

  /**
    * Serializer for `java.time.Instant` as JSON number.
    *
    * {{{
    * import java.time.Instant
    * import play.api.libs.json.Writes
    *
    * implicit val inWrites = Writes.InstantNumberWrites
    * }}}
    */
  val InstantNumberWrites: Writes[Instant] = new Writes[Instant] {
    def writes(t: Instant): JsValue =
      JsNumber(BigDecimal valueOf t.toEpochMilli)
  }

  implicit def scalaBigDecimal2JavaBigDecimal(x:scala.math.BigDecimal):java.math.BigDecimal = x.underlying()


  implicit def scalaRoundingModeToJavaRoundingMode(roundingMode:RoundingMode):java.math.RoundingMode = java.math.RoundingMode.valueOf(roundingMode.toString)

}

