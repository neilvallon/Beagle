// package Generator.Models
// import java.util.Random

//abstract class Maze extends Graph{
//	val width, height = 100
//	val random = new Random()
//
//	def connect(n1: Node, n2: Node): Graph = new MazeGraph( connections + ((n1, n2)) )
//	def union(g: Graph): Graph = new MazeGraph( connections ++ g.connections )
//	
//	def neighbors(n: Edge[(Int, Int)]) = {
//		val p1 = Set((n.state._1 - 1, n.state._2), (n.state._1 + 1, n.state._2), (n.state._1, n.state._2 - 1), (n.state._1, n.state._2 + 1))
//		val p2 = p1 filter ((c) => (c._1>=0 && c._1<=width) && (c._2>=0 && c._2<=height))
//		val p3 = p2 map (Edge(_))
//		val p4 = p3 filterNot(e => connections contains (e, e))
//		p3 //filter (_ => (random nextFloat) < 1)
//	}
//	
//	def difference(e1: Edge[(Int, Int)], e2: Edge[(Int, Int)]): (Int, Int) = (e2.state._1 - e1.state._1, e2.state._2 - e1.state._2)
//	
//	def direction(e: (Int, Int)) = 
//		e match{
//			case (0, -1) => "border-top:1px solid white;"
//			case (0, 1) => "border-bottom:1px solid white;"
//			case (-1, 0) => "border-left:1px solid white;"
//			case (1, 0) => "border-right:1px solid white;"
//		}
//	
//	def css(x: Int, y: Int) =
//		this(Edge(x, y)) map(ce => direction (difference(Edge(x, y), ce.asInstanceOf[Edge[(Int, Int)]] ))) mkString
//		
//	override def toString() = 
//		(0 to height) map (y => ((for(x <- 0 to width) yield "<div style='" + css(x, y) + "'></div>") mkString)) mkString "<br />"
//}
//
//case class EmptyMaze extends Maze{
//	val connections: GraphMap = Set()
//}
//
//case class SingletonMaze(sNode: Node) extends Maze{
//	val connections: GraphMap = Set( (new Entrance, sNode) )
//}
//
//case class MazeGraph(graph: Set[(Node, Node)]) extends Maze{
//	val connections: GraphMap = graph
//}
