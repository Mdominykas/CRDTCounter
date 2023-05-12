interface Messaging {
    fun sendMessage(receiver: Int, message: Message)
    fun sendMessageToAll(sender: Int, message: Message)
    fun receiveMessage(receiver: Int) : Message?
    fun getNewId() : Int
}