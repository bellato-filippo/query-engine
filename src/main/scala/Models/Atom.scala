package Models

class Atom(val name: String, val terms: List[Term]):
    override def equals(that: Any): Boolean =
        that match {
            case a: Atom =>
                this.name == a.name && this.terms.size == a.terms.size && 
                this.terms.zip(a.terms).forall { case (term1, term2) =>
                    term1 == term2
                }
            case _ => false
        }
    
    override def hashCode(): Int =
        val prime = 31
        prime * name.hashCode + terms.hashCode()

    override def toString(): String = 
        s"$name(${terms.map(_.toString).mkString(",")})"

class Head(terms: List[Term]) extends Atom("Answer", terms)