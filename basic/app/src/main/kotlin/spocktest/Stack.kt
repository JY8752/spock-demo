package spocktest

class Stack<T> : ArrayList<T>() {
    fun push(element: T) {
        super.add(element)
    }
}
