/**
 * 2015年10月21日hanjiewu
 */
package com.wutianyi.templates;

import java.util.List;
import java.util.Set;

import com.google.api.client.util.Lists;
import com.google.api.client.util.Sets;

/**
 * @author hanjiewu
 *
 */
public class Trace {

	Set<Node> nodes = Sets.newHashSet();

	public void addNode(int index, Set<Node> nexts) {
		add(nodes, 1, index, nexts);
	}

	/**
	 * @param nodes2
	 * @param i
	 * @param index
	 * @param next
	 */
	private void add(Set<Node> ns, int curIndex, int index, Set<Node> nexts) {
		if (curIndex == index) {
			for (Node n : ns) {
				for (Node next : nexts) {
					n.nextNodes.add(new Node(next.id));
				}

			}
		} else {
			for (Node n : ns) {
				add(n.nextNodes, curIndex + 1, index, nexts);
			}
		}
	}

	public List<long[]> getAllPath(int len) {
		List<long[]> paths = Lists.newArrayList();
		for (Node node : nodes) {
			long[] path = new long[len];
			getPath(node, path, 0, len);
			paths.add(path);
		}
		return paths;
	}

	/**
	 * @param node
	 * @param path
	 * @param i
	 * @param len
	 */
	private void getPath(Node node, long[] path, int index, int len) {
		path[index] = node.id;
		for (Node n : node.nextNodes) {
			getPath(n, path, index + 1, len);
		}
	}
}
