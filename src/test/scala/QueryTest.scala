import Models.*

class QueryTest extends munit.FunSuite {
  test("Pretty printing") {
      val cq: Q = Q(1, H(List(V("x"), V("y"), V("z"))), Set(A("R", List(V("x"), V("y"))), A("S", List(V("y"), V("z")))))
      val cqString = "Answer(x,y,z) :- R(x,y),S(y,z)"
      assertEquals(cq.toString(), cqString)
  }

  test("Tests equality of term variables") {
    val first = Variable("x")
    val second = Variable("x")
    assert(first.equals(second))
  }

  test("Tests equality of term constants") {
    val first = Constant("a")
    val second = Constant("a")
    assert(first.equals(second))
  }

  test("Tests that a term constant and a term variable are not equals") {
    val first = Variable("x")
    val second = Constant("x")
    assert(!first.equals(second))
  }

  test("Tests equality of atoms") {
    val first = Atom("S", List(V("x"), V("y")))
    val second = Atom("S", List(V("x"), V("y")))
    assert(first.equals(second))
  }

  test("Tests equality of atoms with different order") {
    val first = Atom("S", List(V("x"), V("y")))
    val second = Atom("S", List(V("y"), V("x")))
    assert(!first.equals(second))
  }

  test("Tests equality of atoms with different names") {
    val first = Atom("S", List(V("x"), V("y")))
    val second = Atom("T", List(V("x"), V("y")))
    assert(!first.equals(second))
  }

  test("Tests equality of atoms with different terms") {
    val first = Atom("S", List(V("x"), V("y")))
    val second = Atom("T", List(V("x"), V("z")))
    assert(!first.equals(second))
  }

  test("Tests equality of queries") {
    val first = Query(1, Head(List(V("x"))), Set(Atom("S", List(V("x"), V("y")))))
    val second = Query(1, Head(List(V("x"))), Set(Atom("S", List(V("x"), V("y")))))
    assert(first.equals(second))
  }

  test("Tests equality of queries with different relations") {
    val first = Query(1, Head(List(V("x"))), Set(Atom("S", List(V("x"), V("y")))))
    val second = Query(1, Head(List(V("x"))), Set(Atom("T", List(V("x"), V("y")))))
    assert(!first.equals(second))
  }

  test("Tests equality of queries with duplicate relations") {
    val first = Query(1, Head(List(V("x"))), Set(Atom("S", List(V("x"), V("y")))))
    val second = Query(1, Head(List(V("x"))), Set(Atom("S", List(V("x"), V("y"))), Atom("S", List(V("x"), V("y")))))
    assert(first.equals(second))
  }

  test("Tests equality of queries with different relations") {
    val first = Query(1, Head(List(V("x"))), Set(Atom("S", List(V("x"), V("y")))))
    val second = Query(1, Head(List(V("x"))), Set(Atom("S", List(V("x"), V("y"), V("z")))))
    assert(!first.equals(second))
  }

  test("Tests equality of queries with different heads") {
    val first = Query(1, Head(List(V("x"))), Set(Atom("S", List(V("x"), V("y")))))
    val second = Query(1, Head(List(V("y"))), Set(Atom("S", List(V("x"), V("y")))))
    assert(!first.equals(second))
  }

  test("Tests equality of queries with different ids") {
    val first = Query(1, Head(List(V("x"))), Set(Atom("S", List(V("x"), V("y")))))
    val second = Query(2, Head(List(V("x"))), Set(Atom("S", List(V("x"), V("y")))))
    assert(!first.equals(second))
  }
}