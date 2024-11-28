package Services

import Models.*

object QueryMinimizerService:
	def isMinimal(query: Query): Boolean =
		query.body.forall(atom =>
			val smallerQuery = Query(query.queryId, query.head, query.body - atom)
			!Services.ContainmentService.isContainedIn(smallerQuery, query, false)
		)

	def logMinimization(query: Query): Unit =
		println(s"Minimization of query $query")