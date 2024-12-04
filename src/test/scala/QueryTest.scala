import Models.*

class QueryTest extends munit.FunSuite {
  test("Pretty printing") {
      val cq: Query = Query(1, Head(List(Variable("x"), Variable("y"), Variable("z"))), Set(Atom("R", List(Variable("x"), Variable("y"))), Atom("S", List(Variable("y"), Variable("z")))))
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
    val first = Atom("S", List(Variable("x"), Variable("y")))
    val second = Atom("S", List(Variable("x"), Variable("y")))
    assert(first.equals(second))
  }

  test("Tests equality of atoms with different order") {
    val first = Atom("S", List(Variable("x"), Variable("y")))
    val second = Atom("S", List(Variable("y"), Variable("x")))
    assert(!first.equals(second))
  }

  test("Tests equality of atoms with different names") {
    val first = Atom("S", List(Variable("x"), Variable("y")))
    val second = Atom("T", List(Variable("x"), Variable("y")))
    assert(!first.equals(second))
  }

  test("Tests equality of atoms with different terms") {
    val first = Atom("S", List(Variable("x"), Variable("y")))
    val second = Atom("T", List(Variable("x"), Variable("z")))
    assert(!first.equals(second))
  }

  test("Tests equality of queries") {
    val first = Query(1, Head(List(Variable("x"))), Set(Atom("S", List(Variable("x"), Variable("y")))))
    val second = Query(1, Head(List(Variable("x"))), Set(Atom("S", List(Variable("x"), Variable("y")))))
    assert(first.equals(second))
  }

  test("Tests equality of queries with different relations") {
    val first = Query(1, Head(List(Variable("x"))), Set(Atom("S", List(Variable("x"), Variable("y")))))
    val second = Query(1, Head(List(Variable("x"))), Set(Atom("T", List(Variable("x"), Variable("y")))))
    assert(!first.equals(second))
  }

  test("Tests equality of queries with duplicate relations") {
    val first = Query(1, Head(List(Variable("x"))), Set(Atom("S", List(Variable("x"), Variable("y")))))
    val second = Query(1, Head(List(Variable("x"))), Set(Atom("S", List(Variable("x"), Variable("y"))), Atom("S", List(Variable("x"), Variable("y")))))
    assert(first.equals(second))
  }

  test("Tests equality of queries with different relations") {
    val first = Query(1, Head(List(Variable("x"))), Set(Atom("S", List(Variable("x"), Variable("y")))))
    val second = Query(1, Head(List(Variable("x"))), Set(Atom("S", List(Variable("x"), Variable("y"), Variable("z")))))
    assert(!first.equals(second))
  }

  test("Tests equality of queries with different heads") {
    val first = Query(1, Head(List(Variable("x"))), Set(Atom("S", List(Variable("x"), Variable("y")))))
    val second = Query(1, Head(List(Variable("y"))), Set(Atom("S", List(Variable("x"), Variable("y")))))
    assert(!first.equals(second))
  }

  test("Tests equality of queries with different ids") {
    val first = Query(1, Head(List(Variable("x"))), Set(Atom("S", List(Variable("x"), Variable("y")))))
    val second = Query(2, Head(List(Variable("x"))), Set(Atom("S", List(Variable("x"), Variable("y")))))
    assert(!first.equals(second))
  }
}