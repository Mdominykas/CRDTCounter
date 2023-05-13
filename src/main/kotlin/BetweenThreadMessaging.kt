import java.util.*

/*
Implementation of Messaging interface for communication between different threads.
Each message is stored in messageQueues and removed, when counter receives them.
 */
class BetweenThreadMessaging() : Messaging {
    private var largestId: Int = 0
    private val messageQueues: MutableMap<Int, LinkedList<Message>> = HashMap()
    /*
    If there is no queue with receiver number then message is discarded.
     */
    @Synchronized
    override fun sendMessage(receiver: Int, message: Message) {
        val queue = messageQueues[receiver]
        queue?.add(message)
    }
    @Synchronized
    override fun sendMessageToAll(sender: Int, message: Message) {
        for (key in messageQueues.keys){
            if (key != sender){
                sendMessage(key, message)
            }
        }
    }
    @Synchronized
    override fun receiveMessage(receiver: Int): Message? {
        val queue = messageQueues[receiver]
        return queue?.poll()
    }
    /*
    Returns ids for counters in increasing order from 1.
     */
    @Synchronized
    override fun getNewId() : Int{
        largestId++
        messageQueues[largestId] = LinkedList()
        return largestId
    }
}