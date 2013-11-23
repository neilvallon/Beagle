package Generator.Models
import Generator._
import java.util.Random

case class HexNode(x:Int, y:Int) extends Node[HexNode]{
	def -(n: HexNode): HexNode = HexNode(x - n.x, y - n.y)

	def distance(n: HexNode) = this - n

	def even(): Set[HexNode] =
		Set(HexNode(x, y-1), HexNode(x+1, y-1),
		HexNode(x+1, y), HexNode(x+1, y+1),
		HexNode(x, y+1), HexNode(x-1, y))
	def odd(): Set[HexNode] =
		Set(HexNode(x-1, y-1), HexNode(x, y-1),
		HexNode(x+1, y), HexNode(x, y+1),
		HexNode(x-1, y+1), HexNode(x-1, y))

	def neighbors(): Set[HexNode] = if(y % 2 == 0) even() else odd()
}

class HexMaze(width: Int, height: Int) extends Generator{
	val random = new Random()
	type NodeType = HexNode
	type Connections = Set[(HexNode, HexNode)];

	def neighbors(n: HexNode): Set[HexNode] =
		n.neighbors() filter ((c) => (c.x>=0 && c.x<=width) && (c.y>=0 && c.y<=height)) filter (_ => (random nextFloat) < 0.6)

	def connect(n: HexNode, ns: Set[HexNode], c: Connections) =
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
	def oddCSS(dif: HexNode) =
		dif match{
			case HexNode(0, -1) => "top-left"
			case HexNode(1, -1) => "top-right"
			case HexNode(1, 0) => "right"
			case HexNode(1, 1) => "bottom-right"
			case HexNode(0, 1) => "bottom-left"
			case HexNode(-1, 0) => "left"
		}

	def evenCSS(dif: HexNode) =
		dif match{
			case HexNode(-1, -1) => "top-left"
			case HexNode(0, -1) => "top-right"
			case HexNode(1, 0) => "right"
			case HexNode(0, 1) => "bottom-right"
			case HexNode(-1, 1) => "bottom-left"
			case HexNode(-1, 0) => "left"
		}


	lazy val newMap = generate(Set(), Set(HexNode(0, 0)), Set())
	lazy val groupedConections = newMap groupBy(_._1) map(kv => ( kv._1 -> (kv._2 map(_._2)) ))
	def apply(n: HexNode): Set[HexNode] = if(groupedConections isDefinedAt(n)) groupedConections(n) else Set()

	def getCSS(gridNode: HexNode) =
		this(gridNode) map(ce => if(gridNode.y % 2 != 0) evenCSS(ce - gridNode) else oddCSS(ce - gridNode) ) mkString " "

	override def toString() = 
		(0 to height) map (y => ((for(x <- 0 to width) yield "<div class='" + getCSS(HexNode(x, y)) + "'><div class='hexagon'></div></div>\n") mkString)) mkString "</div>\n<div class='hexrow'>"
}
