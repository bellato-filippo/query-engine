import Models.*

class MySuite extends munit.FunSuite {
  test("example test that succeeds") {
    val obtained = 42
    val expected = 42
    assertEquals(obtained, expected)
  }

  test("Pretty printing") {
      val cq: Q = Q(1, H(List(V("x"), V("y"), V("z"))), Set(A("R", List(V("x"), V("y"))), A("S", List(V("y"), V("z")))))
      val cqString = "Answer(x,y,z) :- R(x,y),S(y,z)"
      assertEquals(cq.toString(), cqString)
  }
}