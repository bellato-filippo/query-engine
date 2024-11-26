package Models

import upickle.default._

enum TermType:
    case Var, Cons

object TermType:
  // Define a ReadWriter instance for TermType by matching on the enum cases
  implicit val rw: ReadWriter[TermType] = ReadWriter.merge(
    macroRW[TermType.Var.type],
    macroRW[TermType.Cons.type]
  )