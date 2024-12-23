package Services

import Models.*
import java.io._

object QueryMinimizerService:
	def isMinimal(query: Query, log: Boolean = true): Boolean =
		val path = s"./src/test/results/test-minimality-${query.queryId}.txt"
		val writer = new PrintWriter(new FileWriter(path, true))

		if (log)
			writer.println(s"Minimization for query: $query")
		val result = isMinimalRecursive(query, log, writer)
		writer.close()
		result

	def isMinimalRecursive(query: Query, log: Boolean, writer: PrintWriter): Boolean = {
		val allMinimal = query.body.forall { atom =>
			val smallerQuery = Query(query.queryId, query.head, query.body - atom)

			Services.ContainmentService.generateValidHomomorphism(smallerQuery, query) match {
				case Some(homomorphism) =>
					if (log)
						writer.println(s"Remove atom $atom by the virtue of the homomorphism containing the following mappings:")
						writer.println(homomorphism)
						writer.println(s"Current query is: $smallerQuery")
						isMinimalRecursive(smallerQuery, log, writer)
					false
				case None =>
					true
			}
		}
		if (allMinimal && log)
			writer.println("The query is minimal.")
		allMinimal
	}