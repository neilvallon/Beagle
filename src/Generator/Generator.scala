package Generator

trait Generator {
	type NodeType
	type Connections
	
	def neighbors(n: NodeType): Set[NodeType]
	def connect(n: NodeType, ns: Set[NodeType], c: Connections): Connections
	
}
