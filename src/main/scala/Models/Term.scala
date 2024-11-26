package Models

import upickle.default._

case class Term(val name: String, val termType: TermType):
    override def equals(that: Any): Boolean = that match
        case t: Term => this.name == t.name && this.termType == t.termType
        case _ => false
    
    override def toString(): String = 
        if (termType == TermType.Cons)
            s"\'$name\'"
        else
            name

object Term:
    implicit val rw: ReadWriter[Term] = macroRW

class Variable(name: String) extends Term(name, TermType.Var)
class Constant(name: String) extends Term(name, TermType.Cons)