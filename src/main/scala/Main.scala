import Models.*
import Models.Container.extractTermsFromQuery
import upickle.default.write


object Main extends App {
  // val cq: Query = Query(1, Head(List(Variable("x"), Variable("y"), Variable("z"))), Set(Atom("R", List(Variable("x"), Variable("y"))), Atom("S", List(Variable("y"), Variable("z")))))
  
  // println(cq.toString())

  val a = Q(1, H(List(V("x1"))), Set(A("R", List(V("x1"), V("y1"))), A("S", List(V("y1"), V("x2"))), A("R", List(V("x1"), V("y2"))), A("S", List(V("y2"), V("x2")))))
  val b = Q(2, H(List(V("u"))), Set(A("R", List(V("u"), V("v"))), A("S", List(V("v"), V("w"))), A("R", List(V("x"), V("v"))), A("R", List(V("x"), V("y")))))

  val query = Q(3, H(List(V("x"))), Set(A("E", List(V("x"), V("x"))), A("E", List(V("x"), V("y"))), A("E", List(V("y"), V("z")))))
  val queryMinimal = Q(3, H(List(V("x"))), Set(A("E", List(V("x"), V("y"))), A("E", List(V("y"), V("z")))))

  println(query.isMinimal())
  println(queryMinimal.isMinimal())

  println(a isContainedIn b)
  println(b isContainedIn a)
}