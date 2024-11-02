package Models

enum TermType:
    case Var, Cons

class Term(name: String, termType: TermType):
    override def toString(): String = 
        if (termType == TermType.Cons)
            s"\'$name\'"
        else
            name

class Variable(name: String) extends Term(name, TermType.Var)
class Constant(name: String, value: String) extends Term(name, TermType.Cons)

class Atom(name: String, terms: List[Term]):
    override def toString(): String = 
        s"$name(${terms.map(_.toString).mkString(",")})"

class Head(terms: List[Term]) extends Atom("Answer", terms)

class Query(queryId: Int, head: Head, body: Set[Atom]):
    override def toString(): String = 
        s"${head.toString()} :- ${body.map(_.toString).mkString(",")}"