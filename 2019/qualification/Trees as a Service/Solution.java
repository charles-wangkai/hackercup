import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt();
			int M = sc.nextInt();
			Requirement[] requirements = new Requirement[M];
			for (int i = 0; i < requirements.length; i++) {
				int x = sc.nextInt();
				int y = sc.nextInt();
				int z = sc.nextInt();

				requirements[i] = new Requirement(x, y, z);
			}

			System.out.println(String.format("Case #%s: %s", tc, solve(N, requirements)));
		}

		sc.close();
	}

	static String solve(int N, Requirement[] requirements) {
		Node tree = buildTree(IntStream.rangeClosed(1, N).boxed().collect(Collectors.toList()),
				Arrays.asList(requirements));

		if (tree != null) {
			int[] parents = new int[N + 1];
			fillParents(parents, tree);

			return IntStream.range(1, parents.length).mapToObj(i -> String.valueOf(parents[i]))
					.collect(Collectors.joining(" "));
		} else {
			return "Impossible";
		}
	}

	static void fillParents(int[] parents, Node node) {
		for (Node child : node.children) {
			parents[child.value] = node.value;

			fillParents(parents, child);
		}
	}

	static Node buildTree(List<Integer> values, List<Requirement> requirements) {
		Set<Integer> candidateRootValues = buildCandidateRootValues(values, requirements);

		for (int rootValue : candidateRootValues) {
			Node root = new Node(rootValue);

			List<Requirement> rootRequirements = new ArrayList<>();
			List<Requirement> childrenRequirements = new ArrayList<>();
			for (Requirement requirement : requirements) {
				if (requirement.z == root.value) {
					rootRequirements.add(requirement);
				} else {
					childrenRequirements.add(requirement);
				}
			}

			Map<Integer, Integer> valueToGroup = buildValueToGroup(
					values.stream().filter(value -> value != root.value).collect(Collectors.toList()),
					childrenRequirements);

			if (rootRequirements.stream()
					.anyMatch(rootRequirement -> rootRequirement.x != root.value && rootRequirement.y != root.value
							&& valueToGroup.get(rootRequirement.x).equals(valueToGroup.get(rootRequirement.y)))) {
				continue;
			}

			int[] groups = valueToGroup.values().stream().mapToInt(x -> x).distinct().toArray();

			Map<Integer, List<Integer>> groupToValues = buildGroupToValues(groups, valueToGroup);
			Map<Integer, List<Requirement>> groupToRequirements = buildGroupToRequirements(groups, valueToGroup,
					childrenRequirements);

			for (int group : groups) {
				Node child = buildTree(groupToValues.get(group), groupToRequirements.get(group));
				if (child == null) {
					return null;
				}

				root.children.add(child);
			}

			return root;
		}

		return null;
	}

	static Map<Integer, List<Integer>> buildGroupToValues(int[] groups, Map<Integer, Integer> valueToGroup) {
		Map<Integer, List<Integer>> groupToValues = Arrays.stream(groups).boxed()
				.collect(Collectors.toMap(Function.identity(), group -> new ArrayList<>()));

		for (int value : valueToGroup.keySet()) {
			int group = valueToGroup.get(value);

			groupToValues.get(group).add(value);
		}

		return groupToValues;
	}

	static Map<Integer, List<Requirement>> buildGroupToRequirements(int[] groups, Map<Integer, Integer> valueToGroup,
			List<Requirement> requirements) {
		Map<Integer, List<Requirement>> groupToRequirements = Arrays.stream(groups).boxed()
				.collect(Collectors.toMap(Function.identity(), group -> new ArrayList<>()));

		for (Requirement requirement : requirements) {
			int group = valueToGroup.get(requirement.z);

			groupToRequirements.get(group).add(requirement);
		}

		return groupToRequirements;
	}

	static Map<Integer, Integer> buildValueToGroup(List<Integer> values, List<Requirement> requirements) {
		Map<Integer, Integer> valueToParent = new HashMap<>();
		for (int value : values) {
			valueToParent.put(value, null);
		}

		for (Requirement requirement : requirements) {
			union(valueToParent, requirement.x, requirement.z);
			union(valueToParent, requirement.y, requirement.z);
		}

		return values.stream().collect(Collectors.toMap(Function.identity(), value -> findGroup(valueToParent, value)));
	}

	static void union(Map<Integer, Integer> valueToParent, int value1, int value2) {
		int group1 = findGroup(valueToParent, value1);
		int group2 = findGroup(valueToParent, value2);

		if (group1 != group2) {
			valueToParent.put(group1, group2);
		}
	}

	static int findGroup(Map<Integer, Integer> valueToParent, int value) {
		int group = value;
		while (valueToParent.get(group) != null) {
			group = valueToParent.get(group);
		}

		int p = value;
		while (p != group) {
			int next = valueToParent.get(p);
			valueToParent.put(p, group);

			p = next;
		}

		return group;
	}

	static Set<Integer> buildCandidateRootValues(List<Integer> values, List<Requirement> requirements) {
		Set<Integer> result = new HashSet<>(values);
		for (Requirement requirement : requirements) {
			if (requirement.x != requirement.z) {
				result.remove(requirement.x);
			}
			if (requirement.y != requirement.z) {
				result.remove(requirement.y);
			}
		}
		return result;
	}
}

class Requirement {
	int x;
	int y;
	int z;

	Requirement(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}

class Node {
	int value;
	List<Node> children = new ArrayList<>();

	Node(int value) {
		this.value = value;
	}
}