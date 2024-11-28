package Services

import Models.*

object QueryMinimizerService:
	def isMinimal(query: Query, log: Boolean = true): Boolean =
		if (log)
			println(s"Minimization for query: $query")
		isMinimalRecursive(query, log)

	def isMinimalRecursive(query: Query, log: Boolean): Boolean = {
		val allMinimal = query.body.forall { atom =>
			val smallerQuery = Query(query.queryId, query.head, query.body - atom)

			Services.ContainmentService.generateValidHomomorphism(smallerQuery, query) match {
				case Some(homomorphism) =>
					if (log)
						println(s"Remove atom $atom by the virtue of the homomorphism containing the following mappings:")
						println(homomorphism)
						println(s"Current query is: $smallerQuery")
						isMinimalRecursive(smallerQuery, log)
					false
				case None =>
					true // Continue checking other atoms
			}
		}
		if (allMinimal && log)
			println("The query is minimal.")
		allMinimal
	}