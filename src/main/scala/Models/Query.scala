package Models


enum TermType:
    case Var, Cons

class Term(val name: String, val termType: TermType):
    override def equals(that: Any): Boolean = that match
        case t: Term => this.name == t.name && this.termType == t.termType
        case _ => false
    
    override def toString(): String = 
        if (termType == TermType.Cons)
            s"\'$name\'"
        else
            name

class Variable(name: String) extends Term(name, TermType.Var)
class Constant(name: String) extends Term(name, TermType.Cons)

class Atom(val name: String, val terms: List[Term]):
    override def equals(that: Any): Boolean = that match
        case a: Atom => this.name == a.name && this.terms == a.terms
        case _ => false
    
    override def toString(): String = 
        s"$name(${terms.map(_.toString).mkString(",")})"

class Head(terms: List[Term]) extends Atom("Answer", terms)

class Query(val queryId: Int, val head: Head, val body: Set[Atom]):
    override def equals(that: Any): Boolean = that match
        case q: Query => this.queryId == q.queryId && this.head == q.head && this.body.toSet == q.body.toSet
        case _ => false
    
    override def toString(): String = 
        s"${head.toString()} :- ${body.map(_.toString).mkString(",")}"
    
    def GyoAlgorithm(hypergraph: Hypergraph): Hypergraph =
        //per ogni edge guardo se è un orecchio
        if (hypergraph.edges.isEmpty)
          return hypergraph

        hypergraph.edges.foreach(e => {
            if (hypergraph.isEar(e)) {
                hypergraph.edges = hypergraph.edges - e
                println("we remove the edge: " + e)
                return GyoAlgorithm(hypergraph)
            }
        })
        hypergraph
        
    def isAcyclic: Boolean =
        val h: Hypergraph = new Hypergraph(this)
        val acyclic: Hypergraph = GyoAlgorithm(h)
        return acyclic.edges.isEmpty
            //se l'edge e è un ear allora lo rimuovo
            //richiamo la funzione GYOAlghoritm