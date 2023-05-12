import java.util.*

class BetweenThreadMessaging() : Messaging {
    private var largestId: Int = 0
    private val messageQueues: MutableMap<Int, LinkedList<Message>> = HashMap()
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
    @Synchronized
    override fun getNewId() : Int{
        largestId++
        messageQueues[largestId] = LinkedList()
        return largestId
    }
}