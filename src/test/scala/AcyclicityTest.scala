import Models.*

class AcyclicityTest extends munit.FunSuite {
  test("Acyclic Query 1") {
    val cq: Query = Query(1, Head(List()), Set(Atom("R", List(Variable("y"), Variable("z"))), Atom("R", List(Variable("x"), Variable("y"))), Atom("R", List(Variable("z"), Variable("x"))), Atom("R", List(Variable("z"), Variable("x"), Variable("y")))))
    assert(cq.isAcyclic)
  }

  test("Acyclic Query 2") {
    val cq: Query = Query(2, Head(List()), Set(Atom("R", List(Variable("x"), Variable("y"), Variable("z"))), Atom("R", List(Variable("y"), Variable("z"), Variable("w"))), Atom("R", List(Variable("w"), Variable("u"))), Atom("R", List(Variable("u"), Variable("s"), Variable("t")))))
    assert(cq.isAcyclic)
  }

  test("Acyclic Query 3") {
    val cq: Query = Query(3, Head(List()), Set(Atom("R", List(Variable("x"), Variable("y"))), Atom("R", List(Variable("y"), Variable("x"))), Atom("R", List(Variable("u"), Variable("s"), Variable("t")))))
    assert(cq.isAcyclic)
  }

  test("Acyclic Query 4") {
    val cq: Query = Query(4, Head(List()), Set(Atom("R", List(Variable("x"), Variable("x"))), Atom("R", List(Variable("y"), Variable("x"))), Atom("R", List(Variable("x"), Variable("y")))))
    assert(cq.isAcyclic)
  }

  test("Cyclic Query 1") {
    val cq: Query = Query(1, Head(List()), Set(Atom("R", List(Variable("x"), Variable("y"))), Atom("R", List(Variable("y"), Variable("z"))), Atom("R", List(Variable("x"), Variable("z")))))
    assert(!cq.isAcyclic)
  }

  test("Cyclic Query 2") {
    val cq: Query = Query(2, Head(List()), Set(Atom("R", List(Variable("a"), Variable("b"), Variable("c"))), Atom("R", List(Variable("b"), Variable("c"), Variable("d"))), Atom("R", List(Variable("a"), Variable("c"), Variable("d")))))
    assert(!cq.isAcyclic)
  }

  test("Cyclic Query 3") {
    val cq: Query = Query(3, Head(List()), Set(Atom("R", List(Variable("a"), Variable("b"))), Atom("R", List(Variable("b"), Variable("c"))), Atom("R", List(Variable("c"), Variable("d"))), Atom("R", List(Variable("a"), Variable("d")))))
    assert(!cq.isAcyclic)
  }

  test("Cyclic Query 4") {
    val cq: Query = Query(4, Head(List()), Set(Atom("R", List(Variable("x"), Variable("y"))), Atom("R", List(Variable("y"), Variable("z"))), Atom("R", List(Variable("x"), Variable("z"))), Atom("R", List(Variable("y"), Variable("v"))), Atom("R", List(Variable("x"), Variable("s"))), Atom("R", List(Variable("t"), Variable("u")))))
    assert(!cq.isAcyclic)
  }

}