import Models.*
import Services.ContainmentService

class ContainmentServiceTest extends munit.FunSuite {
    test("Extract terms from query test"):
        val q1: Query = Query(0, Head(List(Variable("x"), Variable("y"), Variable("z"))), Set(Atom("R", List(Variable("x"), Variable("y"))), Atom("S", List(Variable("y"), Variable("z")))))
        val terms1: Set[Term] = ContainmentService.extractTermsFromQuery(q1)
        val terms2: Set[Term] = Set(Variable("x"), Variable("y"), Variable("z"))
        assertEquals(terms1, terms2)

    test("Extract terms from query test 2"):
        val q1: Query = Query(0, Head(List(Variable("x"))), Set(Atom("R", List(Variable("x"), Variable("y")))))
        val terms1: Set[Term] = ContainmentService.extractTermsFromQuery(q1)
        val terms2: Set[Term] = Set(Variable("x"), Variable("y"))
        assertEquals(terms1, terms2)

    test("Extract terms from query test 3"):
        val q1: Query = Query(0, Head(List(Variable("x"))), Set(Atom("R", List(Variable("x"))), Atom("S", List(Variable("y"), Constant("a")))))
        val terms1: Set[Term] = ContainmentService.extractTermsFromQuery(q1)
        val terms2: Set[Term] = Set(Variable("x"), Variable("y"), Constant("a"))
        assertEquals(terms1, terms2)
}