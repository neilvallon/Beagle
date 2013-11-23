package Generator

trait Node[Self <: Node[Self]] { self: Self =>
	def neighbors(): Set[Self]
	def distance(n: Self)
}
