package Generator.Models

abstract class Node
case class Entrance extends Node
case class Exit extends Node
case class Edge(state: AnyVal) extends Node{
	override def toString() = state toString
}


trait Graph{
	type GraphMap = Set[(Node, Node)]
	val connections: GraphMap

	override def toString() = connections toString

	def apply(n: Node): Set[Node] = connections filter(_._1 == n) map(_._2)
	
	def connect(n1: Node, n2: Node): Graph
	
	def union(g: Graph): Graph
}
