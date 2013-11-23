package Generator.Models
import Generator._
import java.util.Random


case class MapNode(x:Int, y:Int) extends Node[MapNode]{
	def -(n: MapNode): MapNode = MapNode(x - n.x, y - n.y)

	def distance(n: MapNode) = this - n

	def neighbors(): Set[MapNode] =
		Set(MapNode(x+1, y), MapNode(x-1, y),
		    MapNode(x, y+1), MapNode(x, y-1))
}

class GridMap(width: Int, height: Int, random:Random = new Random()) extends Generator{
	type NodeType = MapNode
	type Connections = Vector[Vector[Boolean]];

	def neighbors(n: NodeType): Set[NodeType] =
		n.neighbors() filter ((c) => (c.x>=0 && c.x<width) && (c.y>=0 && c.y<height)) filter (_ => (random nextFloat) < 0.5)

	def connect(n: NodeType, ns: Set[NodeType], c: Connections) =
		c.updated(n.y, c(n.y).updated(n.x, true))

	def generate(c: Connections, f: Set[NodeType], e: Set[NodeType]): Connections =
		f toList match{
			case Nil => c
			case h::t => {
				val n = neighbors(h)
				
				val newG = connect(h, n, c)
				
				val newF = t ++ n.filterNot (e contains _)
				
				val newE = e+h
				generate(newG, newF toSet, newE)
			}
		}


//////////////////////// code bellow is required for web output only
	def mazeStream(): Stream[Connections] = 
		generate(Vector.fill(height, width)(false), Set(MapNode(width/2, height/2)), Set()) #:: mazeStream()
	
	def trueCount(c: Connections): Int = c flatMap (_ filter(a => a) ) size
	
	lazy val newMap = mazeStream filter (trueCount(_) >= 2*(width+height)) head
	
	override def toString() = 
		(0 until height) map (y => ((for(x <- 0 until width) yield "<div class='" + (if(newMap(y)(x)) "active") + "'></div>") mkString)) mkString "<br />"
}
