package actors.utils

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{
  DeserializationFeature,
  ObjectMapper,
  SerializationFeature
}
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.reflect.ClassTag

object JsonUtil {

  private val _mapper = new ObjectMapper()
  _mapper.registerModule(DefaultScalaModule)
  _mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  _mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
  _mapper.configure(SerializationFeature.INDENT_OUTPUT, true)
  _mapper.setSerializationInclusion(Include.ALWAYS)

  private val _mapperCondensed =
    _mapper.copy().configure(SerializationFeature.INDENT_OUTPUT, false)

  def mapper: ObjectMapper = _mapper

  def copy: ObjectMapper = _mapper.copy()

  def extract[T](json: String)(implicit m: ClassTag[T]): T = {
    _mapper.readValue(json, m.runtimeClass).asInstanceOf[T]
  }

  def toJson(v: Any): String = {
    _mapper.writeValueAsString(v)
  }

  def toJson(v: Any, formatting: Boolean = false): String = {
    if (formatting) {
      toJson(v)
    } else {
      _mapperCondensed.writeValueAsString(v)
    }
  }
}

trait JsonData {
  def toJson: String = JsonUtil.mapper.writeValueAsString(this)
}
