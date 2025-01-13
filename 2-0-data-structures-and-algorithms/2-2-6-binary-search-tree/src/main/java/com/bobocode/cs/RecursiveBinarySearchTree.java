package com.bobocode.cs;

import com.bobocode.util.ExerciseNotCompletedException;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * {@link RecursiveBinarySearchTree} is an implementation of a {@link BinarySearchTree} that is based on a linked nodes
 * and recursion. A tree node is represented as a nested class {@link Node}. It holds an element (a value) and
 * two references to the left and right child nodes.
 * <p><p>
 * <strong>TODO: to get the most out of your learning, <a href="https://www.bobocode.com">visit our website</a></strong>
 * <p>
 *
 * @param <T> a type of elements that are stored in the tree
 * @author Taras Boychuk
 * @author Maksym Stasiuk
 */
public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    private Node<T> root;
    private int depth = 0;
    private int size = 0;

    private static class Node<T> {
        T value;
        Node<T> right;
        Node<T> left;

        public Node(T value) {
            this.value = value;
        }
    }

    public static <T extends Comparable<T>> RecursiveBinarySearchTree<T> of(T... elements) {
        RecursiveBinarySearchTree<T> tree = new RecursiveBinarySearchTree<>();
        for (T element : elements) {
            tree.insert(element);
        }
        return tree;
    }

    @Override
    public boolean insert(T element) {
        if (root == null) {
            root = new Node<>(element);
            size++;
            return true;
        }
        return insert(root, element, 1);
    }

    private boolean insert(Node<T> node, T element, int depth) {
        if (node == null) {
            return false;
        }
        if (element.compareTo(node.value) < 0) {
            if (node.left == null) {
                node.left = new Node<>(element);
                size++;
                this.depth = Math.max(this.depth, depth);
                return true;
            }
            return insert(node.left, element, depth + 1);
        } else if (element.compareTo(node.value) > 0) {
            if (node.right == null) {
                node.right = new Node<>(element);
                size++;
                this.depth = Math.max(this.depth, depth);
                return true;
            }
            return insert(node.right, element, depth + 1);
        }
        return false;
    }

    @Override
    public boolean contains(T element) {
        return contains(root, Objects.requireNonNull(element));
    }

    private boolean contains(Node<T> node, T element) {
        if (node == null) {
            return false;
        }
        if (element.compareTo(node.value) < 0) {
            return contains(node.left, element);
        } else if (element.compareTo(node.value) > 0) {
            return contains(node.right, element);
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int depth() {
        return depth;
    }

    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
//        inOrderTraversal(root, consumer);
        LinkedStack<Node<T>> elementsStack = new LinkedStack<>();
        Node<T> currentNode = root;

        while (currentNode != null || !elementsStack.isEmpty()) {
            while (currentNode != null) {
                elementsStack.push(currentNode);
                currentNode = currentNode.left;
            }
            Node<T> retrievedNode = elementsStack.pop();
            consumer.accept(retrievedNode.value);
            currentNode = retrievedNode.right;
        }
    }

    private void inOrderTraversal(Node<T> node, Consumer<T> consumer) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, consumer);
        consumer.accept(node.value);
        inOrderTraversal(node.right, consumer);
    }
}
