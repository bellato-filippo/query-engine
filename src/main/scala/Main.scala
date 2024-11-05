import Models.*

object Main extends App {
  val cq: Query = Query(1, Head(List(Variable("x"), Variable("y"), Variable("z"))), Set(Atom("R", List(Variable("x"), Variable("y"))), Atom("S", List(Variable("y"), Variable("z")))))
  val cq2: Query = Query(2, Head(List()), Set(Atom("R", List(Variable("y"), Variable("z"))), Atom("R", List(Variable("x"), Variable("y"))), Atom("R", List(Variable("z"), Variable("x"))), Atom("R", List(Variable("z"), Variable("x"), Variable("y")))))
  val hg: Hypergraph = Hypergraph(cq2)
  println(cq2.isAcyclic)
}