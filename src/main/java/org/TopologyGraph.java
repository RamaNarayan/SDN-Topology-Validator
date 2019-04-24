package org;

import org.Graph.Factory;
import org.Graph.GraphAlgorithm;
import org.Graph.Vertex;

public class TopologyGraph extends GraphAlgorithm<TopologyGraph.NetworkNode>{	

	public TopologyGraph(Graph g) {
		super(g,new NetworkNode(null));
	}
	public static class NetworkNode implements Factory {
		int cno; //connected component number
		int top; //topological order
		boolean seen; //boolean to store whether is vertex already seen
		Vertex parent; //parent of current DFSVertex
		public NetworkNode(Vertex u) {
			this.top = 0;
			this.seen = false;
			this.parent = null;
		}
		public NetworkNode make(Vertex u) {
			return new NetworkNode(u);
		}
	}

}
