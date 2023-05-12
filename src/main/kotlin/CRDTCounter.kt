import java.util.*

class CRDTCounter(private val messaging: Messaging) {
    private val id: Int = messaging.getNewId()
    private var addition: Int = 0
    private var subtraction: Int = 0
    private val countersStates: MutableMap<Int, Pair<Int, Int>> = HashMap()

    private fun receiveAllMessages(){
        var msg = messaging.receiveMessage(id)
        while(msg != null){
            when (msg) {
                is UpdatedValueMessage -> updateValue(msg)
                else -> {
                    assert(false)
                }
            }
            msg = messaging.receiveMessage(id)
        }
    }
    fun get(): Int {
        receiveAllMessages()
        var count = addition - subtraction
        for ((_, value) in countersStates){
            count += value.first - value.second
        }
        return count
    }
    fun inc(){
        receiveAllMessages()
        addition++
        messaging.sendMessageToAll(id, UpdatedValueMessage(id, addition, subtraction))
    }
    fun dec(){
        receiveAllMessages()
        subtraction++
        messaging.sendMessageToAll(id, UpdatedValueMessage(id, addition, subtraction))
    }
    private fun updateValue(message: UpdatedValueMessage){
        countersStates[message.senderId] = Pair(message.newAdditionValue, message.newSubtractionValue)
    }
}