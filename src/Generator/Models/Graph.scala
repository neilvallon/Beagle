// package Generator.Models

//abstract class Node
//case class Entrance extends Node
//case class Exit extends Node
//case class Edge[T](state: T) extends Node{
//	override def toString() = state toString
//}
//
//
//trait Graph{
//	type GraphMap = Set[(Node, Node)]
//	val connections: GraphMap
//	
//	lazy val groupedConections = connections groupBy(_._1) map(kv => ( kv._1 -> (kv._2 map(_._2)) ))
//	
//	override def toString() = groupedConections toString
//
//	def apply(n: Node): Set[Node] = if(groupedConections isDefinedAt(n)) groupedConections(n) else Set()
//	
//	def connect(n1: Node, n2: Node): Graph
//	
//	def union(g: Graph): Graph
//}
