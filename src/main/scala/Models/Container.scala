package Models

import scala.collection.mutable
import scala.compiletime.ops.boolean

object Container:
    def isContainedIn(a: Query, b: Query): Boolean = 
        // xy
        val termsQueryA: Set[Term] = extractTermsFromQuery(a)
        // uv
        val termsQueryB: Set[Term] = extractTermsFromQuery(b)

        val possibleHomomorphisms: List[Homomorphism] = generateAllHomomorphisms(termsQueryB, termsQueryA)

        // possibleHomomorphisms.foreach(u => println(u.toString()))

        val candidates: List[Query] = possibleHomomorphisms.map(homo => {
            substituteQueryTerms(b, homo)
        })
        // candidates.foreach(u => println(u.toString()))

        someCandidateContained(a, candidates)

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
        // Step 1: Generate all possible mappings from each element in `source` to any element in `destination`
        val possibleMappings: List[List[(Term, Term)]] = source.toList.map { term =>
            destination.toList.map(d => term -> d)
        }

        // Step 2: Create Cartesian product of mappings for each term
        val allCombinations: List[Map[Term, Term]] = possibleMappings.foldLeft(List(Map.empty[Term, Term])) {
            (acc, mappings) =>
            for {
                map <- acc
                (term, dest) <- mappings
            } yield map + (term -> dest)
        }

        // Step 3: Create Homomorphisms
        allCombinations.map(map => new Homomorphism(source, destination, map))


    def extractTermsFromQuery(query: Query): Set[Term] =
        val setOfTerms: mutable.Set[Term] = mutable.Set[Term]()
        setOfTerms.addAll(query.head.terms)
        query.body.foreach(atom => {
            setOfTerms.addAll(atom.terms)
        })
        setOfTerms.toSet