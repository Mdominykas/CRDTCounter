import java.util.*

class CRDTCounter(private val messaging: Messaging) {
    private val id: Int = messaging.getNewId()
    private var addition: Int = 0
    private var subtraction: Int = 0
    private val countersStates: MutableMap<Int, Pair<Int, Int>> = HashMap()

    init {
        messaging.sendMessageToAll(id, AskingValueMessage(id))
        receiveAllMessages()
    }

    private fun receiveAllMessages() {
        var msg = messaging.receiveMessage(id)
        while (msg != null) {
            when (msg) {
                is UpdatedValueMessage -> updateValue(msg)
                is AskingValueMessage -> sendOwnValue(msg)
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
        for ((_, value) in countersStates) {
            count += value.first - value.second
        }
        return count
    }

    fun inc() {
        receiveAllMessages()
        addition++
        messaging.sendMessageToAll(id, UpdatedValueMessage(id, addition, subtraction))
    }

    fun dec() {
        receiveAllMessages()
        subtraction++
        messaging.sendMessageToAll(id, UpdatedValueMessage(id, addition, subtraction))
    }

    private fun updateValue(message: UpdatedValueMessage) {
        countersStates.compute(message.senderId) { _, oldValue ->
            if (oldValue != null) {
//                here maximum is taken, because there is possibility that messages arrive out of order
                Pair(
                    maxOf(message.newAdditionValue, oldValue.first),
                    maxOf(message.newSubtractionValue, oldValue.second)
                )
            } else {
                Pair(message.newAdditionValue, message.newSubtractionValue)
            }
        }
    }

    /*
    When other counter asks for this one's value, it responds with UpdatedValueMessage
     */
    private fun sendOwnValue(message: AskingValueMessage) {
        messaging.sendMessage(message.sender, UpdatedValueMessage(id, addition, subtraction))
    }
}