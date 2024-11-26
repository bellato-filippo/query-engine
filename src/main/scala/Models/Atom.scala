package Models

import upickle.default._

class Atom(val name: String, val terms: List[Term]):
    override def equals(that: Any): Boolean = that match
        case a: Atom => this.name == a.name && this.terms == a.terms
        case _ => false
    
    override def toString(): String = 
        s"$name(${terms.map(_.toString).mkString(",")})"

class Head(terms: List[Term]) extends Atom("Answer", terms)