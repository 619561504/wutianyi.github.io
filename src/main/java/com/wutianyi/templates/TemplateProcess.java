/**
 * 2015年10月21日hanjiewu
 */
package com.wutianyi.templates;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.google.api.client.util.Lists;
import com.google.api.client.util.Maps;
import com.google.api.client.util.Sets;

/**
 * @author hanjiewu
 *
 */
public class TemplateProcess {
	Pattern SPLIT_PATTERN = Pattern.compile(" \\{[0-9]*\\} ?");

	Map<Long, Node> maps = Maps.newHashMap();

	Set<String> idPaths = Sets.newHashSet();

	Set<Node> roots = Sets.newHashSet();

	Set<Node> tails = Sets.newHashSet();

	public void add(String line) {
		String[] cs = SPLIT_PATTERN.split(line);
		List<Node> nodes = Lists.newArrayList();

		for (String c : cs) {
			nodes.add(getNode(c));
		}

		int disConn = 0;
		boolean isLastDisConn = false;

		Map<Integer, Set<Node>> poses = Maps.newHashMap();

		for (int i = 0; i < nodes.size() - 1; i++) {
			Node f = nodes.get(i);
			Node s = nodes.get(i + 1);

			if (isLastDisConn) {
				if (i - 1 == 0) {
					Set<Node> firstNodes = Sets.newHashSet();
					for (Node node : roots) {
						if (node.isConn(f)) {
							firstNodes.add(node);
						}
					}
					if (firstNodes.size() > 0) {
						poses.put(i - 1, firstNodes);
					}
				} else {
					Node lastNode = nodes.get(i - 1);
					Set<Node> middleNodes = lastNode.middleNodes(s);
					if (null != middleNodes && middleNodes.size() > 0) {
						middleNodes.add(f);
						poses.put(i, middleNodes);
					}
				}

			}

			if (!f.isConn(s)) {
				if (!isLastDisConn) {
					++disConn;
				}
				isLastDisConn = true;

			} else {
				isLastDisConn = false;
			}
		}

		if (disConn < 3 && poses.size() > 0) {
			poses = traces(nodes, poses);
		}
		tails.add(nodes.get(nodes.size() - 1));
		roots.add(nodes.get(0));
		List<Long> ids = Lists.newArrayList();
		for (int i = 0; i < nodes.size() - 1; i++) {
			Node pNode = nodes.get(i);
			Node nNode = nodes.get(i + 1);
			pNode.nextNodes.add(nNode);
			ids.add(pNode.id);
			// nNode.preNodes.add(pNode);
		}
		ids.add(nodes.get(nodes.size() - 1).id);
		idPaths.add(StringUtils.join(ids, ","));
	}

	/**
	 * @param nodes
	 * @param poses
	 * @return
	 */
	private Map<Integer, Set<Node>> traces(List<Node> nodes, Map<Integer, Set<Node>> poses) {
		Trace trace = new Trace();
		Map<Integer, Set<Node>> _poses = Maps.newHashMap();
		for (int i = 0; i < nodes.size(); i++) {
			if (poses.containsKey(i)) {
				if (i == 0) {
					for (Node node : poses.get(i)) {
						trace.nodes.add(new Node(node.id));
					}
				} else {
					trace.addNode(i, poses.get(i));
				}
			} else {
				if (i == 0) {
					trace.nodes.add(nodes.get(i));
				} else {
					Set<Node> nexts = Sets.newHashSet();
					nexts.add(nodes.get(i));
					trace.addNode(i, nexts);
				}
			}
		}
		List<long[]> paths = trace.getAllPath(nodes.size());

		Set<String> sets = Sets.newHashSet();
		for (long[] pp : paths) {
			String idPath = toString(pp);
			if (idPaths.contains(idPath)) {
				for (Integer index : poses.keySet()) {
					sets.add(index + "," + pp[index]);
				}
			}
		}

		for (Entry<Integer, Set<Node>> entry : poses.entrySet()) {
			int index = entry.getKey();
			Set<Node> nNode = Sets.newHashSet();
			for (Node node : entry.getValue()) {
				if (sets.contains(index + "," + node.id)) {
					nNode.add(node);
				}
			}
			_poses.put(index, nNode);
		}
		return _poses;
	}

	String toString(long[] pp) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < pp.length; i++) {
			if (i != 0) {
				builder.append(',');
			}
			builder.append(pp[i]);
		}
		return builder.toString();
	}

	Node getNode(String seg) {
		long id = Dictionary.getIndex(seg);
		Node node = maps.get(id);
		if (null == node) {
			node = new Node(id);
			maps.put(id, node);
		}
		return node;
	}

	public static void main(String[] args) throws IOException {
		List<String> contents = FileUtils.readLines(new File("datas/fe0_head"));
		TemplateProcess templateProcess = new TemplateProcess();
		for (String content : contents) {
			String[] cs = content.split("\t");
			templateProcess.add(cs[2]);
		}
	}
}
