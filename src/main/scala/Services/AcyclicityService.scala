package Services

import Models.*
import java.io._

object AcyclicityService:
    def isAcyclic(query: Query): Boolean =
        val path = s"./src/test/results/test-aciclicity-${query.queryId}.txt"
        val writer = new PrintWriter(new FileWriter(path, true))

        writer.println("GYO for query: " + this.toString())
        val h: Hypergraph = new Hypergraph(query)
        val acyclic: Hypergraph = GyoAlgorithm(h, writer)
        if(acyclic.edges.nonEmpty)
          writer.println("no more ears")
        else writer.println("Algorithm terminate with an empty query")
        writer.close()
        acyclic.edges.isEmpty

    def GyoAlgorithm(hypergraph: Hypergraph, writer: PrintWriter): Hypergraph =
        if (hypergraph.edges.isEmpty)
          return hypergraph

        hypergraph.edges.foreach(e => {
            if (hypergraph.isEar(e)) {
                hypergraph.edges = hypergraph.edges - e
                writer.println("We remove the ear: " + e)
                writer.println("Current hypergraph edges are: " + hypergraph.edges.map(_.mkString("{",", ","}")).mkString("; ") )
                return GyoAlgorithm(hypergraph, writer)
            }
        })
        hypergraph