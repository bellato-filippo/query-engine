import Models.*
import Services.QueryParserService

class QueryParserServiceTest extends munit.FunSuite {
    test("Parser test 1"):
        val q1: Query = Query(0, Head(List(Variable("x"), Variable("y"), Variable("z"))), Set(Atom("R", List(Variable("x"), Variable("y"))), Atom("S", List(Variable("y"), Variable("z")))))
        val q2: Query = QueryParserService.parse("Answer(x,y,z) :- R(x,y),S(y,z)")
        assertEquals(q1, q2)

    test("Parser test 2"):
        val q1: Query = Query(0, Head(List(Variable("x"))), Set(Atom("R", List(Variable("x"), Variable("y")))))
        val q2: Query = QueryParserService.parse("Answer(x) :- R(x,y)")
        assertEquals(q1, q2)

    test("Parser test 3"):
        val q1: Query = Query(0, Head(List(Variable("x"))), Set(Atom("R", List(Variable("x"))), Atom("S", List(Variable("y")))))
        val q2: Query = QueryParserService.parse("Answer(x) :- R(x),S(y)")
        assertEquals(q1, q2)

    test("Parser test 4"):
        val q1: Query = Query(0, Head(List(Variable("x"))), Set(Atom("R", List(Variable("x"))), Atom("S", List(Variable("y"))), Atom("T", List(Variable("z")))))
        val q2: Query = QueryParserService.parse("Answer(x) :- R(x),S(y),T(z)")
        assertEquals(q1, q2)

    test("Parser test 5"):
        val q1: Query = Query(0, Head(List(Variable("x"), Constant("a"))), Set(Atom("R", List(Variable("x"), Constant("a")))))
        val q2: Query = QueryParserService.parse("Answer(x,'a') :- R(x,'a')")
        assertEquals(q1, q2)

    test("Parser test 6"):
        val q1: Query = Query(0, Head(List(Variable("x"), Constant("a"))), Set(Atom("R", List(Variable("x"), Constant("a"))), Atom("S", List(Variable("y"), Constant("b")))))
        val q2: Query = QueryParserService.parse("Answer(x,'a') :- R(x,'a'),S(y,'b')")
        assertEquals(q1, q2)

    test("Parser test 7"):
        val q1: Query = Query(0, Head(List()), Set(Atom("R", List(Variable("x"), Constant("a"))), Atom("S", List(Variable("y"), Constant("b"))), Atom("T", List(Variable("z"), Constant("c")))))
        val q2: Query = QueryParserService.parse("Answer() :- R(x,'a'),S(y,'b'),T(z,'c')")
        assertEquals(q1, q2)

    test("Parser test 8"):
        val q = QueryParserService.parse("Answer(z, y, x) :- R2(x, y), R3(y, z), R1(z, x)")
        val atom = Atom("R2", List(Variable("x"), Variable("y")))
        assert(q.body.contains(atom))
}