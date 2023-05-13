/*
interface describing general pattern in which any messaging between counters should happen
 */
interface Messaging {
    /*
    sendMessage sends single message to receiver
     */
    fun sendMessage(receiver: Int, message: Message)

    /*
    by using sendMessageToAll same message is send to all counters except the sender
     */
    fun sendMessageToAll(sender: Int, message: Message)

    /*
    when counter calls receiveMessage, it gets earliest message or null if it isn't available
     */
    fun receiveMessage(receiver: Int): Message?

    /*
    function getNewId() should generate a unique integer for each counter that asks.
    */
    fun getNewId(): Int
}