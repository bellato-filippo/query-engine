package Models

class T(name: String, termType: TermType, value: String) extends Term(name, termType)

class V(name: String) extends Variable(name)
class C(name: String) extends Constant(name)

class A(name: String, terms: List[Term]) extends Atom(name, terms)
class H(terms: List[Term]) extends Head(terms)
class Q(queryId: Int, head: Head, body: Set[Atom]) extends Query(queryId, head, body)