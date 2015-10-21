/**
 * 2015年10月21日hanjiewu
 */
package com.wutianyi.templates;

import java.util.Set;

import com.google.api.client.util.Sets;

/**
 * @author hanjiewu
 *
 */
public class Node {

	long id;

	Set<Node> nextNodes = Sets.newHashSet();

	// Set<Node> preNodes = Sets.newHashSet();

	public Node(long id) {
		this.id = id;
	}

	public boolean isConn(Node node) {
		return nextNodes.contains(node);
	}

	public Set<Node> middleNodes(Node node) {
		Set<Node> middleNodes = Sets.newHashSet();
		for (Node next : nextNodes) {
			if (next.isConn(node)) {
				middleNodes.add(next);
			}
		}
		return middleNodes;
	}

	@Override
	public int hashCode() {

		return new Long(id).hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			Node node = (Node) obj;
			return id == node.id;
		}
		return false;
	}

}
