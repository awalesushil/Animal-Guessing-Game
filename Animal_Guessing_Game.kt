
class Node ( /* Node data structure for binary search tree */
        var data: String,
        var leftChild: Node? = null,
        var rightChild: Node? = null
)

class BinarySearchTree {

    var rootNode = Node("") /* Initialize root node */
    var currentNode = Node("") /* A pointer variable to the current node*/

    /* Initialize the Binary Search Tree with initial data */
    fun initialize(data: String, yesAnswer: String, noAnswer: String) {
        rootNode = Node(data, Node(noAnswer), Node(yesAnswer)) /* Create new node and set it as root node */
    }

    /* Function to insert child nodes for left and right node of current node */
    fun insertChild(currentNode: Node, data: String, userAnswer: String) {
        currentNode.leftChild = Node(currentNode.data) /* Shift the current answer to a new left node */
        currentNode.rightChild = Node(userAnswer) /* Store the user answer */
        currentNode.data = data /* Replace answer with new hint given by user */
    }
}

class AnimalGuessingAgent {

    var BST = BinarySearchTree() /* Initialze binary search tree */
    var counter: Int = 0 /* Initialize counter to calculate number of steps */

    fun start() {
        println ("Think of an animal. I will try to guess which animal you are thinking of.")
        println ("I will ask some questions. Please, answer with a YES or NO.")

        BST.initialize("Can it fly?", "Pigeon", "Elephant")
        play()
    }

    private fun play() {

        BST.currentNode = BST.rootNode /* Each time the user plays the game, the pointer points to the initial node */
        counter = 0 /* Set counter to zero for new game */

        while (BST.currentNode.leftChild != null || BST.currentNode.rightChild != null){ /*  Traverse until a child node is null. */
            askQuestion()
            var userResponse: String = readLine()!!
            when (userResponse.toUpperCase()) {
                "YES" -> BST.currentNode = BST.currentNode.rightChild!! /* Traverse right */
                "NO" -> BST.currentNode = BST.currentNode.leftChild!! /* Traverse left */
                else -> println("My creator only designed me to understand a YES or NO. :( ")
            }
            counter++ /* Increment counter */
        }
            guess() /* Make a guess */
    }

    private fun guess() { /* This function makes a guess when the pointer reaches the last node */

        println("Is it " + BST.currentNode.data + "?") /* Make a guess */

        var userConfirmation: String = readLine()!!
        if (userConfirmation.toUpperCase() == "YES") {
            println ("Woohoo! I got the right answer in $counter steps. ")
            replay()
        } else if (userConfirmation.toUpperCase() == "NO") {
            learn()
        }
    }

    private fun replay() {
        println ("Do you want to have another go?")

        var userResponse: String = readLine()!!
        when (userResponse.toUpperCase()) {
            "YES" -> play()
            "NO" -> println ("Bye! Thanks for teaching me.")
            else -> println ("My creator only designed me to understand a YES or NO. :( ")
        }
    }

    private fun askQuestion() = print(BST.currentNode.data)

    private fun learn() {

        println ("Ok. I give up. What is it?")
        var userAnswer: String = readLine()!!

        println ("What question should have I asked?")
        var newHint: String = readLine()!!

        BST.insertChild(BST.currentNode, newHint, userAnswer) /* Insert newly learned information */
        println("Ok. Got it.")
        replay()
    }
}

fun main (args : Array<String>) {

    var agent = AnimalGuessingAgent() /* Create an instance of the Animal Guessing Agent */
    agent.start() /* The agent will start the game */
}