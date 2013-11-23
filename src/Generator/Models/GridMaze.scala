package Generator.Models
import Generator._
import java.util.Random


case class GridNode(x:Int, y:Int) extends Node[GridNode]{
	def -(n: GridNode): GridNode = GridNode(x - n.x, y - n.y)

	def distance(n: GridNode) = this - n

	def neighbors(): Set[GridNode] =
		Set(GridNode(x+1, y), GridNode(x-1, y),
		    GridNode(x, y+1), GridNode(x, y-1))
}

class GridMaze(width: Int, height: Int) extends Generator{
	val random = new Random()
	type NodeType = GridNode
	type Connections = Set[(NodeType, NodeType)];

	def neighbors(n: NodeType): Set[NodeType] =
		n.neighbors() filter ((c) => (c.x>=0 && c.x<=width) && (c.y>=0 && c.y<=height)) filter (_ => (random nextFloat) < 0.6)

	def connect(n: NodeType, ns: Set[NodeType], c: Connections) =
		ns.flatMap(b => Set((n, b), (b, n))) | c

	def generate(c: Connections, f: Set[NodeType], e: Set[NodeType]): Connections =
		f toList match{
			case Nil => c
			case h::t => {
				val n = neighbors(h)
				
				val nprime = n.filterNot (n => (e contains n) || (f contains n))
				
				val newG = connect(h, nprime, c)
				
				val newF = t ++ n.filterNot (e contains _)
				
				val newE = e+h
				generate(newG, newF toSet, newE)
			}
		}


//////////////////////// code bellow is required for web output only
	lazy val newMap = generate(Set(), Set(GridNode(0, 0)), Set())
	lazy val groupedConections = newMap groupBy(_._1) map(kv => ( kv._1 -> (kv._2 map(_._2)) ))
	
	def apply(n: NodeType): Set[NodeType] = if(groupedConections isDefinedAt(n)) groupedConections(n) else Set()
	
	
	def cssClass(e: NodeType) = 
		e match{
			case GridNode(0, -1) => "top"
			case GridNode(0, 1) => "bottom"
			case GridNode(-1, 0) => "left"
			case GridNode(1, 0) => "right"
		}
	
	def css(n: NodeType) =
		this(n) map(ce => cssClass(ce - n)) mkString " "


	override def toString() = 
		(0 to height) map (y => ((for(x <- 0 to width) yield "<div class='" + css(GridNode(x, y)) + "'></div>") mkString)) mkString "<br />"

}
