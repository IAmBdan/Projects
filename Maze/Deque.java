import java.util.function.Predicate;

// represents and ANode which can either be a Sentinel or a Node
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  ANode(ANode<T> next, ANode<T> prev) {
    this.next = next;
    this.prev = prev;
  }

  ANode() {
    this.next = this;
    this.prev = this;
  }

  // sets the previous ANode of the called on ANode to the given ANode
  void updatePrev(ANode<T> other) {
    this.prev = other;
  }

  // sets the next ANode of the called on ANode to the given ANode
  void updateNext(ANode<T> other) {
    this.next = other;
  }

  // counts the nodes in the deque
  abstract int sizeHelp();

  // EFFECT: adds a node either at the beginning or the end of a deque
  ANode<T> addHelp(T data, ANode<T> next, ANode<T> prev) {
    return new Node<T>(data, next, prev);
  }

  // EFFECT: reassigns the next and previous in a node in such a way that a node
  // is removed from the deque
  // returns the data removed
  abstract T removeHelp();

  // tests the data against the predicate
  abstract ANode<T> findHelp(Predicate<T> pred);
}

class Sentinel<T> extends ANode<T> {
  Sentinel() {
    super();
  }

  // counts the nodes in the deque
  @Override
  int sizeHelp() {
    return 0;
  }

  // EFFECT: reassigns the next and previous in a node in such a way that a node
  // is removed from the deque
  // returns the data removed
  @Override
  T removeHelp() {
    throw new RuntimeException("A node cannot be removed from an empty deque");
  }

  // tests the data against the predicate
  @Override
  ANode<T> findHelp(Predicate<T> pred) {
    return this;
  }
}

class Node<T> extends ANode<T> {
  T data;

  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  Node(T data, ANode<T> next, ANode<T> prev) {
    super(next, prev);
    this.data = data;
    if (this.prev == null) {
      throw new IllegalArgumentException("The given previous node is null");
    }
    else if (this.next == null) {
      throw new IllegalArgumentException("The given next node is null");
    }
    else {
      this.next.updatePrev(this);
      this.prev.updateNext(this);
    }
  }

  // counts the nodes in the deque
  @Override
  int sizeHelp() {
    return 1 + this.next.sizeHelp();
  }

  // EFFECT: reassigns the next and previous in a node in such a way that a node
  // is removed from the deque
  // returns the data removed
  @Override
  T removeHelp() {
    this.prev.updateNext(this.next);
    this.next.updatePrev(this.prev);
    return this.data;
  }

  // tests the data against the predicate
  @Override
  ANode<T> findHelp(Predicate<T> pred) {
    if (pred.test(this.data)) {
      return this;
    }
    else {
      return this.next.findHelp(pred);
    }
  }
}

class Deque<T> {
  Sentinel<T> header;

  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // returns the size of the deque
  int size() {
    return this.header.next.sizeHelp();
  }

  // EFFECT: adds a node to the beginning of the deque
  void addAtHead(T data) {
    this.header.next = this.header.next.addHelp(data, this.header.next, this.header);
  }

  // EFFECT: adds a node to the end of the deque
  void addAtTail(T data) {
    this.header.prev = this.header.prev.addHelp(data, this.header, this.header.prev);
  }

  // EFFECT: removes a node from the beginning of the deque
  // returns the data removed
  T removeFromHead() {
    return this.header.next.removeHelp();
  }

  // EFFECT: removes a node from the end of the deque
  // returns the data removed
  T removeFromTail() {
    return this.header.prev.removeHelp();
  }

  // returns the first node that meets the given predicate and returns the
  // sentinel if none of the nodes meet the predicate conditions
  ANode<T> find(Predicate<T> pred) {
    return this.header.next.findHelp(pred);
  }
}

// predicate object to find numbers that are even
class FilterEven implements Predicate<Integer> {

  @Override
  public boolean test(Integer t) {
    return t % 2 == 0;
  }
}

//predicate object to find numbers divisible by 7
class FilterBySeven implements Predicate<Integer> {

  @Override
  public boolean test(Integer t) {
    return t % 7 == 0;
  }
}