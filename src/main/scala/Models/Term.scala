package Models

class Term(val name: String, val termType: TermType):
    override def equals(that: Any): Boolean =
        that match
        case t: Term =>
            this.name == t.name && this.termType == t.termType
        case _ => false
    
    override def hashCode(): Int =
        val prime = 31
        prime * name.hashCode + termType.hashCode()

    override def toString(): String = 
        if (termType == TermType.Cons)
            s"\'$name\'"
        else
            name

class Variable(name: String) extends Term(name, TermType.Var)
class Constant(name: String) extends Term(name, TermType.Cons)