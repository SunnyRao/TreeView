package com.sunnyrao.treeview.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.sunnyrao.treeview.R;
import com.sunnyrao.treeview.utils.annotation.TreeNodeId;
import com.sunnyrao.treeview.utils.annotation.TreeNodeLabel;
import com.sunnyrao.treeview.utils.annotation.TreeNodePid;

public class TreeHelper {

	/**
	 * ���û�������ת��Ϊ��������
	 * 
	 * @param data
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static <T> List<Node> convertDataToNodes(List<T> data)
			throws IllegalArgumentException, IllegalAccessException {
		List<Node> nodes = new ArrayList<Node>();
		Node node = null;
		for (T t : data) {
			int id = -1;
			int pid = -1;
			String label = null;

			node = new Node();
			Class clazz = t.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (field.getAnnotation(TreeNodeId.class) != null) {
					field.setAccessible(true);
					id = field.getInt(t);
				}
				if (field.getAnnotation(TreeNodePid.class) != null) {
					field.setAccessible(true);
					pid = field.getInt(t);
				}
				if (field.getAnnotation(TreeNodeLabel.class) != null) {
					field.setAccessible(true);
					label = (String) field.get(t);
				}
			}
			node = new Node(id, pid, label);
			nodes.add(node);
		}

		/**
		 * ����Node��Ľڵ��ϵ
		 */
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);

			for (int j = i + 1; j < nodes.size(); j++) {
				Node m = nodes.get(j);

				if (m.getpId() == n.getId()) {
					n.getChildren().add(m);
					m.setParent(n);
				} else if (m.getId() == n.getpId()) {
					m.getChildren().add(n);
					n.setParent(m);
				}
			}
		}

		for (Node n : nodes) {
			setNodeIcon(n);
		}

		return nodes;
	}

	/**
	 * ΪNode����ͼ��
	 */
	private static void setNodeIcon(Node n) {
		if (n.getChildren().size() > 0 && n.isExpand()) {
			n.setIcon(R.drawable.tree_ex);
		} else if (n.getChildren().size() > 0 && !n.isExpand()) {
			n.setIcon(R.drawable.tree_ec);
		} else {
			n.setIcon(-1);
		}
	}

	/**
	 * �Խڵ�����
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> List<Node> getSortedNodes(List<T> data,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException {
		List<Node> result = new ArrayList<Node>();
		List<Node> nodes = convertDataToNodes(data);
		// ������ĸ����
		List<Node> rootNodes = getRootNodes(nodes);

		for (Node node : rootNodes) {
			addNode(result, node, defaultExpandLevel, 1);
		}

		return result;
	}

	/**
	 * ��һ���ڵ�����к��ӽڵ㶼����result
	 */
	private static void addNode(List<Node> result, Node node,
			int defaultExpandLevel, int currentLevel) {
		result.add(node);

		if (defaultExpandLevel >= currentLevel) {
			node.setExpand(true);
			;
		}
		if (node.isLeaf())
			return;

		for (int i = 0; i < node.getChildren().size(); i++) {
			addNode(result, node.getChildren().get(i), defaultExpandLevel,
					currentLevel + 1);
		}
	}

	/**
	 * ���˳��ɼ��Ľڵ�
	 */
	public static List<Node> filterVisibleNodes(List<Node> nodes) {
		List<Node> result = new ArrayList<Node>();
		
		for (Node node : nodes) {
			if (node.isRoot() || node.isParentExpand()) {
				setNodeIcon(node);
				result.add(node);
			}
		}
		return result;
	}
	
	/**
	 * �����нڵ��й��˳����ڵ�
	 */
	private static List<Node> getRootNodes(List<Node> nodes) {
		List<Node> root = new ArrayList<Node>();

		for (Node node : nodes) {
			if (node.isRoot()) {
				root.add(node);
			}
		}
		return root;
	}
}
