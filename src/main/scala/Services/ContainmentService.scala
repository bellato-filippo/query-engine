package Services

import scala.collection.mutable
import scala.compiletime.ops.boolean
import Models.*
import scala.annotation.switch

object ContainmentService:
    def isContainedIn(a: Query, b: Query, log: Boolean = true): Boolean = 
        if (a.head.terms.length != b.head.terms.length)
            if (log)
                println("q1 and q2 have head atoms with different arities.")
            return false
        
        val termsQueryA: Set[Term] = extractTermsFromQuery(a)
        val termsQueryB: Set[Term] = extractTermsFromQuery(b)
        
        // println("First query: " + a)
        // println("Second query: " + b)
        // generates all possible homomorphisms of the two queries B -> A
        val possibleHomomorphisms: List[Homomorphism] = generateAllHomomorphisms(termsQueryB, termsQueryA)
        // println("Possible homomorphisms: " + possibleHomomorphisms.length)

        val filteredHomomorphism = possibleHomomorphisms.filter(u => u.isValid())

        val validHomomorphism: Option[Homomorphism] = validHomomorphismExists(a, b, filteredHomomorphism)

        if (log)
            println(s"q1 is: $a")
            println(s"q2 is: $b")

        validHomomorphism.filter(u => u.isValid()) match {
            case Some(homomorphism) => 
                if (log)
                    println(s"A possible homomorphism h from q2 to q1 contains the following mappings:")
                    println(homomorphism)
                    println(s"Then h(q2) is: ${substituteQueryTerms(b, homomorphism)}")
                true
            case None => 
                if (log)
                    println("A possible counterexample database D contains the following atoms:")
                    val database = createCounterexampleDatabase(a, b)
                    println(database.map(value => s"${value.toString}").mkString("\n"))
                    val outputValue = computeQueryOnDatabase(a, database)
                    println(s"Then q1(D) contains the tuple $outputValue")
                    println(s"However, $outputValue is not in q2(D) since q2(D) is empty.")
                false
        }

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