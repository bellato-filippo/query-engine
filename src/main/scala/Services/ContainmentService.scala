package Services

import scala.collection.mutable
import scala.compiletime.ops.boolean
import Models.*
import java.io._

object ContainmentService:
	def isContainedIn(a: Query, b: Query, log: Boolean = true): Boolean = 
		val path = s"./src/test/results/test-containment-${a.queryId}-${b.queryId}.txt"
		val writer = new PrintWriter(new FileWriter(path, true))

		if (a.head.terms.length != b.head.terms.length) {
			if (log) writer.println("q1 and q2 have head atoms with different arities.")
			return false
		}

		val validHomomorphism = generateValidHomomorphism(a, b)

		if (log)
			writer.println(s"q1 is: $a")
			writer.println(s"q2 is: $b")

		validHomomorphism match {
			case Some(homomorphism) =>
				if (log)
					writer.println(s"A possible homomorphism h from q2 to q1 contains the following mappings:")
					writer.println(homomorphism)
					writer.println(s"Then h(q2) is: ${substituteQueryTerms(b, homomorphism)}")
				true
			case None =>
				if (log) {
					writer.println("A possible counterexample database D contains the following atoms:")
					val database = createCounterexampleDatabase(a, b)
					writer.println(database.map(_.toString).mkString("\n"))
					if (a.isBoolean() && b.isBoolean())
						writer.println("Then q1(D) is true.")
						writer.println(s"However, q2(D) is false.")
					else
						val outputValue = computeQueryOnDatabase(a, database)
						writer.println(s"Then q1(D) contains the tuple $outputValue")
						writer.println(s"However, $outputValue is not in q2(D) since q2(D) is empty.")
				}
				false
		}

	def generateValidHomomorphism(a: Query, b: Query): Option[Homomorphism] = {
		val termsQueryA = extractTermsFromQuery(a)
		val termsQueryB = extractTermsFromQuery(b)
		generateValidMapping(termsQueryB.toList, termsQueryA.toSet, Map.empty, a, b)
	}

	def generateValidMapping(source: List[Term], destination: Set[Term], currentMapping: Map[Term, Term], a: Query, b: Query): Option[Homomorphism] =
		source match
			case Nil =>
				// All terms in the source are mapped; validate the current mapping
				val homomorphism = Homomorphism(source.toSet, destination, currentMapping)
				if (atomsContained(substituteQueryTerms(b, homomorphism), a)) Some(homomorphism)
				else None

			case sourceTerm :: remainingSource =>
				// Try mapping the current sourceTerm to each term in the destination
				destination.view.flatMap { destinationTerm =>
					val updatedMapping = currentMapping + (sourceTerm -> destinationTerm)
					generateValidMapping(remainingSource, destination, updatedMapping, a, b)
				}.headOption // Short-circuit if a valid homomorphism is found

	def computeQueryOnDatabase(query: Query, database: Set[Atom]): String =
		val mapping: mutable.Map[Term, Term] = mutable.Map.empty                       
		// create map
		for (atom <- query.body)
			val databaseAtomOption = database.find(item => item.name == atom.name)
			databaseAtomOption match
				case Some(databaseAtom) =>
					for (i <- atom.terms.indices)
						mapping += (atom.terms(i) -> databaseAtom.terms(i))
				case None =>

		"(" + query.head.terms.map(element => {s"${mapping(element).toString}"}).mkString(",") + ")"

	def createCounterexampleDatabase(a: Query, b: Query): Set[Atom] =
		val database: mutable.Set[Atom] = mutable.Set.empty
		for (atom <- a.body)
			database += Atom(atom.name, atom.terms.map(term => Constant(term.name)))
		database.toSet

	def validHomomorphismExists(a: Query, b: Query, possibleHomomorphisms: List[Homomorphism]): Option[Homomorphism] =
		possibleHomomorphisms.find(homomorphism => atomsContained(substituteQueryTerms(b, homomorphism), a))

	def atomsContained(q1: Query, q2: Query): Boolean =
		val head = q1.head == q2.head
		val body = q1.body.forall(atom => q2.body.contains(atom))
		head && body

	def someCandidateContained(query: Query, candidates: List[Query]): Boolean =
		candidates.exists { candidate =>
			candidate.head == query.head &&
			candidate.body.forall(atom => query.body.contains(atom))
		}

	def substituteQueryTerms(query: Query, homomorphism: Homomorphism): Query =
		val head: Head = substituteHead(query.head, homomorphism)
		val body: Set[Atom] = substituteBody(query.body, homomorphism)
		Query(query.queryId, head, body)


	def substituteBody(body: Set[Atom], homomorphism: Homomorphism): Set[Atom] =
		body.map(atom => {
			Atom(atom.name, atom.terms.map(term => {
				homomorphism.map(term)
			}))  
		})

	def substituteHead(head: Head, homomorphism: Homomorphism): Head =
		val headTerms: List[Term] = head.terms.map(term => {
			homomorphism.map(term)
		})
		Head(headTerms)
		

	def generateAllHomomorphisms(source: Set[Term], destination: Set[Term]): List[Homomorphism] =
		val possibleMappings = generateAllMappings(source, destination)
		possibleMappings.map(mapping => {
			Homomorphism(source, destination, mapping)
		})


	def generateAllMappings(source: Set[Term], destination: Set[Term]): List[Map[Term, Term]] =
		var mappings: mutable.ListBuffer[Map[Term, Term]] = mutable.ListBuffer[Map[Term, Term]](Map.empty)
		for (sourceTerm <- source)
			val currentMappings: mutable.ListBuffer[Map[Term, Term]] = mutable.ListBuffer.empty
			for (mapping <- mappings)
				for (destinationTerm <- destination)
					val newMapping = mapping + (sourceTerm -> destinationTerm)
					currentMappings += newMapping
			mappings = currentMappings
		mappings.toList


	def extractTermsFromQuery(query: Query): Set[Term] =
		query.head.terms.toSet ++ query.body.flatMap(_.terms).toSet