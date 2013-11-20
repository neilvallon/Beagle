package Generator.Models

abstract class Maze extends Graph{
	def connect(n1: Node, n2: Node): Graph = new MazeGraph( connections + ((n1, n2)) )
	def union(g: Graph): Graph = new MazeGraph( connections ++ g.connections )
}

case class SingletonMaze(sNode: Node) extends Maze{
	val connections: GraphMap = Set( (new Entrance, sNode) )
}

case class MazeGraph(graph: Set[(Node, Node)]) extends Maze{
	val connections: GraphMap = graph
}
